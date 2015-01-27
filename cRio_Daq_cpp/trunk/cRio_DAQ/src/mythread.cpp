/*
 * mythread.c
*/
#include "mythread.h"

void myusleep(uint32_t micros) {
#ifdef WINDOWS
	Sleep(micros/1000);
#else
	usleep(micros);
#endif
}
