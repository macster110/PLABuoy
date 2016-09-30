/*
 * FPGADaqSystem.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef FPGADAQSYSTEM_H_
#define FPGADAQSYSTEM_H_

#include "DAQSystem.h"
#include "../nifpga/NiFpga.h"
#include "../mythread.h"

#include "../mythread.h"

class FPGADaqSystem: public DAQSystem {
public:
	FPGADaqSystem();

	virtual ~FPGADaqSystem();

	bool prepareSystem();

	bool startSystem();

	bool stopSystem();

	int getStatus();

	int getErrorCount();

	void resetErrorCount();

	void run_FPGA_tasks();

	void read_FIFO_threadFunction();

	NiFpga_Status prepare_FPGA();

	void read_FIFO_Data(NiFpga_Session session, NiFpga_Status *status, NiFpga_IrqContext *irqContext);

	void record_DAQ();

	float getTemp();

	NiFpga_Status close_FPGA();

	NiFpga_Status get_Status_DAQ();

	NiFpga_Status get_Status_FPGA();
	void set_Status_FPGA(NiFpga_Status status);
	NiFpga_Session get_Session_FPGA();
	NiFpga_IrqContext get_IrqContext_FPGA();
private:
	/* this variable is our reference to the second thread */
	THREADID read_FIFO_thread;/*The status of the DAQ portion of the FPGA*/
	THREADHANDLE read_fifo_thread_handle;
	/*thread on which FPGA tasks run*/
	THREADID fpga_task_thread;
	THREADHANDLE fpga_task_thread_handle;

	NiFpga_Status status_DAQ;

	/*Counts the errors*/
	int volatile errorCount_FPGA;

	/*Boolean to indicate whether FPGA should be running*/
	bool volatile FPGA_Go;

	/*Defines the session*/
	NiFpga_Session session_FPGA;
	NiFpga_IrqContext irqContext_FPGA;
	/*Holds any error flagged up*/
	NiFpga_Status  status_FPGA;

};

#endif /* FPGADAQSYSTEM_H_ */
