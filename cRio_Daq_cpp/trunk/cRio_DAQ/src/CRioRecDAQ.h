/*
 * CRioRecDAQ.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef CRIORECDAQ_H_
#define CRIORECDAQ_H_

void exitTerminalLoop();

bool start();

bool stop();

extern class DAQSystem* daqSystem;

#endif /* CRIORECDAQ_H_ */
