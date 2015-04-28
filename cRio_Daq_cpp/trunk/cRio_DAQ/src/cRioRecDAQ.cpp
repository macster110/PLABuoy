//============================================================================
// Name        : cRioTestC.cpp
// Author      : Jamie Macaualay
// Version     :
// Copyright   : None
// Description : Main class for cRio
//============================================================================

#include "cRioRecDAQ.h"

/* Required for console interactions */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*pthread library for running on different threads*/
#include "mythread.h"
#include <string.h>
#include <unistd.h>
/* Includes high level funct
 * ions for reading DAQ card*/
//#include "NiFpgaManager.h"
///*Includes high level functions for reading Serial port */
//#include "ReadSerial.h"
/*Useful functions including switching on and off LEDs*/
#include "Utils.h"
#include "process/processdata.h"
#include "RealTimer.h"
#include "command/CommandManager.h"
#include "Settings.h"
#include "ReadSerial.h"
#include "WriteWav.h"
#include "nifpga/NiFpgaChoice.h"



#include "command/UDPCommands.h"
#include "daq/SimulatedDaq.h"
#ifdef WINOWS
#include "daq/DaqMxSystem.h"
#include "winsock.h"
#else
#include "daq/FPGADaqSystem.h"
#endif

using namespace std;

/**
 * Data acquisition system
 */
DAQSystem* daqSystem;

/*Define some functions*/
void get_user_commands();
//void user_command(int command);
int get_user_input_num();

/**
 * User input
 */
std::string get_user_input_string();

/**
 * Indicator for running the command loop
 */
bool volatile go=true;

/**
 * Indicator for continuing to acquire data.
 */
volatile bool acquire = false;

/**
 * indicates the watchdog has detected an error.
 */
volatile bool watch_dog_error = false;


/**
 * declare watch dog thread.
 */
DECLARETHREAD(processWatchDogFunction, PLAWatchDog, watchdog_monitor)

/**
 * The user input- number to specify command for cRio
 */
int UserInput;


int main(int argc, char *argv[]){

//create_Sound_File(8, 500000);
//testWavWrite();


//	cout<<"Number of arguments "<<argc<<endl;
//	for (int i=0; i<argc ; i++){
//		cout<<"Argument "<<i<<" "<<*argv[i]<<endl;
//	}
	printf("Current time = %s\n", currentDateTime().c_str());

	// initialise winsock straight away if it's Windows
	#ifdef WINDOWS
	WORD wVersionRequested;
	WSADATA wsaData;
	int err;

	wVersionRequested = MAKEWORD( 2, 0 );

	err = WSAStartup( wVersionRequested, &wsaData );
	if ( err != 0 ) {
		/* Tell the user that we couldn't find a usable */
		/* WinSock DLL.                                  */
		return false;
	}
#endif

#ifdef WINDOWS
	daqSystem = new SimulatedDaq();
//	daqSystem = new DaqMxSystem();
#else
	daqSystem = new FPGADaqSystem();
#endif
//	RealTimer* rt = new RealTimer();
//	printf("System clock has %11.9fs resolution\n", rt->getResolution());
//	rt->start();
//	for (int i = 0; i < 100; i++) {
//		myusleep(10000);
//		printf("tic %d, time %4.5f\n", i, rt->stop());
//		fflush(stdout);
//	}
//	delete(rt);

//	/**Make sure LED's are off**/
//	set_user_LED_status(LED_USER1_OFF);
//	set_user_LED_status(LED_STATUS_OFF);

//	//create watch dog class.
	cRioDAQWatchDog = new PLAWatchDog();

	// create the data processes.
	processCreate();

	/**
	 * Create and launch the UDP command browser.
	 */
	udpCommands = new UDPCommands();

	/*Send single long command string to the command manager. */
	if (argc>=2) {
		string allCommands = joinstrings(argc-1, &argv[1]);
		commandManager->processCommand(allCommands, NULL);
	}

//	/*Print console to output file*/
//	string consoleOut=SAVE_LOCATION+ "/console_out.txt";
//    freopen(consoleOut.c_str(),"w",stdout);

	/*Switch on LED to show program is on*/
	set_user_LED_status(LED_USER1_GREEN);

	/*Wait for commands on main thread;*/
	get_user_commands();

	/**Make sure LED's are off**/
//	set_user_LED_status(LED_USER1_OFF);
//	set_user_LED_status(LED_STATUS_OFF);
//	printf("Stop UDP Thread\n");
	udpCommands->stopUDPThread(false);
//	printf("Stoped UDP Thread\n");
	// clean up processes.
	processDelete();

//	printf("This is the last line of the program !\n");
	return 0;
}

