/*
 * RealTimer.cpp
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#include "RealTimer.h"


RealTimer::RealTimer() {
	timespec timeRes;
//	clock_getres(CLOCK_REALTIME, &timeRes);
	timeResolution = (float) timeRes.tv_sec + (float) timeRes.tv_nsec/1.0e9;
}

RealTimer::~RealTimer() {
	// TODO Auto-generated destructor stub
}


void RealTimer::start() {
//	clock_gettime(CLOCK_REALTIME, &startTime);
	gettimeofday(&startTime, 0);
}
float RealTimer::stop() {
	timeval endTime;
//	clock_gettime(CLOCK_REALTIME, &endTime);
	gettimeofday(&endTime, 0);
	return (float) (endTime.tv_sec-startTime.tv_sec) + (float) (endTime.tv_usec-startTime.tv_usec)/1.0e6;
}
float RealTimer::getResolution() {
	return timeResolution;
}
