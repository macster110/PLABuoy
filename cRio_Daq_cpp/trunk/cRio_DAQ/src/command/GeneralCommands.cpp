/*
 * GeneralCommands.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "GeneralCommands.h"
#include "CommandManager.h"
#include "../CRioRecDAQ.h"

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
