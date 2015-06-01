package arrayModelling;

import java.util.ArrayList;

import dataUnits.hArray.HArray;

/**
 * Models arrays based on vector math rather than Euler angles (although these provide the raw data). This gets round many of the problems interpolating between
 * angles which introduces a plethera of issues, including U shaped arrays when headings are 180 apart, taking the shortest route to new angles, 
 * pitch going wrong when arrays change angles etc etc...
 * <p>
 * Note, this model is currently only implemented for vertical linear arrays although, now vector math has been properly implmented adapting for other array types should
 * be trivial. 
 * 
 * @author Jamie Macaulay
 *
 */
public class LinearFlexibleModel3 extends LinearFlexibleModel {

	public LinearFlexibleModel3(HArray array) {
		super(array);
		// TODO Auto-generated constructor stub
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
	@Override
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
		
		//want to get max size - must remember to include sensors as sensor might be below last hydrophone
		double maxLength1=findMaxLength(hydrophonePos);
		double maxLength2=findMaxLength(sensorPos);
		
		double maxLength=maxLength1;
		if (Math.abs(maxLength2)>Math.abs(maxLength1)) maxLength=maxLength2;
		double chunkSize=(maxLength)/iterations; 
		
		//angles of chunks (heading, pitch, roll)
		double[][] chunkVectors=new double[iterations+1][3];
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
		 double[] unitVector;
		for (int i=0; i<iterations+1; i++){
			
			chunkPosStart=i*chunkSize;
			chunkPosEnd=(i+1)*chunkSize; 
			
//			System.out.println(" chunkPosEnd: "+ chunkPosEnd+ " sensorPos[0]: "+sensorPos[0]);
//			System.out.println(" chunkPosStart: "+ chunkPosEnd+ " sensorPos[sensorPos.length-1]: "+sensorPos[sensorPos.length-1]);

			//check if the chunk is 'above' (if vertical dimension) the first tag
			if (chunkPosEnd>sensorPos[sensorPos.length-1]){
				//System.out.println("LinearFelxibleModel: Below deepest tag"+chunkPosStart+" sensorPos[sensorPos.length-1]] "+sensorPos[sensorPos.length-1]);
				unitVector= calcVertUnitVector(angles.get(sensorPos.length-1)[0] , angles.get(sensorPos.length-1)[1]); 
				chunkUnitVectors[i]=unitVector;
			}
			
			//check if the chunk is 'below' (if vertical dimension) the last tag. 
			else if (chunkPosStart<sensorPos[0]){
				//System.out.println("LinearFelxibleModel: Above shallowest tag "+chunkPosStart+" sensorPos[0] "+sensorPos[0]);
				unitVector= calcVertUnitVector(angles.get(sensorPos.length-1)[0] , angles.get(sensorPos.length-1)[1]); 
				chunkUnitVectors[i]=unitVector;
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
						
						//calc unit vector for start and end sensors
						double[] unitVectorVec1= calcVertUnitVector(angles.get(sensIndxStart)[0] , angles.get(sensIndxStart)[1]); 
						double[] unitVectorVec2= calcVertUnitVector(angles.get(sensIndxEnd)[0] , angles.get(sensIndxEnd)[1]); 

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
							
							//Interpolate between different vectors. 
							double chunkFraction=(i-sensorChunkPos[sensIndxStart])/(double) (sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]); 
							double x = chunkFraction*(unitVectorVec2[0]-unitVectorVec1[0])+unitVectorVec1[0]; 
							double y = chunkFraction*(unitVectorVec2[1]-unitVectorVec1[1])+unitVectorVec1[1]; 
							double z = chunkFraction*(unitVectorVec2[2]-unitVectorVec1[2])+unitVectorVec1[2]; 
							double magnitude=Math.sqrt(x*x+y*y+z*z);
							
							chunkUnitVectors[i][0]=x/magnitude;
							chunkUnitVectors[i][1]=y/magnitude; 
							chunkUnitVectors[i][2]=z/magnitude; 

							break; 
						}
						break; 
					}
					
					//the chunk contains a sensor
					else if (i==sensorChunkPos[j]){
						unitVector= calcVertUnitVector(angles.get(j)[0] , angles.get(j)[1]); 
						chunkUnitVectors[i]=unitVector;
					}
						
				}

			}
			
			//now we have the unit vector, can multiply by chunk size and then add to prev ious chunk
			//create true chunk vector
			double[] vector=new double[3]; 
			vector[0]=chunkUnitVectors[i][0]*chunkSize; vector[1]=chunkUnitVectors[i][1]*chunkSize; vector[2]=chunkUnitVectors[i][2]*chunkSize;
			
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

}
