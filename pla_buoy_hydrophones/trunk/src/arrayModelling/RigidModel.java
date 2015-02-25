package arrayModelling;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
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
		

		
		ArrayList<double[]> hydrophonePositions=new ArrayList<double[]>(); 
		for (int i=0; i<hydrophones.size(); i++ ){
			
			//// TEMP ////
			hydrophones.get(i).xPosProperty().setValue(hydrophones.get(i).getPosition()[0]+Math.random()*5);
			hydrophones.get(i).yPosProperty().setValue(hydrophones.get(i).getPosition()[1]+Math.random()*5);
			System.out.println("hydrophones.get(i).getPosition()[0] "+hydrophones.get(i).getPosition()[0] + " hydrophones.get(i).getPosition()[1] "+hydrophones.get(i).getPosition()[1]);
			//// TEMP ////
			
			hydrophonePositions.add(hydrophones.get(i).getPosition());
		}
		
		ArrayList<double[]> childArrayPositions=new ArrayList<double[]>(); 
		for (int i=0; i<childArrays.size(); i++ ){
			childArrayPositions.add(childArrays.get(i).getParentAttachPoint());
		}
		
		//a rigid array can only have one sensor reading-> one depth -> one latitude/longitude -> one set of Euler angles. Find array positions. 
		Double[] sensorData=getSensorData(movementSensors);
		
		// transform array. 
		ArrayPos arrayPos=transformPositions(childArrayPositions, hydrophonePositions, sensorData);
		//set arrays. 
		arrayPos.setParentHArray(getArray());
		arrayPos.setChildArrays(childArrays);
	
		return arrayPos;
	}
	
	/**
	 * Calculate transform hydrophone positions.  
	 * @param childArrayPos - position of children on array
	 * @param hydrophonePos - position of hydrophones on array 
	 * @param sensorData - sensor data - because rigid arrays are rigid they may only have one of each type of sensor data type. e.g one heading, pitch, roll, depth and 
	 * latitude/longitude. sensorData is in folowing form. sensorDara={heading, pitch, roll, depth, latitude, longitude}. Element are null if no data exists 
	 * @return
	 */
	private ArrayPos transformPositions(ArrayList<double[]> childArrayPos, 	ArrayList<double[]> hydrophonePos, Double[] sensorData, Point3D rotationPoint){
		
		ArrayPos arrayPos=new ArrayPos(); 
		ArrayList<double[]> hydrophonePosTRansform= new ArrayList<double[]>(); 
		ArrayList<double[]> childArrayPosTRansform= new ArrayList<double[]>(); 


		//lets rotate stuff - use the JavaFX 3D library as all rotation matrices have already been done for us!
		//heading - we rotate around the z axis
		Rotate rotHeading=new Rotate(Math.toDegrees(sensorData[0]), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.Z_AXIS);
		//pitch- we rotate about y axis
		Rotate rotPitch=new Rotate(Math.toDegrees(sensorData[1]), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.Y_AXIS);
		//roll - we rotate about x axis
		Rotate rotRoll=new Rotate(Math.toDegrees(sensorData[2]), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.X_AXIS);
		
		//now apply transform. 
		
		Point3D transformedPoint; 
		for (int i=0; i<hydrophonePos.size(); i++){
			transformedPoint =new Point3D(hydrophonePos.get(i)[0], hydrophonePos.get(i)[1], hydrophonePos.get(i)[2]); 
			transformedPoint=rotHeading.transform(transformedPoint);
			transformedPoint=rotPitch.transform(transformedPoint);
			transformedPoint=rotRoll.transform(transformedPoint);
			double[] transformedPos={transformedPoint.getX(), transformedPoint.getY(), transformedPoint.getZ()};
		}
		
		
		Point3D newPoint;
		
		
		
		return null;
	}
	
	private Double[] getSensorData(ArrayList<MovementSensor> movementSensors){
		
		for (int i=0; i<movementSensors.size(); i++){
			
		}
		
		return new Double[5];
	}

	@Override
	public HArray getArray() {
		return array;
	}




}
