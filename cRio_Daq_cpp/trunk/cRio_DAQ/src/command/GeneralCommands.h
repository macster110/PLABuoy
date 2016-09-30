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
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Start processing";
	}
};

class StopCommand : public Command {
public:
	StopCommand();
	virtual ~StopCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Stop processing";
	}
};

class PingCommand : public Command {
public:
	PingCommand();
	virtual ~PingCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Check PLABuoy response on network";
	}
};

class VerboseCommand : public Command {
public:
	VerboseCommand();
	virtual ~VerboseCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Set verboseness of printed output - high number = lots of printing !";
	}
};

class HelpCommand : public Command {
public:
	HelpCommand();
	virtual ~HelpCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "List modules and available commands";
	}
};

class ExitCommand : public Command {
public:
	ExitCommand();
	virtual ~ExitCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Stop and exit the program";
	}
};

class UDPPortCommand : public Command {
public:
	UDPPortCommand();
	virtual ~UDPPortCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Set the UDP port for controlling the program";
	}
};

class NChanCommand : public Command {
public:
	NChanCommand();
	virtual ~NChanCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Set the number of channels (8 or 12)";
	}
};

class ChassisCommand : public Command {
public:
	ChassisCommand();
	virtual ~ChassisCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Set the type of chassis (9067 or 9068)";
	}
};

class TempCommand : public Command {
public:
	TempCommand();
	virtual ~TempCommand() {};
	std::string execute(std::string command, struct sockaddr_in* udpSock);
	std::string getHint() {
		return "Get the chassis temperature in degrees";
	}
};
#endif /* GENERALCOMMANDS_H_ */
