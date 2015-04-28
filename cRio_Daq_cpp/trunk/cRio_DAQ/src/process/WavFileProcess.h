/*
 * WavFileProcess.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef WAVFILEPROCESS_H_
#define WAVFILEPROCESS_H_

#include "processdata.h"
#include <iostream>


class WavFileProcess : public PLAProcess {


public:
	WavFileProcess();

	~WavFileProcess();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	int getErrorStatus();

	void endProcess();

	std::string getSummaryData();

private:
	int nCalls;

	volatile int error;
};

#endif /* WAVFILEPROCESS_H_ */
