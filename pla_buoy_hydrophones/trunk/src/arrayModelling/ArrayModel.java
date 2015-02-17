package arrayModelling;

import java.util.ArrayList;

import dataUnits.Hydrophone;
import dataUnits.movementSensors.MovementSensor;

/**
 * Method to model the positions of hydrophones
 * @author Jamie Macaulay
 *
 */
public interface ArrayModel {
	
	void getHydrophonePositions(ArrayList<Hydrophone> hydrophones, ArrayList<MovementSensor> movementSensors);

}
