/*
 * AudioLevels.cpp
 *
 *  Created on: 16 Apr 2015
 *      Author: Lab
 */

#include "AudioLevels.h"
#include "Settings.h"
#include "process/processdata.h"


int16_t* getMaxLevels(PLABuff* plaBuffer){

	//create pointer to maxLevels
	int16_t maxLevels[plaBuffer->nChan];

	//calculate the size of the buffer i.e how many elements we have rather than bytes
	int16_t bufSize=plaBuffer->dataBytes/sizeof(int16_t);

	for (int16_t i=0; i<plaBuffer->nChan; ++i){

		//find max in audio buffer. Iterate through interleaved audio data.
		int16_t j=i;
		int16_t max=-32678; //this will need to change if there's a different type of soundcard.

		while (j<bufSize){
			if (*(plaBuffer->data+j)>max){
				max=*(plaBuffer->data+j);
			}
			j=j+plaBuffer->nChan;
		}
		maxLevels[i]=max;
	}

	return maxLevels;
}

void calcAudioLevels(int16_t * audio, PLABuff* plaBuffer){
	//get levels;
	int16_t* maxLevels=getMaxLevels(plaBuffer);

	int16_t newLevel;
	//now apply decaying average and set in int array
	for (int16_t i=0; i<plaBuffer->nChan; ++i){
		newLevel=decayConst*maxLevels[i]+(1-decayConst)*audio[i];
		//printf("Audio Level: %d %d", i, newLevel);
		audio[i]=newLevel;
	}


}



