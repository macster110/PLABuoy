/*
 * Generated with the FPGA Interface C API Generator 14.0.0
 * for NI-RIO 14.0.0 or later.
 */

#ifndef __NiFpga_Crio9067_12Chan_h__
#define __NiFpga_Crio9067_12Chan_h__

#ifndef NiFpga_Version
   #define NiFpga_Version 1400
#endif

#include "NiFpga.h"

/**
 * The filename of the FPGA bitfile.
 *
 * This is a #define to allow for string literal concatenation. For example:
 *
 *    static const char* const Bitfile = "C:\\" NiFpga_Crio9067_12Chan_Bitfile;
 */
#define NiFpga_Crio9067_12Chan_Bitfile "NiFpga_Crio9067_12Chan.lvbitx"

/**
 * The signature of the FPGA bitfile.
 */
static const char* const NiFpga_Crio9067_12Chan_Signature = "963EE63821302E51B4B1DEBF71D47FDA";

typedef enum
{
   NiFpga_Crio9067_12Chan_IndicatorBool_Overwrite = 0x1801A,
   NiFpga_Crio9067_12Chan_IndicatorBool_SampleGated = 0x1801E,
   NiFpga_Crio9067_12Chan_IndicatorBool_Stopped = 0x18002,
   NiFpga_Crio9067_12Chan_IndicatorBool_TimedOut = 0x18016,
} NiFpga_Crio9067_12Chan_IndicatorBool;

typedef enum
{
   NiFpga_Crio9067_12Chan_IndicatorI16_ChassisTemperature = 0x1800A,
} NiFpga_Crio9067_12Chan_IndicatorI16;

typedef enum
{
   NiFpga_Crio9067_12Chan_IndicatorI32_nChannels = 0x18028,
} NiFpga_Crio9067_12Chan_IndicatorI32;

typedef enum
{
   NiFpga_Crio9067_12Chan_ControlBool_Boolean = 0x18026,
   NiFpga_Crio9067_12Chan_ControlBool_SystemReset = 0x1800E,
   NiFpga_Crio9067_12Chan_ControlBool_USERFPGALED = 0x18022,
   NiFpga_Crio9067_12Chan_ControlBool_UserFpgaLed = 0x18006,
} NiFpga_Crio9067_12Chan_ControlBool;

typedef enum
{
   NiFpga_Crio9067_12Chan_ControlU32_SamplePerioduSec = 0x18010,
} NiFpga_Crio9067_12Chan_ControlU32;

typedef enum
{
   NiFpga_Crio9067_12Chan_TargetToHostFifoI16_FIFO = 0,
} NiFpga_Crio9067_12Chan_TargetToHostFifoI16;

#endif
