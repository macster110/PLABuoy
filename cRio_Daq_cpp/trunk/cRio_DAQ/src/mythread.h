/*
 * mythread.h
 *
 *  Created on: 22 Jan 2015
 *      Author: doug
 */

#ifndef MYTHREAD_H_
#define MYTHREAD_H_

/**
 * Definitions enabling code to create a threading system which can build for both Linux and Windows
 * Since each system uses a quite different set of functions and references to threads, some of
 * the functions have parameters which may be annoyingly redundant for each system.
 * The functions are designed only to work with classes, The function created by the
 * DECLARETHREAD should be places at the top of a cpp file. It is passed a pointer back to the
 * class and will call straight back into it.
 *
 */
#ifdef CRIO
#undef WINDOWS
#endif

#ifdef WINDOWS
#include <windows.h>

typedef HANDLE THREADHANDLE;
typedef DWORD THREADID;

#define DECLARETHREAD(starterName, className, functionName) \
unsigned long __stdcall starterName (void* threadData) { \
	className* classReference = (className*) threadData; \
	classReference->functionName(); \
	return 0; \
}
#define STARTTHREAD(starterName, classPointer, threadId, threadHandle, threadState) \
	threadHandle = CreateThread(NULL, 0, starterName, (void*)classPointer, 0, &threadId); \
	threadState = (threadHandle != 0);

#define WAITFORTHREAD(threadId, threadHandle, threadReturnVal) \
		threadReturnVal = (int) WaitForSingleObject(threadHandle, INFINITE);

#else

#include <pthread.h>

typedef int THREADHANDLE;
typedef pthread_t THREADID;

#define DECLARETHREAD(starterName, className, functionName) \
void* starterName (void* threadData) { \
	className* classReference = (className*) threadData; \
	classReference->functionName(); \
	return NULL; \
}

#define STARTTHREAD(starterName, classPointer, threadId, threadHandle, threadState) \
	int err; \
	err = pthread_create(&threadId, NULL , starterName, (void*) classPointer); \
	threadHandle = 0; \
	threadState = (err == 0);

#define WAITFORTHREAD(threadId, threadHandle, threadReturnVal) \
		void* hiddenRetVal; \
		pthread_join(threadId, &hiddenRetVal); \
		threadReturnVal = (int) hiddenRetVal;


#endif

#endif /* MYTHREAD_H_ */
