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

	bool ok = stop();
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
	stop();
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
