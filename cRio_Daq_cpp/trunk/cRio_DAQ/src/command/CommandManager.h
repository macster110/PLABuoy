/*
 * CommandManager.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef COMMANDMANAGER_H_
#define COMMANDMANAGER_H_

#include "CommandList.h"
#include <string>
#include "../mythread.h"

class CommandManager : public CommandList {
public:
	CommandManager();
	virtual ~CommandManager();

	std::string processCommand(std::string command, struct sockaddr_in* udpSock);

	void listAllCommands();

private:
//	pthread_mutex_t commandLock;

};

std::string trimstring(std::string aStr);

std::string firstword(std::string aStr);

int countWords(std::string aStr);

std::string nthword(std::string aStr, int iWord);

std::string joinstrings(int nArg, char* argV[]);


extern CommandManager* commandManager;

#endif /* COMMANDMANAGER_H_ */
