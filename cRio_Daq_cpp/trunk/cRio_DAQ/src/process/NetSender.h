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
#include "../mythread.h"
#include "../command/Command.h"

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

	int socketWaitThread();

	bool setDestinationIp(std::string newIpAddress);

	bool setDestinationPort(int portId);

	int clearQueue();

private:
	queue<PLABuff> networkQueue;

	volatile int socketId; // socket for writing data to

	volatile int listenSocket; // id of socket that waits for connections.

	struct hostent* hostEntity;

	int sendData(PLABuff* data);

	int writeSendHeader(void* pBuff, int dataBytes, int32_t datatType2);

	bool openConnection();

	void closeConnection();

	bool sendX3Header(int socketId);

	bool startSocketThread();

	int64_t queuedBytes;

	RealTimer* conTimer;

	THREADID netsendThread;
	THREADHANDLE netSendThreadHandle;

	// handles etc. for the thread which waits for a socket open request.
	THREADID socketThread;
	THREADHANDLE socketThreadHandle;

	DECLARE_LOCK(socketLock);

	int nSends;

	int64_t dataWritten;

	volatile int ipPort = 8013;

};


class ClearNetQueue: public Command {
public:
	ClearNetQueue(NetSender* netSender);
	virtual ~ClearNetQueue();

	std::string execute(std::string command, struct sockaddr_in* udpSock);

	std::string getHint() {
		return "Clear the TCPIP send queue";
	}
private:
	NetSender* netSender;
};

#endif /* NETSENDER_H_ */
