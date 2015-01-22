/*
 * Command.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef COMMAND_H_
#define COMMAND_H_

#include <string>

class PLAProcess;

class Command {
public:
	Command(const PLAProcess* plaProcess, const std::string name);
	virtual ~Command();

	virtual std::string execute(std::string command) = 0;

	virtual bool canExecute(std::string command) {
		return true;
	}

	const std::string& getName() const {
		return name;
	}

	const PLAProcess* getPlaProcess() const {
		return plaProcess;
	}

private:
	std::string name;
	const PLAProcess* plaProcess;
};

#endif /* COMMAND_H_ */
