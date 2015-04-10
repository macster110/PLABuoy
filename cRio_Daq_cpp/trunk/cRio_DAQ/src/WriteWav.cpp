/*
 * WriteWav.cpp
 * A simple class which allows users to create, write and close .wav files.
 * Created on: 5 Jun 2014
 * Author: Jamie Macaulay
 */

#include <iostream>
/*Required for console interactions */
#include <stdio.h>
/*Basic math functions*/
#include "WriteWav.h"
/*Required to use a vector, a resizable array which is used as the buffer holding data between the FIFO read and .wav write threads*/
#include <vector>
/*Sound file library installed on cRio through opkg package manager*/
#include <iterator>
/*Needed to get the system time. */
#include <time.h>
/*math*/
#include <cmath>
/**need for rand*/
#include <stdlib.h>
#include <malloc.h>
#include <fstream>
#include <cstdio>
/**Some useful functions for the Linux RealTime*/
#include "Utils.h"
/*for creating folder*/
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.hh>

using namespace std;

/*The current file which is being written*/
SNDFILE *sndFile;

/*Info on the current sound file*/
SF_INFO info;

string currentFolder = "";

/*The total number of bytes which have successfully been written to the last open file*/
int totalWrite=0;

int write_Sound_File(int16_t* dataBufVec, int size)
{
	if (sndFile == NULL){
		//fprintf(stderr, "Error write_Sound_File: Not a .wav file\n");
		return NOT_WAV_FILE;
	}

	if (size%info.channels!=0){

		/**
		 * There is something wrong with the data or the file handle- the sound
		 * data should be interleaved and therefore must be a divisible unit of the number of channels
		 * Return a buffer size error.
		 */
		fprintf(stderr, "Error write_Sound_File: Buffer size error\n");
		return BUFFER_SIZE_ERROR;
	}

	/**
	 * Figure out the number of frames. The number of frames multiplied
	 * by the number of channels should be the same size as the buffer. If this is
	 * not the case return an error.
	 */
	sf_count_t frames = size/info.channels;

	/*Write to the .wav file*/
	long writtenFrames=sf_writef_short(sndFile, dataBufVec, frames);

	/*Check correct number of frames saved*/
	if (writtenFrames != frames) {
	    fprintf(stderr, "Error write_Sound_File: Did not write enough frames for source: %s\n", sf_strerror(sndFile));
//		cout << " Size " << size<< " frames " <<frames
//				<< " writtenFrames " <<writtenFrames <<" buffer: "
//				<< sizeof(dataBufVec) << " size: " << size << " channels: " << info.channels<< endl;
		return NUM_WRITTEN_FRAMES_ERROR;
	}

//	int i;
//	for (i=0; i<8; i++){
//		cout << " sample " << i << " " << dataBufVec[i] << endl;
//	}

	/* the total number of written frames*/
	totalWrite+=writtenFrames*info.channels;

	return 0;
}


int close_Sound_File(){
	/* Call the operating system's function to force the writing of all file cache buffers to disk*/
	sf_write_sync(sndFile);
	totalWrite=0;
	return sf_close(sndFile);
}


int total_File_Size(){
 return totalWrite;
}

/**
 * Create a .wav file with file name time stamped.
 */
int create_Sound_File(int channels, int SR)
{
	string desiredFolder = folderString();
	if (desiredFolder != currentFolder) {
		string fullPath = wav_location+"/"+desiredFolder;
		mkpath(fullPath);
		currentFolder = desiredFolder;
	}

	/*Get current data and time for the counter*/
	string dateTime=currentDateTime();

	/*Create a new out file name based on the system time*/
	string outfilename=(wav_location+"/"+desiredFolder+"/"+wav_prefix+"_"+dateTime+".wav");
	const char* outfile_char = outfilename.c_str();
	cout << "New filename " << outfilename << endl;

	/*Create file for out file*/
	// Set file settings, 16bit Mono PCM
	info.format = SF_FORMAT_WAV | SF_FORMAT_PCM_16;
	info.channels = 1;
	info.samplerate = SR;
	sndFile = sf_open(outfile_char, SFM_WRITE, &info);

	if (sndFile == NULL){
		fprintf(stderr, "Error create_Sound_File : Not a .wav file %s\n", sf_strerror(sndFile));
		return NOT_WAV_FILE;
	}

	/*Reset write counter*/
	totalWrite=0;

	return 0;
}

//void testWavWrite()
//{
//	if (not wavFile){
//	   cout << "Error: Not a .wav file" << endl;
//	   return;
//	}
//
//	int channels=wavFile.channels();
//	int SR=wavFile.samplerate();
//	double duration = 10;
//
//	/**Create a buffer for 'duration' seconds of data**/
//	int bufferSize=duration*SR*channels;
//	int numFrames=duration*SR;
//
//	/*Create a vector of frequencies*/
//	vector<double> freq;
//	for (long n=0; n<channels; n++){
//		/*Random frequency between 1000 and 20000*/
//		double freqS=(rand()%20+1)*1000;
//		freq.push_back(freqS);
//	}
//
//	/*Create the buffer*/
//	int16_t* buffer = (int16_t *) malloc(bufferSize * sizeof(int16_t));
//	if (buffer == NULL) {
//		fprintf(stderr, "Could not allocate test buffer for output\n");
//	}
//
//	/*Fill the buffer array*/
//	long f;
//	long i=0;
//	for (f=0 ; f<numFrames ; f++) {
//		double time = f * duration / numFrames;
//		for (long j=0; j<channels; j++){
//			int pos=i+j;
//			buffer[pos] = 0.4*pow(2, 16)*sin(2.0 * M_PI * time * freq.at(j));   // Channel 1
//		}
//		i+=channels;
//	}
//
//
//	cout << "Total test samples in buffer: "  << i <<endl;
//
//	/*Now write the file*/
//	write_Sound_File(buffer,bufferSize);
//
//	/*Close the file and clean up*/
//	sf_close(wavFile.rawHandle());
//    free(buffer);
//
//}


bool is_Sound_File_Error(int error){
	if (error!=0) {
		return true;
	}
	return false;
}

