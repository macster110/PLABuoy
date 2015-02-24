package layout.arrays;

import javafx.scene.layout.VBox;
import dataUnits.hArray.HArray;

public abstract class HArrayPane<T extends HArray> extends VBox  {
	
	/**
	 * Get data from input controls and set for movementSensor 
	 * @param movementSensor - movementSensor to set data for. 
	 * @return - status flag to return. 0 if all OK and >0 for any errors.
	 */
	public abstract int getParams(T array); 
	
	/**
	 * Set data from the movement sensor into controls in pane. 
	 * @param movementSensor - the movement sensor to get data from
	 */
	public abstract void setParams(T array); 
	
	/**
	 * Show an error warning. Called if getParams() returns an error. 
	 * @param errorType - flag for the error. 
	 * @return true if warning can be acknowledged and carry on as usual. False to prevent closing dialog. 
	 */
	public abstract boolean showErrorWarning(int errorType); 

}
