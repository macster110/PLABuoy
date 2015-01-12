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

#define NET_HDR_LEN (24)

#define DATASTARTFLAG (0xFFEEDDCC)

char* ipV4 = "138.251.190.177";

int ipPort = 8013;

/*
 * Types of data defined s ofar for PAMGUARD
 */
#define NET_PAM_DATA        1
#define NET_REMOTE_COMMAND  2
#define NET_SPEED_DATA      3
#define NET_SYSTEM_DATA     4
#define NET_PAM_COMMAND     5

#define SYSTEM_GPSDATA       1
#define SYSTEM_BATTERYDATA   4
#define SYSTEM_COMPASSDATA  16


void *netsendThreadFunction(void *param)
{
	NetSender* obj = (NetSender*) param;
	obj->sendThreadLoop();
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
	writeSendHeader(buffer, plaBuffer);
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
void NetSender::writeSendHeader(void* pBuff, PLABuff* data) {
	int32_t* ints = (int32_t*) pBuff;
	int16_t* shorts = (int16_t*) pBuff;
	ints[0] = htonl(DATASTARTFLAG);
	ints[1] = htonl(data->dataBytes + NET_HDR_LEN);
	shorts[4] = htons(1);
	shorts[5] = htons(0);
	shorts[6] = htons(0);
	shorts[7] = htons(NET_PAM_DATA);
	ints[4] = htonl(0);
	ints[5] = htonl(data->dataBytes);
}
void NetSender::endProcess() {
	return;
}

int NetSender::sendThreadLoop() {
	int calls = 0;
	openConnection();
	while (1) {
		if (networkQueue.size() == 0) {
			usleep(10000); // sleep for a millisecond.
			continue;
		}
		PLABuff data = networkQueue.front();
		if (calls == 0) {
			printf("Mem loc read from queue = %p\n", (void*) &data);
		}
		sendData(&data);
		free(data.data);
		networkQueue.pop();
		//		free(data);
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
	return bytesWrote = data->dataBytes;
}

bool NetSender::openConnection() {
	closeConnection();
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
		printf("Unable to make network connection to %s on port %d\n", ipV4, ipPort);
		return false;
	}

	socketId = sockfd;
	printf("Network connection to %s on port %d is open\n", ipV4, ipPort);
	return true;
}

void NetSender::closeConnection() {
	if (socketId == 0) return;
	close(socketId);
	socketId = 0;
}
