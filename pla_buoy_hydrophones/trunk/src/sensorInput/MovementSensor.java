package sensorInput;

/**
 * Any sensor which detects changes in position or orinetation. Sensors could be IMU units, Vector GPS, Inclinometer etc. etc. 
 * TODO - describe the orientation and x,y,z conventions for whole program. 
 * @author Jamie Macualay 
 *
 */
public interface MovementSensor {
	
	/**
	 * Get the reference orientation i.e. the orientation when the 
	 * @return
	 */
	double[] getOrientationReference(); 
	
	/**
	 * Get Euler angles.
	 * @param time - time in millis 
	 * @return the orientation of the sensor in euler angle; 
	 */
	double[] getOrientationData(long time);
	
	/**
	 * Get the x,y,z position of the sensor relative the (0,0,0). Note, as sensors are usually fixed to an array 
	 * then this will almost always be the same as {@link getReferencePosition()} 
	 * @return the position of the sensor
	 */
	double[] getPosition(long time); 
	
	/**
	 * Get the position of the sensor relative to the (0,0,0) of the hydrophone array. 
	 * @return the x,y,z position relative to 0,0,0 of the hydrophone array
	 */
	double[] getReferencePosition();
		
	

}
