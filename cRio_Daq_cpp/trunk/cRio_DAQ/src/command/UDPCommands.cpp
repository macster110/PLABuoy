/*
 * UDPCommands.cpp
 *
 *  Created on: 5 Feb 2015
 *      Author: doug
 */

#include "UDPCommands.h"
#include "CommandManager.h"

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "../mythread.h"
#include "../Reporter.h"
#ifdef WINDOWS
#include "Windows.h"
#include <ctime>
#include "Winsock2.h"
#else
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#endif


#ifdef WINDOWS
typedef int socklen_t;
#endif

#define UDP_RX_BUFF_LEN (256)

UDPCommands* udpCommands = 0;

DECLARETHREAD(udpThreadStart, UDPCommands, udpThread);

UDPCommands::UDPCommands() {
	updThreadId = 0;
	udpThreadHandle = 0;
	thread_run = false;
	portId = 8000;
	udpSocket = 0;
	startUDPThread();
}

UDPCommands::~UDPCommands() {
	stopUDPThread();
}

bool UDPCommands::setUDPPort(int portId) {
	if (this->portId == portId && updThreadId) {
		return true;
	}
	this->portId = portId;
	return startUDPThread();
}

bool UDPCommands::startUDPThread() {
	stopUDPThread();
	thread_run = true;
	bool threadOk;
	STARTTHREAD(udpThreadStart, this, updThreadId, udpThreadHandle, threadOk);
	return threadOk;
}

void UDPCommands::stopUDPThread(bool wait) {
	if (updThreadId || udpThreadHandle) {
		int retVal;
		thread_run = false;
		if (udpSocket > 0) {
			close(udpSocket);
		}
		if (wait) {
			WAITFORTHREAD(updThreadId, udpThreadHandle, retVal);
		}
	}
	updThreadId = 0;
	udpThreadHandle = 0;
}
int UDPCommands::udpThread() {

	udpSocket = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP );
	if (udpSocket <= 0) {
		reporter->report(0, "Unable to open UDP control socket\n");
	}
	struct sockaddr_in serv_addr, cli_addr;

	memset((char *) &serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(0);
	serv_addr.sin_port = htons(portId);
	if (bind(udpSocket, (struct sockaddr *) &serv_addr,
			sizeof(serv_addr)) < 0) {
		return false;
	}

//	struct timeval tv;
//	tv.tv_sec = 1;
//	tv.tv_usec = 0;
	int tOut = 1000;
	if (setsockopt(udpSocket, SOL_SOCKET, SO_RCVTIMEO, (char*) &tOut, sizeof(int)) < 0) {
	    perror("Error");
	}

	char rxBuffer[UDP_RX_BUFF_LEN + 10];
	int received, sent;

	socklen_t clientlen = sizeof(cli_addr);

	while (thread_run) {

//		reporter->report(0, "Waiting for UDP Command\n");
		if ((received = recvfrom(udpSocket, rxBuffer, UDP_RX_BUFF_LEN, 0,
				(struct sockaddr *) &cli_addr,
				&clientlen)) < 0) {
//			reporter->report(0, "UDP Receive socket returned %d\n", received);
//			break;
		}
		else {
			rxBuffer[received] = 0; // null terminate the data in case it's text.
			std::string retCmd = commandManager->runCommand(rxBuffer);
			sent = sendto(udpSocket, retCmd.c_str(), retCmd.length(), 0,
					(struct sockaddr *) &cli_addr,
					sizeof(cli_addr));
			if (sent != retCmd.length()) {
				reporter->report(0, "Unable to reply message \"%s\" to UDP client\n", retCmd.c_str());
			}
		}

		/*
		 * Not needed since it gets closed in order to force the thread to close.
		 */
		//	close(udpSocket);
	}
	thread_run = false;
	reporter->report(0, "Leaving UDP Receive thread\n");
	return 0;
}
