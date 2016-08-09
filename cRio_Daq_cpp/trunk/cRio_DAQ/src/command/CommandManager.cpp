/*
 * CommandManager.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: Doug Gillespie
 */

#include "CommandManager.h"
#include "../process/processdata.h"
#include "GeneralCommands.h"

#include <stdio.h>

#include "../Reporter.h"

using namespace std;

CommandManager::CommandManager() : CommandList() {
//	pthread_mutex_init(&commandLock, NULL);
	addCommand(new HelpCommand());
	addCommand(new PingCommand());
	addCommand(new VerboseCommand());
	addCommand(new StartCommand());
	addCommand(new StopCommand());
	addCommand(new ExitCommand());
	addCommand(new UDPPortCommand());
	addCommand(new NChanCommand());
	addCommand(new ChassisCommand());
}

CommandManager::~CommandManager() {

}

void CommandManager::listAllCommands() {
	reporter->report(0,"List of available general commands: \n");
	int n = getnCommands();
	for (int i = 0; i < n; i++) {
		std::string ahint = getCommand(i)->getHint();
		if (ahint.length() > 0) {
			reporter->report(0,"       %s - %s\n", getCommand(i)->getName().c_str(), ahint.c_str());
		}
		else {
			reporter->report(0,"       %s\n", getCommand(i)->getName().c_str());
		}
	}
	// now work through the modules, listing all commands for each module.
	for (int m = 0; m < getNumProcesses(); m++) {
		PLAProcess* aProcess = getProcess(m);
		std::string procState = aProcess->isEnabled() ? "Enabled" : "Disabled";
		int n = aProcess->getnCommands();
		if (n == 0) {
			reporter->report(0, "Process %s (%s) has no optional commands\n", aProcess->getProcessName().c_str(), procState.c_str());
		}
		else {
			reporter->report(0, "Process %s (%s) has the following commands: \n", aProcess->getProcessName().c_str(), procState.c_str());
			for (int i = 0; i < n; i++) {
				std::string hint = aProcess->getCommand(i)->getHint();
				if (hint.length() > 0) {
					reporter->report(0, "       %s - %s\n", aProcess->getCommand(i)->getName().c_str(), hint.c_str());
				}
				else {
					reporter->report(0, "       %s\n", aProcess->getCommand(i)->getName().c_str());
				}
			}
		}
	}
}

/**
 * Process a command. Do inside a lock to make sure that
 * competing commands are executed one at a time.
 */
std::string CommandManager::processCommand(std::string command, struct sockaddr_in* udpSock) {
//	pthread_mutex_lock( &commandLock );
	string returnedString;
	command = trimstring(command);
	if (command.size() == 0) return "null";
	/**
	 * If this has come in from the command line it may have comma delinated
	 * multiple commands in it, so check for commas and break it up.
	 */
	reporter->report(2, "Process command %s\n", command.c_str());
	size_t comPos = command.find(',');
	size_t nCom = 0;
	size_t prevCom = 0;
	if (comPos != string::npos) {
		// break the string up and return.
		returnedString = "";
		command += ",";
		while (comPos >= prevCom) {
//			printf("Compos in string = %d, prevCom %d\n", (int)comPos, (int)prevCom);
			if (nCom > 0) {
				returnedString += ",";
			}
			string subString = command.substr(prevCom, comPos-prevCom);
			returnedString += processCommand(subString, udpSock);
//			printf("Process sub command \"%s\"\n", subString.c_str());
			prevCom = comPos+1;
			if (prevCom >= command.size()) break;
			comPos = command.find(',', prevCom);
			nCom++;
		}
		return returnedString;
	}
	/*
	 * first extract the first work of the command and see if it
	 * matches any of the modules.
	 */
	string firstWord = firstword(command);
//	printf("Command module \"%s\" with \"%s\"\n", firstWord.c_str(), command.c_str());
//	int nWords = countWords(command);
//	for (int i = 0; i < nWords; i++) {
//		string aWord = nthword(command, i);
//		printf("word %d = \"%s\"\n", i, aWord.c_str());
//	}
	PLAProcess* process = findProcess(firstWord);
	if (process) {
		// strip of first word.
		command += " ";
//		printf("Send rest of command ... \"%s\"\n", command.c_str());
		string cmd = command.substr(command.find(' '),string::npos);
		cmd = trimstring(cmd);
//		printf("Send rest of command ... \"%s\"\n", cmd.c_str());
		return process->runCommand(cmd, udpSock);
	}
	else {
		return this->runCommand(command, udpSock);
	}

	return "unknown command";

//	pthread_mutex_unlock( &commandLock );1
}

std::string trimstring(std::string aStr) {
	if (aStr.size() == 0) return aStr;
	while (aStr.size() && aStr.at(0) == ' ') {
		aStr = aStr.substr(1, aStr.size()-1);
	}
	while (aStr.size() > 1 && aStr.at(aStr.size()-1) == ' ') {
		aStr = aStr.substr(0, aStr.size()-1);
	}
	return aStr;
}

std::string firstword(std::string aStr) {
	aStr = trimstring(aStr);
	int bPos = aStr.find(' ');
	if (bPos <= 0) {
		return aStr;
	}
	else {
		return aStr.substr(0, bPos);
	}
}

int countWords(std::string aStr) {
	int nWords = 1;
	aStr = trimstring(aStr);
	if (aStr.size() == 0) return 0;
	int lastPos = -1;
	// make sure to skip adjacent blanks.
	for (unsigned i = 0; i < aStr.size(); i++) {
		if (aStr.at(i) == ' ') {
			if (i > lastPos+1) {
				nWords++;
			}
			lastPos = i;
		}
	}
	return nWords;
}

std::string nthword(std::string aStr, int iWord) {
	aStr = trimstring(aStr) + " ";
	int skipped = 0;
	size_t nextSpace = 0;
	while (iWord > skipped++) {
		nextSpace = aStr.find(' ', nextSpace+1);
		if (nextSpace < 0) {
			return "";
		}
		// check for adjacent spaces
		while (nextSpace < aStr.size()-1 && aStr.at(nextSpace+1) == ' ') {
			nextSpace++;
		}
	}
	if (nextSpace >= aStr.size() -1) return "end";
	return trimstring(aStr.substr(nextSpace, aStr.find(' ', nextSpace+1)-nextSpace));
}

std::string joinstrings(int nArg, char* argV[]) {
	if (nArg == 0) return "";
	string allString = argV[0];
	for (int i = 1; i < nArg; i++) {
		allString += " " + trimstring(argV[i]);
	}
	return allString;
}


CommandManager* commandManager = new CommandManager();
