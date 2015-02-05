//============================================================================
// Name        : cRioTestC.cpp
// Author      : Jamie Macaualay
// Version     :
// Copyright   : None
// Description : Hello World in C++, Ansi-style
//============================================================================

/* Required for console interactions */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
/*pthread library for running on different threads*/
#include "mythread.h"
#include <string.h>
#include <unistd.h>
#include <stdio.h>
/* Includes high level functions for reading DAQ card*/
//#include "NiFpgaManager.h"
///*Includes high level functions for reading Serial port */
//#include "ReadSerial.h"
/*Useful functions including switching on and off LEDs*/
#include "Utils.h"
#include "process/processdata.h"
#include "RealTimer.h"
#include "command/CommandManager.h"
#include "Settings.h"

#include "command/UDPCommands.h"
#include "daq/SimulatedDaq.h"
#ifdef WINOWS
#include "daq/DaqMxSystem.h"
#include "winsock.h"
#else
#include "daq/FPGADaqSystem.h"
#endif

using namespace std;
//#include <thread>

DAQSystem* daqSystem;

/*Define some functions*/
void watchdog_monitor();
void watchdog_thread();
void get_user_commands();
//void user_command(int command);
int get_user_input_num();
std::string get_user_input_string();

/**
 * Indicator for running the command loop
 */
bool volatile go=true;

/**
 * flag for continuing to acquire data.
 */
volatile bool acquire = false;

/**
 * The user input- number rto specify command for cRio
 */
int UserInput;

/**
 * Command thread.
 */
pthread_t user_input_thread;

int main(int argc, char *argv[]){

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

	// create the data processes.
	processCreate();

	/*
	 * Start a watch dog thread to make sure we're not clocking up large number of errors and show
	 * status leds.
	 */
	watchdog_thread();

	/**
	 * Create and launch the UDP command browser.
	 */
	udpCommands = new UDPCommands();

	/*Send single long command string to the command manager. */
	if (argc>=2) {
		string allCommands = joinstrings(argc-1, &argv[1]);
		commandManager->processCommand(allCommands);
	}

	/*Wait for commands on main thread;*/
	get_user_commands();

//	/*Test serial read*/
//	record_Serial(1,B4800);

//	/*Test write .wav file*/
//	createSoundFile(8, 42000);
//	testWavWrite();

	/**Make sure LED's are off**/
//	set_user_LED_status(LED_USER1_OFF);
//	set_user_LED_status(LED_STATUS_OFF);
	udpCommands->stopUDPThread(true);
	// clean up processes.
	processDelete();

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
		string ans = commandManager->processCommand(cmd);
//		fflush(stdout);
		printf("Command \"%s\" answered \"%s\"\n", cmd.c_str(), ans.c_str());
//		fflush(stdout);

		count++;
	}
	printf("Leaving terminal control loop\n");
}

bool start() {
	if (acquire==true) {
		printf("Daq system is already running\n");
		return false;
	}
	acquire=true;
	printf("Initiating cRio recording\n");
	processInit(NCHANNELS, SAMPLERATE);
	/*Start recording data from serial port*/
	//			record_Serial(1,B4800);
	/**Start FPGA tasks**/
	daqSystem->prepare();
	return daqSystem->start();
//	FPGA_Tasks_Thread();
//	return true;
}

bool stop() {
	if (acquire == false) return false;
	acquire=false;
	bool ans = daqSystem->stop();
//	set_FPGA_go(false);
//	set_serial_go(false);
//	pthread_join(get_FPGA_thread(), NULL);
	processEnd();
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
/**
 * Entry function for pthread to monitor FPGA
 */
void *watchdog_thread_function(void *param)
{
	watchdog_monitor();
	return NULL;
}

/**
 * Start FPGA monitor on new thread.
 */
void watchdog_thread(){
	pthread_t watchdog_monitor_thread;
	int thread_var1 = 0;
//	if(pthread_create(&watchdog_monitor_thread, NULL, watchdog_thread_function, &thread_var1)){
//			fprintf(stderr, "Watchdog: creating watchdog thread\n");
//			return;
//	}
}


/**
 * Sits and monitors the FPGA every half second.
 */
void watchdog_monitor(){
	int countuSecs=500000; //us for each while loop iteration
	int erroruSec=15000000; //us for checking total errors -10 exceeding threshold.
	int errorThreshold=5; //number of errors in erruSec before a system restart.
	int count=0;
	int countCheck=erroruSec/countuSecs;
	int led=0;
	return;
	while(acquire){
		myusleep(500000); //sleep for half a second
		if (daqSystem->getErrorCount()>0){
			printf("Watchdog: FPGA error count>0: %d!\n",daqSystem->getErrorCount());
			led=1;
		}
		else{
			led=LED_USER1_GREEN;
		}

		set_user_LED_status(LED_USER1_OFF);
		set_user_LED_status(led);

		/*keep track of the total while loop iterations*/
		count++;

		if (count%countCheck==0 ){
			printf(currentDateTime().c_str());
			printf(" Watchdog: Checking error count. Total Errors = %d\n", daqSystem->getErrorCount());
			if (daqSystem->getErrorCount()>errorThreshold){
				/*
				 * Gone over the max number of errors allowed in error period. Restart the cRio.
				 * Although this can be achived using FPGA, if it is broken for whatever need to perform a restart using
				 * OS.
				 */
//				acquire=false;
//				set_FPGA_go(false);
//				set_serial_go(false);
				stop();
				myusleep(500000); //sleep for half a second to allow things to finish up.
				system("/sbin/reboot");
//				NiFpga_WriteBool(session_FPGA, NiFpga_NI_9222_Anologue_DAQ2_FPGA_ControlBool_SystemReset,true);
			}
			daqSystem->resetErrorCount(); //return the error count to zero.
		}

	}
}


///**
// * Entry function for pthread for user input
// */
//void *command_input_function(void *param)
//{
//	get_user_commands();
//	return NULL;
//}
//
///**
// * Start FPGA monitor on new thread.
// */
//void command_input_thread(){
//	int thread_var1 = 0;
//	if(pthread_create(&user_input_thread, NULL, command_input_function, &thread_var1)){
//			fprintf(stderr, "User input: creating input thread\n");
//			return;
//	}
//}










