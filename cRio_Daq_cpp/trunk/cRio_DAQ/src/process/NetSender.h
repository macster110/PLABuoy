/*
 * NetSender.h
 *
 *  Created on: 12 Jan 2015
 *      Author: doug
 */

#ifndef NETSENDER_H_
#define NETSENDER_H_

#include "processdata.h"
#include "../RealTimer.h"

#include <queue>
#include <vector>
#include <string>
//#include <string>
using namespace std;
/*
 * Types of data defined so far for PAMGUARD
 * Commented out ones not used in this particular software
 */
//#define NET_PAM_DATA        1
//#define NET_REMOTE_COMMAND  2
//#define NET_SPEED_DATA      3
//#define NET_SYSTEM_DATA     4
//#define NET_PAM_COMMAND     5
#define NET_AUDIO_DATA      10

//#define SYSTEM_GPSDATA       1
//#define SYSTEM_BATTERYDATA   4
//#define SYSTEM_COMPASSDATA  16

#define NET_AUDIO_HEADINFO 1
#define NET_AUDIO_SOUND    2

class NetSender : public PLAProcess {
public:
	NetSender();

	virtual ~NetSender();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();

	int sendThreadLoop();

	bool setDestinationIp(std::string newIpAddress);

private:
	queue<PLABuff> networkQueue;

	int socketId;
	struct hostent* hostEntity;

	int sendData(PLABuff* data);

	int writeSendHeader(void* pBuff, int dataBytes, int32_t datatType2);

	bool openConnection();

	void closeConnection();

	bool sendX3Header(int socketId);

	int64_t queuedBytes;

	RealTimer* conTimer;
};

#endif /* NETSENDER_H_ */
