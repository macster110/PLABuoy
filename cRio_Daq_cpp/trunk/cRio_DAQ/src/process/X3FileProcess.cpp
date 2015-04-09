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

#ifdef WINDOWS
#include <Winsock2.h>
#else
//#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#endif

#include <string>
using namespace std;
/**
 * Take compressed data from CompressProcess and
 * write it to x3a files.
 */

#define MAX_FILE_SIZE (2000000000L)

X3FileProcess::X3FileProcess() : PLAProcess("x3record", "WAV") {
	x3File = NULL;
	uncompressedBytes = compressedBytes = 0;
	headerDataLength = 512;
	headerData = (char*) malloc(headerDataLength);
	repTimer = new RealTimer();
	repTimer->start();
}

X3FileProcess::~X3FileProcess() {
	free (headerData);
}


int X3FileProcess::initProcess(int nChan, int sampleRate) {
	return 0;
}

int X3FileProcess::process(PLABuff* plaBuffer) {
	static int count = 0;
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
	if (++count < 3) {
		short* data = plaBuffer->data;
		printf("Writing %d bytes (%d frames) of file data key %d, ch %d ns %d nw %d\n", plaBuffer->dataBytes, plaBuffer->soundFrames,
				data[0], data[1], data[2], data[3]);
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
	/*
	 * Get the XML chain describing what's going into the file
	 */
	mxml_node_t *doc = mxmlNewXML("1.0");
	mxml_node_t *mainEl = mxmlNewElement(doc, "CONFIG");
	getXMLProcessChain(doc, mainEl, &timeStamp);

	int dataBytes = mxmlSaveString(doc, headerData+X3_HDRLEN*2, headerDataLength-X3_HDRLEN*2, MXML_NO_CALLBACK);
	if (dataBytes >= headerDataLength-X3_HDRLEN*2) {
		// need more room !
		headerDataLength = dataBytes + X3_HDRLEN*2 + 10;
		headerData = (char*) realloc(headerData, headerDataLength);
		dataBytes=mxmlSaveString(mainEl, headerData+X3_HDRLEN*2, headerDataLength-X3_HDRLEN*2, MXML_NO_CALLBACK);
	}
	printf("Write %d byte xml %s", dataBytes, headerData+X3_HDRLEN*2);
	mxmlDelete(doc);
	// number of bytes returned by mxmlSaveString may be odd, but we need to align on a short
	// boundary - so set the next char to 0 as well (assume currently null terminated).
	headerData[X3_HDRLEN*2+strlen(headerData+X3_HDRLEN*2)+1] = 0;

//	int dataBytes = X3_prepareXMLheader(hData+X3_HDRLEN*2, 500000, 8, X3BLOCKSIZE);
	int nw = (dataBytes+1)>>1;
	int cd = crc16((short*) (headerData+X3_HDRLEN*2), nw);
	nw += x3frameheader((short*) headerData,0,0,0,nw,NULL,cd) ;
	/*
	 * Now swap the bytes in the header, but NOT in the data
	 * Since they were in character format and will therefore already
	 * be in the correct byte order.
	 */
	short* swapBuff = (short*) headerData;
	for (int i = 0; i < X3_HDRLEN; i++) {
		swapBuff[i] = htons(swapBuff[i]);
	}
	fwrite(headerData, 2, nw, aFile);
	uncompressedBytes = compressedBytes = 0;
	printf("Opened new x3 file %s with xml information:\n", fileName.c_str());
	printf(headerData+X3_HDRLEN*2);
	printf("\n");
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
