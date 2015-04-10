/*
 * SerialReadProcess.cpp
 *
 *  Created on: 7 Apr 2015
 *      Author: Jamioe Macaulay
 */

#include "SerialReadProcess.h"
#include "processdata.h"
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.h>
#include "../ReadSerial.h"
#include "../mythread.h"
#include <termios.h>
#include <iostream>
#include "../Utils.h"

/**
 * Declare the serial reading and writing to file thread.
 */
DECLARETHREAD(recordSerialThreadFunction, SerialReadProcess, recordSerialThread)

/**
 * The serial port number- note: see SerialRead for what these ports mean (port==0 -> /dev/ttyS01)
 */
const int port=0;

/**
 * Number of lines to write for each file.
 */
const int n_size=36000;

/**
 * BAUDRATE-keep B but change number to change.
 */
speed_t baudRate=B4800;

SerialReadProcess::SerialReadProcess() : PLAProcess("serialRead", "SERIAL") {

}

SerialReadProcess::~SerialReadProcess() {

}

int SerialReadProcess::initProcess(int nChan, int sampleRate) {
	bool threadState;
	STARTTHREAD(recordSerialThreadFunction, this, serialReadThread, serialReadThreadHandle, threadState)
	if (!threadState) {
		fprintf(stderr, "Error starting read serial thread \n");
		return false;
	}
	return 0;
}

int SerialReadProcess::process(PLABuff* plaBuffer) {
	return 0;
}

void SerialReadProcess::endProcess() {
	int threadReturn;
	//set flag to false- this will stop serial processes.
	setSerialGo(false);
	WAITFORTHREAD(serialReadThread, serialReadThreadHandle, threadReturn)
}

int SerialReadProcess::recordSerialThread(){

	//set go flag.
	setSerialGo(true);

	//create a new serial port structure.
	/*Allocate structure to memory. Remember serial port is on different thread so structure has to be volatile*/
	Serial_Port* volatile ptr_port=(Serial_Port*) malloc(sizeof(Serial_Port));
	ptr_port->f =(FILE*) malloc(sizeof(FILE));
	/**Define baud rate to use*/
	ptr_port->BAUDRATE=baudRate;
	/**Define port to use*/
	ptr_port->port=port;

	// begin recording thread;
	serialPortReadFunction(ptr_port, n_size);

	return 0;
}

