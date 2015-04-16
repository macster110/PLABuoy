/*
 * ProcessSummary.cpp
 *
 * Command to get summary information from a process.
 *
 *  Created on: 14 Apr 2015
 *      Author: Jamie Macaulay
 */

#include "ProcessSummary.h"
#include "../process/processdata.h"
#include <stdint.h>

using namespace std;

ProcessSummary::ProcessSummary(PLAProcess* plaProcess) : Command(plaProcess, "summarydata") {
	// TODO Auto-generated constructor stub
}

ProcessSummary::~ProcessSummary() {
	// TODO Auto-generated destructor stub
}

std::string ProcessSummary::execute(std::string command, struct sockaddr_in* udpSock) {
	return getPlaProcess()->getSummaryData();
}
