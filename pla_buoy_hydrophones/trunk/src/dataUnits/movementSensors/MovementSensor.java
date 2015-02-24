package dataUnits.movementSensors;

import main.SensorManager.SensorType;
import dataUnits.hArray.HArray;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 * Any sensor which detects changes in position or orientation. Sensors could be IMU units, Vector GPS, Inclinometer etc. etc. 
 * <p><p>
 * Conventions
 * <p><p>
 * Orientation (Euler angles)
 * <p>
 * Bearing- 0==north, 90==east 180/-180=south, -90==west 0==north
 * <p>
 * Pitch- 90=-g, 0=0g, -90=g
 * <p>
 * Tilt 0->180 -sensor turning towards left to upside down 0->-180 sensor turning right to upside down
 * <p><p>
 * Position
 * <p>
 * Latitude - decimal
 * <p>
 * Longitude - decimal
 * <p>
 * height - 0 is sea level. Positive values are above sea. Negative values are below sea.
 * 
 * @author Jamie Macualay 
 */
public interface MovementSensor {
	
	/**
	 * The name of the sensor. 
	 * @return the name of the sensor
	 */
	public StringProperty sensorNameProperty(); 

	/**
	 * The type of sensor. e.g. Open Tag, IS Inclinometer, Hemisphere vector GPS vs100
	 * @return the type of sensor. 
	 */
	public ObjectProperty<SensorType> sensorTypeProperty();
	
	/**
	 * The array the sensor belongs to. 
	 * @return the array the sensor belongs to. 
	 */
	public ObjectProperty<HArray> parentArrayProperty(); 

	/**
	 * Get the reference orientation i.e. the orientation when the sensor is sitting on the array in it's default position. 
	 * @return the orientation reference, (heading, pitch and roll) in RADIANS.
	 */
	public double[] getOrientationReference(); 
	
	/**
	 * The reference heading property. Heading of the tag when array is in default position (i.e no movement) in RADIANS. 
	 * @return the heading in RADIANS, 
	 */
	public DoubleProperty headingRefProperty();
	
	/**
	 * The reference pitch property (-pi/2->pi/2). Pitch of the tag when array is in default position (i.e no movement) in RADIANS. 
	 * @return the pitch in RADIANS, 
	 */
	public DoubleProperty pitchRefProperty();

	/**
	 * The reference roll property. Roll of the tag when array is in default position (i.e no movement) in RADIANS. 
	 * @return the roll in RADIANS, 
	 */
	public DoubleProperty rollRefProperty();

	/**
	 * Get Euler angles. 
	 * @param time - time in millis. -1 is used when simulating array positions 
	 * @return the orientation of the sensor  in Euler angles (heading, pitch and roll) in RADIANS.
	 */
	public double[] getOrientationData(long time);
	
	/**
	 * Get the x,y,z position of the sensor relative the (0,0,0). Note, as sensors are usually fixed to an array 
	 * then this will almost always be the same as {@link getReferencePosition()} 
	 * @param time - time in millis. -1 is used when simulating array positions 
	 * @return the position of the sensor
	 */
	public double[] getPosition(long time); 
	
	/**
	 * Get the position of the sensor relative to the (0,0,0) of the hydrophone array thew sensor is attached to. . 
	 * @return the x,y,z position relative to 0,0,0 of the hydrophone array
	 */
	public double[] getReferencePosition();
	
	/**
	 * The x reference position of the sensor. x is in relation to the (0,0,0) point on the array the sensor belongs to.
	 * @return the property for the x position of the sensor in relation to the array the sensor belongs to. 
	 */
	public DoubleProperty xRefPositionProperty();
	
	/**
	 * The y reference position of the sensor. y is in relation to the (0,0,0) point on the array the sensor belongs to.
	 * @return the property for the y position of the sensor in relation to the array the sensor belongs to. 
	 */
	public DoubleProperty yRefPositionProperty();

	/**
	 * The z reference position of the sensor. z is in relation to the (0,0,0) point on the array the sensor belongs to.
	 * @return the property for the z position of the sensor in relation to the array the sensor belongs to. 
	 */
	public DoubleProperty zRefPositionProperty();
	
	/**
	 * Loads data for sensor. Called before every movement simulation. 
	 * @return true if successful data load. False otherwise. 
	 */
	public boolean loadData();
	
	/**
	 * A list of sensor types the movment sensor has.  
	 * <p>
	 * List is as follows
	 * <p>
	 * heading, pitch , roll, latitude, longitude, depth.  
	 * @return list of sensor types. true if the has sensor type, false otherwise. 
	 */
	public boolean[] getHasSensors(); 
	
	

}
