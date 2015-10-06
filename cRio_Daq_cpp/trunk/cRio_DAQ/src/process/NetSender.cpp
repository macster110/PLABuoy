/*
 * NetSender.cpp
 *
 *  Created on: 12 Jan 2015
 *      Author: doug
 */

#include "NetSender.h"

#include <errno.h>

#include "NetCommands.h"

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "../mythread.h"
#include "../Reporter.h"
#ifdef WINDOWS
#include "Winsock2.h"
#else
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <error.h>
#endif

#include "../x3/x3frame.h"

//using namespace std;

#define NET_HDR_LEN (24)

#define DATASTARTFLAG (0xFFEEDDCC)

//char* ipV4 = "138.251.190.177";
#ifdef WINDOWS
std::string ipV4 = "localhost";
#else
std::string ipV4 = "192.168.2.101";
#endif



#ifdef WINDOWS
int sendFlags = 0;
#else
int sendFlags = MSG_NOSIGNAL;
#endif

DECLARETHREAD(netsendThreadFunction, NetSender, sendThreadLoop)
DECLARETHREAD(socketThreadFunction, NetSender, socketWaitThread)
//void *netsendThreadFunction(void *param)
//{
//	NetSender* obj = (NetSender*) param;
//	obj->sendThreadLoop();
//	return NULL;
//}

NetSender::NetSender() : PLAProcess("netsend", "NETTX") {
	// launch the thread to connect to and send data to the
	// other machine.
	PREPARE_LOCK(socketLock);
	socketId = 0;
	listenSocket = 0;
	hostEntity = NULL;
	queuedBytes = 0;
	conTimer = new RealTimer();
	nSends = 0;
	dataWritten = 0;
	addCommand(new SetDestIP(this));
	addCommand(new SetDestPort(this));
	addCommand(new ClearNetQueue(this));

	// start with it disabled.
	setEnabled(false);

	//	pthread_t netsendThread;
	//	pthread_create(&netsendThread, NULL, netsendThreadFunction, this);
	bool threadState;
	STARTTHREAD(netsendThreadFunction, this, netsendThread, netSendThreadHandle, threadState)

	startSocketThread();

}

NetSender::~NetSender() {
	DELETE_LOCK(socketLock);
}


bool NetSender::setDestinationIp(std::string newIpAddress) {
	struct hostent* hostEnt = gethostbyname(newIpAddress.c_str());
	if (hostEnt == NULL) {
		reporter->report(0, "%s is not a valid ip address. Keeping %s\n", newIpAddress.c_str(), ipV4.c_str());
		return false;
	}
	ipV4 = newIpAddress;
	closeConnection(); // will open again automatically next time data are sent.
	return true;
}

bool NetSender::setDestinationPort(int portId) {
	ipPort = portId;
	closeConnection(); // will open again automatically next time data are sent.
	startSocketThread();
	return true;
}

