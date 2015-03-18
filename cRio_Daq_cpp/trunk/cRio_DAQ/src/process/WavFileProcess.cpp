/*
 * WavFileProcess.cpp
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#include "WavFileProcess.h"
#include "processdata.h"
#include "../WriteWav.h"
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.h>

WavFileProcess::WavFileProcess() : PLAProcess("wavrecord", "WAV") {
	nCalls = 0;
}

WavFileProcess::~WavFileProcess() {
}


int WavFileProcess::initProcess(int nChan, int sampleRate) {
	PLAProcess::initProcess(nChan, sampleRate);
	create_Sound_File(nChan, sampleRate);
	nCalls = 0;
	return 0;
}

int WavFileProcess::process(PLABuff* plaBuffer) {
	if (nCalls++ == 10000) {
		create_Sound_File(getNChan(), getSampleRate());
		nCalls = 0;
	}
	return write_Sound_File(plaBuffer->data, plaBuffer->soundFrames*plaBuffer->nChan);
}

void WavFileProcess::endProcess() {
	close_Sound_File();
}