/**
 * Get commands from the or from linux shell scripts.
 */
void get_user_commands(){
	/* Enter main loop. Exit if there is an error or the user requests the program to end */
	int count=0;
	std::string cmd;
	while (go || count==0)
	{
		/* Generate Menu */
//		printf("\n");
		printf("|---------------------------------------------------------------|\n");
		printf("|             type help for a list of commands                  |\n");
		printf("|---------------------------------------------------------------|\n");
		fflush(stdout);

//		UserInput = get_user_input_num();
//		if (UserInput >= 0) {
//			user_command(UserInput);
//		}
		while (go) {
		cmd = get_user_input_string();
		cmd = trimstring(cmd);
		if (cmd.size()) break;
		}
		string ans = commandManager->processCommand(cmd, NULL);
//		fflush(stdout);
		printf("Command \"%s\" answered \"%s\"\n", cmd.c_str(), ans.c_str());
//		fflush(stdout);

		count++;
	}
	printf("Leaving terminal control loop\n");
}


bool start() {
	cRioDAQWatchDog->startWatchDog();
	if (acquire==true) {
		printf("DAQ system is already running\n");
		return false;
	}
	acquire=true;
	printf("Initiating cRio recording\n");
	processInit(DEFAULTNCHANNELS, DEFAULTSAMPLERATE);
	/*Start recording data from serial port*/
	//			record_Serial(1,B4800);
	/**Start FPGA tasks**/
	daqSystem->prepare();
	return daqSystem->start();
}

/**
 * Stop DAQ and processes.
 * @param restart true- to start acquiring again after stop.
 */
bool stop(bool restart) {
	if (acquire == false) return false;
	acquire=false;
	bool ans = daqSystem->stop();
//	set_FPGA_go(false);
//	set_serial_go(false);
//	pthread_join(get_FPGA_thread(), NULL);
	processEnd();

	if (ans && restart){
		start();
		return true;
	}

	return ans;
}

/**
 * Perform an operation based on command flag.
 */
//void user_command(int command){
//	switch(command)
//	{
//	case 0:
//		if (go==true) return;
//		go=true;
//		printf("Initiating cRio recording\n");
//		/*Start recording data from serial port*/
//		record_Serial(1,B4800);
//		/**Start FPGA tasks**/
//		FPGA_Tasks_Thread();
//		break;
//	case 1:
//		go=false;
//		set_FPGA_go(false);
//		set_serial_go(false);
//		pthread_join(get_FPGA_thread(), NULL);
//		break;
//	default:
//		printf("INVALID INPUT! Please choose an integer between 0 and 4.\n");
//	}
//
//}

/**
 * Function to get an input integer from the stream.
 * Returns the number entered. Returns -1 for invalid input
 * A number followed by a string of non-numerics will get interpreted as that number
 * For example, "32reqwd" will return "32".
 * This function also prints out a warning message for each extra character entered into the stream
 * Note this function is directly copied from FPGA Interface C API tutorial
 * @return Number
 */
int get_user_input_num()
{
	int input;
	int Number = -1;

	/* Get number from stream */
	scanf("%d", &Number);

	/* Clear out extra characters from stream. Set limit at 100 characters to prevent infinite loops */
	int i=0;
	while(i<100)
	{
		input = getchar();
		//exit this loop if we hit EOF (-1) or a new line character
		if (input == '\n' || input == -1) break;
		printf("WARNING: Extra character(s) detected! Clearing stream: %d\n",input);
		i++;
	}
	return Number;

}

std::string get_user_input_string() {
	const int slen = 256;
	static char cmd[slen];
//	printf("waiting for user nput\n");
	fgets(cmd, slen-1, stdin);
//	scanf(cmd, "%s");
//	printf("have user input\n");
	int lStr = strlen(cmd);
	if (lStr > 0 && cmd[lStr-1] == '\n') {
		cmd[lStr-1] = 0;
	}
	if (lStr > 1 && cmd[lStr-1] == '\r') {
		cmd[lStr-1] = 0;
	}
//	printf("\"%s\" %d chars %d\n", cmd, strlen(cmd), (int) cmd[0]);
	return string(cmd);
}

void exitTerminalLoop() {
	go = false;
}

