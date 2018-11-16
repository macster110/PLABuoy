/*
 * DAQSystem.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef DAQSYSTEM_H_
#define DAQSYSTEM_H_
#include <string>
#include <stdint.h>
#include "../mythread.h"
#include "../Utils.h"

#define DAQ_STATUS_IDLE    1
#define DAQ_STATUS_RUNNING 2
#define DAQ_STATUS_ERROR   3

/**
 * Base class for daq systems. Will put the FPGA class
 * into one of these and also an NI max system.
 */
class DAQSystem {
public:

	DAQSystem(std::string name);

	virtual ~DAQSystem();

	bool prepare(int nChan);

	bool start();

	bool stop();

	void read_Data_Buffer();

	virtual int getStatus() = 0;

	virtual int getErrorCount() = 0;

	virtual void resetErrorCount() = 0;

	const std::string& getName() const {
		return name;
	}

	int getChan() const {
		return nChan;
	}

	void setChan(int chan) {
		nChan = chan;
	}

protected:

	virtual bool prepareSystem() = 0;

	virtual bool startSystem() = 0;

	virtual bool stopSystem() = 0;

	/* Buffer fields*/
	int bufferSize; //5MSample buffer
	int16_t* volatile dataBufVec; //note declare volatile after pointer to make pointer volatile.
	/*Pointer to buffer start*/
	int16_t* volatile bufstart;
	/*Pointer to end of the buffer array*/
	int16_t* volatile bufend;
	/*The number of sample in the ring buffer*/
	int volatile samplesInBuff;

	/*
	 * daq_go is a flag set as soon as the FPGA is loaded since acquisition will start immediately.
	 * However, data will be dumped and not used (i.e. sent) until the additional daq_use flag
	 * is set.
	 */
	volatile bool daq_go;
	volatile bool daq_use;

	timeval daqStart;

	DECLARE_LOCK(bufferLock);

private:

	std::string name;

	THREADID write_data_thread;

	THREADHANDLE write_thread_handle;

	bool createBuffer();

	bool deleteBuffer();

	int nChan;

	int readBlockSize;
};

#endif /* DAQSYSTEM_H_ */
