/*
 * FPGADaqSystem.cpp
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#include "FPGADaqSystem.h"

#include <stdio.h>
#include <string.h>


#include "../NIFpgaManager.h"
#include "../Settings.h"
#include <string>
#include <unistd.h>
#include <iosfwd>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>

using namespace std;


/**
 * Entry function for pthread for user input
 */
void *FPGA_input_function(void *param)
{
	((FPGADaqSystem*) param)->run_FPGA_tasks();
	return NULL;
}

/*Entry function for pthread to read FIFO*/
void *FIFO_thread_function(void *param)
{
	FPGADaqSystem* fpgaDaqSystem = ((FPGADaqSystem*) param);
	NiFpga_Session session_FPGA=fpgaDaqSystem->get_Session_FPGA();
	NiFpga_IrqContext irqContext_FPGA=fpgaDaqSystem->get_IrqContext_FPGA();
	/*Holds any errors flagged up. Reset error to zero. Important, otherwise we just carry any previous errors and hit an 'infinite error loop' until system resets.*/
	NiFpga_Status status_DAQ=0;
	/*Merge with error from FPGA manager just in case something has been flagged up there*/
	status_DAQ=NiFpga_MergeStatus(&status_DAQ, fpgaDaqSystem->get_Status_FPGA());
	printf("start to read_FIFO_Data .....\n");
	fpgaDaqSystem->read_FIFO_Data(session_FPGA, &status_DAQ, &irqContext_FPGA);
	printf("Completed read_FIFO_Data .....\n");

	return NULL;
}

FPGADaqSystem::FPGADaqSystem() : DAQSystem("FPGA Daq") {
	status_DAQ = 0;

	errorCount_FPGA=0;

	FPGA_Go=false;
	/*Defines the session*/
	session_FPGA = 0;
	irqContext_FPGA = 0;
	/*Holds any error flagged up*/
	status_FPGA=-1;
}

FPGADaqSystem::~FPGADaqSystem() {
	// TODO Auto-generated destructor stub
}

bool FPGADaqSystem::prepareSystem() {
	return true;
}

bool FPGADaqSystem::startSystem() {
	if(pthread_create(&fpga_task_thread, NULL, FPGA_input_function, this)){
		fprintf(stderr, "FPGA monitor: creating input thread\n");
		return false;
	}
	else {
		return true;
	}
}

void FPGADaqSystem::run_FPGA_tasks()
{
	printf("Startint run_FPGA_tasks\n");
	FPGA_Go=true;
	/*Create while loops so recording restartes after error*/
	while (FPGA_Go){

		/*
		 * Initialise processes (done in read module).
		 */
		//		processInit(NCHANNELS);
		/**
		 * Prepare the FPGA
		 */
		prepare_FPGA();

		/*Check to make sure no errors during FPGA start up*/
		if (!NiFpga_IsNotError(status_FPGA)){
			printf("FPGA Manager: Error preparing FPGA %d!\n", status_FPGA);
			close_FPGA();
			errorCount_FPGA++;
			continue;
		}


		/*Put FPGA tasks here*/
		/*
		 * Start recording from DAQ card: Note that this function MUST wait for recording to stop
		 * or an error to occur before exiting.
		 */
		record_DAQ();

		break;
	}
}
NiFpga_Status FPGADaqSystem::prepare_FPGA()
{

	printf("FPGA Manager: Attempting to connect to cRio...\n");

	/**
	 * Initialise the FPGA- errors are stored in status.
	 */
	status_FPGA = NiFpga_Initialize();

	/**
	 * Opens a session with the FPGA, downloads the bit stream, and runs the FPGA, storing any error info in "status"
	 * The names of the .lvbitx file and signature values are stored in the /cRioTestC/NiFpga_NI_9222_Anologue_DAQ2_FPGA.h file.
	 */
	printf("FPGA Manager: Opening a Session... status %d\n",status_FPGA);
	NiFpga_MergeStatus(&status_FPGA, NiFpga_Open(NiFpga_NI_9222_Anologue_DAQ2_FPGA_Bitfile,
			NiFpga_NI_9222_Anologue_DAQ2_FPGA_Signature,
			"RIO0",
			0,
			&session_FPGA));

	/* reserve a context for this thread to wait on IRQs */
	NiFpga_MergeStatus(&status_FPGA, NiFpga_ReserveIrqContext(session_FPGA, &irqContext_FPGA));

	return status_FPGA;

}
/**
 * Read the FIFO as quickly as possible, removing data and storing in a data buffer. As little as possible happens in this function;
 * @param[in]		session		Uses FPGA session reference
 * @param[in,out]	status		Uses and updates error status
 * @param[in]		irqContext	Uses irqContext for IRQ
 */
