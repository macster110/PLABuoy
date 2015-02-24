package layout;

/**
 * Used in any pane which needs to notify/ recieve notifications when arrays/hydrophones/sensors are added/removed. 
 * @author Jamie Macaulay	
 *
 */
public interface ControlPane {
	
	public enum ChangeType {ARRAY_CHANGED, SENSOR_CHANGED, 
		HYDROPHONE_CHANGED, SIM_SENSOR_TRIGGERED, 
		NEW_ARRAY_POS_CALCULATED}; 
	
	/**
	 * Called whenever a change has occured in any other ControlPane. 
	 * @param type - change flag. 
	 */
	public void notifyChange(ChangeType type);
	
	
	
	

}
