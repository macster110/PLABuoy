/*
 * Reporter.cpp
 *
 *  Created on: 2 Feb 2015
 *      Author: doug
 */

#include "Reporter.h"

#include <stdio.h>
#include "stdarg.h"

Reporter::Reporter() {
	verbosity = 2;
}

Reporter::~Reporter() {
	// TODO Auto-generated destructor stub
}

/**
 * Report a message. If verbosity is <= the set verbose level the
 * message will be printed.
 */
bool Reporter::report(int verbosity, std::string message) {
	if (verbosity > this->verbosity) {
		return false;
	}
	printf(message.c_str());
	fflush(stdout);
	return true;
}

bool Reporter::report(int verbosity, char* arg, ...) {
	if (verbosity > this->verbosity) {
		return false;
	}
	va_list args;
    va_start(args,arg);
    vprintf(arg,args);
    va_end(args);
	fflush(stdout);
    return true;
}

Reporter* reporter = new Reporter();
