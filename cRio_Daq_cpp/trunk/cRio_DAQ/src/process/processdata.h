/*
 * processdata.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef PROCESSDATA_H_
#define PROCESSDATA_H_


#include <stdint.h>

typedef struct {
	int16_t* data; // pointer to data buffer
	int16_t nChan; // number of channels of audio data
	int16_t soundFrames; // number of audio frames
	int16_t dataBytes; // data bytes = 2*nChan*soundFrames for raw audio.
} PLABuff;

class PLAProcess;

//extern PLAProcess** plaProcesses;

/*
 * Create the processes
 */
void processCreate();

/*
 * Initialise the processes
 */
bool processInit(int nChan, int sampleRate);

/*
 * Send data to the processes
 */
bool processData(int16_t* data, int nSamples);

/*
 * End the processes
 */
void processEnd();

/**
 * Delete the processes.
 */
void processDelete();

/**
 * Make a very simple processing class which can be used for everything.
 * Will have to have a list of downstream process in each unfortunately,
 * but will keep this really simple.
 */
class PLAProcess {

public:

	PLAProcess();

	virtual ~PLAProcess();

	virtual int initProcess(int nChan, int sampleRate);

	virtual int process(PLABuff* plaBuffer);

	virtual void endProcess();

	void addChildProcess(PLAProcess* childProcess);

	int getNChan() const {
		return nChan;
	}

	int getSampleRate() const {
		return sampleRate;
	}

protected:

	int forwardData(PLABuff* plaBuffer);

private:
	PLAProcess** childProcesses;
	int nChildProcesses;
	int nChan;
	int sampleRate;
};

#endif /* PROCESSDATA_H_ */
