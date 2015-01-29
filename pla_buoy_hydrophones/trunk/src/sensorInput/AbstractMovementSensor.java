package sensorInput;

import javafx.scene.layout.Pane;

abstract class AbstractMovementSensor implements MovementSensor {
	
	/**
	 * Get the path to the data 
	 * @return the path to the folder which contains data
	 */
	abstract String getDataPath(); 
	
	/**
	 * Load sensor data between two times so available in memory. 
	 * @param time1 - start time in millis to load data from. 
	 * @param time2 - end time in millis to load data to.
	 */
	abstract void loadData(long time1, long time2);
	
	/**
	 * Get pane with GUI to allow users to change sensor settings
	 * @return pane with GUI to allow users to change settings of sensor. 
	 */
	abstract Pane getSettingsPane(); 
	

}
