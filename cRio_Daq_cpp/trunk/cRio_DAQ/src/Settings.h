/*
 * Settings.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef SETTINGS_H_
#define SETTINGS_H_

/*
 * Number of channels for acquisition.
 */
#define NCHANNELS (8)
/*
 * number of samples to acquire each loop much be a multiple of NCHANNELS
 */
#define READBLOCKSIZE (1024)
/**
 * Overall buffer size in samples - will generate a 10 MByte buffer. .
 * This is a large buffer that data are copied to from the 4096 byte FIFO buffer
 * on the FPGA.
 */
#define READBUFFERLENGTH (NCHANNELS * 1024 * 1024)


#endif /* SETTINGS_H_ */
