/*
 * ReadSerial.h
 *
 *  Created on: 18 Jun 2014
 *      Author: user
 */

#ifndef READSERIAL_H_
#define READSERIAL_H_
#include <iostream>
#include <termios.h>

using namespace std;

/* Serial ports as named for cRio 9068 in Linux Realtime*/
const string PORT0 ="/dev/ttyS0"; //cRio port 1 -rs232
const string PORT1 ="/dev/ttyS1"; //cRio port 2 -rs232
const string PORT2 ="/dev/ttyS2"; //cRio port 3 -rs485

/* The prefix for the serial files.*/
const string serial_prefix="GPS";

/* The folder on the external drive to save wav files in.*/
/*const string wavlocation="/home/admin/cRioKE/";*/
const string s_file_location="/V";

/*The file could not be created or does not exist.*/
const int NO_FILE_ERROR=-1;

/*Failed to open port*/
const int PORT_ERROR=-2;

/**
 * Structure which contains info for serial port. The structure has a default baud rate of 9600.
 */
typedef struct {
	/**Serial port to open*/
	int volatile port;
	/*Reference to the open serial port;*/
	int volatile fd;
	/*Reference to output file*/
	FILE* volatile f;
	/*Baudrate*/
	speed_t volatile BAUDRATE;
} Serial_Port;

/**
 * Open one of the serial ports on the cRio.
 * @param port Structure containing info for serial ports.
 * @return status
 */
int openSerialPort(Serial_Port *port);

/**
 * Read the current serial port, time stamps and saves the data to a txt file.
 * @param n- number fo serial port reads -1 to keep reading data.
 * @return error flag. 0 if no error.
 */
int readSerialPort(Serial_Port port, int n);

/**
 * Close the serial port and the file it is writting to.
 * @return status
 */
int closeSerialPort(Serial_Port *port);

/**
 * Open file to save serial port data.
 */
int openWriteFile(Serial_Port *port);

/**
 * Set whether serial port should be monitored. False will cause all monitorting to stop.
 */
void setSerialGo(bool go);

/**
 * Convenient entry function for a thread to read serial data. Reads and time stamps serial data and creates new files every n_lines read.
 * @param port - serial port structure
 * @param n_lines - the number of lines to read before starting a new file.
 */
void serialPortReadFunction(Serial_Port *port_ptr, const int n_lines);


#endif
