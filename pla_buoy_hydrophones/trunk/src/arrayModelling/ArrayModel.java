package arrayModelling;

import java.util.ArrayList;

import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;
import dataUnits.movementSensors.MovementSensor;

/**
 * Method to model the positions of hydrophones. Different array types may have the same or different associated array models. These cna be relatively simple, e.g. standard 3D 
 * transfomation or more complex utilising multiple sensors, e.g. threading hydrophone. 
 * @author Jamie Macaulay
 *
 */
public interface ArrayModel {
	
	/**
	 * Model the positions of hydrophones and the reference positions of child arrays for an array. 
	 * @param childArrays - any arrays which are a child of the array utilising this model will need new reference positions,.
	 * @param hydrophones - hydrophone belonging to the array utilising this model require new positions. 
	 * @param movementSensors - movement sensors used to model array movement
	 * @param time - the time to model array for. If -1 the model uses simulated movement sensor readings {@link SensorsSimPane} 
	 * @return a new ArrayPos- contains new hydrophone positions
	 */
	public ArrayPos getTransformedPositions(ArrayList<HArray> childArrays, ArrayList<Hydrophone> hydrophones, ArrayList<MovementSensor> movementSensors, long time);
	
	/**
	 * Get the array the model belongs to. 
	 * @return the array the model belongs to. 
	 */
	public HArray getArray();
		
	
	
}
