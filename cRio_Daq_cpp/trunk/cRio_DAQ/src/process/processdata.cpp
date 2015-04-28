
/*
 * Main process functions and file. C functions to call in at start / end
 * of acquisition, then calling into C++ processes to do the actual work.
 */
#include "processdata.h"
#include "WavFileProcess.h"
#include "X3FileProcess.h"
#include "CompressProcess.h"
#include "NetSender.h"
#include "SerialReadProcess.h"
#include "../command/ProcessEnable.h"
#include "../command/ProcessSummary.h"
#include "../Utils.h"
#include <stdio.h>
#include <stdlib.h>
#include "../x3/xml_if.h"
#include "../mxml/mxml.h"
#include "../Settings.h"
#include "../AudioLevels.h";
#include <sstream>      // std::stringstream, std::stringbuf



PLAProcess** plaProcesses;

static int globalProcessId = 0;

int nChannels = 0;

/**
 * Average levels in units (2^16/2=max -2^16/2=min)
 */
volatile int16_t* avrgLevels;

/**
 * Keeps a count of audio data packets processed. Should be good for a few
 * hundred thousand million of continuous recording.
 */
long count=0;


#define NPROCESSES (4) //number of process

/*
 * Create the processes
 */
void processCreate() {

	plaProcesses = (PLAProcess**) malloc(sizeof(PLAProcess*) * NPROCESSES);

/***Processes setup to record raw wav files**/
	plaProcesses[0] = new PLAProcess("Audio", "AUDIO");
	plaProcesses[1] = new WavFileProcess();
	plaProcesses[2] = new NetSender();
	plaProcesses[3] = new SerialReadProcess();

	//add wav files to input process
	plaProcesses[0]->addChildProcess(plaProcesses[1]);
//	// attach net sender to output of wav files.
	//plaProcesses[1]->addChildProcess(plaProcesses[2]);

/********************************************/


/***Processes set up for record and X3*******/

//	plaProcesses[0] = new PLAProcess("Audio", "AUDIO");
//	plaProcesses[1] = new CompressProcess();
//	plaProcesses[2] = new X3FileProcess();
//	plaProcesses[3] = new NetSender();
//	/*
//	 * Add both the wav writing and the compression process to the
//	 * input process.
//	 */
//	plaProcesses[0]->addChildProcess(plaProcesses[1]);
//
////#ifndef WINDOWS
//	// attach x3 write process to compressor.
//	plaProcesses[1]->addChildProcess(plaProcesses[2]);
////#endif
//
//	// attach net sender to output of compression.
//	plaProcesses[1]->addChildProcess(plaProcesses[3]);
//
/*********************************************/

	// set the default sample rates, etc.
	plaProcesses[0]->setSampleRate(DEFAULTSAMPLERATE);
	plaProcesses[0]->setNChan(DEFAULTNCHANNELS);

//	mxml_node_t *doc = mxmlNewXML("1.0");
//	mxml_node_t *mainEl = mxmlNewElement(doc, "PLABuoy");
//	timeval tv;
//	gettimeofday(&tv, 0);
////	for (int i = 0; i < NPROCESSES; i++) {
////		plaProcesses[i]->getXMLInfo(doc, mainEl, &tv);
////	}
//	plaProcesses[2]->getXMLProcessChain(doc, mainEl, &tv);
//	char xmlCharData[2048];
//	mxmlSaveString(doc, xmlCharData, 2048, MXML_NO_CALLBACK);
//	printf(xmlCharData);
//	exit(0);
}

int getNumProcesses() {
	return NPROCESSES;
}
/**
 * Get a process.
 */
PLAProcess* getProcess(int iProcess) {
	if (iProcess < 0 || iProcess >= NPROCESSES) {
		return NULL;
	}
	return plaProcesses[iProcess];
}

/*
 * Initialise the processes
 */
bool processInit(int nChan, int sampleRate) {
	nChannels = nChan;

	for (int i = 0; i < NPROCESSES; i++) {
		plaProcesses[i]->initProcess(nChan, sampleRate);
	}
	return true;
}

