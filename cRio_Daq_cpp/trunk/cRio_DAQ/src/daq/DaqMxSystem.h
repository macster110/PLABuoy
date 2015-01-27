/*
 * DaqMxSystem.h
 *
 *  Created on: 27 Jan 2015
 *      Author: doug
 */

#ifndef DAQMXSYSTEM_H_
#define DAQMXSYSTEM_H_

#include "DAQSystem.h"

class DaqMxSystem: public DAQSystem {
public:
	DaqMxSystem();
	virtual ~DaqMxSystem();

	virtual int getStatus();

	virtual int getErrorCount();

	virtual void resetErrorCount();

protected:

	virtual bool prepareSystem();

	virtual bool startSystem();

	virtual bool stopSystem();

};

#endif /* DAQMXSYSTEM_H_ */
