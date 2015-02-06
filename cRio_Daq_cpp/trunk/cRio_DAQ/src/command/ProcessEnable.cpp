/*
 * ProcessEnable.cpp
 *
 *  Created on: 30 Jan 2015
 *      Author: doug
 */

#include "ProcessEnable.h"
#include "../process/processdata.h"
#include <stdint.h>

using namespace std;

ProcessEnable::ProcessEnable(PLAProcess* plaProcess) : Command(plaProcess, "enable") {
	// TODO Auto-generated constructor stub

}

ProcessEnable::~ProcessEnable() {
	// TODO Auto-generated destructor stub
}

std::string ProcessEnable::execute(std::string command, struct sockaddr_in* udpSock) {
	// search the string for true or false and act accordingly
	if (command.find("true") >= 0) {
		getPlaProcess()->setEnabled(true);
		return "Process " + getPlaProcess()->getProcessName() + " enabled";
	}
	if (command.find("false") >= 0) {
		getPlaProcess()->setEnabled(true);
		return "Process " + getPlaProcess()->getProcessName() + " disabled";
	}

	return "unknown enable - use \"true\" or \"false\" in the command string";
}

