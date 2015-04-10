/*
 * UtilsNiFpga.h
 *	Contains functions which might be useful but require access to the FPGA in order to aquire data.
 *  Created on: 7 Apr 2015
 *  Author: Jamie Macaulay
 */

#ifndef UTILSNIFPGA_H_
#define UTILSNIFPGA_H_

/* Includes all FPGA Interface C API functions required */
#include "../nifpga/NiFpgaChoice.h"
#include "../nifpga/NiFpga.h"

/*
 * Functions interfaces with the FPGA to read the current chassis temperature through a read/write control.
 * This function utilizes basic FPGA IO Reads
 * @param[in]		session		Uses FPGA session reference
 * @param[in,out]	status		Uses and updates error status
*/
void ChassisTemperature(NiFpga_Session session, NiFpga_Status *status);

#endif /* UTILSNIFPGA_H_ */
