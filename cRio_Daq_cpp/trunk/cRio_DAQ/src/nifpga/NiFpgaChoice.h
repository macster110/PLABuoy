/*
 * NiFpgaChoice.h
 *
 *  Created on: 2 Mar 2015
 *      Author: doug
 */

/*
 * This file is a wrapper around the NI constants which are needed to communicate with the various different
 * lvbitx files. It seems that not only will we need a different lvbitx for the 8 and 12 channel versions
 * but also for the 9067 and 9068 chassis. Fortunately, other code, such as NiFpga.c and NiFpga.h is common
 * to both systems. In future releases, we may be able to work out dynamically what system it is we're running
 * and change these constants into variables which will save having to compile different versions for different
 * chassis.
 */
#ifndef NIFPGACHOICE_H_
#define NIFPGACHOICE_H_

#define CRIO9067

#ifdef CRIO9067
#include "NiFpga_Crio9067_8chan.h"
#define FPGABITFILE NiFpga_Crio9067_8chan_Bitfile
#define FPGABITFILESIGNATURE NiFpga_Crio9067_8chan_Signature
#define NiFpga_IndicatorBool_Overwrite NiFpga_Crio9067_8chan_IndicatorBool_Overwrite
#define NiFpga_IndicatorBool_SampleGated NiFpga_Crio9067_8chan_IndicatorBool_SampleGated
#define NiFpga_IndicatorBool_Stopped NiFpga_Crio9067_8chan_IndicatorBool_Stopped
#define NiFpga_IndicatorBool_TimedOut NiFpga_Crio9067_8chan_IndicatorBool_TimedOut

#define NiFpga_IndicatorI16_ChassisTemperature NiFpga_Crio9067_8chan_IndicatorI16_ChassisTemperature
#define NiFpga_IndicatorI32_nChannels NiFpga_Crio9067_8chan_IndicatorI32_nChannels
#define NiFpga_ControlBool_Boolean NiFpga_Crio9067_8chan_ControlBool_Boolean
#define NiFpga_ControlBool_SystemReset NiFpga_Crio9067_8chan_ControlBool_SystemReset
#define NiFpga_ControlBool_USERFPGALED NiFpga_Crio9067_8chan_ControlBool_USERFPGALED
#define NiFpga_ControlBool_UserFpgaLed NiFpga_Crio9067_8chan_ControlBool_UserFpgaLed
#define NiFpga_ControlU32_SamplePerioduSec NiFpga_Crio9067_8chan_ControlU32_SamplePerioduSec
#define NiFpga_TargetToHostFifoI16_FIFO NiFpga_Crio9067_8chan_TargetToHostFifoI16_FIFO
/**Some extra error codes- note these override other errors i.e. designated more serious by error merge function. */
/**
 * There has been an overflow error in the a read/write buffer.
 */
static const NiFpga_Status NiFpga_Status_Read_Buffer_Overflow=-63199;

/**
 * Error in writing to external storage.
 */
static const NiFpga_Status NiFpga_Status_External_Storage=-64001;
#endif

#ifdef CRIO9068
#include "NiFpga_Crio9068_8chan.h"
#define FPGABITFILE NiFpga_Crio9068_8chan_Bitfile
#define FPGABITFILESIGNATURE NiFpga_Crio9068_8chan_Signature
#define NiFpga_IndicatorBool_Overwrite NiFpga_Crio9068_8chan_IndicatorBool_Overwrite
#define NiFpga_IndicatorBool_SampleGated NiFpga_Crio9068_8chan_IndicatorBool_SampleGated
#define NiFpga_IndicatorBool_Stopped NiFpga_Crio9068_8chan_IndicatorBool_Stopped
#define NiFpga_IndicatorBool_TimedOut NiFpga_Crio9068_8chan_IndicatorBool_TimedOut

#define NiFpga_IndicatorI16_ChassisTemperature NiFpga_Crio9068_8chan_IndicatorI16_ChassisTemperature
#define NiFpga_IndicatorI32_nChannels NiFpga_Crio9068_8chan_IndicatorI32_nChannels
#define NiFpga_ControlBool_Boolean NiFpga_Crio9068_8chan_ControlBool_Boolean
#define NiFpga_ControlBool_SystemReset NiFpga_Crio9068_8chan_ControlBool_SystemReset
#define NiFpga_ControlBool_USERFPGALED NiFpga_Crio9068_8chan_ControlBool_USERFPGALED
#define NiFpga_ControlBool_UserFpgaLed NiFpga_Crio9068_8chan_ControlBool_UserFpgaLed
#define NiFpga_ControlU32_SamplePerioduSec NiFpga_Crio9068_8chan_ControlU32_SamplePerioduSec
#define NiFpga_TargetToHostFifoI16_FIFO NiFpga_Crio9068_8chan_TargetToHostFifoI16_FIFO
/**Some extra error codes- note these override other errors i.e. designated more serious by error merge function. */
/**
 * There has been an overflow error in the a read/write buffer.
 */
static const NiFpga_Status NiFpga_Status_Read_Buffer_Overflow=-63199;

/**
 * Error in writing to external storage.
 */
static const NiFpga_Status NiFpga_Status_External_Storage=-64001;
#endif





#endif /* NIFPGACHOICE_H_ */
