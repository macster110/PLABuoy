/*
 * CompressProcess.cpp
 *
 *  Created on: 11 Jan 2015
 *      Author: doug
 */

#include "CompressProcess.h"
#include "../x3/x3cmp.h"
#include "../x3/x3frame.h"
#include "../x3/crc16v3.h"
#include "../Settings.h"
#include <sys/socket.h>
#include <netinet/in.h>

#include <stdio.h>

#define NET_AUDIO_DATA (10)

#define NET_AUDIO_HEADINFO (1)
#define NET_AUDIO_SOUND    (2)
#define NET_AUDIO_X3SOUND  (3)

// Allocate a local buffer for handling frames. This should be larger than the
// largest allowable frame.
short    PBUFF[MAXFRAME+X3_HDRLEN] ;

CompressProcess::CompressProcess() : PLAProcess("compress") {

}

CompressProcess::~CompressProcess() {
}

int CompressProcess::initProcess(int nChan, int sampleRate) {
	PLAProcess::initProcess(nChan, sampleRate);
	return 0;
}

int CompressProcess::process(PLABuff* plaBuffer) {
	static int count = 0;
	XBuff ibuff[1];
	ibuff->data = plaBuffer->data;
	ibuff->nch = plaBuffer->nChan;
	ibuff->nsamps = plaBuffer->soundFrames;// write a frame of compressed audio data to the open file

	short   *pb = PBUFF, cd ;
	XBuff   pbuff = {PBUFF+X3_HDRLEN,MAXFRAME,1} ;
	int     ns, nw, k ;

	ns = ibuff->nsamps * ibuff->nch ;
	if(ns>MAXFRAME) {
		printf("input buffer too large for a single frame\n)") ;
		return(1) ;
	}
//	for (k = 0; k < ibuff->nch*ibuff->nsamps; k++) {
//		ibuff->data[k] = 7;
//	}

	nw = X3_compress_def(&pbuff,ibuff) ; // compresses a multi channel buffer, returns len of compressed data.
	cd = crc16(pbuff.data,nw) ; // get a crc code for the compressed buffer.
	// write the header into the X3_HDRLEN (10) bytes at the start of the buffer
	nw += x3frameheader(PBUFF,1,ibuff->nch,ibuff->nsamps,nw,NULL,cd) ;
	// good to go and write to file.
	// need to byte swap here - not 100% sure what Mark was up to in his code.
	short* swapBuff = (short*) PBUFF;
	for (int i = 0; i < nw; i++) {
		swapBuff[i] = htons(swapBuff[i]);
	}

	PLABuff outBuff;
	outBuff.data = PBUFF;
	outBuff.nChan = plaBuffer->nChan;
	outBuff.soundFrames = plaBuffer->soundFrames;
	outBuff.dataBytes = nw*2;

//	if (++count % 10000 == 0) {
//		printf("Compression count %d: %d bytes to %d\n", count, plaBuffer->dataBytes, outBuff.dataBytes);
//	}
	return forwardData(&outBuff);
}

void CompressProcess::endProcess() {

}
