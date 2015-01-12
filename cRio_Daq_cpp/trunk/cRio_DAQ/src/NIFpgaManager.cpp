/*
 * NIFpgaManager.cpp
 *
 *
 * The NIFpgaManager manages the FPGA. Functions include starting the FPGA, closing the FPGA, cat ches errors ande deals with
 * LEDs, system restarts etc.
 *
 *  Created on: 21 Jul 2014
 *   Author: Jamie Macaulay
 */


#include "NIFpgaManager.h"
/* Required for console interactions */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*pthread library for running on different threads*/
#include <pthread.h>
#include <unistd.h>
/*Needed for memcpy*/
#include <cstring>
/* Includes all FPGA Interface C API functions required */
#include "NiFpga_NI_9222_Anologue_DAQ2_FPGA.h"
/*Functions to read DAQ cards and write data*/
#include "ReadNIDAQ.h"
/*Useful functions including switching on and off LEDs*/
#include "Utils.h"
/* general constants, to be replaced later by control structures */
#include "Settings.h"

#include "process/processdata.h"

/*FPGA fields*/
/*Defines the session*/
NiFpga_Session session_FPGA;
NiFpga_IrqContext irqContext_FPGA;
/*Holds any error flagged up*/
NiFpga_Status  status_FPGA=-1;

/*Counts the errors*/
int volatile errorCount_FPGA=0;

/*Boolean to indicate whether FPGA should be running*/
bool volatile FPGA_Go=true;

/*thread on which FPGA tasks run*/
pthread_t fpga_task_thread;


/**
 * Check FPGA and prepare for reading
 * See NI for code to string.
 */
NiFpga_Status prepare_FPGA()
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
	printf("FPGA Manager: Opening a Session... %d\n",status_FPGA);
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
 * Entry function for pthread for user input
 */
void *FPGA_input_function(void *param)
{
	run_FPGA_tasks();
	return NULL;
}

/**
 * Start FPGA monitor on new thread.
 */
void FPGA_Tasks_Thread(){
	int thread_var1 = 0;
	if(pthread_create(&fpga_task_thread, NULL, FPGA_input_function, &thread_var1)){
			fprintf(stderr, "FPGA monitor: creating input thread\n");
			return;
	}
}


void run_FPGA_tasks()
{
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
		 * Start recording from DAQ card: Note that this function waits for recording to stop
		 * or an error to occur before exiting.
		 */
		record_DAQ();

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

		processEnd();

		usleep(500000); //sleep for half a second

	}
}

pthread_t get_FPGA_thread(){
	return fpga_task_thread;
}


/*
 * Functions interfaces with the FPGA to read the current chassis temperature through a read/write control.
 * This function utilizes basic FPGA IO Reads
 * @param[in]		session		Uses FPGA session reference
 * @param[in,out]	status		Uses and updates error status
*/
void ChassisTemperature(NiFpga_Session session, NiFpga_Status *status)
{
	float Temperature = 0;
	int16_t RawTemperature = 0;

	printf("Acquiring Chassis Temperature...\n");

	NiFpga_MergeStatus(status, NiFpga_ReadI16(session,
			NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI16_ChassisTemperature,
											 &RawTemperature));
	//To convert temperature returned from the FPGA to Celsius, divide by 4
	Temperature = RawTemperature;
	Temperature /= 4;
	printf("FPGA Manager: Measured Internal Chassis Temperature is %.1f Celsius\n",Temperature);
}

/**
 * Close the FPGA.
 * @return error output. See NI for code to string.
 */
NiFpga_Status close_FPGA()
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


void set_FPGA_go(bool go){
	FPGA_Go=go;
	set_DAQ_Go(go);
}

/*
 * Get the current FPGA error status_FPGA.
 */
NiFpga_Status get_Status_FPGA(){
	return status_FPGA;
}


/*
 * Set the current FPGA error status_FPGA.
 */
void set_Status_FPGA(NiFpga_Status status){
	status_FPGA=status;
}


/*
 * Get the current FPGA session.
 */
NiFpga_Session get_Session_FPGA(){
	return session_FPGA;
}


/*
 * Get the current irq context.
 */
NiFpga_IrqContext get_IrqContext_FPGA(){
	return irqContext_FPGA;
}

/**
 * Get the number of errors recorded from FPGA
 */
int get_FPGA_Error_Count(){
	return errorCount_FPGA;
}

/**
 * Reset the errors to zero.
 */
void reset_FPGA_Error_Count(){
	errorCount_FPGA=0;
}



