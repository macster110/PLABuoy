/*
 * Generated with the FPGA Interface C API Generator 14.0.0
 * for NI-RIO 14.0.0 or later.
 */

#ifndef __NiFpga_CRio9063_4chan_9223_h__
#define __NiFpga_CRio9063_4chan_9223_h__

#ifndef NiFpga_Version
   #define NiFpga_Version 1400
#endif

#include "NiFpga.h"

/**
 * The filename of the FPGA bitfile.
 *
 * This is a #define to allow for string literal concatenation. For example:
 *
 *    static const char* const Bitfile = "C:\\" NiFpga_CRio9063_4chan_9223_Bitfile;
 */
#define NiFpga_CRio9063_4chan_9223_Bitfile "NiFpga_CRio9063_4chan_9223.lvbitx"

/**
 * The signature of the FPGA bitfile.
 */
static const char* const NiFpga_CRio9063_4chan_9223_Signature = "58BCC1C039B75FC429CC0B219A76FEA2";

typedef enum
{
   NiFpga_CRio9063_4chan_9223_IndicatorBool_Overwrite = 0x1801A,
   NiFpga_CRio9063_4chan_9223_IndicatorBool_SampleGated = 0x1801E,
   NiFpga_CRio9063_4chan_9223_IndicatorBool_Stopped = 0x18002,
   NiFpga_CRio9063_4chan_9223_IndicatorBool_TimedOut = 0x18016,
} NiFpga_CRio9063_4chan_9223_IndicatorBool;

typedef enum
{
   NiFpga_CRio9063_4chan_9223_IndicatorI16_ChassisTemperature = 0x1800A,
} NiFpga_CRio9063_4chan_9223_IndicatorI16;

typedef enum
{
   NiFpga_CRio9063_4chan_9223_IndicatorI32_nChannels = 0x18028,
} NiFpga_CRio9063_4chan_9223_IndicatorI32;

typedef enum
{
   NiFpga_CRio9063_4chan_9223_ControlBool_Boolean = 0x18026,
   NiFpga_CRio9063_4chan_9223_ControlBool_SystemReset = 0x1800E,
   NiFpga_CRio9063_4chan_9223_ControlBool_USERFPGALED = 0x18022,
   NiFpga_CRio9063_4chan_9223_ControlBool_UserFpgaLed = 0x18006,
} NiFpga_CRio9063_4chan_9223_ControlBool;

typedef enum
{
   NiFpga_CRio9063_4chan_9223_ControlU32_SamplePerioduSec = 0x18010,
} NiFpga_CRio9063_4chan_9223_ControlU32;

typedef enum
{
   NiFpga_CRio9063_4chan_9223_TargetToHostFifoI16_FIFO = 0,
} NiFpga_CRio9063_4chan_9223_TargetToHostFifoI16;

#endif
