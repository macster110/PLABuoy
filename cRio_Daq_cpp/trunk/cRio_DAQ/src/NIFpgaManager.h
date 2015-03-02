/*
 * NIFpgaManager.h
 *
 * Manages thwe FPGA.
 *
 *  Created on: 21 Jul 2014
 *  Author: Jamie Macaulay
 */

#ifndef NIFPGAMANAGER_H_
#define NIFPGAMANAGER_H_
/* Includes all FPGA Interface C API functions required */
//#include "NiFpga_NI_9222_Anologue_DAQ2_FPGA.h"
#include "nifpga/NiFpgaChoice.h"
/*pthread library for running on different threads*/
#include <pthread.h>

/**
 * Check FPGA and prepare for reading
 */
NiFpga_Status prepare_FPGA();

/**
 * Close the FPGA.
 * @return error output. See NI for code to string.
 */
NiFpga_Status close_FPGA();



/**
 * Start FPGA task on a new thread;
 */
void FPGA_Tasks_Thread();

/**
 * Get the current thread the FPGA is running on.
 */
pthread_t get_FPGA_thread();
/**
 * Start the FPGA tasks. Assumes the FPGA is open and ready to go.
 */
void run_FPGA_tasks();

/*
 * Get the current FPGA error status_FPGA.
 */
NiFpga_Status get_Status_FPGA();

/*
 * Get the current FPGA seesion.
 */
NiFpga_Session get_Session_FPGA();

/*
 * Get the current irq context.
 */
NiFpga_IrqContext get_IrqContext_FPGA();

/**
 * Get the tempertaure of the chasis.
 * @param[in]		session		Uses FPGA session reference
 * @param[in,out]	status		Uses and updates error status
 */
void ChassisTemperature(NiFpga_Session session, NiFpga_Status *status);

/**
 * Get the number of errors recorded from FPGA
 */
int get_FPGA_Error_Count();

/**
 * Reset the errors to zero.
 */
void reset_FPGA_Error_Count();

/**
 * Set if the FPGA should be running or not. False will stop any currently running FPGA tasks.
 */
void set_FPGA_go(bool go);




#endif




