/*
 * X3FileProcess.h
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#ifndef X3FILEPROCESS_H_
#define X3FILEPROCESS_H_

#include <stdint.h>
#include <stdio.h>
#include <string>


#include "processdata.h"

class X3FileProcess: public PLAProcess {
public:
	X3FileProcess();

	virtual ~X3FileProcess();

	int initProcess(int nChan, int sampleRate);

	int process(PLABuff* plaBuffer);

	void endProcess();

private:
	FILE* x3File;
	int64_t compressedBytes, uncompressedBytes;
	bool checkFile();
	bool needNewFile();
	FILE* openFile();
	void closeFile();
	class RealTimer* repTimer;
	std::string openFileName;
};

#endif /* X3FILEPROCESS_H_ */
