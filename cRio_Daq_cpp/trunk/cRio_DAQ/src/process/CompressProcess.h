/*
 * CompressProcess.h
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#ifndef COMPRESSPROCESS_H_
#define COMPRESSPROCESS_H_

#include "processdata.h"
#include "../Utils.h"

class CompressProcess : public PLAProcess {
public:
	CompressProcess();

	virtual ~CompressProcess();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();
private:
	void packtimeval(struct timeval timeVal, short* packedTime);
};

#endif /* COMPRESSPROCESS_H_ */
