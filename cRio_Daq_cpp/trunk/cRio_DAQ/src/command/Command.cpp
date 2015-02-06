/*
 * Command.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "Command.h"
#include <string>

Command::Command(PLAProcess* plaProcess, std::string name) {
	this->name = name;
	this->plaProcess = plaProcess;
}

Command::~Command() {
	// TODO Auto-generated destructor stub
}




