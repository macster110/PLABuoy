/*
 * ProcessEnable.h
 *
 *  Created on: 30 Jan 2015
 *      Author: doug
 */

#ifndef PROCESSENABLE_H_
#define PROCESSENABLE_H_

#include "Command.h"
#include <string>

class ProcessEnable: public Command {
public:
	ProcessEnable(PLAProcess* plaProcess);
	virtual ~ProcessEnable();

	std::string execute(std::string command, struct sockaddr_in* udpSock);

	std::string getHint() {
		return "Enable / Disable the process";
	}

};

#endif /* PROCESSENABLE_H_ */
