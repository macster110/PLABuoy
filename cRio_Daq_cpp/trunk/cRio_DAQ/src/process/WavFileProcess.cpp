/*
 * WavFileProcess.cpp
 *
 *  Created on: 11 Jan 2015
 *      Author:  Doug Gillespie
 */

#include "WavFileProcess.h"
#include "processdata.h"
#include "../WriteWav.h"
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.h>

WavFileProcess::WavFileProcess() : PLAProcess("wavrecord", "WAV") {
	nCalls = 0;
	error = 0;
}

WavFileProcess::~WavFileProcess() {
}


int WavFileProcess::initProcess(int nChan, int sampleRate) {
	PLAProcess::initProcess(nChan, sampleRate);
	create_Sound_File(nChan, sampleRate);
	nCalls = 0;
	error = 0;
	return 0;
}

int WavFileProcess::process(PLABuff* plaBuffer) {
	//|| not wavFile
	if (nCalls++ == 10000) {
		//close the current sound file
		close_Sound_File();
		//create a new sound file.
		error=create_Sound_File(getNChan(), getSampleRate());
		nCalls = 0;
	}

	error = write_Sound_File(plaBuffer->data, plaBuffer->soundFrames*plaBuffer->nChan);

	if (error!=0){
		//fprintf(stderr, "Error WavFileProcess: Error in file: ateempting to close file  \n");
		close_Sound_File();
		//problem here is that the computer create thousands of empty sound files if there's an error
		//create_Sound_File(getNChan(), getSampleRate());
	}

	return error;
}

int WavFileProcess::getErrorStatus(){
	//printf("Error WavFileProcess: %d\n", error);
	return error;
}


void WavFileProcess::endProcess() {
	close_Sound_File();
}