void FPGADaqSystem::read_FIFO_Data(NiFpga_Session session, NiFpga_Status *status, NiFpga_IrqContext *irqContext)
{
	//unsigned int Sample_Rate_us = 20;
	uint32_t fifo=0;
	size_t depth=100000; //need to make this this size to prevent overflow errors in FIFO.
	uint32_t Sample_Rate_us = 1000000/SAMPLERATE;
	printf("Set microsecond tick to %d\n", Sample_Rate_us);
	// Set the sample rate
	NiFpga_MergeStatus(status, NiFpga_WriteU32(session,
			NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU32_SamplePerioduSec,
			Sample_Rate_us));

	/**
	 * Set the size of the depth buffer
	 */
	NiFpga_MergeStatus(status, NiFpga_ConfigureFifo(session,fifo,depth));

	printf("Current Status in read FIFO thread %d!\n", *status);

	/* IRQ related variables */
	uint32_t irqsAsserted;
	uint32_t irqTimeout = 2000; //2 seconds
	NiFpga_Bool TimedOut = false;
	int weeKips = 0;

	//Wait on IRQ to ensure FPGA is ready
	NiFpga_MergeStatus(status, NiFpga_WaitOnIrqs(session,
			irqContext,
			NiFpga_Irq_0,
			irqTimeout,
			&irqsAsserted,
			&TimedOut));

	/* DMA FIFO related variables */
	/**the size of the FPGA is 4096. 1024
	 * Should give us plenty of wiggle room in the FPGA**/
	unsigned int Number_Acquire = 1024;
	unsigned int Elements_Remaining;
	unsigned int Fifo_Timeout = 2000; //timeout in milliseconds

	// Acknowledge IRQ to begin DMA acquisition
	NiFpga_MergeStatus(status, NiFpga_AcknowledgeIrqs(session,
			irqsAsserted));

	/* Enter main loop. Exit if there is an error or the user requests the program to end */
	long count=0;
	int16_t* cpw=bufstart;
	int16_t Fifo_Data[Number_Acquire];
	while (daq_go && NiFpga_IsNotError(*status) && !TimedOut)
	{

		/* Read FIFO data into the Fifo_Data array */
		NiFpga_MergeStatus(status, NiFpga_ReadFifoI16(session,
				NiFpga_NI_9222_Anologue_DAQ2_FPGA_TargetToHostFifoI16_FIFO,
				Fifo_Data,
				Number_Acquire,
				Fifo_Timeout,
				&Elements_Remaining));

		/*Check for an error*/
		if (!NiFpga_IsNotError(*status) ){
			printf("Error in read FIFO thread %d!\n", *status);
			continue;
		}

		/*Add the data to the buffer. Pointer for start of FIFO array and size of FIFO array in bytes*/
		memcpy(cpw, Fifo_Data, sizeof(int16_t)*Number_Acquire);
		/*Move the pointer along to the correct point in the buffer*/
		cpw+=Number_Acquire;
		/*Record the total number of samples in the array that haven't been saved.*/
		samplesInBuff+=Number_Acquire;
		if (cpw>=bufend){
//			std::cout << "Reset write buffer on call " << count <<std::endl;
			cpw=bufstart;
		}
		if (Elements_Remaining < 1024) {
			/**
			 * can afford to have a bit of a kip and free up some CPU.
			 * Having a week kip like this when the buffer is near empty massively
			 * reduces CPU load since it seems that the blocking NiFpga_ReadFifoI16
			 * eats up CPU like nobodies business. For an 8 channel, 500kHz system
			 * this has reduced CPU usage on the acquisition from 50% (i.e. an entire
			 * core of the processor running flat out) to 8%. This will hopefully
			 * improve stability of the rest of the system.
			 * The 500ms value seems to let if have a kip about one time in three indicating
			 * that it couldn't sleep much more.
			 */
			usleep(500);
			weeKips++;
		}

		/*Check we don't have more samples than the buffer*/
		if (samplesInBuff>bufferSize){
			cout << "Error in read FIFO thread. Buffer overflow error! buffer samples: " << samplesInBuff << " loop count: " << count << endl;
			*status=NiFpga_Status_Read_Buffer_Overflow; //tell status there has been an error.
			break;
		}

		/*print some info every 5000 counts*/
		if (count%100000==0){
			printf("FIFO Read Loop count = %dk, samples in buffer %d, %d remain in FIFO, managed %d wee kips\n",
					(int) (count/1000), samplesInBuff, Elements_Remaining, weeKips);
			weeKips = 0;
		}

		count++;
	}

	/* Stop the Fifo*/
	NiFpga_MergeStatus(status, NiFpga_StopFifo(session, fifo));


	/*Release all elements from the FIFO so don't end up with a NiFpga_Status_FifoElementsCurrentlyAcquired 63083 error if run again*/
	NiFpga_MergeStatus(status, NiFpga_ReleaseFifoElements(session, fifo,
			Number_Acquire));

	/*
	 *Typical error codes for refereence
	 * Error-63101 : no .lvbitx file found. We need this binary file to talk the cRio
	 * Error-50400 : timeout FPGA
	 */
	if (!NiFpga_IsNotError(status_DAQ)){
		printf("Error in read FIFO thread %d!\n", *status);
	}
	else {
		printf("LEaving read FIFO thread status %d, daqgo %d, timout %d\n", *status, (int) daq_go, (int) TimedOut);
	}

}

