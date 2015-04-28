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
#include <iostream>
#include <math.h>
#include <sstream>


/*total number of megabytes written since process was first started*/
double wav_mbyte_count=0;

/*total number of files written - note this is from the first call of init to the program exiting*/
int file_count=0;

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
		file_count++;
		nCalls = 0;
	}

	error = write_Sound_File(plaBuffer->data, plaBuffer->soundFrames*plaBuffer->nChan);

	if (error!=0){
		//fprintf(stderr, "Error WavFileProcess: Error in file: ateempting to close file  \n");
		close_Sound_File();
		//problem here is that the computer create thousands of empty sound files if there's an error
		//create_Sound_File(getNChan(), getSampleRate());
	}
	else {
		wav_mbyte_count=wav_mbyte_count+(plaBuffer->dataBytes/ 1024.) / 1024.;
	}

	return error;
}

int WavFileProcess::getErrorStatus(){
//	std::string summary= getSummaryData();
//	printf("Error WavFileProcess: %s\n", summary.c_str());
	return error;
}


void WavFileProcess::endProcess() {
	close_Sound_File();
}

/**
 * Return tow numbers, 1. the number of wac files written and 2. the size of all wav files.
 */
std::string WavFileProcess::getSummaryData() {
    std::stringstream ss;
    ss << file_count << "," << wav_mbyte_count;
    return ss.str();
}

