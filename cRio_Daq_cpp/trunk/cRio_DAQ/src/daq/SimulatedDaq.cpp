/*
 * SimulatedDaq.cpp
 *
 *  Created on: 27 Jan 2015
 *      Author: doug
 */

#include "SimulatedDaq.h"

#include <stdio.h>

#include "../RealTimer.h"
#include "../Settings.h"
#include "../mythread.h"
#include <unistd.h> // for usleep

DECLARETHREAD(SimThreadStarter, SimulatedDaq, simThread)

#define BUFFERBLOCKSAMPLES 4096

SimulatedDaq::SimulatedDaq() : DAQSystem("Simulated DAQ") {
	simTimer = new RealTimer();

}

SimulatedDaq::~SimulatedDaq() {
	// TODO Auto-generated destructor stub
}

void* SimulatedDaq::simThread(){

	uint64_t samplesAcquired = 0;
	uint64_t expectedSamples;
	short* p = bufstart;
	short nextVal = 0;
	short valStep = 1;
	simTimer->start();
	daq_go = true;
	printf("Enter simulation loop buf Start %0xX, Buff end %0xX\n", bufstart, bufend);
	fflush(stdout);
	samplesInBuff = 0;
	while (daq_go) {
		expectedSamples = (uint64_t) (simTimer->stop() * 500000);
//		if (expectedSamples < samplesAcquired) {
			myusleep(20000);
//			continue;
//		}
		printf("Simulate more data Acquired = %ld, expected %ld\n", (int) samplesAcquired, (int)expectedSamples);
		for (int i = 0; i < BUFFERBLOCKSAMPLES; i++, p++, nextVal += valStep) {
			*p = nextVal;
		}
		samplesInBuff += BUFFERBLOCKSAMPLES;
		samplesAcquired += (BUFFERBLOCKSAMPLES / NCHANNELS);
//		if (p >= bufend) {
			p = bufstart;
//		}

	}
	printf("LEave simulation loop\n");

	return NULL;
}

int SimulatedDaq::getStatus(){
	return (daq_go ? DAQ_STATUS_RUNNING : DAQ_STATUS_IDLE);
}

int SimulatedDaq::getErrorCount(){
	return 0;
}

void SimulatedDaq::resetErrorCount(){
}

bool SimulatedDaq::prepareSystem(){
	return true;
}

bool SimulatedDaq::startSystem(){
	bool threadState;
	daq_go = true;
	printf("Call to start simulation thread\n");
	STARTTHREAD(SimThreadStarter, this, simThreadId, simThreadHandle, threadState)
	return threadState;
}

bool SimulatedDaq::stopSystem(){
	daq_go = false;
	int threadReturn;
	WAITFORTHREAD(simThreadId, simThreadHandle, threadReturn);
	return threadReturn;
}
