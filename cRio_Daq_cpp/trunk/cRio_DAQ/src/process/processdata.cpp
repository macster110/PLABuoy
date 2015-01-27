
/*
 * Main process functions and file. C functions to call in at start / end
 * of acquisition, then calling into C++ processes to do the actual work.
 */
#include "processdata.h"
#include "WavFileProcess.h"
#include "X3FileProcess.h"
#include "CompressProcess.h"
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
	plaProcesses[0] = new PLAProcess("base");
	plaProcesses[1] = new CompressProcess();
	plaProcesses[2] = new X3FileProcess();
	plaProcesses[3] = new NetSender();

	/*
	 * Add both the wav writing and the compression process to the
	 * input process.
	 */
	plaProcesses[0]->addChildProcess(plaProcesses[1]);

#ifndef WINDOWS
	// attach x3 write process to compressor.
	plaProcesses[1]->addChildProcess(plaProcesses[2]);
#endif

	// attach net sender to output of compression.
	plaProcesses[1]->addChildProcess(plaProcesses[3]);
}

int getNumProcesses() {
	return NPROCESSES;
}
/**
 * Get a process.
 */
PLAProcess* getProcess(int iProcess) {
	if (iProcess < 0 || iProcess >= NPROCESSES) {
		return NULL;
	}
	return plaProcesses[iProcess];
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

/**
 * Find a process with a given name. Return null if nothing found
 */
class PLAProcess* findProcess(std::string processName) {
	if (processName.size() == 0) {
		return NULL;
	}
	for (int i = 0; i < NPROCESSES; i++) {
		if (plaProcesses[i] == NULL) continue;
		if (plaProcesses[i]->getProcessName() == processName) {
			return plaProcesses[i];
		}
	}
	return NULL;
}

PLAProcess::PLAProcess(const string processName) : CommandList() {
	this->processName = processName;
	childProcesses = NULL;
	parentProcess = NULL;
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

/**
 * Get configuration data, ideally in an xml like format for any downstream
 * modules to use.
 */
int PLAProcess::getModuleConfiguration(char* configData, int configDataLength) {
	return 0;
}

void PLAProcess::addChildProcess(PLAProcess* childProcess) {
	if (nChildProcesses == 0) {
		childProcesses = (PLAProcess**) malloc(sizeof(PLAProcess*));
	}
	else {
		childProcesses = (PLAProcess**) realloc(childProcesses, sizeof(PLAProcess*) * (nChildProcesses+1));
	}
	childProcesses[nChildProcesses++] = childProcess;
	childProcess->setParentProcess(this);
}
