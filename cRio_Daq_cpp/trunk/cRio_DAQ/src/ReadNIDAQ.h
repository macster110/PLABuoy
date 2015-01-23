/*
 * ReadNIDAQ.h
 *
 * I higher level class than NiFpga allowing NI 9222 card to be initilised, data read from FPGA through FIFO and
 * that data saved to file. Data should be read on one thread and saved on another.
 *
 * Created on: 17 Jun 2014
 * Author: Jamie Macaulay
 *
 */

#ifndef READNIDAQ_H_
#define READNIDAQ_H_
/* Includes all FPGA Interface C API functions required */
#include "NiFpgaManager.h"

using namespace std;

/**
 * The sample rate in micro-seconds.
 */
//const unsigned int Sample_Rate_us = 2;

/**
 * Create the buffer that will hold samples form the FPGA. The FIFO executes very very fast and hence
 * a buffer is needed to store FIFO data allowing time for a another thread to read, save and discard data from the buffer.
 */
void create_Buffer();


/**
 * Start the data acquisition system running and recording. This uses the prepare_FPGA(), read_FIFO_Data()
 * ,read_DATA_Buffer() and close_FPGA() functions and starts two separate threads to begin recordings.
 */
void record_DAQ();

/**
 * Set boolean "go" to true or false, quitting all recording loops.
 * @param go True for no change/stay recording or false to stop recording if recording has started.
 */
void set_DAQ_Go(bool go);


/**
 * Begin a loop to read the FIFO as quickly as possible, removing data and storing in a data buffer. As little as possible happens in this function;
 * @param[in]		session		Uses FPGA session reference
 * @param[in,out]	status		Uses and updates error status
 * @param[in]		irqContext	Uses irqContext for IRQ
 */
void read_FIFO_Data(NiFpga_Session session, NiFpga_Status *status, NiFpga_IrqContext *irqContext);

/**
 * Begin a loop to constantly acquire data form buffer and save to file.
 * @param[in] channels. The number of channels contained as interleaved samples in the data buffer.
 */
void read_Data_Buffer(int channels);

/**
 * Get the current status of the FPGA from reading DAQ cards.
 * @return NiFpga_Status error status specific to DAQ recordings.
 */
NiFpga_Status get_Status_DAQ();

/*
 * Start the readout thread for the main buffer read.
 */
bool startBufferRead();
/*
 * Stop the readout thread for the main buffer read.
 */
bool stopBufferRead();

#endif

