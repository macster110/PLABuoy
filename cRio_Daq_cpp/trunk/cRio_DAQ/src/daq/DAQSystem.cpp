/*
 * DAQSystem.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "DAQSystem.h"
#include "../Settings.h"
#include "../process/processdata.h"
#include "../mythread.h"
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*pthread library for running on different threads*/
#include <unistd.h>

///*Entry function for pthread to write FIFO data*/
//void *read_Buffer_thread_function(void *param)
//{
//	/*Acquire the number of channels from the FPGA*/
//	//	DAQSystem* daqSystem = (DAQSystem*) param;
//	//	int16_t channels = 0;
//	//	 NiFpga_ReadI16(daqSystem->get_Session_FPGA(),
//	//			NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI16_ChassisTemperature,
//	//			&channels);
//	//	printf("Acquired number of channels from FPGA...%d\n",channels);
//	((DAQSystem*) param)->read_Data_Buffer(NCHANNELS);
//
//	return NULL;
//}

DECLARETHREAD(read_Buffer_thread_function, DAQSystem, read_Data_Buffer)


/*Number of samples to Aquire from FIFO on each loop iteration*/
const unsigned int Number_Acquire = READBLOCKSIZE;

DAQSystem::DAQSystem(std::string name) {
	this->name = name;
	dataBufVec = NULL;
	bufend = bufstart = NULL;
	write_data_thread = 0;
	bufferSize=READBUFFERLENGTH; //5MSample buffer
}

DAQSystem::~DAQSystem() {
	// TODO Auto-generated destructor stub
}


bool DAQSystem::prepare() {
	deleteBuffer();
	createBuffer();
	return prepareSystem();
}

bool DAQSystem::start() {
	daq_go = true;
	/**Create thread to read data from FIFO buffer and save data to .wav file*/

	printf("Buffer Read thread is initialising...\n");
	bool threadState;
	STARTTHREAD(read_Buffer_thread_function, this, write_data_thread, write_thread_handle, threadState)
	if (!threadState) {
//	if(pthread_create(&write_data_thread, NULL, read_Buffer_thread_function, this)){
		printf("Error creating thread to read data buffer\n");
		return false;
	}
	printf("Buffer Read thread has initialised...\n");

	return startSystem();
}

bool DAQSystem::stop() {
	daq_go = false;
	int threadReturn;
//	printf("Wait for daq loop to complete *********************\n");
//	fflush(stdout);
	WAITFORTHREAD(write_data_thread, write_thread_handle, threadReturn)
//	printf("Dq loop has sompleted ***************************e\n");
//	fflush(stdout);
//	pthread_join(write_data_thread, NULL);
	return stopSystem();
}

bool DAQSystem::createBuffer()
{
	dataBufVec=(int16_t*) malloc(bufferSize*sizeof(int16_t));
	bufstart=dataBufVec;
	bufend=dataBufVec+bufferSize;
	samplesInBuff = 0;
	return dataBufVec != NULL;
}

bool DAQSystem::deleteBuffer()
{
	if (dataBufVec) free(dataBufVec);
	dataBufVec = NULL;
	return true;
}
/**
 *
 * Need to modify this one so that it no longer uses any of the FPGA specific functions.
 * Will put an isOK call into the daqsystem class.
 *
 * Begin a loop to constantly acquire data from buffer and process (save to file, queue for transmission, etc).
 * @param[in] channels. The number of channels contained as interleaved samples in the data buffer.
 */
void DAQSystem::read_Data_Buffer(){
	int channels = 8;
	/*Need to represent sample rate as no. samples per second*/
	//	int SR=(1./(double)Sample_Rate_us) *1000*1000;

	int toWrite=0;
	int count=0;
	/*Sound file error*/
	int error=0;
	/*Pointer for the current read location of the buffer.*/
	int16_t* cpr=bufstart;
	printf("Read loop beginning \n");
	/**
	 * Currently seems to read data in variable block sizes. Will be slightly
	 * nicer for network ops if block size is kept constant. For an open stream,
	 * TCP will break the data into packets by itself, so it doesn't really
	 * matter what we do here. PAMGuard likes
	 * data unit packets of about .1s, which would be 50000 samples at this rate
	 * which is considerably larger than Mark typically compressed as a block, which
	 * is about 1000 samples. Can either make really large packets, or stick with smaller ones
	 * and let PAMGuard stitch them back into larger data units. Probably a mix of both ?
	 *
	 */
	while (daq_go)// && getStatus() == DAQ_STATUS_RUNNING)
	{

		/*Create a new sound file if N loops have executed*/
		// get's handled in sound file writer.
		//		if (count%file_size_N==0 && NiFpga_IsNotError(status_DAQ)){
		//			create_Sound_File(8,SR);
		//			cout << "Creating new file: sample rate  = " << SR << endl;
		//		}

		/**
		 * Don't want to have this while loop going at full pelt so wait for a number of us and then
		 * try again. Ignore unsigned int warning as samplesInBuff is always positive.
		 */
		if (count++ % 100000 == 0) {
			printf("Loop %d samples in buffer %d, last read %d samples\n",
					count, samplesInBuff, toWrite);
		}
		//		samplesInBuff = 0;
		if (samplesInBuff<READBLOCKSIZE){
			myusleep(10000); //10000us seems to work well for high sample rates.
			continue;
		}

//		count++;

		/**
		 * Need to get an expression for the number of samples to read but we don't want to read samples beyond the end of the array
		 * toWrite is the number of samples to read until we reach the end of the array. Once the end of the ring buffer has been reached
		 * we reset the cpr pointer and go round the loop again to grab the data from the buffer.
		 */
//		int toEnd=bufend-cpr;
		// change so it's always reading 1024 samples
		toWrite = READBLOCKSIZE;
		//		toWrite=samplesInBuff;
		//		if (toWrite>toEnd){
		//			cout << "Reached end of buffer: going to start: => samples to write " << toWrite <<" To buffer end: " << toEnd << std::endl;
		//			toWrite=toEnd;
		//		}

		/*
		 * Now write the wav file safe in the knowledge there will not be a segmentation fault due
		 * to overshooting the end of the array.
		 *
		 * This is also where data will have to be sent off to the compressor and sent off to
		 * PAMGuard for real time operation. Buffer is currently 5 Megabytes which is just over
		 * half a second of data. Clearly the Network send will need a much bigger queue than
		 * this, with multiple packets waiting to be written from a different thread. Should
		 * however be OK to do the compression, file writing and writing to the output queue
		 * in this thread.
		 *
		 */
		if (getStatus() == DAQ_STATUS_RUNNING){
//			printf("processData %d samples at 0x%x\n", toWrite, cpr);
//			fflush(stdout);
			error = processData(cpr, toWrite);
		}
		else{
//			printf("processEnd\n");
//			fflush(stdout);
			processEnd();
		}

		/*Check for error during sound file write*/
		//		if (error) NiFpga_MergeStatus(&status_DAQ, NiFpga_Status_External_Storage);

		/*Move the current read pointer along*/
		cpr+=toWrite;
		samplesInBuff-=toWrite;
		if (cpr>=bufend){
			cpr=bufstart;
		}

	}

	printf("LEave the read buffer thread function ...\n");

	/**
	 * If an error is flagged in the FIFO thread then PRINT OUT STATEMENT
	 */
	//	if (!NiFpga_IsNotError(status_DAQ)){
	//		printf("Error detected in read FIFO thread. Read thread is exiting %d!\n", status_DAQ);
	//	}
}
