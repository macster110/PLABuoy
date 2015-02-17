package arrayModelling;

import java.util.ArrayList;

import dataUnits.Hydrophone;
import dataUnits.movementSensors.MovementSensor;

/**
 * Models a linear arrays with movement sensor located at different points along the array. e.g. free hanging vertical array. 
 * @author Jamie Macaulay
 *
 */
public class LinearFlexibleModel implements ArrayModel {
	
	/**
	 * A vertical array of hydrophones.
	 */
	public static final int  FLEXIBLE_VERTICAL=0; 
	
	/**
	 * The type of model. 
	 */
	private int type=FLEXIBLE_VERTICAL;


	public LinearFlexibleModel(){
		
	}
	

	@Override
	public void getHydrophonePositions(ArrayList<Hydrophone> hydrophones,
			ArrayList<MovementSensor> movementSensors) {
		// TODO Auto-generated method stub

	}
	
	public int getModelAlgorithmType() {
		return type;
	}


	public void setModelAlgorithmType(int type) {
		this.type = type;
	}


}
