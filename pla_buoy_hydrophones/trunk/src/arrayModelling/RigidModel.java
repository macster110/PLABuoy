package arrayModelling;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
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
//			//// TEMP ////
//			hydrophones.get(i).xPosProperty().setValue(hydrophones.get(i).getPosition()[0]+Math.random()*5);
//			hydrophones.get(i).yPosProperty().setValue(hydrophones.get(i).getPosition()[1]+Math.random()*5);
//			System.out.println("hydrophones.get(i).getPosition()[0] "+hydrophones.get(i).getPosition()[0] + " hydrophones.get(i).getPosition()[1] "+hydrophones.get(i).getPosition()[1]);
//			//// TEMP ////
			
			hydrophonePositions.add(hydrophones.get(i).getPosition());
		}
		
		ArrayList<double[]> childArrayPositions=new ArrayList<double[]>(); 
		for (int i=0; i<childArrays.size(); i++ ){
			childArrayPositions.add(childArrays.get(i).getParentAttachPoint());
		}
		
		ArrayList<double[]> sensorPositions=new ArrayList<double[]>(); 
		for (int i=0; i<movementSensors.size(); i++ ){
			double[] position={movementSensors.get(i).getPosition(-1)[0], movementSensors.get(i).getPosition(-1)[1], movementSensors.get(i).getPosition(-1)[2]};
			sensorPositions.add(position);
		}
		
		//a rigid array can only have one sensor reading-> one depth -> one latitude/longitude -> one set of Euler angles. Find array positions. 
		Double[] sensorData=getSensorData(movementSensors, time);
		
		// transform array. 
		ArrayPos arrayPos=transformPositions(childArrayPositions, hydrophonePositions, sensorPositions, sensorData, new Point3D(0,0,0));
		//set arrays. 
		arrayPos.setHArray(getArray());
		arrayPos.setChildArrays(childArrays);
		arrayPos.setTime(time);

		return arrayPos;
	}
	
	/**
	 * Calculate transform hydrophone positions. Note we are still in the arrays own co-oridnate frame here, not the reference array co-ordinate frame.   
	 * @param childArrayPos - position of children on array
	 * @param hydrophonePos - position of hydrophones on array 
	 * @param sensorData - sensor data - because rigid arrays are rigid they may only have one of each type of sensor data type. e.g one heading, pitch, roll, depth and 
	 * latitude/longitude. sensorData is in following form. sensorDara={heading, pitch, roll, depth, latitude, longitude}. Element are null if no data exists 
	 * @param. 
	 * @return
	 */
	private ArrayPos transformPositions(ArrayList<double[]> childArrayPos, 	ArrayList<double[]> hydrophonePos, ArrayList<double[]> sensorPos, Double[] sensorData, Point3D rotationPoint){
		
		ArrayList<double[]> hydrophonePosTRansform= new ArrayList<double[]>(); 
		ArrayList<double[]> childArrayPosTRansform= new ArrayList<double[]>(); 
		ArrayList<double[]> sensorPosTransform= new ArrayList<double[]>(); 

		ArrayList<ArrayList<Point3D>> streamers=new ArrayList<ArrayList<Point3D>>(); 
		
		//lets rotate stuff - use the JavaFX 3D library as all rotation matrices have already been done for us!
		//heading - we rotate around the z
//		System.out.println("SensorData :"+sensorData); 
//		System.out.println("SensorData: heading "+(sensorData[0]==null  ? 0: Math.toDegrees(sensorData[0])) + " pitch: "+
//		(sensorData[1]==null  ? 0: Math.toDegrees(sensorData[1]))+ " roll: "+-(sensorData[2]==null  ? 0: Math.toDegrees(sensorData[2])));
		
		Rotate rotHeading=new Rotate(sensorData[0]==null  ? 0: -Math.toDegrees(sensorData[0]), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.Z_AXIS);
		//pitch- we rotate about y axis
		Rotate rotPitch=new Rotate(sensorData[1]==null  ? 0: Math.toDegrees(sensorData[1]), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.X_AXIS);
		//roll - we rotate about x axis
		Rotate rotRoll=new Rotate(-(sensorData[2]==null  ? 0: Math.toDegrees(sensorData[2])), rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ(), Rotate.Y_AXIS);
		
		//now apply transform to hydrophone and child array positions 
		Point3D transformedPoint; 
		double[] transformedPos;
		ArrayList<Point3D> streamerPoints;
		for (int i=0; i<hydrophonePos.size(); i++){
//			transformedPoint =new Point3D(hydrophonePos.get(i)[0], hydrophonePos.get(i)[1], hydrophonePos.get(i)[2]); 
//			transformedPoint=rotPitch.transform(transformedPoint);
//			transformedPoint=rotHeading.transform(transformedPoint);
//			transformedPoint=rotRoll.transform(transformedPoint);
//			transformedPos=new double[3];
//			transformedPos[0]=transformedPoint.getX(); transformedPos[1]=transformedPoint.getY(); transformedPos[2]=transformedPoint.getZ();
			hydrophonePosTRansform.add(calcTransformPoint(hydrophonePos.get(i),rotHeading,rotPitch,rotRoll));
			
			//create streamer for each hydrophone
			streamerPoints=new  ArrayList<Point3D>();
			streamerPoints.add(new Point3D(0,0,0));
			streamerPoints.add(new Point3D(hydrophonePosTRansform.get(i)[0],hydrophonePosTRansform.get(i)[1],hydrophonePosTRansform.get(i)[2]));
			streamers.add(streamerPoints);
			
		}
		
		for (int i=0; i<childArrayPos.size(); i++){
//			transformedPoint =new Point3D(childArrayPos.get(i)[0], childArrayPos.get(i)[1], childArrayPos.get(i)[2]); 
//			transformedPoint=rotPitch.transform(transformedPoint);
//			transformedPoint=rotHeading.transform(transformedPoint);
//			transformedPoint=rotRoll.transform(transformedPoint);
//			transformedPos=new double[3];
//			transformedPos[0]=transformedPoint.getX(); transformedPos[1]=transformedPoint.getY(); transformedPos[2]=transformedPoint.getZ();
			childArrayPosTRansform.add(calcTransformPoint(childArrayPos.get(i),rotHeading,rotPitch,rotRoll));
		}

		for (int i=0; i<sensorPos.size(); i++){
//			transformedPoint =new Point3D(sensorPos.get(i)[0], sensorPos.get(i)[1], sensorPos.get(i)[2]); 
//			transformedPoint=rotPitch.transform(transformedPoint);
//			transformedPoint=rotHeading.transform(transformedPoint);
//			transformedPoint=rotRoll.transform(transformedPoint);
//			transformedPos=new double[3];
//			transformedPos[0]=transformedPoint.getX(); transformedPos[1]=transformedPoint.getY(); transformedPos[2]=transformedPoint.getZ();
//			sensorPosTransform.add(transformedPos);

			sensorPosTransform.add(calcTransformPoint(sensorPos.get(i),rotHeading,rotPitch,rotRoll));

		}
		
	
		//now add to ArrayPos
		ArrayPos arrayPos=new ArrayPos(); 
		arrayPos.setTransformHydrophonePos(hydrophonePosTRansform);
		arrayPos.setTransformChildArrayPos(childArrayPosTRansform);
		arrayPos.setTransformSensorPos(sensorPosTransform);
		arrayPos.setStreamerPositions(streamers);
		
		return arrayPos;
	}
	
	/**
	 * Transform point. Note: rotate transforms contain point of rotation. 
	 * @param transformPos - point to transform. 
	 * @param rotHeading - heading transform.
	 * @param rotPitch - pitch transform.
	 * @param rotRoll - roll transform. 
	 * @return transformed point {x,y,z} relative to arrays co-ordinate frame. 
	 */
	private  double[] calcTransformPoint(double[] transformPos, Rotate rotHeading, Rotate rotPitch, Rotate rotRoll){
		Point3D transformedPoint=new Point3D(transformPos[0], transformPos[1], transformPos[2]); 
		transformedPoint=rotPitch.transform(transformedPoint);
		transformedPoint=rotHeading.transform(transformedPoint);
		transformedPoint=rotRoll.transform(transformedPoint);
		double[] transformedPos=new double[3];
		transformedPos[0]=transformedPoint.getX(); transformedPos[1]=transformedPoint.getY(); transformedPos[2]=transformedPoint.getZ();
		
		return transformedPos;
	}
	
	/**
	 * Get sensor data. Rigid arrays currently only use orientation and lat/long to determine position and can only utilise one. This function finds
	 * the relevent sensor info from an array of sensors. If more than one data type  is found, e.g, say there tow sensors with heading data, the first on the list
	 * will be used and the other ignored. 
	 * @param movementSensors - list of movement sensors. 
	 * @param time- time in millis to find data for
	 * @return a list of sensor data, heading, pitch, roll (all in RADIANS), latitude, longitude (in DECIMAL). Null in list indicates no data for reading was found. 
	 */
	private Double[] getSensorData(ArrayList<MovementSensor> movementSensors, long time){
		
		Double[] sensorData=new Double[5]; 
		
		//now iterate through all sensors and find info. 
		for (int i=0; i<movementSensors.size(); i++){
			if (movementSensors.get(i).getOrientationData(time)!=null){
				if (sensorData[0]==null)  sensorData[0]=movementSensors.get(i).getOrientationData(time)[0]-movementSensors.get(i).getReferenceOrientation()[0]; 
				if (sensorData[1]==null)  sensorData[1]=movementSensors.get(i).getOrientationData(time)[1]-movementSensors.get(i).getReferenceOrientation()[1]; 
				if (sensorData[2]==null)  sensorData[2]=movementSensors.get(i).getOrientationData(time)[2]-movementSensors.get(i).getReferenceOrientation()[2]; 
			}
			if (movementSensors.get(i).getLatLong(time)!=null){
				if (sensorData[3]==null)  sensorData[3]=movementSensors.get(i).getLatLong(time)[0];
				if (sensorData[4]==null)  sensorData[4]=movementSensors.get(i).getLatLong(time)[1]; 
			}
		}
		return sensorData;
	}

	@Override
	public HArray getArray() {
		return array;
	}




}
