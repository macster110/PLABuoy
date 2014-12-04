/*
 * Generated with the FPGA Interface C API Generator 13.0.0
 * for NI-RIO 13.0.0 or later.
 */

#ifndef __NiFpga_NI_9222_Anologue_DAQ2_FPGA_h__
#define __NiFpga_NI_9222_Anologue_DAQ2_FPGA_h__

#ifndef NiFpga_Version
   #define NiFpga_Version 1300
#endif

#include "NiFpga.h"

/**
 * The filename of the FPGA bitfile.
 *
 * This is a #define to allow for string literal concatenation. For example:
 *
 * static const char* const Bitfile = "C:\\" NiFpga_NI_9222_Anologue_DAQ2_FPGA_Bitfile;
 */

/**Some extra error codes- note these override other errors i.e. designated more serious by error merge function. */
/**
 * There has been an overflow error in the a read/write buffer.
 */
static const NiFpga_Status NiFpga_Status_Read_Buffer_Overflow=-63199;

/**
 * Error in writing to external storage.
 */
static const NiFpga_Status NiFpga_Status_External_Storage=-64001;


/*#define NiFpga_NI_9222_Anologue_DAQ2_FPGA_Bitfile "NiFpga_NI_9222_Anologue_DAQ2_FPGA.lvbitx"*/
 static const char* const NiFpga_NI_9222_Anologue_DAQ2_FPGA_Bitfile = "/home/admin/cRioKE/NiFpga_NI_9222_Anologue_DAQ2_FPGA.lvbitx";

 /**
  * The signature of the FPGA bitfile.
  */
 static const char* const NiFpga_NI_9222_Anologue_DAQ2_FPGA_Signature = "26EEA713CF59C4CC5A131643BD62023B";

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorBool_Overwrite = 0x1801E,
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorBool_SampleGated = 0x18022,
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorBool_Stopped = 0x18002,
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorBool_TimedOut = 0x1801A,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorBool;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI16_ChassisTemperature = 0x1800A,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI16;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI32_nChannels = 0x18010,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_IndicatorI32;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlBool_SystemReset = 0x1800E,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlBool;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU8_UserFpgaLed = 0x18006,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU8;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU32_SamplePerioduSec = 0x18014,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlU32;

 typedef enum
 {
    NiFpga_NI_9222_Anologue_DAQ2_FPGA_TargetToHostFifoI16_FIFO = 0,
 } NiFpga_NI_9222_Anologue_DAQ2_FPGA_TargetToHostFifoI16;

 #endif
