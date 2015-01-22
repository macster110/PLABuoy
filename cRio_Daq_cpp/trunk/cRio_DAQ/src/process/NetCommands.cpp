/*
 * NetCommands.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "NetCommands.h"
#include "NetSender.h"
#include "../command/CommandManager.h"

SetDestIP::SetDestIP(NetSender* netSender) : Command(netSender, "destip") {

}
std::string SetDestIP::execute(std::string command) {
	int nW = countWords(command);
	if (nW < 2) {
		return "error - no ip address";
	}
	std::string newIp = nthword(command, 1);
	bool ok = ((NetSender*) getPlaProcess())->setDestinationIp(newIp);
	if (!ok) {
		return "Setting ip address to \"" + newIp + "\" failed";
	}
	return "Set ip address to " + newIp;
}

SetDestPort::SetDestPort(NetSender* netSender) : Command(netSender, "destport") {

}
std::string SetDestPort::execute(std::string command) {

	return command;
}
