package arrayModelling;

import java.util.ArrayList;

import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;
import dataUnits.movementSensors.MovementSensor;

/**
 * Models a rigid array. Note that this means you can only have one heading, pitch, roll measurment, perhaps form one sensor, perhaps from multiple sensors. 
 * @author Jamie Macaulay
 *
 */
public class RigidModel implements ArrayModel  {

	@Override
	public ArrayPos getTransformedPositions(ArrayList<HArray> childArrays,
			ArrayList<Hydrophone> hydrophones,
			ArrayList<MovementSensor> movementSensors, long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HArray getArray() {
		// TODO Auto-generated method stub
		return null;
	}




}
