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


class SerialReadProcess : public PLAProcess {
public:
	SerialReadProcess();

	~SerialReadProcess();

	int initProcess(int nChan, int sampleRate);

	int recordSerialThread();

	int process(PLABuff* plaBuffer);

	void endProcess();

private:

	THREADID serialReadThread;

	THREADHANDLE serialReadThreadHandle;

};

#endif /* SERIALREADPROCESS_H_ */
