/*
 * ReadNIDAQ.cpp
 *
 *  Created on: 17 Jun 2014
 *  Author: Jamie Macaulay
 */
/*include header file*/
#include "ReadNIDAQ.h"
/*Needed for memcpy*/
#include <cstring>
/* Required for sleep() */
#include <unistd.h>
/* Required for console interactions */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*pthread library for running on different threads*/
#include <pthread.h>
///* For this to work on the cRio -std=c++0x must be added to the build variables*/
//#include <thread>
/*Class for managing writing .wav files to the cRio external storage device.*/
//#include "WriteWav.h"
/* Manages the FPGA- contains status and session fields etc.*/
#include "NIFpgaManager.h"
#include "CRioRecDAQ.h"
/* general constants, to be replaced later by control structures */
#include "Settings.h"

#include "process/processdata.h"

using namespace std;

///* Buffer fields*/
//int16_t* volatile dataBufVec; //note declare volatile after pointer to make pointer volatile.
///*Pointer to buffer start*/
//int16_t* volatile bufstart=0;
///*Pointer to end of the buffer array*/
//int16_t* volatile bufend=0;
///*The number of sample in the ring buffer*/
//int volatile samplesInBuff=0;
/*The size of the buffer*/
const int bufferSize=READBUFFERLENGTH; //5MSample buffer
/*Number of samples to Aquire from FIFO on each loop iteration*/
const unsigned int Number_Acquire = READBLOCKSIZE;

/*Boolean to indicate whether read DAQ should be running*/
//bool volatile daq_go=true;

///*The status of the DAQ portion of the FPGA*/
//NiFpga_Status status_DAQ;

/*The number of read from buffer loops to execute before a new loop is executed*/
//int file_size_N=10000;


///*Entry function for pthread to write FIFO data*/
//void *read_Buffer_thread_function(void *param)
//{
//	/*Acquire the number of channels from the FPGA*/
//	int16_t channels = 0;
//	NiFpga_MergeStatus(&status_DAQ, NiFpga_ReadI16(get_Session_FPGA(),
//			NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI16_ChassisTemperature,
//			&channels));
//	printf("Acquired number of channels from FPGA...%d\n",channels);
//	read_Data_Buffer(channels);
//
//	return NULL;
//}


///*Entry function for pthread to read FIFO*/
//void *FIFO_thread_function(void *param)
//{
//	NiFpga_Session session_FPGA=get_Session_FPGA();
//	NiFpga_IrqContext irqContext_FPGA=get_IrqContext_FPGA();
//	/*Holds any errors flagged up. Reset error to zero. Important, otherwise we just carry any previous errors and hit an 'infinite error loop' until system resets.*/
//	status_DAQ=0;
//	/*Merge with error from FPGA manager just in case something has been flagged up there*/
//	status_DAQ=NiFpga_MergeStatus(&status_DAQ, get_Status_FPGA());
//
//	read_FIFO_Data(session_FPGA, &status_DAQ, &irqContext_FPGA);
//
//	return NULL;
//}

///**
// * This function get's called from run_FPGA_tasks to record data. It
// * sits in this function until recording stops. It's been launched in
// * a separate thread, so is OK.
// */
//void record_DAQ()
//{
//	daq_go=true;
//
//	/* this variable is our reference to the second thread */
//	pthread_t read_FIFO_thread;
//	pthread_t write_data_thread;
//	int thread_var1 = 0, thread_var2 = 0;
//
//	/*Create the main inter-thread buffer*/
//	create_Buffer();
//
//	/**Create thread to read FIFO data*/
//	printf("FIFO thread is initialising...\n");
//	if(pthread_create(&read_FIFO_thread, NULL, FIFO_thread_function, &thread_var1)){
//		fprintf(stderr, "Error creating thread to read data from FIFO\n");
//		return;
//	}
//	printf("FIFO thread has initialised...\n");
//
//	/**Create thread to read data from FIFO buffer and save data to .wav file*/
//	printf("Buffer Read thread is initialising...\n");
//	if(pthread_create(&write_data_thread, NULL, read_Buffer_thread_function, &thread_var2)){
//		fprintf(stderr, "Error creating thread to read data buffer\n");
//		return ;
//	}
//	printf("Buffer Read thread has initialised...\n");
//
//	/*Wait for both threads to finish before closing up*/
//	pthread_join(read_FIFO_thread, NULL);
//	pthread_join(write_data_thread, NULL);
//
//	/**Free up memory used for buffer*/
//	free(dataBufVec);
//}

/*
 * Start the readout thread for the main buffer read.
 */
bool startBufferRead() {

}
/*
 * Stop the readout thread for the main buffer read.
 */
bool stopBufferRead() {

}

//void create_Buffer()
//{
//	dataBufVec=(int16_t*) malloc(bufferSize*sizeof(int16_t));
//	bufstart=dataBufVec;
//	bufend=dataBufVec+bufferSize;
//}

