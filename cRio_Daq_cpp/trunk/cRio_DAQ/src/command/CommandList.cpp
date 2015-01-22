/*
 * CommandList.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "CommandList.h"
#include "Command.h"
#include "CommandManager.h"

CommandList::CommandList() {
	// TODO Auto-generated constructor stub

}

CommandList::~CommandList() {
	// TODO Auto-generated destructor stub
}

std::string CommandList::runCommand(std::string command) {
	Command* aCommand = findCommand(command);
	if (aCommand == NULL) {
		return "Unknown command \"" + command + "\"";
	}
	else if (aCommand->canExecute(command) == false) {
		return "Command \"" + command + "\" cannot be executed";
	}
	else {
		return aCommand->execute(command);
	}
}

void CommandList::addCommand(Command* command) {
	commands.push_back(command);
}

Command* CommandList::findCommand(std::string commandName) {
	std::string name = firstword(commandName);
	name = trimstring(name);
	for (unsigned i = 0; i < commands.size(); i++) {
		if (commands.at(i)->getName() == name) {
			return commands.at(i);
		}
	}
	return NULL;
}

Command* CommandList::getCommand(int iCommand) {
	return commands.at(iCommand);
}

int CommandList::getnCommands() {
	return commands.size();
}
