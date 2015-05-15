package IMUUtils.MadwickAlgorithm;


/**
 * 
 * Converts sensors data from an IMU, e.g. three axis accelerometer, magnetometer and gyroscope into
 * Euler angles. 
 * 
 * Madwick Algorithm in Java- basically copied directly from Madwick C code,
 * MadgwickAHRS.c
 *
 * Implementation of Madgwick's IMU and AHRS algorithms.
 * See: http://www.x-io.co.uk/node/8#open_source_ahrs_and_imu_algorithms
 *
 * Date			Author          Notes
 * 29/09/2011	SOH Madgwick    Initial release
 * 02/10/2011	SOH Madgwick	Optimised for reduced CPU load
 * 19/02/2012	SOH Madgwick	Magnetometer measurement is normalised
 *
 * @author Jamie Macaulay 
 *
 */
public class MadwickAlgorithm {


//---------------------------------------------------------------------------------------------------
//Header files

//---------------------------------------------------------------------------------------------------
//Definitions

private double sampleFreq	= 100.	;	// sample frequency in Hz
private double betaDef	=	0.45;		// 2 * proportional gain


//---------------------------------------------------------------------------------------------------
//Variable definitions
 private double beta = betaDef;								// 2 * proportional gain (Kp)
 private float q0 = 1.0f, q1 = 0.0f, q2 = 0.0f, q3 = 0.0f;	// quaternion of sensor frame relative to auxiliary frame

//---------------------------------------------------------------------------------------------------

/**
 * AHRS update
 * @param gx - Gyroscope x axis measurement in radians/s.
 * @param gy - Gyroscope y axis measurement in radians/s.
 * @param gz - Gyroscope z axis measurement in radians/s.
 * @param ax - Accelerometer x axis measurement in calibrated units
 * @param ay - Accelerometer y axis measurement in calibrated units
 * @param az - Accelerometer z axis measurement in calibrated units
 * @param mx - Magnetometer x axis measurement in any calibrated units.
 * @param my - Magnetometer y axis measurement in any calibrated units.
 * @param mz - Magnetometer z axis measurement in any calibrated units.
 * @return quaternion representing heading, pitch and roll. 
 */
public double[] MadgwickAHRSupdate(double gx, double gy, double gz, double ax, double ay, double az, double mx, double my, double mz) {
	
	double recipNorm;
	double s0, s1, s2, s3;
	double qDot1, qDot2, qDot3, qDot4;
	double hx, hy;
	double _2q0mx, _2q0my, _2q0mz, _2q1mx, _2bx, _2bz, _4bx, _4bz, _2q0, _2q1, _2q2, _2q3, _2q0q2, _2q2q3, q0q0, q0q1, q0q2, q0q3, q1q1, q1q2, q1q3, q2q2, q2q3, q3q3;

	// Use IMU algorithm if magnetometer measurement invalid (avoids NaN in magnetometer normalisation)
	if((mx == 0.0) && (my == 0.0) && (mz == 0.0)) {
		return MadgwickAHRSupdateIMU(gx, gy, gz, ax, ay, az);
	}

	// Rate of change of quaternion from gyroscope
	qDot1 = 0.5 * (-q1 * gx - q2 * gy - q3 * gz);
	qDot2 = 0.5 * (q0 * gx + q2 * gz - q3 * gy);
	qDot3 = 0.5 * (q0 * gy - q1 * gz + q3 * gx);
	qDot4 = 0.5 * (q0 * gz + q1 * gy - q2 * gx);

	// Compute feedback only if accelerometer measurement valid (avoids NaN in accelerometer normalisation)
	if(!((ax == 0.0) && (ay == 0.0) && (az == 0.0))) {

		// Normalise accelerometer measurement
		recipNorm = invSqrt(ax * ax + ay * ay + az * az);
		ax *= recipNorm;
		ay *= recipNorm;
		az *= recipNorm;   

		// Normalise magnetometer measurement
		recipNorm = invSqrt(mx * mx + my * my + mz * mz);
		mx *= recipNorm;
		my *= recipNorm;
		mz *= recipNorm;

		// Auxiliary variables to avoid repeated arithmetic
		_2q0mx = 2.0 * q0 * mx;
		_2q0my = 2.0 * q0 * my;
		_2q0mz = 2.0 * q0 * mz;
		_2q1mx = 2.0 * q1 * mx;
		_2q0 = 2.0 * q0;
		_2q1 = 2.0 * q1;
		_2q2 = 2.0 * q2;
		_2q3 = 2.0 * q3;
		_2q0q2 = 2.0 * q0 * q2;
		_2q2q3 = 2.0 * q2 * q3;
		q0q0 = q0 * q0;
		q0q1 = q0 * q1;
		q0q2 = q0 * q2;
		q0q3 = q0 * q3;
		q1q1 = q1 * q1;
		q1q2 = q1 * q2;
		q1q3 = q1 * q3;
		q2q2 = q2 * q2;
		q2q3 = q2 * q3;
		q3q3 = q3 * q3;

		// Reference direction of Earth's magnetic field
		hx = mx * q0q0 - _2q0my * q3 + _2q0mz * q2 + mx * q1q1 + _2q1 * my * q2 + _2q1 * mz * q3 - mx * q2q2 - mx * q3q3;
		hy = _2q0mx * q3 + my * q0q0 - _2q0mz * q1 + _2q1mx * q2 - my * q1q1 + my * q2q2 + _2q2 * mz * q3 - my * q3q3;
		_2bx =  Math.sqrt(hx * hx + hy * hy)*2.0; // 13/05/2015- think this has to be multipled by 2?
		_2bz = 2.0*(-_2q0mx * q2 + _2q0my * q1 + mz * q0q0 + _2q1mx * q3 - mz * q1q1 + _2q2 * my * q3 - mz * q2q2 + mz * q3q3); // 13/05/2015- think this has to be multipled by 2?
		_4bx = 2.0 * _2bx;
		_4bz = 2.0 * _2bz;

		// Gradient decent algorithm corrective step
		s0 = -_2q2 * (2.0 * q1q3 - _2q0q2 - ax) + _2q1 * (2.0 * q0q1 + _2q2q3 - ay) - _2bz * q2 * (_2bx * (0.5 - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) + (-_2bx * q3 + _2bz * q1) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) + _2bx * q2 * (_2bx * (q0q2 + q1q3) + _2bz * (0.5 - q1q1 - q2q2) - mz);
		s1 = _2q3 * (2.0 * q1q3 - _2q0q2 - ax) + _2q0 * (2.0 * q0q1 + _2q2q3 - ay) - 4.0 * q1 * (1 - 2.0 * q1q1 - 2.0 * q2q2 - az) + _2bz * q3 * (_2bx * (0.5 - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) + (_2bx * q2 + _2bz * q0) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) + (_2bx * q3 - _4bz * q1) * (_2bx * (q0q2 + q1q3) + _2bz * (0.5 - q1q1 - q2q2) - mz);
		s2 = -_2q0 * (2.0 * q1q3 - _2q0q2 - ax) + _2q3 * (2.0 * q0q1 + _2q2q3 - ay) - 4.0 * q2 * (1 - 2.0 * q1q1 - 2.0 * q2q2 - az) + (-_4bx * q2 - _2bz * q0) * (_2bx * (0.5 - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) + (_2bx * q1 + _2bz * q3) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) + (_2bx * q0 - _4bz * q2) * (_2bx * (q0q2 + q1q3) + _2bz * (0.5 - q1q1 - q2q2) - mz);
		s3 = _2q1 * (2.0 * q1q3 - _2q0q2 - ax) + _2q2 * (2.0 * q0q1 + _2q2q3 - ay) + (-_4bx * q3 + _2bz * q1) * (_2bx * (0.5 - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) + (-_2bx * q0 + _2bz * q2) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) + _2bx * q1 * (_2bx * (q0q2 + q1q3) + _2bz * (0.5 - q1q1 - q2q2) - mz);
		recipNorm = invSqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3); // normalise step magnitude
		s0 *= recipNorm;
		s1 *= recipNorm;
		s2 *= recipNorm;
		s3 *= recipNorm;

		// Apply feedback step
		qDot1 -= beta * s0;
		qDot2 -= beta * s1;
		qDot3 -= beta * s2;
		qDot4 -= beta * s3;
	}

