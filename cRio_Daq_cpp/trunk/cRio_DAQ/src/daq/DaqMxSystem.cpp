/*
 * DaqMxSystem.cpp
 *
 *  Created on: 27 Jan 2015
 *      Author: doug
 */

#include "DaqMxSystem.h"
#include "NIDAQmx.h"

DaqMxSystem::DaqMxSystem() : DAQSystem("NI Daq MX"){
	// TODO Auto-generated constructor stub

}

DaqMxSystem::~DaqMxSystem() {
	// TODO Auto-generated destructor stub
}


int DaqMxSystem::getStatus(){

	return 0;
}

int DaqMxSystem::getErrorCount(){

	return 0;
}

void DaqMxSystem::resetErrorCount(){

}

bool DaqMxSystem::prepareSystem(){
	return false;
}

bool DaqMxSystem::startSystem(){
	return false;
}

bool DaqMxSystem::stopSystem(){
	return false;
}
