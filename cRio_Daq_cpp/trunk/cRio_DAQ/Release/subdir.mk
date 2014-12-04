################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../NIFpgaManager.cpp \
../ReadNIDAQ.cpp \
../ReadSerial.cpp \
../Utils.cpp \
../WriteWav.cpp \
../cRioTestC.cpp 

C_SRCS += \
../NiFpga.c 

OBJS += \
./NIFpgaManager.o \
./NiFpga.o \
./ReadNIDAQ.o \
./ReadSerial.o \
./Utils.o \
./WriteWav.o \
./cRioTestC.o 

C_DEPS += \
./NiFpga.d 

CPP_DEPS += \
./NIFpgaManager.d \
./ReadNIDAQ.d \
./ReadSerial.d \
./Utils.d \
./WriteWav.d \
./cRioTestC.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cross G++ Compiler'
	arm-none-linux-gnueabi-g++ -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	arm-none-linux-gnueabi-gcc -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


