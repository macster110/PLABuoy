/*
 * SerialReadProcess.h
 *
 *  Created on: 7 Apr 2015
 *      Author: Jamie Macaulay
 */

#ifndef SERIALREADPROCESS_H_
#define SERIALREADPROCESS_H_

#include "processdata.h"
#include "../mythread.h"
#include <iostream>


class SerialReadProcess : public PLAProcess {
public:
	SerialReadProcess();

	~SerialReadProcess();

	int initProcess(int nChan, int sampleRate);

	int recordSerialThread();

	int process(PLABuff* plaBuffer);

	std::string getSummaryData();

	int getErrorStatus();

	void endProcess();

	void setSummaryString(std::string time);

private:

	THREADID serialReadThread;

	THREADHANDLE serialReadThreadHandle;

};

#endif /* SERIALREADPROCESS_H_ */
