/*
 * RealTimer.h
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#ifndef REALTIMER_H_
#define REALTIMER_H_
#ifdef WINDOWS
#include "time.h"
#include "Winsock2.h"
#else
#include <sys/time.h>
#endif

/**
 * Simple timer that returns a time difference in
 * seconds (floating point).
 */
class RealTimer {
public:
	RealTimer();
	virtual ~RealTimer();
	void start();
	float stop();
	float getResolution();
private:
	struct timeval startTime;
	int winTickCount;
	float timeResolution;
};

#endif /* REALTIMER_H_ */