	// Integrate rate of change of quaternion to yield quaternion
	q0 += qDot1 * (1.0 / sampleFreq);
	q1 += qDot2 * (1.0 / sampleFreq);
	q2 += qDot3 * (1.0 / sampleFreq);
	q3 += qDot4 * (1.0 / sampleFreq);

	// Normalise quaternion
	recipNorm = invSqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
	q0 *= recipNorm;
	q1 *= recipNorm;
	q2 *= recipNorm;
	q3 *= recipNorm;
	
	double[] quaternion={q0,q1,q2,q3};
	
	return quaternion;
	
}


public float[] MadgwickAHRSupdatef(float gx, float gy, float gz, float ax, float ay, float az, float mx, float my, float mz) {
	float recipNorm;
	float s0, s1, s2, s3;
	float qDot1, qDot2, qDot3, qDot4;
	float hx, hy;
	float _2q0mx, _2q0my, _2q0mz, _2q1mx, _2bx, _2bz, _4bx, _4bz, _2q0, _2q1, _2q2, _2q3, _2q0q2, _2q2q3, q0q0, q0q1, q0q2, q0q3, q1q1, q1q2, q1q3, q2q2, q2q3, q3q3;

	// Use IMU algorithm if magnetometer measurement invalid (avoids NaN in magnetometer normalisation)
	if((mx == 0.0f) && (my == 0.0f) && (mz == 0.0f)) {
		MadgwickAHRSupdateIMU(gx, gy, gz, ax, ay, az);
		return null;
	}

	// Rate of change of quaternion from gyroscope
	qDot1 = 0.5f * (-q1 * gx - q2 * gy - q3 * gz);
	qDot2 = 0.5f * (q0 * gx + q2 * gz - q3 * gy);
	qDot3 = 0.5f * (q0 * gy - q1 * gz + q3 * gx);
	qDot4 = 0.5f * (q0 * gz + q1 * gy - q2 * gx);

	// Compute feedback only if accelerometer measurement valid (avoids NaN in accelerometer normalisation)
	if(!((ax == 0.0f) && (ay == 0.0f) && (az == 0.0f))) {

		// Normalise accelerometer measurement
		recipNorm = invSqrt(ax * ax + ay * ay + az * az); //FIXME
		recipNorm =(float) (1/Math.sqrt(ax * ax + ay * ay + az * az)); 
		ax *= recipNorm;
		ay *= recipNorm;
		az *= recipNorm;   

		// Normalise magnetometer measurement
		recipNorm = invSqrt(mx * mx + my * my + mz * mz); //FIXME
		recipNorm =(float) (1/Math.sqrt(mx * mx + my * my + mz * mz)); 
		mx *= recipNorm;
		my *= recipNorm;
		mz *= recipNorm;
		
		System.out.println("ax: "+ax +" ay: "+ay +" az: "+az);
		System.out.println("mx: "+mx +" my: "+my +" az: "+mz);


		// Auxiliary variables to avoid repeated arithmetic
		_2q0mx = 2.0f * q0 * mx;
		_2q0my = 2.0f * q0 * my;
		_2q0mz = 2.0f * q0 * mz;
		_2q1mx = 2.0f * q1 * mx;
		_2q0 = 2.0f * q0;
		_2q1 = 2.0f * q1;
		_2q2 = 2.0f * q2;
		_2q3 = 2.0f * q3;
		_2q0q2 = 2.0f * q0 * q2;
		_2q2q3 = 2.0f * q2 * q3;
		q0q0 = q0 * q0;
		q0q1 = q0 * q1;
		q0q2 = q0 * q2;
		q0q3 = q0 * q3;
		q1q1 = q1 * q1;
		q1q2 = q1 * q2;
		q1q3 = q1 * q3;
		q2q2 = q2 * q2;
		q2q3 = q2 * q3;
		q3q3 = q3 * q3;

		// Reference direction of Earth's magnetic field
		hx = mx * q0q0 - _2q0my * q3 + _2q0mz * q2 + mx * q1q1 + _2q1 * my * q2 + _2q1 * mz * q3 - mx * q2q2 - mx * q3q3;
		hy = _2q0mx * q3 + my * q0q0 - _2q0mz * q1 + _2q1mx * q2 - my * q1q1 + my * q2q2 + _2q2 * mz * q3 - my * q3q3;
		_2bx = (float) (2.0f*(Math.sqrt(hx * hx + hy * hy))); //b(2) in MATLAB (note: had to multiply this by 2 as bug in code? )
		_2bz = 2.0f*(-_2q0mx * q2 + _2q0my * q1 + mz * q0q0 + _2q1mx * q3 - mz * q1q1 + _2q2 * my * q3 - mz * q2q2 + mz * q3q3); //b(4) in MATLAB (note: had to multiply this by 2 as bug in code? )
		_4bx = 2.0f * _2bx;
		_4bz = 2.0f * _2bz;
		
//		System.out.println("hx: "+hx +" hy: "+hy + " _2bx "+_2bx+" _2bz "+ _2bz +" _4bx "+ _4bx+ " _4bz "+_4bz);
		System.out.println("q0 "+ q0+" q1 "+q1+" q2 "+q2+" q3 "+q3);
		
//		System.out.println("s0 -1: "+(-_2q2 * (2.0f * q1q3 - _2q0q2 - ax)));
//		System.out.println("s0 -2: "+(_2q1 * (2.0f * q0q1 + _2q2q3 - ay) ));
//		System.out.println("s0 -3: "+(0));
//		System.out.println("s0 -4: "+(- _2bz * q2 * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) ));
//		System.out.println("s0 -5: "+((-_2bx * q3 + _2bz * q1) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my)));
//		System.out.println("s0 -6: "+(_2bx * q2 * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz)));
		
		System.out.println("s1 -1: "+(_2q3 * (2.0f * q1q3 - _2q0q2 - ax) ));
		System.out.println("s1 -2: "+(_2q0 * (2.0f * q0q1 + _2q2q3 - ay) ));
		System.out.println("s1 -3: "+(- 4.0f * q1 * (1 - 2.0f * q1q1 - 2.0f * q2q2 - az) ));
		System.out.println("s1 -4: "+(_2bz * q3 * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) 	));
		System.out.println("s1 -5: "+((_2bx * q2 + _2bz * q0) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) 		));
		System.out.println("s1 -6: "+(_2bx * q3 - _4bz * q1) * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz));


		// Gradient decent algorithm corrective step
		s0 = -_2q2 * (2.0f * q1q3 - _2q0q2 - ax) 	+ _2q1 * (2.0f * q0q1 + _2q2q3 - ay) 	- _2bz * q2 * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) 				+ (-_2bx * q3 + _2bz * q1) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) 			+ _2bx * q2 * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz);
		s1 = _2q3 * (2.0f * q1q3 - _2q0q2 - ax) 	+ _2q0 * (2.0f * q0q1 + _2q2q3 - ay)	- 4.0f * q1 * (1 - 2.0f * q1q1 - 2.0f * q2q2 - az) 										+ _2bz * q3 * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) 					+ (_2bx * q2 + _2bz * q0) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) 		+ (_2bx * q3 - _4bz * q1) * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz);
		s2 = -_2q0 * (2.0f * q1q3 - _2q0q2 - ax) 	+ _2q3 * (2.0f * q0q1 + _2q2q3 - ay) 	- 4.0f * q2 * (1 - 2.0f * q1q1 - 2.0f * q2q2 - az) 										+ (-_4bx * q2 - _2bz * q0) * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) 		+ (_2bx * q1 + _2bz * q3) * (_2bx * (q1q2 - q0q3) + _2bz * (q0q1 + q2q3) - my) 		+ (_2bx * q0 - _4bz * q2) * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz);
		s3 = _2q1 * (2.0f * q1q3 - _2q0q2 - ax) 	+ _2q2 * (2.0f * q0q1 + _2q2q3 - ay) 	+ (-_4bx * q3 + _2bz * q1) * (_2bx * (0.5f - q2q2 - q3q3) + _2bz * (q1q3 - q0q2) - mx) 	+ (-_2bx * q0 + _2bz * q2) * (_2bx * (q1q2 - q0q3)+ _2bz * (q0q1 + q2q3) - my) 				+ _2bx * q1 * (_2bx * (q0q2 + q1q3) + _2bz * (0.5f - q1q1 - q2q2) - mz);
		
		System.out.println("s0: "+s0 +" s1: "+s1 +" s2: "+s2+" s3: "+s3);
		
		recipNorm = invSqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3); // normalise step magnitude
		s0 *= recipNorm;
		s1 *= recipNorm;
		s2 *= recipNorm;
		s3 *= recipNorm;

		// Apply feedback step
		qDot1 -= beta * s0;
		qDot2 -= beta * s1;
		qDot3 -= beta * s2;
		qDot4 -= beta * s3;
	}

	// Integrate rate of change of quaternion to yield quaternion
	q0 += qDot1 * (1.0f / sampleFreq);
	q1 += qDot2 * (1.0f / sampleFreq);
	q2 += qDot3 * (1.0f / sampleFreq);
	q3 += qDot4 * (1.0f / sampleFreq);

	// Normalise quaternion
	recipNorm = (float) (1/Math.sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3));
	q0 *= recipNorm;
	q1 *= recipNorm;
	q2 *= recipNorm;
	q3 *= recipNorm;
	
	float[] quaternion={q0,q1,q2,q3};
	
	return quaternion;
}

