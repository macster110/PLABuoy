/*
 * CommandList.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef COMMANDLIST_H_
#define COMMANDLIST_H_

//#include "Command.h"
#include <string>
#include <vector>

class Command;

class CommandList {
public:
	CommandList();
	virtual ~CommandList();
	void addCommand(Command* command);
	std::string runCommand(std::string command);
	Command* findCommand(std::string commandName);
	Command* getCommand(int iCommand);
	int getnCommands();
private:
	std::vector<Command*> commands;
};

#endif /* COMMANDLIST_H_ */
