package arrayModelling;

import java.util.ArrayList;

import dataUnits.hArray.HArray;

public class LinearFlexibleModel2 extends LinearFlexibleModel {

	public LinearFlexibleModel2(HArray array) {
		super(array);
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
		int sensIndxEnd=-1; 
		int sensIndxStart=-1; 
		double[] H=null; 
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
				//find the two tags the chunk is between. Iterate through tag chunk positions. 
				for (int j=0; j<sensorChunkPos.length-1; j++){
					if (i<=sensorChunkPos[j] && i>=sensorChunkPos[j+1]){

						//record whether we have changed chunks
						boolean calcFunction=false; 
						if (sensIndxStart!=j+1 && sensIndxEnd!=j) calcFunction=true;
							
						//record index of sensors chunks are between. 
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
							if (Math.abs(headingDiff)>Math.PI) headingDiff = headingDiff>0? headingDiff=headingDiff-2*Math.PI : headingDiff+2*Math.PI; 
							
							double pitchDiff=angles.get(sensIndxEnd)[1]-angles.get(sensIndxStart)[1]; 
							double rollPitch=angles.get(sensIndxEnd)[2]-angles.get(sensIndxStart)[2]; //at moment roll is not used in this algorithm but added anyway
							
							// Vertical array. For a vertical array we only consider tag heading and pitch. It is assumed the roll is twist in cable so makes little or no difference
							double chunkFraction=(i-sensorChunkPos[sensIndxStart])/(double) (sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]); 
							
							if (!calcFunction || H==null) H=angleFunction((sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]), headingDiff); 
							double chunkFractionH = getHeadingChunkFrac(H, (i-sensorChunkPos[sensIndxStart]), (sensorChunkPos[sensIndxEnd]-sensorChunkPos[sensIndxStart]));
							
							//work out angles of the chunk
							chunkAngles[i][0]=chunkFractionH*headingDiff+angles.get(sensIndxStart)[0];
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
	
	
	
	/**
	 * Find chunk fraction for the heading, with a compensation factor. 
	 * @param H - the step angle function
	 * @param i - the ith chunk
	 * @param N-  the total number of chunks. 
	 */
	private double getHeadingChunkFrac(double[] H, int i, int N){
		
		if (i==N) return 1; 
		double c;
		if (H[i]>=0){
			c=1-H[i]; 
		}
		else {
			c=H[i]*(1-N/(double) i)+1; 
		}
		//System.out.println(" H: "+H.length+" i "+i+ " N "+N +" old chunk fraction: "+(i/(double) N) + " new chunk fraction "+(c*(i/(double) N))); 

		return c*(i/(double) N); 
	}
	
	/**
	 * Calculates a step like function to compensate angle difference. For 180 degree difference this is a step funcion and for 90-180 the function
	 * gradually turns into a linear line. This is used to distribute changes in heading angle for vertical linear arrays. 
	 * @param N - the number of chunks
	 * @param angleDiff - total difference in heading between chunks (RADIANS)
	 * @return function to compensate heading angles. 
	 */
	private double[] angleFunction(int N, double angleDiff){
		
		double e=Math.pow(0.27*(2*Math.PI-angleDiff),15); 
		
		double[] H=new double[N]; 
		for (int i=0; i<N; i++){
		     H[i]=(2/Math.PI)*Math.atan2(e,i-N/2)-1; 
		}
		return H;
	}
	

	

}
