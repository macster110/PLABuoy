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
};

class StopCommand : public Command {
public:
	StopCommand();
	virtual ~StopCommand() {};
	std::string execute(std::string command);
};

class PingCommand : public Command {
public:
	PingCommand();
	virtual ~PingCommand() {};
	std::string execute(std::string command);
};

class HelpCommand : public Command {
public:
	HelpCommand();
	virtual ~HelpCommand() {};
	std::string execute(std::string command);
};

class ExitCommand : public Command {
public:
	ExitCommand();
	virtual ~ExitCommand() {};
	std::string execute(std::string command);
};
#endif /* GENERALCOMMANDS_H_ */