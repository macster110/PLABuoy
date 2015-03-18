/*
 * Utils.cpp
 *
 * Some useful functions.
 *
 *  Created on: 23 Jun 2014
 *   Author: Jamie Macaulay
 */

#include "Utils.h"
#include <string>
#include <sys/time.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <ctime>
#include <string.h>
#include <iostream>
#include <stdlib.h>
#include <errno.h>
#include <sys/stat.h>
#include <unistd.h>

using namespace std;

const string wav_location="/U";

#define MAX(a,b) (a>b?a:b)
/*
 * Get current date/time, format is YYYYMMDD_HHmmss. This can be read by PAMGUARD.
 * @return the current data and time as a string.
 *
 *
 */

string folderString() {
	timeval tv;
	gettimeofday(&tv, 0);

	struct tm  *tm;
	tm = localtime(&tv.tv_sec);
	char fmt[80];
	//    // Visit http://en.cppreference.com/w/cpp/chrono/c/strftime
	//    // for more information about date/time format
	strftime(fmt, sizeof fmt, "%Y%m%d_%H", tm);
	return fmt;
}

string currentDateTime(){
	timeval tv;
	gettimeofday(&tv, 0);
	return currentDateTime(tv, "%Y%m%d_%H%M%S_%%06u");
}

string currentDateTime(timeval timeStamp){
	return currentDateTime(timeStamp, "%Y%m%d_%H%M%S_%%06u");
}

string currentDateTime(timeval tv, const char* format)
{
	struct tm  *tm;
	tm = localtime(&tv.tv_sec);

	char fmt[80], buf[80];
	//    // Visit http://en.cppreference.com/w/cpp/chrono/c/strftime
	//    // for more information about date/time format
	strftime(fmt, sizeof fmt, format, tm);
	snprintf(buf, sizeof buf, fmt, tv.tv_usec);

	return buf;
}



void set_user_LED_status(int ledstatus){

	const char* LED_ON = "255\n";
	const char* LED_OFF = "0\n";

	bool on=false;

	FILE *LED = NULL;

	/**Select led to switch on/off*/
	switch (ledstatus){
	case LED_USER1_GREEN:
		//		printf("Switch USER1 LED green \n");
		LED = fopen("/sys/class/leds/nizynqcpld:user1:green/brightness","w");
		on=true;
		break;
	case LED_USER1_YELLOW:
		//		printf("Switch USER1 LED yellow \n");
		LED = fopen("/sys/class/leds/nizynqcpld:user1:yellow/brightness","w");
		on=true;
		break;
	case LED_STATUS_YELLOW:
		//		printf("Switch Status LED yellow \n");
		LED = fopen("/sys/class/leds/nizynqcpld:status:yellow/brightness","w");
		on=true;
		break;
	case LED_STATUS_RED:
		//		printf("Switch Status LED red \n");
		LED = fopen("/sys/class/leds/nizynqcpld:status:red/brightness","w");
		on=true;
		break;
	}

	if (on){
		fwrite(LED_ON,sizeof(char),4,LED);
		fflush(LED);
		fclose(LED);
	}

	else{
		switch (ledstatus){
		case LED_USER1_OFF:
			//			printf("Switch USER1 LED off \n");
			LED = fopen("/sys/class/leds/nizynqcpld:user1:green/brightness","w");
			fwrite(LED_OFF,sizeof(char),2,LED);
			fflush(LED);
			fclose(LED);
			LED = fopen("/sys/class/leds/nizynqcpld:user1:yellow/brightness","w");
			fwrite(LED_OFF,sizeof(char),2,LED);
			fflush(LED);
			fclose(LED);
			on=true;
			break;
		case LED_STATUS_OFF:
			//			printf("Switch STATUS LED off \n");
			LED = fopen("/sys/class/leds/nizynqcpld:status:red/brightness","w");
			fwrite(LED_OFF,sizeof(char),2,LED);
			fflush(LED);
			fclose(LED);
			LED = fopen("/sys/class/leds/nizynqcpld:status:yellow/brightness","w");
			fwrite(LED_OFF,sizeof(char),2,LED);
			fflush(LED);
			fclose(LED);
			on=true;
			break;
		}
	}


}

std::string createFileName(const char* prefix, const char* filetype, timeval timeStamp) {
	string desiredFolder = wav_location + "/" + folderString();
//	printf("out folder = %s\n", desiredFolder.c_str());
	if (!checkFolder(desiredFolder.c_str())) {
		printf("Error - folder %s doens't exist and cannot be created\n", desiredFolder.c_str());
	}
//	string dateTime=currentDateTime();
	string dateTime = currentDateTime(timeStamp);
	/*Create a new out file name based on the system time*/
	string outfilename=(desiredFolder+"/"+prefix+"_"+dateTime+filetype);
//	}
	return outfilename;
}

bool checkFolder(const char* folderName) {
	if (chdir(folderName) == 0) {
		return true;
	}
	if (mkpath(folderName) == -1) {
		return false;;
	}
	return (chdir(folderName) == 0);

}
// http://stackoverflow.com/questions/675039/how-can-i-create-directory-tree-in-c-linux
bool mkpath( std::string path )
{
	bool bSuccess = false;
#ifdef WINDOWS
	int nRC = ::mkdir( path.c_str());
#else
	int nRC = ::mkdir( path.c_str(), 0777 );
#endif
	int lastSep, lastSepL;
	if( nRC == -1 )
	{
		switch( errno )
		{
		case ENOENT:
			//parent didn't exist, try to create it
			//			lastSep = MAX(path.find_last_of('/'),path.find_last_of('\\'));
			lastSep = path.find_last_of('\\');
			lastSepL = path.find_last_of('/');
			lastSep = MAX(lastSep, lastSepL);
			bSuccess = false;
			if (lastSep > 0 && lastSep < path.length()) {
				if( mkpath( path.substr(0, lastSep) ) ) {
					//Now, try to create again.
#ifdef WINDOWS
					bSuccess = 0 == ::mkdir( path.c_str());
#else
					bSuccess = 0 == ::mkdir( path.c_str(), 0777 );
#endif
				}
			}

			break;
		case EEXIST:
			//Done!
			bSuccess = true;
			break;
		default:
			bSuccess = false;
			break;
		}
	}
	else {
		bSuccess = true;
	}
	return bSuccess;
}

/**
 * Add a number of microseconds to a timeval.
 */
timeval addMicroseconds(timeval oldTime, uint64_t microseconds) {
	timeval newVal = oldTime;
	int32_t newSecs = microseconds / 1000000;
	int32_t newMics = microseconds % 1000000;
	newVal.tv_sec += newSecs;
	newVal.tv_usec += newMics;
	while (newVal.tv_usec >= 1000000) {
		newVal.tv_sec += 1;
		newVal.tv_usec -= 1000000;
	}
	return newVal;
}
