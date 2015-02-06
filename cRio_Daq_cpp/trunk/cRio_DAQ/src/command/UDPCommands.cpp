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
#include <netinet/in.h>
#include <arpa/inet.h>
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


/**
 * Can't actually stop and start the thread here since it will
 * end up in a locked state if this port change is called from
 * the udp thread. Instead, just close and open the connection
 * on the new port.
 *
 * It seems that UDP sockets can't really be unbound, so this prevents us from
 * rebinding to a port that's already been used - so set udpport once, then
 * don't mess with it !
 */
bool UDPCommands::setUDPPort(int portId) {
	if (this->portId == portId && updThreadId) {
		return true;
	}
	this->portId = portId;
	closeConnection();
	return openConnection();
}

/**
 * have to NOT wait for an old thread to close since
 * if we do, it locks then a call to change the port comes in
 * since the function calling this will be in the same thread as
 * the one we're trying to kill.
 */
bool UDPCommands::startUDPThread() {
	stopUDPThread(false);
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
			closeConnection();
		}
		if (wait) {
			WAITFORTHREAD(updThreadId, udpThreadHandle, retVal);
		}
	}
	updThreadId = 0;
	udpThreadHandle = 0;
}

bool UDPCommands::openConnection() {

	reporter->report(0, "Prepare for UDP commands on port %d\n", portId);

	udpSocket = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP );
		if (udpSocket <= 0) {
			reporter->report(0, "Unable to open UDP control socket\n");
		}
		struct sockaddr_in serv_addr;

		memset((char *) &serv_addr, 0, sizeof(serv_addr));
		serv_addr.sin_family = AF_INET;
		serv_addr.sin_addr.s_addr = htonl(0);
		serv_addr.sin_port = htons(portId);
		if (bind(udpSocket, (struct sockaddr *) &serv_addr,
				sizeof(serv_addr)) < 0) {
			reporter->report(0, "Unable to bind udp port %d\n", portId);
			close(udpSocket);
			udpSocket = 0;
			return false;
		}

	//	struct timeval tv;
	//	tv.tv_sec = 1;
	//	tv.tv_usec = 0;
		int tOut = 1000;
		if (setsockopt(udpSocket, SOL_SOCKET, SO_RCVTIMEO, (char*) &tOut, sizeof(int)) < 0) {
		    perror("Error");
		}


		reporter->report(0, "Wait for UDP commands on port %d\n", portId);
		return udpSocket > 0;

}
void UDPCommands::closeConnection() {
	if (udpSocket) {
		close(udpSocket);
		udpSocket = 0;
	}
}
int UDPCommands::udpThread() {

	openConnection();

	struct sockaddr_in cli_addr;
	socklen_t clientlen = sizeof(cli_addr);
	char rxBuffer[UDP_RX_BUFF_LEN + 10];
	int received, sent;

	while (thread_run) {

//		reporter->report(0, "Waiting for UDP Command\n");
		if ((received = recvfrom(udpSocket, rxBuffer, UDP_RX_BUFF_LEN, 0,
				(struct sockaddr *) &cli_addr,
				&clientlen)) < 0) {
//			reporter->report(0, "UDP Receive socket returned %d\n", received);
//			break;
		}
		else {
//			char str[INET_ADDRSTRLEN];
//		inet_ntop( AF_INET, ntohl(cli_addr.sin_addr), str, INET_ADDRSTRLEN );
			rxBuffer[received] = 0; // null terminate the data in case it's text.
			reporter->report(1, "UDP Command %s sent from %s\n", rxBuffer, inet_ntoa(cli_addr.sin_addr));

			std::string retCmd = commandManager->processCommand(std::string(rxBuffer), &cli_addr);
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
