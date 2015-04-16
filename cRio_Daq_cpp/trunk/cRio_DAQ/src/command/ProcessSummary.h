/*
 * ProcessSummary.h
 *
 *  Created on: 14 Apr 2015
 *      Author: Lab
 */

#ifndef PROCESSSUMMARY_H_
#define PROCESSSUMMARY_H_

#include "Command.h"
#include <string>

class ProcessSummary: public Command {
public:
	ProcessSummary(PLAProcess* plaProcess);
	virtual ~ProcessSummary();

	std::string execute(std::string command, struct sockaddr_in* udpSock);

	std::string getHint() {
		return "Get summary information from process";
	}

};

#endif /* PROCESSSUMMARY_H_ */
