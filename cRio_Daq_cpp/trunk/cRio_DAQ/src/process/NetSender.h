/*
 * NetSender.h
 *
 *  Created on: 12 Jan 2015
 *      Author: doug
 */

#ifndef NETSENDER_H_
#define NETSENDER_H_

#include "processdata.h"

#include <queue>
#include <vector>
//#include <string>
using namespace std;

class NetSender : public PLAProcess {
public:
	NetSender();

	virtual ~NetSender();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();

	int sendThreadLoop();

private:
	queue<PLABuff> networkQueue;

	int socketId;
	struct hostent* hostEntity;

	int sendData(PLABuff* data);

	void writeSendHeader(void* pBuff, PLABuff* data);

	bool openConnection();

	void closeConnection();
};

#endif /* NETSENDER_H_ */