int NetSender::initProcess(int nChan, int sampleRate) {
	PLAProcess::initProcess(nChan, sampleRate);
	queuedBytes = 0;
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
	qData.timeStamp = plaBuffer->timeStamp;

	if (first) {
		first = false;
		printf("Mem loc written to queue = %p\n", &qData);
	}
	networkQueue.push(qData);
	queuedBytes += qData.dataBytes;
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

bool NetSender::startSocketThread() {
	bool threadState;
	STARTTHREAD(socketThreadFunction, this, netsendThread, netSendThreadHandle, threadState)
	return threadState;
}
/**
 * Thread which waits for a request to open a connection.
 */
int NetSender::socketWaitThread() {
	int newsockfd;
#ifdef WINDOWS
	int clilen;
#else
	socklen_t clilen;
#endif
	char buffer[256];
	struct sockaddr_in serv_addr, cli_addr;
	int n;
	/*
	 * There may already be a listening socket, in which case we have to close it.
	 */
	if (listenSocket) {
		close(listenSocket); // hopefully this will cause the other instance of this thread to crap out.
	}
	listenSocket = socket(AF_INET, SOCK_STREAM, 0);
	if (listenSocket < 0) {
		reporter->report(0, "ERROR opening socket\n", 0);
		return -1;
	}
	memset((char *) &serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = INADDR_ANY;
	serv_addr.sin_port = htons(ipPort);
	if (bind(listenSocket, (struct sockaddr *) &serv_addr, sizeof(serv_addr))
			< 0) {
		sprintf(buffer, "Error on binding socket on port %d\n", ipPort);
		reporter->report(0, buffer, 3);
	}
	listen(listenSocket, 5);
	clilen = sizeof(cli_addr);
	reporter->report(0, "TCP Listening socket opened on port %d\n", ipPort);
	// that's it all set up and listening, now wait for connections.

	while (1) {

		newsockfd = accept(listenSocket, (struct sockaddr *) &cli_addr, &clilen);
		/*
		 * then create a new NetworkReceiver which will
		 * read data from this socketfd.
		 */
		reporter->report(0, "TCP socket %d accepted \n", newsockfd);
		if (newsockfd < 0) {
			break;
		}
		ENTER_LOCK(socketLock)
		socketId = newsockfd;
		// as soon as we have the socket, send the header information.
		sendX3Header(socketId);
		LEAVE_LOCK(socketLock);
	}
	reporter->report(0, "TCP Listening socket closed on port %d\n", serv_addr.sin_port);
	return 0;
}
int NetSender::sendThreadLoop() {
	int calls = 0;
	//	openConnection(); // let it happen automatically when first data are sent.
	PLABuff data;
	int nDumped;
	RealTimer* msgTimer = new RealTimer();
	msgTimer->start();
	while (1) {
		if (msgTimer->stop() > 10) {
			reporter->report(2, "In send thread loop with %d elements (%d Mbytes) in queue\n",
					networkQueue.size(), (int) (queuedBytes>>20));
			msgTimer->start();
		}
		if (networkQueue.size() > 2000) {
			nDumped = 0;
			while (networkQueue.size() > 1500) {
				data = networkQueue.front();
				free(data.data);
				queuedBytes -= data.dataBytes;
				networkQueue.pop();
				nDumped++;
			}
			if (nDumped) {
				reporter->report(1, "Dumped %d chunks from data queue, %d (%d MBytes) remaining\n",
						nDumped, networkQueue.size(), (int) (queuedBytes>>20));
				myusleep(10000); // sleep for 10 millisecond.
			}
		}
		if (networkQueue.size() == 0) {
			myusleep(10000); // sleep for 10 millisecond.
			continue;
		}
		bool workDone = false;
		ENTER_LOCK(socketLock)
		if (socketId != 0) {
			data = networkQueue.front();
			if (sendData(&data)) {
				free(data.data);
				queuedBytes -= data.dataBytes;
				networkQueue.pop(); // remove from queue.
				workDone = true;
			}
		}
		LEAVE_LOCK(socketLock)
		if (!workDone) {
			myusleep(10); // sleep for 10 millisecond.
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
	static int errors;
	int bytesWrote = send(socketId, (char*) data->data, data->dataBytes, sendFlags);
	if (bytesWrote != data->dataBytes && (errors++ < 2 || errors%200 == 0)) {
		reporter->report(0, "TCP write failed %d bytes written on port %d with error %d: %s\n",
				bytesWrote, socketId, errno, strerror(errno));
	}
	if (bytesWrote <= 0) {
		socketId = 0; // socket must have failed, so flag it as closed so nothing else is attempted.
		// try to open and send again - no longer. Now acting as a server, so socket opened from remote host.
		//		if (openConnection()) {
		//			bytesWrote = send(socketId, (char*) data->data, data->dataBytes, sendFlags);
		//		}
	}
	if (bytesWrote == data->dataBytes) {
		nSends ++;
		dataWritten += bytesWrote;
		errors = 0;
		return true;
	}
	return false;
}

/**
 * This function is no longer uses. Instead connections are opened from
 * the PAMGuard end, so as to avoid firewall issues. There is a listener thread
 * chugging away somewhere waiting for socket open requests from the
 * remote host.
 */
//bool NetSender::openConnection() {
//	closeConnection();
//	conTimer->start();
//	static int errorCount = 0;
//	if (hostEntity == NULL) {
//		hostEntity = gethostbyname(ipV4.c_str());
//		printf ("Network host is %s\n", hostEntity->h_name);
//		if (hostEntity == NULL) {
//			reporter->report(0, "Unable to look up host name in gethostbyname for %s\n", ipV4.c_str());
//			return false;
//		}
//	}
//	sockaddr_in sockAddr;
//	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
//	if (sockfd <= 0) {
//		reporter->report(0, "Unable to open socket in NetworkSender->openConnection()\n");
//		return false;
//	}
//	/*
//	 * Set a 10s timeout.
//	 */
//	timeval timeout = {10, 0};
//	if (setsockopt (sockfd, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout,
//			sizeof(timeout)) < 0)
//		reporter->report(0, "setsockopt SO_RCVTIMEO failed\n");
//	if (setsockopt (sockfd, SOL_SOCKET, SO_SNDTIMEO, (char *)&timeout,
//			sizeof(timeout)) < 0)
//		reporter->report(0, "setsockopt SO_SNDTIMEO failed\n");
//
//
//	memset(&sockAddr, 0, sizeof(sockaddr_in));
//	sockAddr.sin_family = AF_INET;
//	memcpy((char *)&sockAddr.sin_addr.s_addr,
//			(char *)hostEntity->h_addr,
//			hostEntity->h_length);
//	sockAddr.sin_port = htons(ipPort);
//	if (connect(sockfd,(struct sockaddr *) &sockAddr,sizeof(sockAddr)) < 0) {
//		close(sockfd);
//		sockfd = 0;
//		if (errorCount%100 == 0 || errorCount < 5) {
//			reporter->report(0, "Unable to make network connection to %s on port %d after %3.2fs\n",
//					ipV4.c_str(), ipPort, conTimer->stop());
//		}
//		errorCount++;
//		return false;
//	}
//
//	socketId = sockfd;
//	//	printf("Network connection to %s on port %d is open\n", ipV4.c_str(), ipPort);
//	reporter->report(0, "Network connection %d to %s on port %d is open\n", socketId, ipV4.c_str(), ipPort);
//	errorCount = 0;
//	/*
//	 * Now send through details of how teh x3 compression is working.
//	 */
//	nSends = 0;
//	dataWritten = 0;
//	return sendX3Header(socketId);
//
//}

/*
 * Send X3 header information through to the socket receiver.
 * This happens immediately after a Network connect has returned success.
 *
 * Ultimately, this should not go straight to the x3 code, but go to a function in
 * the parent module which can give us an xml string to send to the socket. All
 * modules should be able to do this, whatever their purpose using the function
 * int PLAProcess::getModuleConfiguration(char* configData, int configDataLength)
 */
bool NetSender::sendX3Header(int socketId) {
	char hData[X3HEADLEN+NET_HDR_LEN];
	int dataBytes = X3_prepareXMLheader(hData+NET_HDR_LEN, 500000, 8, X3BLOCKSIZE);
	dataBytes += writeSendHeader(hData, dataBytes, NET_AUDIO_HEADINFO);

	int bytesWrote = send(socketId, hData, dataBytes, sendFlags);
	//	reporter->report(0, "Wrote %d bytes %s\n", bytesWrote, hData+NET_HDR_LEN);
	return bytesWrote == dataBytes;
}

void NetSender::closeConnection() {
	if (socketId == 0) return;
	reporter->report(0, "Closing TCP socket %d after %d packets / %dMBytes\n", socketId, nSends, (int) (dataWritten>>20));
	close(socketId);
	socketId = 0;
	hostEntity = NULL;
}

/**
 * Clear the network output queue.
 */
int NetSender::clearQueue() {
	int n = 0;
	while (!networkQueue.empty()) {
		networkQueue.pop();
		n++;
	}
	return n;
}


ClearNetQueue::ClearNetQueue(NetSender* netSender) : Command(netSender, "clearqueue") {
	this->netSender = netSender;
}

ClearNetQueue::~ClearNetQueue() {

}

std::string ClearNetQueue::execute(std::string command, struct sockaddr_in* udpSock) {
	int n = netSender->clearQueue();
	char ans[80];
	sprintf(ans, "Cleared %d packets from network send queue", n);
	//	reporter->report(0, "Cleared %d packets from network send queue\n", n);
	return std::string(ans);

}
