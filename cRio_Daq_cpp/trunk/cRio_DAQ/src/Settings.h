/*
 * Settings.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#include <stdio.h>

#include <string>

#ifndef SETTINGS_H_
#define SETTINGS_H_

#define DEFAULTSAMPLERATE (500000)

/*
 * Kill all these defaults since they now need to become
 * global(ish) variables.
 */
/*
 * Number of channels for acquisition.
 */
#define DEFAULTNCHANNELS (8)

/*
 * Number of samples to acquire each loop much be a multiple of NCHANNELS
 */
//#define READBLOCKSIZE (4096) // was 4096

/**
 * Overall buffer size in samples - will generate a 10 MByte buffer. .
 * This is a large buffer that data are copied to from the 4096 byte FIFO buffer
 * on the FPGA.
 */
//#define READBUFFERLENGTH (DEFAULTNCHANNELS * 4 * 1024 * 1024)

/**
 * Root folder for saving dat.
 */
const std::string SAVE_LOCATION ="/media/sda1";


#endif /* SETTINGS_H_ */
