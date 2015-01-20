/*
 * NetSender.cpp
 *
 *  Created on: 12 Jan 2015
 *      Author: doug
 */

#include "NetSender.h"

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <error.h>

#include "../x3/x3frame.h"

#define NET_HDR_LEN (24)

#define DATASTARTFLAG (0xFFEEDDCC)

//char* ipV4 = "138.251.190.177";
char* ipV4 = "192.168.2.101";

int ipPort = 8013;



void *netsendThreadFunction(void *param)
{
	NetSender* obj = (NetSender*) param;
	obj->sendThreadLoop();
	return NULL;
}

NetSender::NetSender() : PLAProcess() {
	// launch the thread to connect to and send data to the
	// other machine.
	socketId = 0;
	hostEntity = NULL;

	pthread_t netsendThread;
	pthread_create(&netsendThread, NULL, netsendThreadFunction, this);

}

NetSender::~NetSender() {

}


int NetSender::initProcess(int nChan, int sampleRate) {
	PLAProcess::initProcess(nChan, sampleRate);

	return 0;
}

/**
 * Here we should receive packets of compressed data (could also
 * work with uncompressed data - would need some slightly different
 * codes to identify at other end - worry about later).
 * Job for now is to write into a queue. The sent buffers are probably
 * all pointing at the same place, we we'll need to allocate larger
 * buffers to write data into, with the additional header information
 * and then package them up.
 */
int NetSender::process(PLABuff* plaBuffer) {
	static bool first = true;
	PLABuff qData;
	char* buffer = (char*) malloc(NET_HDR_LEN + plaBuffer->dataBytes);
	memcpy(buffer+NET_HDR_LEN, plaBuffer->data, plaBuffer->dataBytes);
	writeSendHeader(buffer, plaBuffer->dataBytes, NET_AUDIO_SOUND);
	qData.data = (int16_t*) buffer;
	qData.dataBytes = plaBuffer->dataBytes + NET_HDR_LEN;
	qData.nChan = plaBuffer->nChan;
	qData.soundFrames = plaBuffer->soundFrames;

	if (first) {
		first = false;
		printf("Mem loc written to queue = %p\n", &qData);
	}
	networkQueue.push(qData);
	return 0;
}

/**
 * Write a standard PAMGaurd data send header into
 * the buffer.
 */
int NetSender::writeSendHeader(void* pBuff, int dataBytes, int32_t dataType2) {
	int32_t* ints = (int32_t*) pBuff;
	int16_t* shorts = (int16_t*) pBuff;
	ints[0] = htonl(DATASTARTFLAG); // flag to id start of data incase it gets out of synch.
	ints[1] = htonl(dataBytes + NET_HDR_LEN); // data + head length
	shorts[4] = htons(1); // version
	shorts[5] = htons(0); // stati0n Id 1
	shorts[6] = htons(0); // station Id 2
	shorts[7] = htons(NET_AUDIO_DATA); // data type 1
	ints[4] = htonl(dataType2); // data type 2
	ints[5] = htonl(dataBytes); // data length
	return NET_HDR_LEN;
}
void NetSender::endProcess() {
	return;
}

int NetSender::sendThreadLoop() {
	int calls = 0;
//	openConnection(); // let it happen automatically when first data are sent.
	PLABuff data;
	int nDumped;
	int i;
	while (1) {
		if (networkQueue.size() > 30000) {
			nDumped = 0;
			while (networkQueue.size() > 20000) {
//			for (i = 0; i < 1000; i++) {
				data = networkQueue.front();
				free(data.data);
				networkQueue.pop();
				nDumped++;
			}
			if (nDumped) {
				printf("Dumped %d chunks from data queue, %d remaining\n", nDumped, networkQueue.size());
				usleep(10000); // sleep for 10 millisecond.
			}
		}
		if (networkQueue.size() == 0) {
			usleep(10000); // sleep for 10 millisecond.
			continue;
		}
		data = networkQueue.front();
		if (calls == 0) {
			printf("Mem loc read from queue = %p\n", (void*) &data.data);
		}
		if (sendData(&data)) {
			free(data.data);
			networkQueue.pop(); // remove from queue.
		}
		else {
			// sleep for a bit to stop it thrashing if no output available.
			usleep(10000); // sleep for 10 millisecond.
		}
		if (++calls % 10000 == 0) {
			printf("In send thread loop with %d elements in queue\n", networkQueue.size());
		}
	}
	closeConnection();
	return 0;
}

/**
 * Send data to a network socket. Open automatically if it's not currently
 * open.
 */
int NetSender::sendData(PLABuff* data) {
	int bytesWrote = send(socketId, data->data, data->dataBytes, MSG_NOSIGNAL);
	if (bytesWrote <= 0) {
		// try to open and send again
		if (openConnection()) {
			bytesWrote = send(socketId, data->data, data->dataBytes, MSG_NOSIGNAL);
		}
	}
	return bytesWrote == data->dataBytes;
}

bool NetSender::openConnection() {
	closeConnection();
	static int errorCount = 0;
	if (hostEntity == NULL) {
		hostEntity = gethostbyname(ipV4);
		printf ("Network host is %s\n", hostEntity->h_name);
		if (hostEntity == NULL) {
			printf("Unable to look up host name in gethostbyname for %s\n", ipV4);
			return false;
		}
	}
	sockaddr_in sockAddr;
	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd <= 0) {
		printf ("Unable to open socket in NetworkSender->openConnection()\n");
		return false;
	}
	memset(&sockAddr, 0, sizeof(sockaddr_in));
	sockAddr.sin_family = AF_INET;
	memcpy((char *)&sockAddr.sin_addr.s_addr,
			(char *)hostEntity->h_addr,
			hostEntity->h_length);
	sockAddr.sin_port = htons(ipPort);
	if (connect(sockfd,(struct sockaddr *) &sockAddr,sizeof(sockAddr)) < 0) {
		close(sockfd);
		sockfd = 0;
//		if (errorCount%100 == 0 || errorCount < 5) {
			printf("Unable to make network connection to %s on port %d\n", ipV4, ipPort);
//		}
		errorCount++;
		return false;
	}

	socketId = sockfd;
	printf("Network connection to %s on port %d is open\n", ipV4, ipPort);
	errorCount = 0;
	/*
	 * Now send through details of how teh x3 compression is working.
	 */
	return sendX3Header(socketId);

}

/*
 * Send X3 header information through to the socket receiver.
 * This happens immediately after a Network connect has returned success.
 */
bool NetSender::sendX3Header(int socketId) {
	char hData[X3HEADLEN+NET_HDR_LEN];
	int dataBytes = X3_prepareXMLheader(hData+NET_HDR_LEN, 500000, X3BLOCKSIZE);
	dataBytes += writeSendHeader(hData, dataBytes, NET_AUDIO_HEADINFO);

	int bytesWrote = send(socketId, hData, dataBytes, MSG_NOSIGNAL);
	printf("Wrote %d bytes %s", bytesWrote, hData+NET_HDR_LEN);
	printf("\n");
	return bytesWrote == dataBytes;
}

void NetSender::closeConnection() {
	if (socketId == 0) return;
	close(socketId);
	socketId = 0;
}
