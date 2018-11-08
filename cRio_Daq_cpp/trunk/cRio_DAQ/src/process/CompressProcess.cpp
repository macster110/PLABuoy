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
#ifdef WINDOWS
#include "Winsock2.h"
#else
#include <sys/socket.h>
#include <netinet/in.h>
#endif

#include <stdio.h>

const char* X3TYPE = "X3V2";

#define NET_AUDIO_DATA (10)

#define NET_AUDIO_HEADINFO (1)
#define NET_AUDIO_SOUND    (2)
#define NET_AUDIO_X3SOUND  (3)

// Allocate a local buffer for handling frames. This should be larger than the
// largest allowable frame.
short    PBUFF[MAXFRAME+X3_HDRLEN] ;

CompressProcess::CompressProcess() : PLAProcess("compress", X3TYPE) {
	fType = (char*) X3TYPE;
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
	short x3time[4];

	short   *pb = PBUFF, cd ;
	XBuff   pbuff = {PBUFF+X3_HDRLEN,MAXFRAME,plaBuffer->nChan} ;
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
	packtimeval(plaBuffer->timeStamp, x3time);
	nw += x3frameheader(PBUFF,1,ibuff->nch,ibuff->nsamps,nw,x3time,cd) ;

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
	outBuff.timeStamp = plaBuffer->timeStamp;

//	static int count = 0;
//	if (count++<5) {
//		printf("in CompressProcess::process. nChan=%d, nFrames = %d\n", ibuff->nch, ibuff->nsamps);
//		fflush(stdout);
//	}
//	if (++count % 10000 == 0) {
//		printf("Compression count %d: %d bytes to %d\n", count, plaBuffer->dataBytes, outBuff.dataBytes);
//		fflush(stdout);
//	}
	return forwardData(&outBuff);
}

void CompressProcess::endProcess() {

}

/**
 * Pack a C timeval into a four word struct.
 */
void CompressProcess::packtimeval(struct timeval timeVal, short* packedTime) {
	packedTime[0] = timeVal.tv_sec >> 16;
	packedTime[1] = timeVal.tv_sec & 0xFFFF;
	packedTime[2] = timeVal.tv_usec >> 16;
	packedTime[3] = timeVal.tv_usec & 0xFFFF;
}

mxml_node_t* CompressProcess::getXMLInfo(mxml_node_t *doc, mxml_node_t *parentNode, timeval* timeVal) {
	codec = getParentProcess()->getProcessId();
	mxml_node_t *node = getXMLStartInfo(doc, parentNode, timeVal);
	mxml_node_t *el;
	char txt[20];
/*
 *
	addxmlfield(s,"CFG","ID=\"0\" FTYPE=\"XML\"",NULL) ;
	openxmlfield(s,"CFG","ID=\"1\" FTYPE=\"WAV\"") ;
	sprintf(sfs,"%d",sampleRate) ;
	addxmlfield(s,"FS","UNIT=\"Hz\"",sfs) ;
	sprintf(sfs,"%d",nChan) ;
	addxmlfield(s,"NCHAN",NULL,sfs);
	addxmlfield(s,"SUFFIX",NULL,"wav") ;
	openxmlfield(s,"CODEC","TYPE=\"X3\" VERS=\"2\"") ;      // name of the encoder
	sprintf(sfs,"%d",blockSize);
	addxmlfield(s,"BLKLEN",NULL,sfs) ;
	addxmlfield(s,"CODES","N=\"4\"","RICE0,RICE1,RICE3,BFP") ;
	addxmlfield(s,"FILTER",NULL,"DIFF") ;
	addxmlfield(s,"NBITS",NULL,"16") ;
	addxmlfield(s,"T","N=\"3\"","3,8,20") ;
 */
	el = mxmlNewElement(node, "SUFFIX");
	mxmlNewText(el, 0, "wav");

	el = mxmlNewElement(node, "FILTER");
	mxmlNewText(el, 0, "diff");

	int      DEF_T[] = X3_DEF_T ;
	int DEF_C[] = X3_DEF_CODES;
	for (int i = 0; i < X3_DEF_NT; i++) {
		sprintf(txt, "RICE%d", DEF_C[i]);
		el = mxmlNewElement(node, "CODE");
		mxmlNewText(el, 0, txt);
		sprintf(txt, "%d", DEF_T[i]);
		mxmlElementSetAttr(el, "THRESH", txt);
	}
	el = mxmlNewElement(node, "CODE");
	mxmlNewText(el, 0, "BFP");
	el = mxmlNewElement(node, "BLKLEN");
	mxmlNewInteger(el, X3BLOCKSIZE);



	return node;
}