//---------------------------------------------------------------------------------------------------


/**
 * Imu algortihm update, 
 * @param gx - Gyroscope x axis measurement in radians/s.
 * @param gy - Gyroscope y axis measurement in radians/s.
 * @param gz - Gyroscope z axis measurement in radians/s.
 * @param ax - Accelerometer x axis measurement in calibrated units
 * @param ay - Accelerometer y axis measurement in calibrated units
 * @param az - Accelerometer z axis measurement in calibrated units
 * @return quaternion representing heading, pitch and roll. 
 */
public double[] MadgwickAHRSupdateIMU(double gx, double gy, double gz, double ax, double ay, double az) {
	double recipNorm;
	double s0, s1, s2, s3;
	double qDot1, qDot2, qDot3, qDot4;
	double _2q0, _2q1, _2q2, _2q3, _4q0, _4q1, _4q2 ,_8q1, _8q2, q0q0, q1q1, q2q2, q3q3;

	// Rate of change of quaternion from gyroscope
	qDot1 = 0.5f * (-q1 * gx - q2 * gy - q3 * gz);
	qDot2 = 0.5f * (q0 * gx + q2 * gz - q3 * gy);
	qDot3 = 0.5f * (q0 * gy - q1 * gz + q3 * gx);
	qDot4 = 0.5f * (q0 * gz + q1 * gy - q2 * gx);

	// Compute feedback only if accelerometer measurement valid (avoids NaN in accelerometer normalisation)
	if(!((ax == 0.0f) && (ay == 0.0f) && (az == 0.0f))) {

		// Normalise accelerometer measurement
		recipNorm = invSqrt(ax * ax + ay * ay + az * az);
		ax *= recipNorm;
		ay *= recipNorm;
		az *= recipNorm;   

		// Auxiliary variables to avoid repeated arithmetic
		_2q0 = 2.0f * q0;
		_2q1 = 2.0f * q1;
		_2q2 = 2.0f * q2;
		_2q3 = 2.0f * q3;
		_4q0 = 4.0f * q0;
		_4q1 = 4.0f * q1;
		_4q2 = 4.0f * q2;
		_8q1 = 8.0f * q1;
		_8q2 = 8.0f * q2;
		q0q0 = q0 * q0;
		q1q1 = q1 * q1;
		q2q2 = q2 * q2;
		q3q3 = q3 * q3;

		// Gradient decent algorithm corrective step
		s0 = _4q0 * q2q2 + _2q2 * ax + _4q0 * q1q1 - _2q1 * ay;
		s1 = _4q1 * q3q3 - _2q3 * ax + 4.0f * q0q0 * q1 - _2q0 * ay - _4q1 + _8q1 * q1q1 + _8q1 * q2q2 + _4q1 * az;
		s2 = 4.0f * q0q0 * q2 + _2q0 * ax + _4q2 * q3q3 - _2q3 * ay - _4q2 + _8q2 * q1q1 + _8q2 * q2q2 + _4q2 * az;
		s3 = 4.0f * q1q1 * q3 - _2q1 * ax + 4.0f * q2q2 * q3 - _2q2 * ay;
		recipNorm = invSqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3); // normalise step magnitude
		s0 *= recipNorm;
		s1 *= recipNorm;
		s2 *= recipNorm;
		s3 *= recipNorm;

		// Apply feedback step
		qDot1 -= beta * s0;
		qDot2 -= beta * s1;
		qDot3 -= beta * s2;
		qDot4 -= beta * s3;
	}

	// Integrate rate of change of quaternion to yield quaternion
	q0 += qDot1 * (1.0f / sampleFreq);
	q1 += qDot2 * (1.0f / sampleFreq);
	q2 += qDot3 * (1.0f / sampleFreq);
	q3 += qDot4 * (1.0f / sampleFreq);

	// Normalise quaternion
	recipNorm = invSqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
	q0 *= recipNorm;
	q1 *= recipNorm;
	q2 *= recipNorm;
	q3 *= recipNorm;
	
	double[] quaternion={q0,q1,q2,q3};
	
	return quaternion;
	
}

