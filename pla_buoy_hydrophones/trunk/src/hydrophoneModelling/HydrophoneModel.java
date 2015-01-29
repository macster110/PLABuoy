package hydrophoneModelling;

import java.util.ArrayList;

import sensorInput.MovementSensor;

/**
 * Method to model the positions of hydrophones
 * @author Jamie Macaulay
 *
 */
public interface HydrophoneModel {
	
	void getHydrophonePositions(ArrayList<Hydrophone> hydrophones, ArrayList<MovementSensor> movementSensors);

}
