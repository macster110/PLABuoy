/*
 * CRioRecDAQ.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */


#ifndef CRIORECDAQ_H_
#define CRIORECDAQ_H_

#include "mythread.h"

void exitTerminalLoop();

bool prepare();

bool start();

bool stop(bool restart);

extern class DAQSystem* daqSystem;

float getChassisTemp();

/*
 *WatchDog class which monitors processes and DAQ system
 */
extern class PLAWatchDog* cRioWatchDog;

/**
 * Simple class for a watch dog- allows watch dog to be deployed in by in mythread class. .
 */
class PLAWatchDog {

public:

	//thread handles for watch dog.
	THREADID processWatchDogThrd;

	THREADHANDLE processWatchDogThrdHnd;

	PLAWatchDog();

	void watchdog_monitor();

	/**
	 * Start watch dog on new thread.
	 */
	void startWatchDog();

	/**
	 * Stop current watch dog thread.
	 */
	void stopWatchDog();

};


#endif /* CRIORECDAQ_H_ */
