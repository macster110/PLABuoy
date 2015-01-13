
/*
 * Main process functions and file. C functions to call in at start / end
 * of acquisition, then calling into C++ processes to do the actual work.
 */
#include "processdata.h"
#include "WavFileProcess.h"
#include "CompressProcess.h";
#include "NetSender.h"
#include <stdio.h>
#include <stdlib.h>


PLAProcess** plaProcesses;

int nChannels = 0;


#define NPROCESSES (4)

/*
 * Create the processes
 */
void processCreate() {
	plaProcesses = (PLAProcess**) malloc(sizeof(PLAProcess*) * NPROCESSES);
	plaProcesses[0] = new PLAProcess();
	plaProcesses[1] = new WavFileProcess();
	plaProcesses[2] = new CompressProcess();
	plaProcesses[3] = new NetSender();

	/*
	 * Add both the wav writing and the compression process to the
	 * input process.
	 */
//	plaProcesses[0]->addChildProcess(plaProcesses[1]);
	plaProcesses[0]->addChildProcess(plaProcesses[2]);

	// attach net sender to output of compression.
	plaProcesses[2]->addChildProcess(plaProcesses[3]);
}

/*
 * Initialise the processes
 */
bool processInit(int nChan, int sampleRate) {
	nChannels = nChan;
	for (int i = 0; i < NPROCESSES; i++) {
		plaProcesses[i]->initProcess(nChan, sampleRate);
	}
	return true;
}

/*
 * Send data to the processes. this function only has to send to the first process,
 * this will send data out through a tree to downstream processes.
 * Data always arrive here as raw - so pack into a PLABuff structure
 * to send off into the rest of the processing system.
 */
bool processData(int16_t* data, int nSamples) {

	PLABuff plaBuff;
	plaBuff.data = data;
	plaBuff.nChan = nChannels;
	plaBuff.soundFrames = nSamples / nChannels;
	plaBuff.dataBytes = sizeof(int16_t) * nSamples;
	return plaProcesses[0]->process(&plaBuff);
}

/*
 * End the processes
 */
void processEnd() {
	for (int i = 0; i < NPROCESSES; i++) {
		plaProcesses[i]->endProcess();
	}
}

/**
 * Delete the processes. Called only at program exit.
 */
void processDelete() {
	for (int i = 0; i < NPROCESSES; i++) {
		delete(plaProcesses[i]);
	}
}

PLAProcess::PLAProcess() {
	childProcesses = NULL;
	nChildProcesses = 0;
	nChan = 0;
	sampleRate = 0;
}

PLAProcess::~PLAProcess() {
	if (childProcesses) free(childProcesses);
}

int PLAProcess::initProcess(int nChan, int sampleRate) {
	this->nChan = nChan;
	this->sampleRate = sampleRate;
	return 0;
}

int PLAProcess::process(PLABuff* plaBuffer) {
	/**
	 * Base function just forwards the data on. all downstream
	 * processes will need to override this.
	 */
	return forwardData(plaBuffer);
}

int PLAProcess::forwardData(PLABuff* plaBuffer) {
	int errors = 0;
	for (int i = 0; i < nChildProcesses; i++) {
		errors += childProcesses[i]->process(plaBuffer);
	}
	return errors;
}

void PLAProcess::endProcess() {

}

void PLAProcess::addChildProcess(PLAProcess* childProcess) {
	if (nChildProcesses == 0) {
		childProcesses = (PLAProcess**) malloc(sizeof(PLAProcess*));
	}
	else {
		childProcesses = (PLAProcess**) realloc(childProcesses, sizeof(PLAProcess*) * (nChildProcesses+1));
	}
	childProcesses[nChildProcesses++] = childProcess;
}
