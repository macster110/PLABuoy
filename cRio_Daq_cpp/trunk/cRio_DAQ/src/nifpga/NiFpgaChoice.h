/*
 * NiFpgaChoice.h
 *
 *  Created on: 2 Mar 2015
 *      Author: doug
 */

#ifndef NIFPGACHOICE_H_
#define NIFPGACHOICE_H_

#define CRIO9067
#ifdef CRIO9067
#include "NiFpga_Crio9067_8Chan.h"
#define FPGABITFILE NiFpga_Crio9067_8Chan_Bitfile
#define FPGABITFILESIGNATURE NiFpga_Crio9067_8Chan_Signature
#define NiFpga_IndicatorBool_Overwrite NiFpga_Crio9067_8Chan_IndicatorBool_Overwrite
#define NiFpga_IndicatorBool_SampleGated NiFpga_Crio9067_8Chan_IndicatorBool_SampleGated
#define NiFpga_IndicatorBool_Stopped NiFpga_Crio9067_8Chan_IndicatorBool_Stopped
#define NiFpga_IndicatorBool_TimedOut NiFpga_Crio9067_8Chan_IndicatorBool_TimedOut

#define NiFpga_IndicatorI16_ChassisTemperature NiFpga_Crio9067_8Chan_IndicatorI16_ChassisTemperature
#define NiFpga_IndicatorI32_nChannels NiFpga_Crio9067_8Chan_IndicatorI32_nChannels
#define NiFpga_ControlBool_Boolean NiFpga_Crio9067_8Chan_ControlBool_Boolean
#define NiFpga_ControlBool_SystemReset NiFpga_Crio9067_8Chan_ControlBool_SystemReset
#define NiFpga_ControlBool_USERFPGALED NiFpga_Crio9067_8Chan_ControlBool_USERFPGALED
#define NiFpga_ControlBool_UserFpgaLed NiFpga_Crio9067_8Chan_ControlBool_UserFpgaLed
#define NiFpga_ControlU32_SamplePerioduSec NiFpga_Crio9067_8Chan_ControlU32_SamplePerioduSec
#define NiFpga_TargetToHostFifoI16_FIFO NiFpga_Crio9067_8Chan_TargetToHostFifoI16_FIFO
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
#include "NiFpga_NI_9222_Anologue_DAQ2_FPGA.h"
#endif





#endif /* NIFPGACHOICE_H_ */
