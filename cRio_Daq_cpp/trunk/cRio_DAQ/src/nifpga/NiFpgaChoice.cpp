/*
 * NiFpgaChoice.cpp
 *
 *  Created on: 15 Feb 2016
 *      Author: dg50
 */

#include "NiFpgaChoice.h"
#include "NiFpga_Crio9067_8chan.h"
#include "NiFpga_Crio9068_8chan.h"

#include <stdio.h>


FpgaChoice* fpgaChoice = 0;

int currentChassis = 9067;
int currentNChan = DEFAULTNCHANNELS;

int getCurrentChassis() {
	return currentChassis;
}

int getCurrentNChan() {
	return currentNChan;
}


FpgaChoice::FpgaChoice() {

	bitFileName = NULL;
	bitFileSignature = NULL;
	NiFpga_IndicatorBool_Overwrite = 0;
	NiFpga_IndicatorBool_SampleGated = 0;
	NiFpga_IndicatorBool_Stopped = 0;
	NiFpga_IndicatorBool_TimedOut = 0;

	NiFpga_IndicatorI16_ChassisTemperature = 0;
	NiFpga_IndicatorI32_nChannels = 0;
	NiFpga_ControlBool_Boolean = 0;
	NiFpga_ControlBool_SystemReset = 0;
	NiFpga_ControlBool_USERFPGALED = 0;
	NiFpga_ControlBool_UserFpgaLed = 0;
	NiFpga_ControlU32_SamplePerioduSec = 0;
	NiFpga_TargetToHostFifoI16_FIFO = 0;

}
FpgaChoice::~FpgaChoice() {

}

FpgaChoice* getFPGAChoice(int chassis, int nChan) {
	currentChassis = chassis;
	currentNChan = nChan;
	if (fpgaChoice) {
		delete(fpgaChoice);
		fpgaChoice = NULL;
	}
	if (chassis == 9067) {
		if (nChan == 8) {
			fpgaChoice = new Fpga9067_8chan();
		}
		else if (nChan == 12) {
			fpgaChoice = new Fpga9067_12chan();
		}
	}
	else if (chassis == 9068) {
		fpgaChoice = new Fpga9068_8chan();
	}
	if (fpgaChoice == NULL) {
		printf("*****************************************************************************************");
		printf("********** No FPGA control available for %d chassis with %d data channels *********");
		printf("*****************************************************************************************");
		fflush(stdout);
	}
}

Fpga9067_8chan::Fpga9067_8chan() : FpgaChoice() {
	bitFileName = (char*) NiFpga_Crio9067_8chan_Bitfile;
	bitFileSignature = (char*) NiFpga_Crio9067_8chan_Signature;
	NiFpga_IndicatorBool_Overwrite = NiFpga_Crio9067_8chan_IndicatorBool_Overwrite;
	NiFpga_IndicatorBool_SampleGated = NiFpga_Crio9067_8chan_IndicatorBool_SampleGated;
	NiFpga_IndicatorBool_Stopped = NiFpga_Crio9067_8chan_IndicatorBool_Stopped;
	NiFpga_IndicatorBool_TimedOut = NiFpga_Crio9067_8chan_IndicatorBool_TimedOut;

	NiFpga_IndicatorI16_ChassisTemperature = NiFpga_Crio9067_8chan_IndicatorI16_ChassisTemperature;
	NiFpga_IndicatorI32_nChannels = NiFpga_Crio9067_8chan_IndicatorI32_nChannels;
	NiFpga_ControlBool_Boolean = NiFpga_Crio9067_8chan_ControlBool_Boolean;
	NiFpga_ControlBool_SystemReset = NiFpga_Crio9067_8chan_ControlBool_SystemReset;
	NiFpga_ControlBool_USERFPGALED = NiFpga_Crio9067_8chan_ControlBool_USERFPGALED;
	NiFpga_ControlBool_UserFpgaLed = NiFpga_Crio9067_8chan_ControlBool_UserFpgaLed;
	NiFpga_ControlU32_SamplePerioduSec = NiFpga_Crio9067_8chan_ControlU32_SamplePerioduSec;
	NiFpga_TargetToHostFifoI16_FIFO = NiFpga_Crio9067_8chan_TargetToHostFifoI16_FIFO;

}
Fpga9067_12chan::Fpga9067_12chan() : FpgaChoice() {

}

Fpga9068_8chan::Fpga9068_8chan() : FpgaChoice() {

	bitFileName = (char*) NiFpga_Crio9068_8chan_Bitfile;
	bitFileSignature = (char*) NiFpga_Crio9068_8chan_Signature;
	NiFpga_IndicatorBool_Overwrite = NiFpga_Crio9068_8chan_IndicatorBool_Overwrite;
	NiFpga_IndicatorBool_SampleGated = NiFpga_Crio9068_8chan_IndicatorBool_SampleGated;
	NiFpga_IndicatorBool_Stopped = NiFpga_Crio9068_8chan_IndicatorBool_Stopped;
	NiFpga_IndicatorBool_TimedOut = NiFpga_Crio9068_8chan_IndicatorBool_TimedOut;

	NiFpga_IndicatorI16_ChassisTemperature = NiFpga_Crio9068_8chan_IndicatorI16_ChassisTemperature;
	NiFpga_IndicatorI32_nChannels = NiFpga_Crio9068_8chan_IndicatorI32_nChannels;
	//	NiFpga_ControlBool_Boolean = NiFpga_Crio9068_8chan_ControlBool_Boolean;
	NiFpga_ControlBool_SystemReset = NiFpga_Crio9068_8chan_ControlBool_SystemReset;
	//	NiFpga_ControlBool_USERFPGALED = NiFpga_Crio9068_8chan_ControlBool_USERFPGALED;
	//	NiFpga_ControlBool_UserFpgaLed = NiFpga_Crio9068_8chan_ControlBool_UserFpgaLed;
	NiFpga_ControlU32_SamplePerioduSec = NiFpga_Crio9068_8chan_ControlU32_SamplePerioduSec;
	NiFpga_TargetToHostFifoI16_FIFO = NiFpga_Crio9068_8chan_TargetToHostFifoI16_FIFO;


}



