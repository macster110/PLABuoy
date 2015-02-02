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

StartCommand::StartCommand() : Command(NULL, "start") {

}
std::string StartCommand::execute(std::string command) {

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
std::string StopCommand::execute(std::string command) {

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
std::string PingCommand::execute(std::string command) {
	return "ping";
}

VerboseCommand::VerboseCommand() : Command(NULL, "verbose") {

}
std::string VerboseCommand::execute(std::string command) {
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
std::string HelpCommand::execute(std::string command) {
	commandManager->listAllCommands();
//	exitTerminalLoop();
	return "Help";
}

ExitCommand::ExitCommand() : Command(NULL, "exit") {

}
std::string ExitCommand::execute(std::string command) {
	stop();
	exitTerminalLoop();
	return "exit";
}