///**
// * Read the FIFO as quickly as possible, removing data and storing in a data buffer. As little as possible happens in this function;
// * @param[in]		session		Uses FPGA session reference
// * @param[in,out]	status		Uses and updates error status
// * @param[in]		irqContext	Uses irqContext for IRQ
// */
//void read_FIFO_Data(NiFpga_Session session, NiFpga_Status *status, NiFpga_IrqContext *irqContext)
//{
//	//unsigned int Sample_Rate_us = 20;
//	uint32_t fifo=0;
//	size_t depth=100000; //need to make this this size to prevent overflow errors in FIFO.
//
//	// Set the sample rate
//	NiFpga_MergeStatus(status, NiFpga_WriteU32(session,
//			NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU32_SamplePerioduSec,
//			Sample_Rate_us));
//
//	/**
//	 * Set the size of the depth buffer
//	 */
//	NiFpga_MergeStatus(status, NiFpga_ConfigureFifo(session,fifo,depth));
//
//	printf("Current Status in read FIFO thread %d!\n", *status);
//
//	/* IRQ related variables */
//	uint32_t irqsAsserted;
//	uint32_t irqTimeout = 2000; //2 seconds
//	NiFpga_Bool TimedOut = false;
//
//	//Wait on IRQ to ensure FPGA is ready
//	NiFpga_MergeStatus(status, NiFpga_WaitOnIrqs(session,
//			irqContext,
//			NiFpga_Irq_0,
//			irqTimeout,
//			&irqsAsserted,
//			&TimedOut));
//
//	/* DMA FIFO related variables */
//	/**the size of the FPGA is 4096. 1024
//	 * Should give us plenty of wiggle room in the FPGA**/
//	unsigned int Number_Acquire = 1024;
//	unsigned int Elements_Remaining;
//	unsigned int Fifo_Timeout = 2000; //timeout in milliseconds
//
//	// Acknowledge IRQ to begin DMA acquisition
//	NiFpga_MergeStatus(status, NiFpga_AcknowledgeIrqs(session,
//			irqsAsserted));
//
//	/* Enter main loop. Exit if there is an error or the user requests the program to end */
//	long count=0;
//	int16_t* cpw=bufstart;
//	int16_t Fifo_Data[Number_Acquire];
//	while (daq_go && NiFpga_IsNotError(*status) && !TimedOut)
//	{
//
//		/* Read FIFO data into the Fifo_Data array */
//		NiFpga_MergeStatus(status, NiFpga_ReadFifoI16(session,
//				NiFpga_NI_9222_Anologue_DAQ2_FPGA_TargetToHostFifoI16_FIFO,
//				Fifo_Data,
//				Number_Acquire,
//				Fifo_Timeout,
//				&Elements_Remaining));
//
//		/*Check for an error*/
//		if (!NiFpga_IsNotError(*status)){
//			printf("Error in read FIFO thread %d!\n", *status);
//			continue;
//		}
//
//		/*Add the data to the buffer. Pointer for start of FIFO array and size of FIFO array in bytes*/
//		memcpy(cpw, Fifo_Data, sizeof(int16_t)*Number_Acquire);
//		/*Move the pointer along to the correct point in the buffer*/
//		cpw+=Number_Acquire;
//		/*Record the total number of samples in the array that haven't been saved.*/
//		samplesInBuff+=Number_Acquire;
//		if (cpw>=bufend){
//			//std::cout << "Reset write buffer on call " << count <<std::endl;
//			cpw=bufstart;
//		}
//
//		/*Check we don't have more samples than the buffer*/
//		if (samplesInBuff>bufferSize){
//			cout << "Error in read FIFO thread. Buffer overflow error! buffer samples: " << samplesInBuff << " loop count: " << count << endl;
//			*status=NiFpga_Status_Read_Buffer_Overflow; //tell status there has been an error.
//			break;
//		}
//
//		/*print some info every 5000 counts*/
//		if (count%100000==0){
//			cout << "FIFO Read Loop count = " << count/1000 << "k" <<endl;
//			//int length=dataBufVec.size();
//			//int length=(sizeof(Fifo_Data)/sizeof(*Fifo_Data));
//			//cout << "cpw = " << cpw << endl;
//			//cout << "Elements left = " << Elements_Remaining << endl;
//			//for (int i=0; i<1024; i=i+8)
//			//{
//			//cout << " Data Example "<< Fifo_Data[i] <<endl;
//			//}
//		}
//
//		count++;
//	}
//
//   /* Stop the Fifo*/
//   NiFpga_MergeStatus(status, NiFpga_StopFifo(session, fifo));
//
//
//   /*Release all elements from the FIFO so don't end up with a NiFpga_Status_FifoElementsCurrentlyAcquired 63083 error if run again*/
//   NiFpga_MergeStatus(status, NiFpga_ReleaseFifoElements(session, fifo,
//		   Number_Acquire));
//
//	/*
//	 *Typical error codes for refereence
//	 * Error-63101 : no .lvbitx file found. We need this binary file to talk the cRio
//	 * Error-50400 : timeout FPGA
//	 */
//   if (!NiFpga_IsNotError(status_DAQ)){
//	   printf("Error in read FIFO thread %d!\n", *status);
//   }
//
//}

