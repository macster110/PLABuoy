/*
 * UtilsNiFpga.cpp
 *
 *  Created on: 7 Apr 2015
 *      Author: Jamie Macaulay
 */

#include "UtilsNiFpga.h"

/* Required for console interactions */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*Needed for memcpy*/
#include <cstring>
/* Includes all FPGA Interface C API functions required */
#include "../nifpga/NiFpgaChoice.h"
#include "../nifpga/NiFpga.h"
/*Useful functions including switching on and off LEDs*/
#include "../Utils.h"
/* general constants, to be replaced later by control structures */
#include "../Settings.h"
#include "../process/processdata.h"

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
			NiFpga_IndicatorI16_ChassisTemperature,
											 &RawTemperature));
	//To convert temperature returned from the FPGA to Celsius, divide by 4
	Temperature = RawTemperature;
	Temperature /= 4;
	printf("FPGA Manager: Measured Internal Chassis Temperature is %.1f Celcius\n",Temperature);
}