public double getSampleFreq() {
	return sampleFreq;
}

public void setSampleFreq(float sampleFreq) {
	this.sampleFreq = sampleFreq;
}

public double getBetaDef() {
	return betaDef;
}

public void setBetaDef(float betaDef) {
	this.betaDef = betaDef;
}

/**
 * 
 * @param timeMillis
 * @param magnotometer
 * @param accelerometer
 * @param gyroscope
 * @param gyroffset
 * @param magnoOffset
 */
public Quaternion[] runMadWickAglorithm(long[] timeMillis, double[][] magnotometer, double[][] accelerometer, double[][] gyroscope, double[] gyroffset, double[] magnoOffset){
	Quaternion[] quaternionArray=new Quaternion[timeMillis.length];
	for (int i=0; i<timeMillis.length; i++){
		if (i%10000==0) System.out.println("Madwick IMU algorithm:  Processed: " +i+" of "+timeMillis.length); 
//		System.out.println("Accelerometer: "+(float) accelerometer[i][1] + " " + (float) accelerometer[i][0] + " "+ (float) accelerometer[i][2]);
//		System.out.println("Gyroscope: "+(float) gyroscope[i][1] + " " + (float) gyroscope[i][0] + " "+ (float) gyroscope[i][2]);
//		System.out.println("Magnotometer: "+(float) magnotometer[i][1] + " " + (float) magnotometer[i][0] + " "+ (float) magnotometer[i][2]);
		
//		float [] quaternionResult=MadgwickAHRSupdate2((float) gyroscope[i][1], (float) gyroscope[i][0], (float) gyroscope[i][2], 
//				(float) accelerometer[i][1], (float) accelerometer[i][0], (float) accelerometer[i][2], 
//				(float) magnotometer[i][1], (float) magnotometer[i][0],  (float) magnotometer[i][2]); 

		double[] quaternionResult=MadgwickAHRSupdate(gyroscope[i][1], gyroscope[i][0], gyroscope[i][2], 
											accelerometer[i][1], accelerometer[i][0], accelerometer[i][2], 
											magnotometer[i][1], magnotometer[i][0],  magnotometer[i][2]); 
		
		
//		quaternionResult=MadgwickAHRSupdateIMU(gyroscope[i][1], gyroscope[i][0], gyroscope[i][2], 
//				accelerometer[i][1], accelerometer[i][0], accelerometer[i][2]);
		
		quaternionArray[i]=new Quaternion(quaternionResult); 
	}
	
	return quaternionArray;
}


//---------------------------------------------------------------------------------------------------
//Fast inverse square-root
//See: http://en.wikipedia.org/wiki/Fast_inverse_square_root
//http://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java

public static float invSqrt(float x) {
	float xhalf = 0.5f*x;
	int i = Float.floatToIntBits(x);
	i = 0x5f3759df - (i>>1);
	x = Float.intBitsToFloat(i);
	x = x*(1.5f - xhalf*x*x);
	return x;
}

public static double invSqrt(double x){
	double xhalf = 0.5d*x;
	long i = Double.doubleToLongBits(x);
	i = 0x5fe6ec85e7de30daL - (i>>1);
	x = Double.longBitsToDouble(i);
	x = x*(1.5d - xhalf*x*x);
	return x;
}

}


