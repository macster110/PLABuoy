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
	int iterations=20;

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
			angles.add(sensorList.get(i).getOrientationData(time));
			sensorPos[i]=sensorList.get(i).getReferencePosition()[dim]; 
			sortIndexS[i]=sensorList.indexOf(movementSensors.get(i)); 
		}
		
//		System.out.println("Input for flexble model: arrayPos "+arrayPos +
//				" hydrophonePos "+hydrophonePos + " sensorPos "+sensorPos);
		
		//run the calculations
		ArrayPos newArrayPos = transformPositions(arrayPos, hydrophonePos, sensorPos,
				angles, iterations, dim);
		//set arrays. 
		newArrayPos.setParentHArray(getArray());
		newArrayPos.setChildArrays(childArrays);
	
		//sort so that everything is back in original order
		//array attachmnet points
		ArrayList<double[]> newArrayAttachPos=new ArrayList<>(); 
		for (int i=0; i<newArrayPos.getChildArrayPos().size(); i++){
			newArrayAttachPos.add(newArrayPos.getChildArrayPos().get(sortIndexArray[i]));
		}
		
		//hydrophones
		ArrayList<double[]> newHydrophonePos=new ArrayList<>(); 
		for (int i=0; i<newArrayPos.getHydrophonePos().size(); i++){
			newHydrophonePos.add(newArrayPos.getHydrophonePos().get(sortIndexH[i]));
		}
		
		//replace positions with sorted positions. 
		newArrayPos.setTransformHydrophonePos(newHydrophonePos);
		newArrayPos.setTransformChildArrayPos(newArrayAttachPos);
		newArrayPos.setParentHArray(getArray());
		newArrayPos.setChildArrays(childArrays);


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
	private ArrayPos transformPositions(double[] childArrayPos, double[] hydrophonePos, double[] sensorPos,
			ArrayList<Double[]> angles, int n, int dim){
		
		/**
		 * TODO Note: for clarity descriptions assume a vertical array model (i.e. dim=2), however it should be remembered that this 
		 * function should work in x and y directions. 
		 */
		ArrayList<double[]> hydrophonePosTrans=new ArrayList<double[]>(); 
		
		//we assume streamer starts at (0,0,0), the reference point of the current array (remember this can be attached to parent array so isn't (0,0,0) of the reference 
		//co-ordinate system.
		
		double maxLength=findMaxLength(hydrophonePos);
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
		for (int i=0; i<iterations+1; i++){
			 chunkPosStart=i*chunkSize;
			 chunkPosEnd=(i+1)*chunkSize; 
			 for (int j=0; j<sensorPos.length; j++){
				 if (sensorPos[j]<chunkPosStart && sensorPos[j]>=chunkPosEnd){
					 sensorChunkPos[j]=i;
				 }
			 }
		}
		
		
		//now model streamer. 
		for (int i=0; i<iterations+1; i++){
			
			chunkPosStart=i*chunkSize;
			chunkPosEnd=(i+1)*chunkSize; 
			
			System.out.println(" chunkPosEnd: "+ chunkPosEnd+ " sensorPos[0]: "+sensorPos[0]);
			System.out.println(" chunkPosStart: "+ chunkPosEnd+ " sensorPos[sensorPos.length-1]: "+sensorPos[sensorPos.length-1]);

			//check if the chunk is 'above' (if vertical dimension) the first tag
			if (chunkPosEnd>sensorPos[sensorPos.length-1]){
				chunkAngles[i][0]= angles.get(sensorPos.length-1)[0]; 
			    chunkAngles[i][1]= angles.get(sensorPos.length-1)[1]; 
				chunkAngles[i][2]= angles.get(sensorPos.length-1)[2]; 
			}
			//check if the chunk is 'below' (if vertical dimension) the last tag. 
			else if (chunkPosStart<sensorPos[0]){
				chunkAngles[i][0]= angles.get(0)[0]; 
			    chunkAngles[i][1]= angles.get(0)[1]; 
				chunkAngles[i][2]= angles.get(0)[2]; 
			}
			//the chunk is between two tags.
			else {
				int sensIndxEnd=-1; 
				int sensIndxStart=-1; 
				//find the two tags the two tags the chunk is between. Iterate through tag chunk positions. 
				for (int j=0; j<sensorChunkPos.length; j++){
					if (i>=sensorChunkPos[j]){
						sensIndxEnd=j-1;
						sensIndxStart=j;
					}
				}

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
					
					//figure out differences in angles. 
					double headingDiff=angles.get(sensIndxEnd)[0]-angles.get(sensIndxStart)[0]; 
					double pitchDiff=angles.get(sensIndxEnd)[1]-angles.get(sensIndxStart)[1]; 
					double rollPitch=angles.get(sensIndxEnd)[2]-angles.get(sensIndxStart)[2]; 
					
					// Vertical array. For a vertical array we only consider tag heading and pitch. It is assumed the 
					double chunkFraction=(i-sensorChunkPos[sensIndxStart])/(double) (sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]); 
					
					//work out angles of the chunk
					chunkAngles[i][0]=chunkFraction*headingDiff+angles.get(sensIndxStart)[0];
					chunkAngles[i][1]=chunkFraction*pitchDiff+angles.get(sensIndxStart)[1];
					chunkAngles[i][2]=chunkFraction*rollPitch+angles.get(sensIndxStart)[2];
					
					break; 
				}
			}
			
			//now work out unit vector for each chunk. 
			double[] unitVector= calcVertUNitVector(chunkAngles[i][0], chunkAngles[i][1]); 
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
		
		//now find the positon of hydrophones and children on the array
	    double[][] transformedPositions=getPositonsOnArray(hydrophonePos, chunkPositions, chunkUnitVectors);
		
		
		for (int i=0; i<childArrayPos.length; i++){
			
		}
		
		
		
		//create class to hold transformed information. 
		ArrayPos arrayPos=new ArrayPos(); 
		arrayPos.setStreamerPositions(chunkToStreamer(chunkPositions));

		//TODO- need to actually model array. //////
		double[] hydrophoneLoc; 
		for (int i=0; i<hydrophonePos.length; i++){
			hydrophoneLoc=new double[3];
			hydrophoneLoc[dim]=hydrophonePos[i];
			hydrophonePosTrans.add(hydrophoneLoc);
			//hydrophonePositions.add()
		}
		arrayPos.setTransformChildArrayPos(new ArrayList<double[]>());

		////////////////////////////////////////
		
		arrayPos.setTransformHydrophonePos(transformedPositions);
		
		return arrayPos;
		
	}
	
	/**
	 * Get the positions on array. 
	 * @param positions - the positions to transform on the one linear array - one dimensional as linear. 
	 * @param chunkPositions - the transformed array divided into 'chunks' 
	 * @param chunkPositions - unit vector of each chunk. Could calc this but to prevent repeat calcs is 
	 * @return the transofmred positions in 3D. 
	 */
	private double[][] getPositonsOnArray(double[] positions, double[][] chunkPositions, double[][] chunkUnitVectors){
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
					System.out.println("Magnitude: "+magnitude + " arrayLength: "+arrayLength);
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
	private static double calcDistance(double[] point1, double[] point2)
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
	private ArrayList<ArrayList<Point3D>> chunkToStreamer(double[][] chunkPosition){
		
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
	 */
	private double[] calcVertUNitVector(double heading, double pitch){
		double x=Math.sin(pitch)*Math.cos(heading); 
		double y=Math.sin(pitch)*Math.sin(heading); 
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
	
	private double findMaxLength(double[] a){
		
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
