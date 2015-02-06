package main;

import main.SensorManager.SensorType;
import javafx.beans.value.ObservableValue;
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
	 * The types of sensor AVAILABLE
	 * @author Jamie Macaulay
	 *
	 */
	public enum SensorType {
		OPEN_TAG
	}; 
	
	/**
	 * Create a new sensor. 
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

	/**
	 * Get the enum type for a sensor class. 
	 * @param movementSensor - the sensor class to find type for. 
	 * @return the type of sensor. Null if not a sensor class. 
	 */
	public SensorType getSensorType(
			MovementSensor movementSensor) {
		if (movementSensor instanceof OpenTagSensor) return SensorType.OPEN_TAG;
		
		return null;
	}

}
