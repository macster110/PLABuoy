package dataUnits.movementSensors;


import javafx.scene.layout.VBox;

public abstract class SensorPane<T extends MovementSensor> extends VBox {
	
	/**
	 * Get data from input controls and set for movementSensor 
	 * @param movementSensor - movementSensor to set data for. 
	 * @return - status flag to return. 0 if all OK and >0 for any errors.
	 */
	public abstract int getParams(T movementSensor); 
	
	/**
	 * Set data from the movement sensor into controls in pane. 
	 * @param movementSensor - the movement sensor to get data from
	 */
	public abstract void setParams(T movementSensor); 
	
	/**
	 * Show an error warning. Called if getParams() returns an error. 
	 * @param errorType - flag for the error. 
	 */
	public abstract void showErrorWarning(int errorType); 
	

}
