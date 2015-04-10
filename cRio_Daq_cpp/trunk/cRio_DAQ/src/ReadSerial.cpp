/*
 * ReadSerial.cpp
 *
 *Read a serial port, time stamp data and save to file .
 *This involves opening the serial port, and then sitting in a  while loop reading data.
 *Since incoming serial port data is so slow there is no need to have a read/write thread
 *or buffer- data is written to file as it is read.
 *Created on: 18 June 2014
 *Author: Jamie Maculay
 */
#include "ReadSerial.h"
#include "mythread.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "Utils.h"
#include <pthread.h>
#include <unistd.h>

using namespace std;

/**
 * Baudrate settings are defined in <asm/termbits.h>, which is
 * included by <termios.h>
 */
//#define BAUDRATE B9600   // Change as needed, keep B
/*Baudrate to use*/
/*speed_t BAUDRATE=B9600;*/

/*The size of the read buffer*/
const int read_size=255;

bool volatile serial_go=true;

/**
 * Open one of the serial ports on the cRio.
 * @param port The cRio 9068 has three serial ports and hence port indicates which port to open (0-2)
 */
int openSerialPort(Serial_Port *port)
{
	/* Figure out which0 device to use*/
	const char* device;
	switch(port->port){
	case 0  :
		device=PORT0.c_str();
		break;
	case 1  :
		device=PORT1.c_str();
		break;
	case 2  :
		device=PORT2.c_str();
		break;
		/* you can have any number of case statements */
	default : /* Optional */
		device=PORT0.c_str();
		break;
	}

	struct termios newtio;

	port->fd = open(device, O_RDWR | O_NOCTTY | O_NDELAY);
	cout<<"Port fd is: "<<port->fd<<endl;
	if(port->fd == -1) {
		printf( "ReadSerial: Failed to open port\n" );
		return PORT_ERROR;
	}

	bzero(&newtio, sizeof(newtio)); /* clear struct for new port settings */

	/* BAUDRATE: Set bps rate. You could also use cfsetispeed and cfsetospeed.
	   CRTSCTS : output hardware flow control (only used if the cable has
	             all necessary lines. See sect. 7 of Serial-HOWTO)
	   CS8     : 8n1 (8bit,no parity,1 stopbit)
	   CLOCAL  : local connection, no modem contol
	   CREAD   : enable receiving characters */
	/*check the port struct does not contain baud rate data- if it doesn't set baud rate to 4800*/
	cout<<"BAUDRATE: "<<port->BAUDRATE<<endl;
	if (port->BAUDRATE==0){
		port->BAUDRATE=B4800;
	}
	newtio.c_cflag = port->BAUDRATE | CRTSCTS | CS8 | CLOCAL | CREAD;

	/* IGNPAR  : ignore bytes with parity errors
        otherwise make device raw (no other input processing) */
	newtio.c_iflag = IGNPAR;

	/*  Raw output  */
	newtio.c_oflag = 0;

	/* ICANON  : enable canonical input
           disable all echo functionality, and don't send signals to calling program */
	newtio.c_lflag = ICANON;

	/* now clean the modem line and activate the settings for the port */
	tcflush(port->fd, TCIFLUSH);
	tcsetattr(port->fd,TCSANOW,&newtio);

	cout<<"Serial Port Opened: "<< device << " Baudrate: " << port->BAUDRATE << endl;

	return 0;
}


int openWriteFile(Serial_Port *port){
	//create time stamped file.
	string filename="";
	filename+=s_file_location;
	filename+="/";
	filename+=serial_prefix;
	filename+="_port_";
//	filename+=port->port; //causes error
//	filename+="_";
	timeval tv;
	gettimeofday(&tv, 0);
	filename+=currentDateTime(tv, "%Y%m%d_%H%M%S");
	filename+=".txt";
	port->f= fopen(filename.c_str(), "w");
	if (port->f  == NULL)
	{
		fprintf(stderr,"Serial Read Error: Could not open file for serial data. \n");
	    printf(filename.c_str());
	    return NO_FILE_ERROR;
	}
    printf("ReadSerial: Open file %s\n",filename.c_str());
	return 0;
}

int readSerialPort(Serial_Port port, int n)
{
	printf("ReadSerial: Begin reading Serial Port. no. of lines %d\n",n);

	int error=0;
	int res;
	char buf[read_size];
	string time;
	int count=0;
	if (port.f  == NULL)
	{
		fprintf(stderr,"ReadSerial: Error: file for serial data is null!\n");
	}

	// NMEA command to output all sentences in case reading serial GPS.
	write(port.fd, "$PTNLSNM,273F,01*27\r\n", 21);

	while (serial_go && (count<n || n<0)) {

		/* loop continuously */
		/*  read blocks program execution until a line terminating character is
        input, even if more than 255 chars are input. If the number
        of characters read is smaller than the number of chars available,
        subsequent reads will return the remaining chars. res will be set
        to the actual number of characters actually read */
		myusleep(10000);

		res = read(port.fd, buf, read_size);

		buf[res] = 0;             /* set end of string, so we can printf */
		time=currentDateTime();
//		printf("%s", buf);
//		printf("res %d", res);
//		if (res>1 && res<=read_size){
		if (res<=read_size){
			time+=",";
			time+=buf;
			if (port.f  != NULL)
			{
				// print out serial port data every often.
				if (count%1==0){
					cout<<"ReadSerial: Serial port data to write: "<< time.c_str() << " loop count:"<< count << endl; //print out statement very 100 reads
				}
				//write to file
				fprintf(port.f ,"%s\n",time.c_str());
				usleep(1000000);
			}
			else return PORT_ERROR;

			count++;
		}

	}
	printf("ReadSerial: Serial reading finished \n");
	if (port.f != NULL) fclose(port.f);
	else return PORT_ERROR;
	printf("ReadSerial: Serial port closed \n");

	return error;


}


void serialPortReadFunction(Serial_Port *port_ptr, const int n_lines)
{
	printf( "ReadSerial: Start serial port thread\n" );
	//	Serial_Port *port_ptr = (Serial_Port *) port; //cast serial port struct
	int error=0;
	error=openSerialPort(port_ptr); //open serial port.
	printf( "ReadSerial: Start serial port thread 1\n");
	if (error>=0){
		while (serial_go){
			printf( "ReadSerial: Start serial port thread 2\n");
			error=openWriteFile(port_ptr); //open file write to.
			error=readSerialPort(*port_ptr, n_lines); //start reading serial port.
		}
	}
	else {
		printf( "ReadSerial: Failed to read port\n" );
	}

}

void setSerialGo(bool go){
	serial_go=go;
}



