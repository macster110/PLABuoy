/*
 * Settings.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#include <stdio.h>

#ifndef SETTINGS_H_
#define SETTINGS_H_

#define DEFAULTSAMPLERATE (500000)

/*
 * Number of channels for acquisition.
 */
#define DEFAULTNCHANNELS (8)

/*
 * Number of samples to acquire each loop much be a multiple of NCHANNELS
 */
#define READBLOCKSIZE (4096)

/**
 * Overall buffer size in samples - will generate a 10 MByte buffer. .
 * This is a large buffer that data are copied to from the 4096 byte FIFO buffer
 * on the FPGA.
 */
#define READBUFFERLENGTH (DEFAULTNCHANNELS * 2 * 1024 * 1024)

/**
 * Root folder for saving dat.
 */
const std::string SAVE_LOCATION ="/U";


#endif /* SETTINGS_H_ */
