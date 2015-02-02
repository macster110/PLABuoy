/*
 * Reporter.h
 *
 *  Created on: 2 Feb 2015
 *      Author: doug
 */

#ifndef REPORTER_H_
#define REPORTER_H_

#include <string>

class Reporter {
public:
	Reporter();
	virtual ~Reporter();
	bool report(int verbosity, std::string message);
	bool report(int verbosity, char* arg, ...);

	int getVerbosity() const {
		return verbosity;
	}

	void setVerbosity(int verbosity) {
		this->verbosity = verbosity;
	}

private:
	int verbosity;
};

extern Reporter* reporter;
#endif /* REPORTER_H_ */
