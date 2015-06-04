package arrayModelling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point3D;
import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;
import dataUnits.movementSensors.MovementSensor;

/**
 * Models a linear arrays with movement sensor located at different points along the array. e.g. free hanging vertical array. 
 * <p> 
 * The important thing to consider with this is movement algorithm is that, without any movement it's essentially a one dimensional system. The calculations therefore 
 * tend to use single numbers to perform calulations e.g. a list of depths, rather than full 3D points which might be extecting in other models. 
 * <p>
 * This make code plus maths significantly simpler.  
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
	
	/**
	 * Number of iterations
	 */
	int iterations=100;

	/**
	 * Reference to the array. 
	 */
	private HArray array; 


	public LinearFlexibleModel(HArray array){
		this.array=array; 
	}

	@Override
	public ArrayPos getTransformedPositions(ArrayList<HArray> childArrays, ArrayList<Hydrophone> hydrophones,
			ArrayList<MovementSensor> movementSensors, long time) {
			
		/**
		 * TODO. dim=2 is z axis- this is currently only implemented for VERTICAL ARRAYS. Other threading streamers etc. could use same algorithm
		 * but will need testing and proper implementation. 
		 */
		int dim=2; 
		
		//want copy of array to mess around with. 
		ArrayList<HArray> arrayList=new ArrayList<HArray>(childArrays); 
		ArrayList<Hydrophone> hydrophoneList=new ArrayList<Hydrophone>(hydrophones); 
		ArrayList<MovementSensor> sensorList=new ArrayList<MovementSensor>(movementSensors); 

		
		//order array reference position by x,y,or z depending on linear dimension
		Collections.sort(arrayList, new Comparator<HArray>(){
		    public int compare(HArray s1, HArray s2) {
		        return (int) (s1.getParentAttachPoint()[dim]-s2.getParentAttachPoint()[dim]); 
		    }
		});
		
		//order hydrophones by x,y,or z depending on linear dimension
		Collections.sort(hydrophoneList, new Comparator<Hydrophone>(){
		    public int compare(Hydrophone s1, Hydrophone s2) {
		        return (int) (s1.getPosition()[dim]-s2.getPosition()[dim]); 
		    }
		});
	
		//order movement sensor by x,y,or z depending on linear dimension
		Collections.sort(sensorList, new Comparator<MovementSensor>(){
		    public int compare(MovementSensor s1, MovementSensor s2) {
		        return (int) (s1.getReferencePosition()[dim]-s2.getReferencePosition()[dim]); 
		    }
		});
		
		
		//convert hydrophone positions to 1D
		double[] hydrophonePos=new double[hydrophones.size()];
		int[] sortIndexH=new int[hydrophones.size()]; //now need a record of the indexes for sorting
		for (int i=0; i<hydrophones.size(); i++){
			hydrophonePos[i]=hydrophoneList.get(i).getPosition()[dim];
			sortIndexH[i]=hydrophoneList.indexOf(hydrophones.get(i));
		}
		
		//convert array positions to 1D
		double[] arrayPos=new double[childArrays.size()];
		int[] sortIndexArray=new int[childArrays.size()]; //now need a record of the indexes for sorting
		for (int i=0; i<childArrays.size(); i++){
			arrayPos[i]=childArrays.get(i).getParentAttachPoint()[dim];
			sortIndexArray[i]=arrayList.indexOf(childArrays.get(i));
		}
		
		//get angles from movement sensor. This model does not deal with depth readings. 
		ArrayList<Double[]> angles=new ArrayList<Double[]>();
		int[] sortIndexS=new int[movementSensors.size()]; //now need a record of the indexes for sorting
		double[] sensorPos = new double[movementSensors.size()];
		for (int i=0; i<movementSensors.size(); i++){
			//remember to compensate for reference positions; 
			Double[] sensorData=new Double[3];
			
			sensorData[0]=sensorList.get(i).getOrientationData(time)[0]-sensorList.get(i).getReferenceOrientation()[0]; 
			sensorData[1]=sensorList.get(i).getOrientationData(time)[1]-sensorList.get(i).getReferenceOrientation()[1]; 
			sensorData[2]=sensorList.get(i).getOrientationData(time)[2]-sensorList.get(i).getReferenceOrientation()[2]; 
			
			angles.add(sensorData);
			
			sensorPos[i]=sensorList.get(i).getReferencePosition()[dim]; 
			sortIndexS[i]=sensorList.indexOf(movementSensors.get(i)); 
		}
		
//		System.out.println("Input for flexble model: arrayPos "+arrayPos +
//				" hydrophonePos "+hydrophonePos + " sensorPos "+sensorPos);
		
		//run the calculations
		ArrayPos newArrayPos = transformPositions(arrayPos, hydrophonePos, sensorPos,
				angles, iterations, dim);
		//set arrays. 
		newArrayPos.setHArray(getArray());
		newArrayPos.setChildArrays(childArrays);
	
		//sort so that everything is back in original order
		//array attachment points
		ArrayList<double[]> newArrayAttachPos=new ArrayList<>(); 
		for (int i=0; i<newArrayPos.getTransformChildArrayPos().size(); i++){
			newArrayAttachPos.add(newArrayPos.getTransformChildArrayPos().get(sortIndexArray[i]));
		}
		
		//hydrophones
		ArrayList<double[]> newHydrophonePos=new ArrayList<>(); 
		for (int i=0; i<newArrayPos.getTransformHydrophonePos().size(); i++){
			newHydrophonePos.add(newArrayPos.getTransformHydrophonePos().get(sortIndexH[i]));
		}
		
		
		//sensors
		ArrayList<double[]> newSensorPos=new ArrayList<>(); 
		for (int i=0; i<newArrayPos.getTransformSensorPos().size(); i++){
			newSensorPos.add(newArrayPos.getTransformSensorPos().get(sortIndexS[i]));
		}
		
		//replace positions with sorted positions. 
		newArrayPos.setTransformHydrophonePos(newHydrophonePos);
		newArrayPos.setTransformChildArrayPos(newArrayAttachPos);
		newArrayPos.setTransformSensorPos(newSensorPos);
		
		newArrayPos.setHArray(getArray());
		newArrayPos.setChildArrays(childArrays);
		newArrayPos.setTime(time);


		//return transformed array positions, hydrophone positions and streamer data for 3D visualisation. 
		return newArrayPos;

	}
	
	/**
	 * Calculate new hydrophone and child array reference positions. 
	 * @param arrayPositions -  positions of child arrays in meters - reference to array (0,0,0).
	 * @param hydrophonePos - hydrophone positions in meters - reference to array (0,0,0) - in numerical order e.g. shallowest to deepest. 
	 * @param sensorPos - sensor positions in meters - reference to array (0,0,0). - in numerical order. 
	 * @param angles - Euler angles of sensors in RADIANS. heading, pitch, roll.  
	 * @param n - number of bins to split streamer into for angle calculation
	 * @param dim - the dimensions of stream (x,y,z)  ->(0,1,2)
	 * @return new transformed hydrophone positions. 
	 */
	protected ArrayPos transformPositions(double[] childArrayPos, double[] hydrophonePos, double[] sensorPos,
			ArrayList<Double[]> anglesRaw, int n, int dim){
		
		
		ArrayList<Double[]> angles=new ArrayList<Double[]>(anglesRaw);
		angles=wrapPitch(angles);
		
		/**
		 * TODO Note: for clarity descriptions assume a vertical array model (i.e. dim=2), however it should be remembered that this 
		 * function should work in x and y directions. 
		 */
		ArrayList<double[]> hydrophonePosTrans=new ArrayList<double[]>(); 
		
		//we assume streamer starts at (0,0,0), the reference point of the current array (remember this can be attached to parent array so isn't (0,0,0) of the reference 
		//co-ordinate system.
		
		//want to get max size - must remmeber to include sensors as sensor might be below last hydrophone
		double maxLength1=findMaxLength(hydrophonePos);
		double maxLength2=findMaxLength(sensorPos);
		
		double maxLength=maxLength1;
		if (Math.abs(maxLength2)>Math.abs(maxLength1)) maxLength=maxLength2;
		double chunkSize=(maxLength)/iterations; 
		
		//angles of chunks (heading, pitch, roll)
		double[][] chunkAngles=new double[iterations+1][3];
		//positons of chunks relative to (0,0,0) of array. x,y,z of chunk end. 
		double[][] chunkPositions=new double[iterations+1][3];
		//unit vector of each chunk
		double[][] chunkUnitVectors=new double[iterations+1][3];

		
		//find where on the list of chunks the sensors are located
		double chunkPosStart;
		double chunkPosEnd;
		int[] sensorChunkPos=new int[sensorPos.length]; //chunk number for each sensor
		 for (int j=0; j<sensorPos.length; j++){
			 for (int i=0; j<iterations; i++){
				 chunkPosStart=i*chunkSize;
				 chunkPosEnd=(i+1)*chunkSize;
				 if (sensorPos[j]<chunkPosStart && sensorPos[j]>=chunkPosEnd){
					 sensorChunkPos[j]=i;
					 break;
				 }
			 }
		 }
		
		//now model streamer. 
		for (int i=0; i<iterations+1; i++){
			
			chunkPosStart=i*chunkSize;
			chunkPosEnd=(i+1)*chunkSize; 
			
//			System.out.println(" chunkPosEnd: "+ chunkPosEnd+ " sensorPos[0]: "+sensorPos[0]);
//			System.out.println(" chunkPosStart: "+ chunkPosEnd+ " sensorPos[sensorPos.length-1]: "+sensorPos[sensorPos.length-1]);

			//check if the chunk is 'above' (if vertical dimension) the first tag
			if (chunkPosEnd>sensorPos[sensorPos.length-1]){
				//System.out.println("LinearFelxibleModel: Below deepest tag"+chunkPosStart+" sensorPos[sensorPos.length-1]] "+sensorPos[sensorPos.length-1]);
				chunkAngles[i][0]= angles.get(sensorPos.length-1)[0]; 
			    chunkAngles[i][1]= angles.get(sensorPos.length-1)[1]; 
				chunkAngles[i][2]= angles.get(sensorPos.length-1)[2]; 
			}
			
			//check if the chunk is 'below' (if vertical dimension) the last tag. 
			else if (chunkPosStart<sensorPos[0]){
				//System.out.println("LinearFelxibleModel: Above shallowest tag "+chunkPosStart+" sensorPos[0] "+sensorPos[0]);
				chunkAngles[i][0]= angles.get(0)[0]; 
			    chunkAngles[i][1]= angles.get(0)[1]; 
				chunkAngles[i][2]= angles.get(0)[2]; 
			}
			
			//check if the chunk is between two tags.
			else {
				int sensIndxEnd=-1; 
				int sensIndxStart=-1; 
				//find the two tags the two tags the chunk is between. Iterate through tag chunk positions. 
				for (int j=0; j<sensorChunkPos.length-1; j++){
					if (i<=sensorChunkPos[j] && i>=sensorChunkPos[j+1]){
						sensIndxEnd=j;
						sensIndxStart=j+1;
						
						/**
						 * We have chunk position of the tag above (sensIndexStart), the tag below (sensIndexEnd) and the current chunk (i).
						 * Now we need to figure out the angles of the chunk. This will depend on the dimension. 
						 */
						switch (dim){
						case 0:
							//TODO- to be implemented in future 
							break;
						case 1:
							//TODO- to be implemented in future 
							break;
						case 2:
							
							//System.out.println("LinearFelxibleModel: Between Sensors: "+chunkPosStart+" Calculateding angles between sensors " +sensIndxStart+ " and  "+sensIndxEnd);
							
							//figure out differences in angles. 
							double headingDiff=angles.get(sensIndxEnd)[0]-angles.get(sensIndxStart)[0];
//							/**
//							 * Say angle 1=10 degrees and angle 2=330 degrees. In this case we want to change to got from 10 0 35 340 330- not all the way round 
//							 * (Of course an array may twist that way but we gotta assume it take least twisted path). So need to check the heading diff and if 
//							 * smaller going going backward through angles make chunk heading change go other way and have different magnitude. 
//							 */
//							if (Math.abs(headingDiff)>Math.PI) headingDiff = headingDiff>0? headingDiff=headingDiff-2*Math.PI : headingDiff+2*Math.PI; 
										
							double pitchDiff=angles.get(sensIndxEnd)[1]-angles.get(sensIndxStart)[1]; 
							double rollPitch=angles.get(sensIndxEnd)[2]-angles.get(sensIndxStart)[2]; //at moment roll is not used in this algorithm but added anyway
							
							// Vertical array. For a vertical array we only consider tag heading and pitch. It is assumed the roll is twist in cable so makes little or no difference
							double chunkFraction=(i-sensorChunkPos[sensIndxStart])/(double) (sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]); 
							
							//work out angles of the chunk
							chunkAngles[i][0]=chunkFraction*headingDiff+angles.get(sensIndxStart)[0];
							chunkAngles[i][1]=chunkFraction*pitchDiff+angles.get(sensIndxStart)[1];
							chunkAngles[i][2]=chunkFraction*rollPitch+angles.get(sensIndxStart)[2];
							
							break; 
						}
						break; 
					}
					//on sensor chunk
					else if (i==sensorChunkPos[j]){
						chunkAngles[i][0]=angles.get(j)[0];
						chunkAngles[i][1]=angles.get(j)[1];
						chunkAngles[i][2]=angles.get(j)[2];
					}
						
				}

			}
			
			//now work out unit vector for each chunk. 
			double[] unitVector= calcVertUnitVector(chunkAngles[i][0], chunkAngles[i][1]); 
			chunkUnitVectors[i]=unitVector;
			//create true chunk vector
			double[] vector=new double[3]; 
			vector[0]=unitVector[0]*chunkSize; vector[1]=unitVector[1]*chunkSize; vector[2]=unitVector[2]*chunkSize;
			
			//now find 'true' position by adding to last chunk
			if (i==0){
				chunkPositions[i]=vector;
			}
			else {
				chunkPositions[i][0]=vector[0]+chunkPositions[i-1][0];
				chunkPositions[i][1]=vector[1]+chunkPositions[i-1][1];
				chunkPositions[i][2]=vector[2]+chunkPositions[i-1][2];
			}
		
		}
		
		//now find the positions of child arrays and hydrophones
	    double[][] hydrophoneTransformPositions=getPositonsOnArray(hydrophonePos, chunkPositions, chunkUnitVectors);
	    double[][] childTransformPositions=getPositonsOnArray(childArrayPos, chunkPositions, chunkUnitVectors);
	    double[][] sensorTransformPositions=getPositonsOnArray(sensorPos, chunkPositions, chunkUnitVectors);

		//create class to hold transformed information. 
		ArrayPos arrayPos=new ArrayPos(); 
		arrayPos.setStreamerPositions(chunkToStreamer(chunkPositions));

		//set info
		arrayPos.setTransformHydrophonePos(hydrophoneTransformPositions);
		arrayPos.setTransformChildArrayPos(childTransformPositions);
		arrayPos.setTransformSensorPos(sensorTransformPositions);

		return arrayPos;
		
	}
	
	//TODO- need to make sure this works for +90
	/**
	 * Make sure the pitch is between -90 and 90. If <90 then need to turn heading 180 and make pitch >90
	 * @param angles - angles
	 * @return
	 */
	protected ArrayList<Double[]> wrapPitch(ArrayList<Double[]> angles) {
		for (int i=0; i<angles.size(); i++){
			System.out.println("Old pitch: "+Math.toDegrees(angles.get(i)[1])+" old heading "+Math.toDegrees(angles.get(i)[0]));
			if (angles.get(i)[1]<0){
				angles.get(i)[1]=Math.abs(angles.get(i)[1]);
				angles.get(i)[0]=(angles.get(i)[0]+Math.PI);
				System.out.println("New pitch: "+Math.toDegrees(angles.get(i)[1])+" new heading "+Math.toDegrees(angles.get(i)[0]));
			}
		}
		return angles;
	}

	/**
	 * Get the positions on array. 
	 * @param positions - the positions to transform on the one linear array - one dimensional as linear. 
	 * @param chunkPositions - the transformed array divided into 'chunks' 
	 * @param chunkPositions - unit vector of each chunk. Could calc this but to prevent repeat calcs is 
	 * @return the transofmred positions in 3D. 
	 */
	protected double[][] getPositonsOnArray(double[] positions, double[][] chunkPositions, double[][] chunkUnitVectors){
		//create array for new positions
		double[][] newPositions=new double[positions.length][3];
		
		double arrayLength=0; 
		double[] previousPos=new double[3]; 
		
		//iterate through streamer chunks. 
		for (int i=0; i<chunkPositions.length; i++){
			double newArrayLength=arrayLength+calcDistance(previousPos,chunkPositions[i]); 
			for (int j=0; j<positions.length; j++){
				if (Math.abs(positions[j])>arrayLength && Math.abs(positions[j])<=newArrayLength){
					//found the chunk the position is on.
					//whats' the difference in distance? 
					double magnitude=Math.abs(positions[j])-arrayLength;
					//System.out.println("Magnitude: "+magnitude + " arrayLength: "+arrayLength);
					//multiple by unit vecotr 
					double[] vector=new double[3]; 
					vector[0]=chunkUnitVectors[i][0]*magnitude; vector[1]=chunkUnitVectors[i][1]*magnitude; vector[2]=chunkUnitVectors[i][2]*magnitude;
					
					double[] newPos={vector[0]+previousPos[0], vector[1]+previousPos[1] ,vector[2]+previousPos[2]};
					newPositions[j]=newPos;
				}
			}
			
			arrayLength=newArrayLength;
			previousPos=chunkPositions[i];
		}
		
		return newPositions; 
	}
	
	/**
	 * Calculate unit vector between two points
	 * @param point1 - point 1 {x,y,z}
	 * @param point2 - point 2 {x,y,z}
	 * @return the unit vector {x,y,z}
	 */
	private static double[] calcUnitVector(double[] point1, double[] point2){
		double magnitude=calcDistance(point1,point2); 
		double[] unitVector = {(point2[0] - point1[0])/magnitude, (point2[1] - point1[1])/magnitude, (point2[2] - point1[2])/magnitude};
		return unitVector; 
	}
	
	/**
	 * Get distance between two points
	 * @param point1 - point 1 {x,y,z}
	 * @param point2 - point 2 {x,y,z}
	 * @return distance between point1 and point2; 
	 */
	protected static double calcDistance(double[] point1, double[] point2)
	{
		double dx = point1[0] - point2[0];
		double dy = point1[1] - point2[1];
		double dz = point1[2] - point2[2];

		// We should avoid Math.pow or Math.hypot due to perfomance reasons
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	
	/**
	 * Convert 'chunks' to streamer format. 
	 * @return transformed streamer,. 
	 */
	protected ArrayList<ArrayList<Point3D>> chunkToStreamer(double[][] chunkPosition){
		
		//initial chunk is at 0,0,0
		Point3D point=new Point3D(0,0,0);
		
		//create array for points 
		ArrayList<Point3D> pointChunk=new ArrayList<Point3D>();
		pointChunk.add(point);
		for (int i=0; i<chunkPosition.length; i++){
			point=new Point3D(chunkPosition[i][0],chunkPosition[i][1],chunkPosition[i][2]); 
			pointChunk.add(point);
		}
		
		//add to 2D array- not needed here but for genarily has to be this format. 
		ArrayList<ArrayList<Point3D>> streamerPosTrans=new ArrayList<ArrayList<Point3D>>(); 
		streamerPosTrans.add(pointChunk);
		
		return streamerPosTrans;
	}
	
	/**
	 * Calculate the unit vector for vertical configuration.
	 * @param heading- heading in RADIANS
	 * @param pitch - pitch in RADIANS.
	 * @return unit vector [x,y,z]. 
	 *
	 */
	protected double[] calcVertUnitVector(double heading, double pitch){
		//have to get heading into correct format here. 
		double x=Math.sin(pitch)*Math.cos(1.5*Math.PI-heading); 
		double y=Math.sin(pitch)*Math.sin(1.5*Math.PI-heading); 
		double z=Math.cos(pitch);
		double[] unitVector={x,y,z};
		return unitVector;
	}
	
	public int getModelAlgorithmType() {
		return type;
	}


	public void setModelAlgorithmType(int type) {
		this.type = type;
	}

	@Override
	public HArray getArray() {
		return array;
	}
	
	protected double findMaxLength(double[] a){
		
	   double max = Double.MIN_VALUE;   
	   int index=-1; 

	    for (int i = 0; i < a.length; i++) {
	      if (Math.abs(a[i])>max){
	    	  max=Math.abs(a[i]);
		      index=i; 
	      }
	    }
	   
	    return a[index];
	}


}
