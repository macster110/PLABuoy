/*
 * CRioRecDAQ.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */
#include "mythread.h"

#ifndef CRIORECDAQ_H_
#define CRIORECDAQ_H_

void exitTerminalLoop();

/**
 * Start data acquisition.
 */
bool start();

/**
 * Stop data acquisition.
 * @param restart - true to restart aquisition after all stop functions have been executed.
 */
bool stop(bool restart);

/**
 * Aquires data from DAQ cards in the cRio.
 */
extern class DAQSystem* daqSystem;


/*
 *WatchDog class which monitors processes and DAQ system
 */
class PLAWatchDog* cRioWatchDog;

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