/**
 * Check whether any processes have an error flagged.
 */
bool isProcessError(){
	for (int i = 0; i < NPROCESSES; i++) {
			if (plaProcesses[i]->getErrorStatus()!=0) return true;
		}
	return false;
}

/*
 * Send data to the processes. this function only has to send to the first process,
 * this will send data out through a tree to downstream processes.
 * Data always arrive here as raw - so pack into a PLABuff structure
 * to send off into the rest of the processing system.
 */
bool processData(int16_t* data, int nSamples, timeval daqTime) {

	PLABuff plaBuff;
	plaBuff.data = data;
	plaBuff.nChan = nChannels;
	plaBuff.soundFrames = nSamples / nChannels;
	plaBuff.dataBytes = sizeof(int16_t) * nSamples;
	plaBuff.timeStamp = daqTime;
	return plaProcesses[0]->process(&plaBuff);
}

/*
 * End the processes
 */
void processEnd() {
	for (int i = 0; i < NPROCESSES; i++) {
		plaProcesses[i]->endProcess();
	}
}

/**
 * Delete the processes. Called only at program exit.
 */
void processDelete() {
	for (int i = 0; i < NPROCESSES; i++) {
		delete(plaProcesses[i]);
	}
}

/**
 * Find a process with a given name. Return null if nothing found
 */
class PLAProcess* findProcess(std::string processName) {
	if (processName.size() == 0) {
		return NULL;
	}
	for (int i = 0; i < NPROCESSES; i++) {
		if (plaProcesses[i] == NULL) continue;
		if (plaProcesses[i]->getProcessName() == processName) {
			return plaProcesses[i];
		}
	}
	return NULL;
}

PLAProcess::PLAProcess(const string processName, const string xmlName) : CommandList() {
	this->processName = processName;
	this->xmlName = xmlName;
	fType = NULL;
	codec = -1;
	enabled = true;
	processId = ++globalProcessId;
	childProcesses = NULL;
	parentProcess = NULL;
	nChildProcesses = 0;
	nChan = 0;
	sampleRate = 0;
	addCommand(new ProcessEnable(this));
	addCommand(new ProcessSummary(this));
}

PLAProcess::~PLAProcess() {
	if (childProcesses) free(childProcesses);
}


int PLAProcess::initProcess(int nChan, int sampleRate) {
	this->nChan = nChan;
	this->sampleRate = sampleRate;
	//allocate some memory levels array.
	avrgLevels=(int16_t*)malloc(nChan*sizeof(int16_t));
	return 0;
}

int PLAProcess::process(PLABuff* plaBuffer) {
	/**
	 * Base function just forwards the data on. all downstream
	 * processes will need to override this.
	 */
	if (count%1000==0){
		calcAudioLevels(avrgLevels, plaBuffer);
	}
	count++;
	return forwardData(plaBuffer);
}

int PLAProcess::forwardData(PLABuff* plaBuffer) {
	int errors = 0;
	for (int i = 0; i < nChildProcesses; i++) {
		if (childProcesses[i]->isEnabled()) {
			errors += childProcesses[i]->process(plaBuffer);
		}
	}
	return errors;
}

void PLAProcess::endProcess() {

}

/**
 * Get configuration data, ideally in an xml like format for any downstream
 * modules to use.
 */
int PLAProcess::getModuleConfiguration(char* configData, int configDataLength) {
	return 0;
}

void PLAProcess::addChildProcess(PLAProcess* childProcess) {
	if (nChildProcesses == 0) {
		childProcesses = (PLAProcess**) malloc(sizeof(PLAProcess*));
	}
	else {
		childProcesses = (PLAProcess**) realloc(childProcesses, sizeof(PLAProcess*) * (nChildProcesses+1));
	}
	childProcesses[nChildProcesses++] = childProcess;
	childProcess->setParentProcess(this);
}

