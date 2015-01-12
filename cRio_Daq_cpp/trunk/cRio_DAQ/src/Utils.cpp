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



using namespace std;

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
	return currentDateTime("%Y%m%d_%H%M%S.%%06u");
}

string currentDateTime(const char* format)
{

	 timeval tv;
	 gettimeofday(&tv, 0);

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

