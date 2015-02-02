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

#include "../Reporter.h"

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
	uint64_t expectedSamples = 0;
	short* p = bufstart;
	short nextVal = 0;
	short valStep = 1;
	simTimer->start();
	daq_go = true;
	samplesInBuff = 0;
	int count = 0;
	while (daq_go) {
		expectedSamples = (uint64_t) (simTimer->stop() * 500000);
		if (expectedSamples < samplesAcquired) {
			myusleep(1000);
			continue;
		}
//			printf("In simulate loop\n");
		if (count++ % 10000 == 0) {
			reporter->report(4, "Simulate more data at t=%3.1fs Acquired = %ld, expected %ld\n", simTimer->stop(),
				(int) samplesAcquired, (int)expectedSamples);
		}
//		myusleep(20000);
		for (int i = 0; i < BUFFERBLOCKSAMPLES; i++, p++, nextVal += valStep) {
			*p = nextVal;
		}
		samplesInBuff += BUFFERBLOCKSAMPLES;
		samplesAcquired += (BUFFERBLOCKSAMPLES / NCHANNELS);
		if (p >= bufend) {
			p = bufstart;
		}

	}
	reporter->report(4, "Leave simulation loop\n");

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
	fflush(stdout);
	STARTTHREAD(SimThreadStarter, this, simThreadId, simThreadHandle, threadState)
	if (threadState) {
		printf("Simulatoin thread launched ok\n");
	}
	else {
		printf("Simulatoin thread failed ok\n");

	}
	fflush(stdout);
	return threadState;
}

bool SimulatedDaq::stopSystem(){
	daq_go = false;
	int threadReturn;
	WAITFORTHREAD(simThreadId, simThreadHandle, threadReturn);
	return threadReturn;
}
