package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	 * The types of sensor. Available. 
	 * @author Jamie Macaulay
	 *
	 */
	public enum SensorType {
		Open_TAG
	}; 
	
	/**
	 * Create a new sensor. 
	 * @return the new sensor. 
	 */
	public MovementSensor createNewSensor(SensorType type){
		switch(type){
		case Open_TAG:
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
