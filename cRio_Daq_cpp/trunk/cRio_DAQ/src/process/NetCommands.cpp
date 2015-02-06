/*
 * NetCommands.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "NetCommands.h"


#include "NetSender.h"
#include "../command/CommandManager.h"

#include <stdlib.h>
#ifdef WINDOWS
#include "Winsock2.h"
#else
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <error.h>
#endif

SetDestIP::SetDestIP(NetSender* netSender) : Command(netSender, "destip") {

}
std::string SetDestIP::execute(std::string command, struct sockaddr_in* udpSock) {

	std::string newIp = interpretIp(command, udpSock);
	if (newIp.size() < 9) {
		return "Invalid ip address in " + command;
	}

	bool ok = ((NetSender*) getPlaProcess())->setDestinationIp(newIp);
	if (!ok) {
		return "Setting destination ip address to \"" + newIp + "\" failed";
	}
	return "Set destination ip address to " + newIp;
}

/**
 * work out WTF the ip address is. If udpSock isn't NULL
 *  then we can take it from there if it's not stated more explicity
 */
std::string SetDestIP::interpretIp(std::string command, struct sockaddr_in* udpSock) {
	//in_addr inAddr;
	char* inAddr = NULL;
	if (udpSock) {
		inAddr = inet_ntoa(udpSock->sin_addr);
	}
	int nW = countWords(command);
	if (nW < 2 && inAddr) {
		return std::string(inAddr);
	}

	std::string w2 = nthword(command, 1);
	if (w2.find("me") > 0 && inAddr) {
		// command can contain 'me' if it's come over udp in which case we can
		// use the ip from udp socket.
		return std::string(inAddr);
	}
	else {
		// return the second word.
		return inAddr;
	}
}

SetDestPort::SetDestPort(NetSender* netSender) : Command(netSender, "destport") {

}
std::string SetDestPort::execute(std::string command, struct sockaddr_in* udpSock) {

	int nW = countWords(command);
	if (nW < 2) {
		return "Command must provide a valid udp port  " + command;
	}

	std::string w2 = nthword(command, 1);
	int p = atoi(w2.c_str());
	if (p <= 0) {
		return "Command must provide a valid udp port  " + command;
	}

	bool ok = ((NetSender*) getPlaProcess())->setDestinationPort(p);

	return "Setting network data TCP port to " + w2;
}
