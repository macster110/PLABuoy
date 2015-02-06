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
	Command(PLAProcess* plaProcess, const std::string name);
	virtual ~Command();

	/**
	 * Alternate version of execute which will be called from the udp command receiver.
	 * It includes the sending sock add so that commands which wish to can
	 * extract the inet address. Be default it's ignored and the command
	 * passes through to the simpler version
	 */
	virtual std::string execute(std::string command, struct sockaddr_in* udpSock) = 0;

	virtual bool canExecute(std::string command) {
		return true;
	}

	virtual std::string getHint() {
		return "";
	}

	const std::string& getName() const {
		return name;
	}

	PLAProcess* getPlaProcess() const {
		return plaProcess;
	}

private:
	std::string name;
	PLAProcess* plaProcess;
};

#endif /* COMMAND_H_ */
