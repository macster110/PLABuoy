/*
 * RealTimer.cpp
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#include "RealTimer.h"

//#include "Winsock2.h"


RealTimer::RealTimer() {
#ifdef WINDOWS
	timeResolution = CLOCKS_PER_SEC;
#else
	struct timespec timeRes;
//	clock_getres(CLOCK_REALTIME, &timeRes);
	timeResolution = (float) timeRes.tv_sec + (float) timeRes.tv_nsec/1.0e9;
#endif
}

RealTimer::~RealTimer() {
	// TODO Auto-generated destructor stub
}


void RealTimer::start() {
#ifdef WINDOWS
	winTickCount = clock();
#else
	gettimeofday(&startTime, 0);
#endif
}
double RealTimer::stop() {
#ifdef WINDOWS
	return ((double) (clock() - winTickCount)) / timeResolution;
#else
	timeval endTime;
	gettimeofday(&endTime, 0);
	return (double) (endTime.tv_sec-startTime.tv_sec) + (double) (endTime.tv_usec-startTime.tv_usec)/1.0e6;
#endif
}
float RealTimer::getResolution() {
	return timeResolution;
}
