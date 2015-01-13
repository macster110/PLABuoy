/*
 * CompressProcess.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef COMPRESSPROCESS_H_
#define COMPRESSPROCESS_H_

#include "processdata.h"

class CompressProcess : public PLAProcess {
public:
	CompressProcess();

	virtual ~CompressProcess();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();
};

#endif /* COMPRESSPROCESS_H_ */
