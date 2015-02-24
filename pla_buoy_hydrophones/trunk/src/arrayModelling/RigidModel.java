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
	
	private HArray array;

	public RigidModel(HArray array){
		this.array=array; 
	}

	@Override
	public ArrayPos getTransformedPositions(ArrayList<HArray> childArrays,
			ArrayList<Hydrophone> hydrophones,
			ArrayList<MovementSensor> movementSensors, long time) {
		
		System.out.println("Rigid Model: no. hydrophones: "+hydrophones.size()+  " no. child arrays: "+childArrays.size());
		
		ArrayPos arrayPos= new ArrayPos(); 
		arrayPos.setParentHArray(getArray());
		arrayPos.setChildArrays(childArrays);

		
		ArrayList<double[]> hydrophonePositions=new ArrayList<double[]>(); 
		for (int i=0; i<hydrophones.size(); i++ ){
			hydrophonePositions.add(hydrophones.get(i).getPosition());
		}
		
		ArrayList<double[]> childArrayPositions=new ArrayList<double[]>(); 
		for (int i=0; i<childArrays.size(); i++ ){
			childArrayPositions.add(childArrays.get(i).getParentAttachPoint());
		}
		
		//a rigid array can only have one sensor reading -> one depth -> one lat/long -> one set of euler angles. 
		
		//TEMP- need to actually calculate positions. 
		arrayPos.setStreamerPositions(null);
		arrayPos.setHydrophonePositions(hydrophonePositions);
		arrayPos.setChildArrayPositions(childArrayPositions);
		
		return arrayPos;
	}
	
	private Double[] getSensorData(ArrayList<MovementSensor> movementSensors){
		return new Double[5];
	}

	@Override
	public HArray getArray() {
		return array;
	}




}
