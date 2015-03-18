/*
 * X3FileProcess.cpp
 *
 *  Created on: 21 Jan 2015
 *      Author: doug
 */

#include "X3FileProcess.h"

#include <stdio.h>

#include "../Utils.h"
#include "../x3/x3frame.h"
#include "../x3/crc16v3.h"
#include "../RealTimer.h"

#include <string>
using namespace std;
/**
 * Take compressed data from CompressProcess and
 * write it to x3a files.
 */

#define MAX_FILE_SIZE (2000000000L)

X3FileProcess::X3FileProcess() : PLAProcess("x3record", "X3V3") {
	x3File = NULL;
	uncompressedBytes = compressedBytes = 0;
	repTimer = new RealTimer();
	repTimer->start();
}

X3FileProcess::~X3FileProcess() {
	// TODO Auto-generated destructor stub
}


int X3FileProcess::initProcess(int nChan, int sampleRate) {
	return 0;
}

int X3FileProcess::process(PLABuff* plaBuffer) {
//	static int count = 0;
	int nBytes = plaBuffer->soundFrames*plaBuffer->nChan*sizeof(short);
	compressedBytes += plaBuffer->dataBytes;
	uncompressedBytes += nBytes;
	if (!checkFile(plaBuffer->timeStamp)) return 0;
	fwrite(plaBuffer->data, plaBuffer->dataBytes, 1, x3File);
	if (repTimer->stop() > 10) {
		repTimer->start();
		printf("writing file, currently %lld MB of data (%lld MB uncompressed) compression %3.2fx\n",
			 (compressedBytes>>20), (uncompressedBytes>>20), (float) uncompressedBytes / (float) (compressedBytes+1));
//		printf("Last block contained %d frames with %d channels = %d bytes, compressed to %d\n",
//				plaBuffer->soundFrames, plaBuffer->nChan, nBytes, plaBuffer->dataBytes);
	}


	return 0;
}

void X3FileProcess::endProcess() {
	closeFile();
}

bool X3FileProcess::checkFile(timeval timeStamp) {
	if (needNewFile()) {
		closeFile();
		x3File = openFile(timeStamp);
	}
	return x3File != NULL;
}
bool X3FileProcess::needNewFile() {
	if (x3File == NULL) return true;
	if (uncompressedBytes >= MAX_FILE_SIZE) return true;
	return false;
}

FILE* X3FileProcess::openFile(timeval timeStamp) {
	string fileName = createFileName("PLA", ".x3a", timeStamp);
	FILE* aFile = fopen(fileName.c_str(),"wb") ;
	fprintf(aFile,X3_FILE_KEY);
	char hData[X3HEADLEN];
	int dataBytes = X3_prepareXMLheader(hData+X3_HDRLEN*2, 500000, 8, X3BLOCKSIZE);
	int nw = (dataBytes+1)>>1;
	int cd = crc16((short*) (hData+X3_HDRLEN*2), nw);
	nw += x3frameheader((short*) hData,0,0,0,nw,NULL,cd) ;
	fwrite(aFile, 2, nw, aFile);
	uncompressedBytes = compressedBytes = 0;
	printf("Opened new x3 file %s\n", fileName.c_str());
	openFileName = fileName;
	return aFile;
}

void X3FileProcess::closeFile() {
	if (x3File != NULL) {
		fclose(x3File);
		x3File = NULL;
		printf("File %s closed. %lld compressed MB. Compression = %3.1fx\n",
				openFileName.c_str(), compressedBytes>>20, (float) uncompressedBytes / (float) (compressedBytes+1));
	}
}
