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
/*The number of serial port reads before starting a new file*/
const int reads_per_file=1000;

bool volatile serial_go=false;

/**
 * Open one of the serial ports on the cRio.
 * @param port The cRio 9068 has three serial ports and hence port indicates which port to open (0-2)
 */
int open_Serial_Port(Serial_Port volatile *port)
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
		printf( "failed to open port\n" );
		return -1;
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

	cout<<"Serial Port Opened: "<<device<<endl;

	return 0;
}


int open_File(Serial_Port volatile *port){
	//create time stamped file.
	string filename="";
	filename+=s_file_location;
	filename+="/";
	filename+=serial_prefix;
	filename+="_port_";
//	filename+=port->port; //causes error
//	filename+="_";
	filename+=currentDateTime("%Y%m%d_%H%M%S");
	filename+=".txt";
	port->f= fopen(filename.c_str(), "w");
	if (port->f  == NULL)
	{
	    printf("Error: opening file for serial data! \n");
	    printf(filename.c_str());
	    return -1;
	}
    printf("Read Serial: Open file %s\n",filename.c_str());
	return 0;
}

void readSerialPort(Serial_Port port, int n)
{
	printf("Begin reading Serial Port %d\n",n);

	int res;
	char buf[read_size];
	string time;
	int count=0;
	if (port.f  == NULL)
	{
		printf("Error: file for serial data is null!\n");
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
		usleep(10000);

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
				if (count%1000==0){
					cout<<"Serial port data to write: "<<time << " count:"<< count <<endl; //print out statement very 100 reads
				}
				fprintf(port.f ,"%s\n",time.c_str());
			}
			count++;
		}

	}
	printf("Serial reading finished \n");
	if (port.f != NULL) fclose(port.f);
	printf("Serial port closed \n");


}


/**
 * Entry function for pthread to read serial data
 */
void *serial_Read_thread_function(void* port)
{
	Serial_Port *port_ptr = (Serial_Port *) port; //cast serial port struct
	open_Serial_Port(port_ptr); //open serial port.
	while (serial_go){
		open_File(port_ptr); //open file write to.
		readSerialPort(*port_ptr, file_size); //start reading serial port.
	}

	return NULL;
}

int record_Serial(int port, speed_t baudRate){

	/*Allocate structure to memory. Remember serial port is on different thread so structure has to be volatile*/
	Serial_Port* volatile ptr_port=(Serial_Port*) malloc(sizeof(Serial_Port));
	ptr_port->f =(FILE*) malloc(sizeof(FILE));
//	/**Define baud rate to use*/
	ptr_port->BAUDRATE=baudRate;
	/**Define port to use*/
	ptr_port->port=port;

	/*Test function for serial port*/
//	cout<<"read serial port"<<endl;
//	serial_Read_thread_function(ptr_port);

//	/**Open serial port on new thread*/
	/*create thread to read serial port data*/
	pthread_t read_serial_thread;
	/**launch thread*/
//	if(pthread_create(&read_serial_thread, NULL, serial_Read_thread_function, ptr_port)){
//		fprintf(stderr, "Error creating thread to read data serial data\n");
//		return 1;  ;
//	}

//	//TEMP- debug only
//	pthread_join(read_serial_thread,NULL);

	return 0;
}

void set_serial_go(bool go){
	serial_go=go;
}