///**
// * Entry function for pthread to monitor FPGA
// */
//void *watchdog_thread_function(void *param)
//{
//	watchdog_monitor();
//	return NULL;
//}
//
///**
// * Start FPGA monitor on new thread.
// */
//void watchdog_thread(){
//	pthread_t watchdog_monitor_thread;
//	int thread_var1 = 0;
////	if(pthread_create(&watchdog_monitor_thread, NULL, watchdog_thread_function, &thread_var1)){
////			fprintf(stderr, "Watchdog: creating watchdog thread\n");
////			return;
////	}
//}


/**
 * Constructor for the watch dog.
 */
PLAWatchDog::PLAWatchDog(){

}

/**
 * Start a watch dog thread.
 */
void PLAWatchDog::startWatchDog(){
	bool threadState;
	STARTTHREAD(processWatchDogFunction, this, processWatchDogThrd, processWatchDogThrdHnd, threadState)
	if (!threadState) {
		fprintf(stderr, "Error starting read serial thread \n");
	}
}

/**
 * Stop watch dog thread.
 */
void PLAWatchDog::stopWatchDog(){
	int threadReturn;
	//set flag to false- this will stop serial processes.
	WAITFORTHREAD(processWatchDogThrd, processWatchDogThrdHnd, threadReturn)
}

/**
 * Sits and monitors the FPGA every half second.
 */
void PLAWatchDog::watchdog_monitor(){
	int led=0;
	int errorCount=0;
	int errorCountMax=5;

	watch_dog_error=false;

	set_user_LED_status(LED_USER1_OFF);

	while(acquire){
		myusleep(500000); //sleep for half a second
		//printf("Watchdog: Checking: %d!\n", errorCount);

		/**
		 * Check both the daq system and all processes. If there's an error in
		 * either then give an error strike. After 5 strikes then the system attempts
		 * DAQ restart. The reason we have strikes is to try giove the processes time to
		 * fix themselves.
		 */
		if (daqSystem->getErrorCount()>0 || isProcessError() ){
			printf("PLAWatchDog: FPGA error count>0: daq: %d processes: %d total error count %d\n", daqSystem->getErrorCount(), isProcessError(),errorCount);
			errorCount++;
			//led flag to yellow if possible, otherwise switch off
			#if defined(CRIO9068)
			led=LED_USER1_YELLOW;
			#else
			led=LED_USER1_OFF;
			#endif
		}
		else{
			//reset error counter.
			errorCount=0;
			//led flag to green.
			led=LED_USER1_GREEN;
		}

		//set_user_LED_status(LED_USER1_OFF);
		set_user_LED_status(led);

		if (errorCount>errorCountMax){
			printf("PLAWatchDog: error count has exceeded threshold: Going for DAQ reset\n");
			watch_dog_error=true;
			stop(true); //be careful here- recipe for threading issues.
			break;
		}


//		cout<< "Hello! I'm a running watchdog daqSystemError %d\n" << daqSystem->getErrorCount() << endl;
//		cout<< "Hello! I'm a running watchdog processError %d\n" << isProcessError() << endl;

	}

	set_user_LED_status(LED_USER1_OFF);


//	int countuSecs=500000; //us for each while loop iteration
//	int erroruSec=15000000; //us for checking total errors -10 exceeding threshold.
//	int errorThreshold=5; //number of errors in erruSec before a system restart.
//	int count=0;
//	int countCheck=erroruSec/countuSecs;
//	int led=0;
//	return;
//	while(acquire){
//		myusleep(500000); //sleep for half a second
//		if (daqSystem->getErrorCount()>0){
//			printf("Watchdog: FPGA error count>0: %d!\n",daqSystem->getErrorCount());
//			led=1;
//		}
//		else{
//			led=LED_USER1_GREEN;
//		}
//
//		set_user_LED_status(LED_USER1_OFF);
//		set_user_LED_status(led);
//
//		/*keep track of the total while loop iterations*/
//		count++;
//
//		if (count%countCheck==0 ){
//			printf(currentDateTime().c_str());
//			printf(" Watchdog: Checking error count. Total Errors = %d\n", daqSystem->getErrorCount());
//			if (daqSystem->getErrorCount()>errorThreshold){
//				/*
//				 * Gone over the max number of errors allowed in error period. Restart the cRio.
//				 * Although this can be achived using FPGA, if it is broken for whatever need to perform a restart using
//				 * OS.
//				 */
////				acquire=false;
////				set_FPGA_go(false);
////				set_serial_go(false);
//				stop(false);
//				myusleep(500000); //sleep for half a second to allow things to finish up.
//				system("/sbin/reboot");
//			}
//			daqSystem->resetErrorCount(); //return the error count to zero.
//		}
//
//	}
}