/**
 * This function get's called from run_FPGA_tasks to record data. It
 * sits in this function until recording stops. It's been launched in
 * a separate thread, so is OK.
 */
void FPGADaqSystem::record_DAQ()
{
	daq_go=true;


//	int thread_var1 = 0, thread_var2 = 0;

	/**Create thread to read FIFO data*/
	if(pthread_create(&read_FIFO_thread, NULL, FIFO_thread_function, this)){
		fprintf(stderr, "Error creating thread to read data from FIFO\n");
		return;
	}
	printf("FIFO thread has initialised...\n");


	/*Wait for both threads to finish before closing up*/
	pthread_join(read_FIFO_thread, NULL);

}

/*
 * Get the current daq error status_FPGA.
 */
NiFpga_Status FPGADaqSystem::get_Status_DAQ(){
	return status_DAQ;
}

bool FPGADaqSystem::stopSystem() {

	/*Merge errors from DAQ read functions and FGPA manager*/
	NiFpga_MergeStatus(&status_FPGA, get_Status_DAQ());

	/*If an error has occured during reading writing then print error*/
	if (!NiFpga_IsNotError(status_FPGA)){
		printf("FPGA Manager: Error during DAQ record %d!\n", status_FPGA);
		errorCount_FPGA++;
	}

	/*Close the FPGA*/
	close_FPGA();

	/*Check for any errors whilst closing the FPGA*/
	if (!NiFpga_IsNotError(status_FPGA)){
		printf("FPGA Manager: Error on FPGA close %d!\n", status_FPGA);
		errorCount_FPGA++;
	}

	usleep(500000); //sleep for half a second
	return true;
}


/**
 * Close the FPGA.
 * @return error output. See NI for code to string.
 */
NiFpga_Status FPGADaqSystem::close_FPGA()
{

	/* unreserve IRQ status to prevent memory leaks */
	NiFpga_MergeStatus(&status_FPGA, NiFpga_UnreserveIrqContext(session_FPGA, irqContext_FPGA));

	/* Close the session */
	NiFpga_MergeStatus(&status_FPGA, NiFpga_Close(session_FPGA, 0));

	/* Finalise must be called before exiting program and after closing FPGA session */
	NiFpga_MergeStatus(&status_FPGA, NiFpga_Finalize());

	printf("FPGA Manager: FPGA closed\n");

	return status_FPGA;
}

int FPGADaqSystem::getStatus() {
	if (NiFpga_IsNotError(status_DAQ) == false) {
		return DAQ_STATUS_ERROR;
	}
	return (daq_go ? DAQ_STATUS_RUNNING : DAQ_STATUS_IDLE);
}

int FPGADaqSystem::getErrorCount() {
	return errorCount_FPGA;
}

void FPGADaqSystem::resetErrorCount() {
	errorCount_FPGA = 0;
}

/*
 * Get the current FPGA error status_FPGA.
 */
NiFpga_Status FPGADaqSystem::get_Status_FPGA(){
	return status_FPGA;
}

/*
 * Set the current FPGA error status_FPGA.
 */
void FPGADaqSystem::set_Status_FPGA(NiFpga_Status status){
	status_FPGA=status;
}

/*
 * Get the current FPGA session.
 */
NiFpga_Session FPGADaqSystem::get_Session_FPGA(){
	return session_FPGA;
}

/*
 * Get the current irq context.
 */
NiFpga_IrqContext FPGADaqSystem::get_IrqContext_FPGA(){
	return irqContext_FPGA;
}