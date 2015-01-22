/*
 * Utils.h
 * Some useful functions for Linux Realtime.
 *  Created on: 23 Jun 2014
 *  Author: Jamie Macaulay
 */

#define UTILS_H_

#include <string>

/*LED status*/
/*USER1 LED*/
const int LED_USER1_GREEN=0;
const int LED_USER1_YELLOW=1;
const int LED_USER1_OFF=2;
const int LED_STATUS_YELLOW=3;
const int LED_STATUS_RED=4;
const int LED_STATUS_OFF=5;

/**
 * Get a name for a folder to put files in, which is based on
 * the date and hour
 */

std::string folderString();

/**
 * Get the string of the current data and time based on current system time.
 * @return current data and time in string format
 */
std::string currentDateTime();

/**
 * Get the string of the current data and time based on current system time.
 * @ param format. Data format. e.g "%Y%m%d_%H%M%S.%%02u" for output 20140623_145859.551128 yyyymmdd_HHMMSS.SSSS
 * @return current data and time in string format
 */
std::string currentDateTime(const char* format);

std::string createFileName(const char* prefix, const char* filetype);

bool checkFolder(const char* folderPath);

bool mkpath( std::string path );

/**
 * Set the STATUS orUSER1 LED on the cRio.
 * @param ledstatus - colour of the LED or LED off. See LED_USER1_GREEN,LED_USER1_YELLOW,LED_USER1_OFF;
 */
void set_user_LED_status(int ledstatus);







