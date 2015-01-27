/*
 * WriteWav.h
 * Writes .wav files to the cRio. This class manages writing .wav file to the cRio external storage,
 * time stamping files in PAMGUARD format.
 * Created on: 5 Jun 2014
 * Author: Jamie Macaulay
 */

/*#ifndef WRITEWAV_H_ */
#define WRITEWAV_H_
/*Sound file library installed on cRio through opkg package manager*/
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.h> // was sndfile.hh !!
#include <fstream> // needed for string

using namespace std;

/**
 * Format to write wav files in. Set for signed int16.
 */
const int wavFormat=SF_FORMAT_WAV | SF_FORMAT_PCM_16;

/* The folder on the external drive to save wav files in.*/
/*const string wavlocation="/home/admin/cRioKE/";*/
const string wav_location="/u";

/* The prefix for the .wav files.*/
const string wav_prefix="PAM";

/**Error constants for writing .wav files**/
/*The buffer array is not a multiple of the number of the channels*/
const int BUFFER_SIZE_ERROR=1;

/*The number of written frames is not equal to the number of input frames- error in writing data*/
const int NUM_WRITTEN_FRAMES_ERROR=2;

/**
* Create a .wav file. This create a .wav file time stamped in PAMGUARD format and save to u/wav
* u is the standard name for an external drive connected to the cRio.
* @param channels - number of channels
* @param SR - number of samples per second.
* @return error output. 0 if no error.
*/
int create_Sound_File(int channels, int SR);

/**
* Write to the current file. It is assumed that the buffer is is  a linear array of samples and can be 
* divided exactly  by channels.
* @param dataBufVec pointer to start of array to write. 
* @param size- size of array to write
* @return error output. 0 if no error.
*/
int write_Sound_File(int16_t* dataBufVec, int size);

/**
 * Close the current sound file;
 * @return error output. 0 if no error.
 */
int close_Sound_File();

/**
 * Creates a .wav file, buffer, some simulated data for each channel and attempts to write .wav file to disk.
 * Used for testing code and hardware.
 */
void testWavWrite();

/**
 * Get the total size of the current wav file in bytes. Returns -1
 */
int total_File_Size();

/**
 * Check whether output is a sound file error.
 */
bool is_Sound_File_Error(int error);


