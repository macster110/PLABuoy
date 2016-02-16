/*
 * GeneralCommands.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "GeneralCommands.h"

#include <stdio.h>
#include "stdlib.h"

#include "CommandManager.h"
#include "../CRioRecDAQ.h"
#include "../Reporter.h"
#include "UDPCommands.h"
#include "../process/processdata.h"
#include "../nifpga/NiFpgaChoice.h"

StartCommand::StartCommand() : Command(NULL, "start") {

}
std::string StartCommand::execute(std::string command, struct sockaddr_in* udpSock) {

	bool ok = start();
	if (ok) {
		return "start ok";
	}
	else {
		return "won't start";
	}
}
StopCommand::StopCommand() : Command(NULL, "stop") {

}
std::string StopCommand::execute(std::string command, struct sockaddr_in* udpSock) {

	bool ok = stop(false);
	if (ok) {
		return "stop ok";
	}
	else {
		return "won't stop";
	}
}

PingCommand::PingCommand() : Command(NULL, "ping") {

}
std::string PingCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	return "ping";
}

VerboseCommand::VerboseCommand() : Command(NULL, "verbose") {

}
std::string VerboseCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	// get the second word out of the command and hope it's a number
	int nW = countWords(command);
	if (nW < 2) {
		return "Invalid verbose command";
	}
	std::string w2 = nthword(command, 1);
	if (w2.size() < 1) {
		return "Invalid verbose command " + command;
	}
//	return "Set verbose level to " + w2;
	int v = atoi(w2.c_str());
	reporter->setVerbosity(v);
	char ans[50];
	sprintf(ans, "Set verbose level to %d", v);
	return ans;
}

HelpCommand::HelpCommand() : Command(NULL, "help") {

}
std::string HelpCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	commandManager->listAllCommands();
//	exitTerminalLoop();
	return "Help";
}

ExitCommand::ExitCommand() : Command(NULL, "exit") {

}
std::string ExitCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	stop(false);
	exitTerminalLoop();
	return "exit";
}

UDPPortCommand::UDPPortCommand() : Command(NULL, "udpport") {

}

std::string UDPPortCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	// look for an integer in the second word.
	int nW = countWords(command);
	if (nW < 2) {
		return "Invalid port id";
	}
	std::string w2 = nthword(command, 1);
	if (w2.size() < 1) {
		return "Invalid port id " + command;
	}
//	return "Set verbose level to " + w2;
	int p = atoi(w2.c_str());
	if (p <= 0) {
		return "Invalid port id " + command;
	}

	udpCommands->setUDPPort(p);

	return "UDP Port set to  " + command;
}


NChanCommand::NChanCommand() : Command(NULL, "nchan") {

}
std::string NChanCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	// look for an integer in the second word.
	int nW = countWords(command);
	if (nW < 2) {
		return "Invalid number of channels (8 or 12) " + command;
	}
	std::string w2 = nthword(command, 1);
	if (w2.size() < 1) {
		return "Invalid number of channels (8 or 12) " + command;
	}
//	return "Set verbose level to " + w2;
	int p = atoi(w2.c_str());
	if (p != 8 && p != 12) {
		return "Invalid number of channels (8 or 12) " + command;
	}
	getFPGAChoice(getCurrentChassis(), p);
	getProcess(0)->setNChan(p);

	return "NChannels set to  " + command;
}

ChassisCommand::ChassisCommand() : Command(NULL, "chassis") {

}
std::string ChassisCommand::execute(std::string command, struct sockaddr_in* udpSock) {
	// look for an integer in the second word.
	int nW = countWords(command);
	if (nW < 2) {
		return "Invalid chassis type (9067 or 9068) " + command;
	}
	std::string w2 = nthword(command, 1);
	if (w2.size() < 1) {
		return "Invalid chassis type (9067 or 9068) " + command;
	}
//	return "Set verbose level to " + w2;
	int p = atoi(w2.c_str());
	if (p != 9067 && p != 9068) {
		return "Invalid chassis type (9067 or 9068) " + command;
	}
	getFPGAChoice(p, getCurrentNChan());

	return "NI chassis type set to  " + command;
}
