/*
 * mythread.c
*/
#include "mythread.h"
#ifndef WINDOWS
#include <unistd.h>
#endif

void myusleep(uint32_t micros) {
#ifdef WINDOWS
	Sleep(micros/1000);
#else
	usleep(micros);
#endif
}
