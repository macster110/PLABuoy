/*
 * GeneralCommands.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef GENERALCOMMANDS_H_
#define GENERALCOMMANDS_H_
#include "Command.h"

class StartCommand : public Command {
public:
	StartCommand();
	virtual ~StartCommand() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Start processing";
	}
};

class StopCommand : public Command {
public:
	StopCommand();
	virtual ~StopCommand() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Stop processing";
	}
};

class PingCommand : public Command {
public:
	PingCommand();
	virtual ~PingCommand() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Check PLABuoy response on network";
	}
};

class HelpCommand : public Command {
public:
	HelpCommand();
	virtual ~HelpCommand() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "List modules and available commands";
	}
};

class ExitCommand : public Command {
public:
	ExitCommand();
	virtual ~ExitCommand() {};
	std::string execute(std::string command);
	std::string getHint() {
		return "Stop and exit the program";
	}
};
#endif /* GENERALCOMMANDS_H_ */
