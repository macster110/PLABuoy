/*
 * WavFileProcess.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef WAVFILEPROCESS_H_
#define WAVFILEPROCESS_H_

#include "processdata.h"

class WavFileProcess : public PLAProcess {
public:
	WavFileProcess();

	~WavFileProcess();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();

private:
	int nCalls;
};

#endif /* WAVFILEPROCESS_H_ */
