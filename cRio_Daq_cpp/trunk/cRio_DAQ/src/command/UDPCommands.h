/*
 * UDPCommands.h
 *
 *  Created on: 5 Feb 2015
 *      Author: doug
 */

#ifndef UDPCOMMANDS_H_
#define UDPCOMMANDS_H_

#include "../mythread.h"

class UDPCommands {
public:
	UDPCommands();
	virtual ~UDPCommands();

	bool setUDPPort(int portId);
	bool startUDPThread();
	void stopUDPThread(bool wait = true);
	bool openConnection();
	void closeConnection();
	int udpThread();

private:
	volatile bool thread_run;
	THREADID updThreadId;
	THREADHANDLE udpThreadHandle;
	int portId;
	volatile int udpSocket;
};

extern UDPCommands* udpCommands;

#endif /* UDPCOMMANDS_H_ */
