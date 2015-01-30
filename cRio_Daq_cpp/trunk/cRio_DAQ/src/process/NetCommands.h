/*
 * NetCommands.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef NETCOMMANDS_H_
#define NETCOMMANDS_H_

#include "../command/Command.h"
class NetSender;

class SetDestIP : public Command {
public:
	SetDestIP(NetSender* netSender);
	virtual ~SetDestIP() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Set the network address data should send to";
	}
};

class SetDestPort : public Command {
public:
	SetDestPort(NetSender* netSender);
	virtual ~SetDestPort() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Set the network port data should send to";
	}
};

#endif /* NETCOMMANDS_H_ */
