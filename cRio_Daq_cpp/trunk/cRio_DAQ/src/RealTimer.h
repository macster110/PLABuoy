/*
 * RealTimer.h
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#ifndef REALTIMER_H_
#define REALTIMER_H_
#include <sys/time.h>

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
	timeval startTime;
	float timeResolution;
};

#endif /* REALTIMER_H_ */
