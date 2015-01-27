/*
 * SimulatedDaq.h
 *
 *  Created on: 27 Jan 2015
 *      Author: doug
 */

#ifndef SIMULATEDDAQ_H_
#define SIMULATEDDAQ_H_

#include "DAQSystem.h"
#include "../mythread.h"

class RealTimer;

class SimulatedDaq: public DAQSystem {
public:
	SimulatedDaq();
	virtual ~SimulatedDaq();

	virtual int getStatus();

	virtual int getErrorCount();

	virtual void resetErrorCount();

	void* simThread();

protected:

	virtual bool prepareSystem();

	virtual bool startSystem();

	virtual bool stopSystem();

private:
	RealTimer* simTimer;

	THREADHANDLE simThreadHandle;
	THREADID simThreadId;

};

#endif /* SIMULATEDDAQ_H_ */