///**
// *
// * Need to modify this one so that it no longer uses any of the FPGA specific functions.
// * Will put an isOK call into the daqsystem class.
// *
// * Begin a loop to constantly acquire data from buffer and process (save to file, queue for transmission, etc).
// * @param[in] channels. The number of channels contained as interleaved samples in the data buffer.
// */
//void read_Data_Buffer(int channels){
//
//	/*Need to represent sample rate as no. samples per second*/
//	int SR=(1./(double)Sample_Rate_us) *1000*1000;
//
//	int toWrite=0;
//	int count=0;
//	/*Sound file error*/
//	int error=0;
//	/*Pointer for the current read location of the buffer.*/
//	int16_t* cpr=bufstart;
//	cout << "Read loop beginning "<< count << std::endl;;
//	/**
//	 * Currently seems to read data in variable block sizes. Will be slightly
//	 * nicer for network ops if block size is kept constant. For an open stream,
//	 * TCP will break the data into packets by itself, so it doens't really
//	 * matter what we do here. PAMGuard likes
//	 * data unit packets of about .1s, which would be 50000 samples at this rate
//	 * which is considerably larger than Mark typically compressed as a block, which
//	 * is about 1000 samples. Can either make really large packets, or stick with smaller ones
//	 * and let PAMGuard stitch them back into larger data units. Probably a mix of both ?
//	 *
//	 */
//	processInit(NCHANNELS, SR);
//	while (daq_go && NiFpga_IsNotError(status_DAQ))
//	{
//
//		/*Create a new sound file if N loops have executed*/
//		// get's handled in sound file writer.
////		if (count%file_size_N==0 && NiFpga_IsNotError(status_DAQ)){
////			create_Sound_File(8,SR);
////			cout << "Creating new file: sample rate  = " << SR << endl;
////		}
//
//		/**
//		 * Don't want to have this while loop going at full pelt so wait for a number of us and then
//		 * try again. Ignore unsigned int warning as samplesInBuff is always positive.
//		 */
////		samplesInBuff = 0;
//		if (samplesInBuff<READBLOCKSIZE){
//			usleep(1000); //2000us seems to work well for high sample rates.
//			continue;
//		}
//
//		count++;
//
//		/**
//		 * Need to get an expression for the number of samples to read but we don't want to read samples beyond the end of the array
//		 * toWrite is the number of samples to read until we reach the end of the array. Once the end of the ring buffer has been reached
//		 * we reset the cpr pointer and go round the loop again to grab the data from the buffer.
//		 */
//		int toEnd=bufend-cpr;
//		// change so it's always reading 1024 samples
//		toWrite = READBLOCKSIZE;
////		toWrite=samplesInBuff;
////		if (toWrite>toEnd){
////			cout << "Reached end of buffer: going to start: => samples to write " << toWrite <<" To buffer end: " << toEnd << std::endl;
////			toWrite=toEnd;
////		}
//
//		/*
//		 * Now write the wav file safe in the knowledge there will not be a segmentation fault due
//		 * to overshooting the end of the array.
//		 *
//		 * This is also where data will have to be sent off to the compressor and sent off to
//		 * PAMGuard for real time operation. Buffer is currently 5 Megabytes which is just over
//		 * half a second of data. Clearly the Network send will need a much bigger queue than
//		 * this, with multiple packets waiting to be written from a different thread. Should
//		 * however be OK to do the compression, file writing and writing to the output queue
//		 * in this thread.
//		 *
//		 */
//		if (NiFpga_IsNotError(status_DAQ)){
////			error=write_Sound_File(cpr, toWrite);
//			error = processData(cpr, toWrite);
//		}
//		else{
////			close_Sound_File();
//			processEnd();
//		}
//
//		/*Check for error during sound file write*/
//		if (error) NiFpga_MergeStatus(&status_DAQ, NiFpga_Status_External_Storage);
//
//		/*Move the current read pointer along*/
//		cpr+=toWrite;
//		samplesInBuff-=toWrite;
//		if (cpr>=bufend){
//			cpr=bufstart;
//		}
//
////		if (count % 10000 == 0) {
////			printf("Loop %d samples in buffer %d, last read %d samples\n",
////					count, samplesInBuff, toWrite);
////		}
//	}
//
//	/**
//	 * If an error is flagged in the FIFO thread then PRINT OUT STATEMENT
//	 */
//	if (!NiFpga_IsNotError(status_DAQ)){
//		printf("Error detected in read FIFO thread. Read thread is exiting %d!\n", status_DAQ);
//	}
//	processEnd();
//}

/*
 * Get the current daq error status_FPGA.
 */
//NiFpga_Status get_Status_DAQ(){
//	return status_DAQ;
//}
//
//void set_DAQ_Go(bool go){
//	daq_go=go;
//}




