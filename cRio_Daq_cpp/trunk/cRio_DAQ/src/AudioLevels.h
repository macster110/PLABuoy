/*
 * Levels.h
 *
 *  Created on: 16 Apr 2015
 *      Author: Lab
 */
#include "process/processdata.h"

#ifndef LEVELS_H_
#define LEVELS_H_

/**
 * The decaying average constant. Increase for faster average.
 */
const double decayConst=0.65;


/**
 *Calculate new audio levels and set in audioLevel array. This takes data from buffer, calculates audio levels in u based
 *on a decaying average. (u is amplitude in bits e.g. there are 2^16 units in a 16 bit soundcard)
 *@param audioLevels- array of current levels. this will be n channels in length;
 *@param plaBuffer - new pla buffer. Contains interleved audio data
 *
 */
void calcAudioLevels(int16_t * audio, PLABuff* plaBuffer);

/**
 * Calculate max levels for all channels within a buffered data.
 */
int16_t* getMaxLevels(PLABuff* plaBuffer);


#endif /* LEVELS_H_ */