int PLAProcess::getChannelBitMap() {
	int map = 0;
	for (int i = 0; i < nChan; i++) {
		map |= 1<<i;
	}
	return map;
}

int PLAProcess::getErrorStatus(){
	return 0;
}

string PLAProcess::getSummaryData(){
	//return a string of levels
	//order goes like this//
	//channels,level ch 1, level ch2, ....

	string levelString;
	stringstream strStream (stringstream::in | stringstream::out);

	strStream << nChan << ",";
	for (int i=0; i < nChan; i++)
	{
	    strStream << avrgLevels[i] << ",";
	}
	levelString = strStream.str();

	return levelString;
}

void PLAProcess::setNChan(int nChan) {
	this->nChan = nChan;
	for (int i = 0; i < nChildProcesses; i++) {
		childProcesses[i]->setNChan(nChan);
	}
}

void PLAProcess:: setSampleRate(int sampleRate) {
	this->sampleRate = sampleRate;
	for (int i = 0; i < nChildProcesses; i++) {
		childProcesses[i]->setSampleRate(sampleRate);
	}
}

// XML info management functions
mxml_node_t* PLAProcess::getXMLInfo(mxml_node_t *doc, mxml_node_t *parentNode, timeval* timeVal) {
	mxml_node_t *thisNode = getXMLStartInfo(doc, parentNode, timeVal);
	return thisNode;
}

mxml_node_t* PLAProcess::getXMLStartInfo(mxml_node_t *doc, mxml_node_t *parentNode, timeval* timeVal) {
	mxml_node_t *node = mxmlNewElement(parentNode, "CFG");
	mxml_node_t *el;
	char txt[20];
	sprintf(txt, "%d", processId);
	mxmlElementSetAttr(node, "ID", txt);
	if (fType) {
		mxmlElementSetAttr(node, "FTYPE",  fType);
	}
	if (codec >= 0) {
		sprintf(txt, "%d", codec);
		mxmlElementSetAttr(node, "CODEC",  txt);
	}
//	if (timeVal) {
//		el = mxmlNewElement(node, "TIME");
//		sprintf(txt, "%d", timeVal->tv_sec);
//		mxmlElementSetAttr(el, "S", txt);
//		sprintf(txt, "%d", timeVal->tv_usec);
//		mxmlElementSetAttr(el, "uS", txt);
//	}
	// work out the source process id ...
	if (parentProcess) {
		el = mxmlNewElement(node, "SRC");
		mxmlNewInteger(el, parentProcess->processId);
	}

	if (xmlName.length() > 0) {
		el = mxmlNewElement(node, "PROC");
		mxmlNewText(el, 0, getXmlName().c_str());
	}
	el = mxmlNewElement(node, "NCHS");
	mxmlNewInteger(el, getNChan());
	el = mxmlNewElement(node, "CHANBITMAP");
	mxmlNewInteger(el, getChannelBitMap());
	el = mxmlNewElement(node, "FS");
	mxmlElementSetAttr(el, "UNIT",  "Hz");
	mxmlNewInteger(el, sampleRate);
	el = mxmlNewElement(node, "NBITS");
	mxmlNewInteger(el, 16);


	return node;
}

/**
 * Get a chain of process information for all processes feeding into the current one ...
 */
mxml_node_t* PLAProcess::getXMLProcessChain(mxml_node_t *doc, mxml_node_t *parentNode, timeval* timeVal) {
	// don't know exactly how many upstream processed there are, but we do know the maximum.
	PLAProcess* proc[NPROCESSES];
	PLAProcess* current;
	int nProc = 0;
	current = this;
	while (current) {
		proc[nProc] = current;
		nProc++;
		current = (PLAProcess*) current->parentProcess;
	}
	for (int i = nProc-1; i >= 0; i--) {
//		printf("Get XML for proc %d id 0x%X %s", i, (unsigned) proc[i], )
		proc[i]->getXMLInfo(doc, parentNode, timeVal);
	}
	return parentNode;
}

