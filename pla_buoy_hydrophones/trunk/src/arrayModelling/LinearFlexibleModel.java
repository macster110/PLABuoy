package arrayModelling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.fxyz.geometry.Point3D;

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
	
	int iterations=100;

	/**
	 * Reference to the array. 
	 */
	private HArray array; 


	public LinearFlexibleModel(HArray array){
		this.array=array; 
	}

	@Override
	public ArrayPos getTransformedPositions(ArrayList<HArray> childrenArray, ArrayList<Hydrophone> hydrophones,
			ArrayList<MovementSensor> movementSensors, long time) {
				
		int dim=2; 
		
		//want copy of array to mess around with. 
		ArrayList<HArray> arrayList=new ArrayList<HArray>(childrenArray); 
		ArrayList<Hydrophone> hydrophoneList=new ArrayList<Hydrophone>(hydrophones); 
		ArrayList<MovementSensor> sensorList=new ArrayList<MovementSensor>(movementSensors); 

		
		//order array reference postion by x,y,or z depending on linear dimension
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
		double[] arrayPos=new double[childrenArray.size()];
		int[] sortIndexArray=new int[childrenArray.size()]; //now need a record of the indexes for sorting
		for (int i=0; i<childrenArray.size(); i++){
			arrayPos[i]=childrenArray.get(i).getParentAttachPoint()[dim];
			sortIndexArray[i]=arrayList.indexOf(childrenArray.get(i));
		}
		
		//get angles from movement sensor. This model does not deal with depth readings. 
		ArrayList<double[]> angles=new ArrayList<double[]>();
		int[] sortIndexS=new int[movementSensors.size()]; //now need a record of the indexes for sorting
		double[] sensorPos = new double[movementSensors.size()];
		for (int i=0; i<movementSensors.size(); i++){
			angles.add(sensorList.get(i).getOrientationData(time));
			sensorPos[i]=sensorList.get(i).getReferencePosition()[dim]; 
			sortIndexS[i]=sensorList.indexOf(movementSensors.get(i)); 
		}
		
		
		//run the calculations
		ArrayPos newArrayPos = calcOrientation(arrayPos, hydrophonePos, sensorPos,
				angles, iterations, dim);
		
		
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
		newArrayPos.setHydrophonePositions(newHydrophonePos);
		newArrayPos.setArrayPositions(newArrayAttachPos);
		newArrayPos.setHArray(getArray());
		newArrayPos.setChildArrays(childrenArray);


		//return transformed array positions, hydrophone positions and streamer data for 3D visualisation. 
		return newArrayPos;

	}
	
	private ArrayPos calcOrientation(double[] arrayPositions, double[] hydrophonePos, double[] sensorPos,
			ArrayList<double[]> angles, int n, int dim){
		
		ArrayList<double[]> hydrophonePosTrans=new ArrayList<double[]>(); 
		ArrayList<Point3D> streamerPos=new ArrayList<Point3D>(); 
		ArrayList<ArrayList<Point3D>> streamerAll=new ArrayList<ArrayList<Point3D>>(); 
		
		ArrayPos arrayPos=new ArrayPos(); 
		
		//TODO- need to actually model array. 
		double[] hydrophoneLoc; 
		for (int i=0; i<hydrophonePos.length; i++){
			hydrophoneLoc=new double[3];
			hydrophoneLoc[dim]=hydrophonePos[i];
			hydrophonePosTrans.add(hydrophoneLoc);
			//hydrophonePositions.add()
		}
		
		
		//TEMP
		streamerPos.add(new Point3D(1,1,1));
		streamerPos.add(new Point3D(4,4,4));
		streamerAll.add(streamerPos);

		
		arrayPos.setHydrophonePositions(hydrophonePosTrans);
		arrayPos.setStreamerPositions(streamerAll);
		
		
		return arrayPos;
		
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


}
