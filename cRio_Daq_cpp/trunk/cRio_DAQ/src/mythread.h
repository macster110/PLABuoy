/*
 * mythread.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef MYTHREAD_H_
#define MYTHREAD_H_

#ifdef WINDOWS
#include <windows.h>
#else
#include <pthread.h>
#endif

#define DECLARETHREAD(starterName, className, functionName) \
unsigned long __stdcall starterName (void* threadData) { \
	className* classReference = (className*) threadData; \
	return classReference->functionName(); \
}

#define STARTTHREAD(starterName, classPointer, threadId) \
	HANDLE winthread = CreateThread(NULL, 0, starterName, (void*)classPointer, 0, &threadId); \
	return (winthread != 0);

#else
#include <pthread.h>
#define DECLARETHREAD(starterName, className, functionName) \
void* starterName (void* threadData) { \
	className* classReference = (className*) threadData; \
	return (void*) classReference->functionName(); \
}

#define STARTTHREAD(starterName, classPointer, threadId) \
	int err; \
	err = pthread_create(&threadId, NULL , starterName, (void*) classPointer); \
	return (err == 0);



#endif /* MYTHREAD_H_ */
