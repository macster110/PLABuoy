package main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import layout.ControlPane.ChangeType;
import dataUnits.movementSensors.MovementSensor;
import dataUnits.movementSensors.OpenTagSensor;

/**
 * Manages the create of new sensors. 
 * @author jamie
 *
 */
public class SensorManager {
	
	/**
	 * All sensors attached to the  arrays. 
	 */
	ObservableList<MovementSensor> sensors = FXCollections.observableArrayList();
	
	/**
	 * Reference to the array mode control. 
	 */
	private ArrayModelControl arrayModelControl;
	
	/**
	 * The types of sensor AVAILABLE
	 * @author Jamie Macaulay
	 *
	 */
	public enum SensorType {
		OPEN_TAG
	}; 
	
	public SensorManager(ArrayModelControl arrayModelControl){
		this.arrayModelControl=arrayModelControl;
		sensors.addListener((ListChangeListener.Change<? extends MovementSensor> c) ->{
			arrayModelControl.notifyModelChanged(ChangeType.SENSOR_CHANGED);
		});
	}
	
	/**
	 * Get simulated data for a sensor. 
	 * @param movementSensor - the sensor to get simulated data.
	 * @return array of simulated data for the sleected sensor in format: heading, pitch , roll, (all in RADIANS) latitude, longitude (in DECIMAL), depth (in METERS). 
	 */
	public Double[] getSimSensorData(MovementSensor movementSensor){
		return arrayModelControl.getMainView().getSensorSimPane().getSimSensorData(movementSensor);
	}
	
	/**
	 * Create a new sensor from sensor type. 
	 * @return the new sensor. 
	 */
	public MovementSensor createNewSensor(SensorType type){
		switch(type){
		case OPEN_TAG:
			return new OpenTagSensor(); 
		default:
			break;
		}
		return null;
	}
	
	/**
	 * Get a current list of movement sensors. 
	 * @return a list of current movement sensors. 
	 */
	public ObservableList<MovementSensor> getSensorList(){
		return sensors;
	}

}
