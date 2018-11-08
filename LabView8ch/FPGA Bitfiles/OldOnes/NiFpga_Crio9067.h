/*
 * Generated with the FPGA Interface C API Generator 14.0.0
 * for NI-RIO 14.0.0 or later.
 */

#ifndef __NiFpga_Crio9067_h__
#define __NiFpga_Crio9067_h__

#ifndef NiFpga_Version
   #define NiFpga_Version 1400
#endif

#include "NiFpga.h"

/**
 * The filename of the FPGA bitfile.
 *
 * This is a #define to allow for string literal concatenation. For example:
 *
 *    static const char* const Bitfile = "C:\\" NiFpga_Crio9067_Bitfile;
 */
#define NiFpga_Crio9067_Bitfile "NiFpga_Crio9067.lvbitx"

/**
 * The signature of the FPGA bitfile.
 */
static const char* const NiFpga_Crio9067_Signature = "44C8C833387650A199C4428315D2DE89";

typedef enum
{
   NiFpga_Crio9067_IndicatorBool_Overwrite = 0x18022,
   NiFpga_Crio9067_IndicatorBool_SampleGated = 0x18026,
   NiFpga_Crio9067_IndicatorBool_Stopped = 0x18002,
   NiFpga_Crio9067_IndicatorBool_TimedOut = 0x1801E,
} NiFpga_Crio9067_IndicatorBool;

typedef enum
{
   NiFpga_Crio9067_IndicatorI16_ChassisTemperature = 0x18012,
} NiFpga_Crio9067_IndicatorI16;

typedef enum
{
   NiFpga_Crio9067_IndicatorI32_nChannels = 0x18028,
} NiFpga_Crio9067_IndicatorI32;

typedef enum
{
   NiFpga_Crio9067_ControlBool_Boolean = 0x1800A,
   NiFpga_Crio9067_ControlBool_SystemReset = 0x18016,
   NiFpga_Crio9067_ControlBool_USERFPGALED = 0x18006,
   NiFpga_Crio9067_ControlBool_UserFpgaLed = 0x1800E,
} NiFpga_Crio9067_ControlBool;

typedef enum
{
   NiFpga_Crio9067_ControlU32_SamplePerioduSec = 0x18018,
} NiFpga_Crio9067_ControlU32;

typedef enum
{
   NiFpga_Crio9067_TargetToHostFifoI16_FIFO = 0,
} NiFpga_Crio9067_TargetToHostFifoI16;

#endif
