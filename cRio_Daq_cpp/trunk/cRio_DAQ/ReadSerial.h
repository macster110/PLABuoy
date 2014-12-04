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
const string s_file_location="/u";

/*The size of files in number of lines read */
const int file_size=36000;

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
int open_Serial_Port(Serial_Port volatile *port);

/**
 * Read the current serial port, time stamps and saves the data to a txt file.
 * @param n- number fo serial port reads -1 to keep reading data.
 */
void readSerialPort(Serial_Port port, int n);

/**
 * Close the serial port and the file it is writting to.
 * @return status
 */
int closeSerialPort(Serial_Port  volatile *port);

/**
 * Open file to save serial port data.
 */
int open_File(Serial_Port  volatile *port);

/**
 * Open and read serial port on new thread- convenience function utilises openSerialPort, openFile and readSerialPort
 * @param[in] port- serial prt to use PORT0,PORT1,PORT2.
 * @param[in] baudRate baudrate for the serial port to record on.
 */
int record_Serial(int port, speed_t baudRate);

/**
 * Set whether serial port should be monitored. False will cause all monitorting to stop.
 */
void set_serial_go(bool go);


#endif
