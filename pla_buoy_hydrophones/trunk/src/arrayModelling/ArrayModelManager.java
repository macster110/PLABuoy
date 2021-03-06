package arrayModelling;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;
import dataUnits.movementSensors.MovementSensor;
import layout.ControlPane.ChangeType;
import main.ArrayModelControl;

/**
 * Manages algorithms to calculate hydrophone positions. 
 * @author Jamie Macaulay
 *
 */
public class ArrayModelManager {
	
	/**
	 * No error has occured
	 */
	public final static int NO_ERROR=0; 
	
	/**
	 * Error due to the fact the parent-> child tier structure has inconsistencies
	 * with the list of arrays. This occurs if say an array is added which is a child of another
	 * array which itself is a child of that array. For any array you should be able to trace
	 * parent back to the reference array. 
	 */
	public final static int DUPLICATE_TIER_ERROR=1; 
	
	/**
	 * The current array position. Note this is the position after transformation have taken place and all 
	 * transformed co-ordinates are relative to reference array, not to parent arrays. The 2D array is a pyramid s
	 * structure with currentArrayPos(0)(0) the reference array currentArrayPos(1)[.....] all children of that array abd so on. 
	 * 
	 */
	private ArrayList<ArrayList<ArrayPos>> currentArrayPos; 

	private ArrayModelControl arrayModelControl;

	
	public ArrayModelManager(ArrayModelControl arrayModelControl) {
		this.arrayModelControl=arrayModelControl; 
	}
	
	/**
	 * Calculate positions of hydrophone based on current array, 
	 * @return error flag. 0 if no error. 
	 */
	public int calculateHydrophonePositions(long timeMillis){
		
		/**
		 * First create a tiered system starting with the reference array and then each level is the child arrays of the previous
		 * level. Once no array have been found in a level then that must be the lowest level.
		 */
		ArrayList<ArrayList<HArray>> tieredArray=new ArrayList<ArrayList<HArray>>(); 
		
		ArrayList<HArray> parentArray=new ArrayList<HArray>();
		parentArray.add(arrayModelControl.getReferenceArray());
		
		int n=0; //used to make sure while loop doesn't hang program. //we assume there are never going to be more than 1000 arrays. 
		int arrayCount=1;
		while (parentArray.size()>0 || n==1000){
			
			tieredArray.add(parentArray);
			
			ArrayList<HArray> childArray=new ArrayList<HArray>();
			HArray array; 
			for (int i=0; i<arrayModelControl.getHArrayManager().getHArrayList().size(); i++){
				array=arrayModelControl.getHArrayManager().getHArrayList().get(i);
				//check whether array is a child of previous array tier. 
				boolean isChild=false; 
				for (int j=0; j<parentArray.size(); j++){
					if (array.getParentHArray()==parentArray.get(j)){
						isChild=true;
						continue; 
					}
				}
				if (isChild){
					childArray.add(array);
					arrayCount++;
				}
			}
			parentArray=childArray;
			n++; 
		}
		
		//System.out.println("ArrayModelManager: TieredArray: "+tieredArray + " n "+n);
		
		/**
		 * Now sanity check - are the number of arrays in the tiered array the same as the 
		 * number of arrays in the observable list within the array manager?
		 */
		if (arrayCount!=arrayModelControl.getHArrayManager().getHArrayList().size()) return DUPLICATE_TIER_ERROR;
		
		/**
		 * Now we have the tier structure can calculate hydrophone positions.
		 * first send array, associated movement sensor and associated hydrophones to algorithm. 
		 */
		ArrayList<ArrayList<ArrayPos>> tierredArrayResults=new ArrayList<ArrayList<ArrayPos>>(); 
		
		for (int i=0; i<tieredArray.size(); i++){
			ArrayList<ArrayPos> arrayResults=new ArrayList<ArrayPos>(); 
			for (int j=0; j<tieredArray.get(i).size(); j++){
				HArray array=tieredArray.get(i).get(j);
				//TRANSFORM THE ARRAY POSITIONS. 
				arrayResults.add(array.getArrayModel().getTransformedPositions(array.getChildArrays(), 
						new ArrayList<Hydrophone>(array.getHydrophones()), new ArrayList<MovementSensor>(array.getMovementSensors()), timeMillis));
			}
			tierredArrayResults.add(arrayResults);
		}
		
		/**
		 * Now we have a set of array results but each array has it's own co-ordinate system with (0,0,0) being where it connects to it's parent. 
		 * Next thing to do is to combine these into one result which can be sent to the 3D map and saved. 
		 * Need to be careful here as we have to propagate up the reference positions of each array and compensate hydrophone array positions by adding
		 * attachment points. Note: this could have been integrated into previous nested loop but was kept separate for readability purposes. 
		 */
		for (int i=0; i<tierredArrayResults.size()-1; i++){
			for (int j=0; j<tierredArrayResults.get(i).size(); j++){
				ArrayPos parent= tierredArrayResults.get(i).get(j);
				//find children in lower tier
				ArrayList<ArrayPos> children =findChildren( parent, tierredArrayResults.get(i+1));
				//now go through the children of this array and transform their positions.
				for (int k=0;k<children.size(); k++){
					//first find the results for child in tier below
					//now transform all results in children 
					transfromResults(parent, children.get(k));
				}
			}
		}

		//notify that a new hydrophone array has been calculated  
		currentArrayPos=tierredArrayResults;

		arrayModelControl.notifyModelChanged(ChangeType.NEW_ARRAY_POS_CALCULATED);
				
		//print results.
		//System.out.println("Print transformed array positions "+currentArrayPos);
		//printCurrentArrayPos(); 
	
		return 0; 
	}
	
	public void printCurrentArrayPos(){
		System.out.println("Current Array Pos: ");
		for (int i=0; i<currentArrayPos.size(); i++){
			for (int j=0; j<currentArrayPos.get(i).size(); j++){
				System.out.println(currentArrayPos.get(i).get(j).toString());
			}
		}
	}
	
	/**
	 * Transform arrayPos results 
	 * @param parent
	 * @param child
	 * @return transformed child. 
	 */
	private ArrayPos transfromResults(ArrayPos parent, ArrayPos child){
		//find referencePos
		int index=parent.getChildArrays().indexOf(child.getHArray());
		if (index<0){
			System.err.println("Warning: ArrayModelManager: The parent ArrayPos did not contain child. ");
		}
		
		double[] referencePos=parent.getTransformChildArrayPos().get(index); 
		
		//get data in terms of array reference point. 
		ArrayList<double[]> hydrophones=child.getTransformHydrophonePos();
		ArrayList<double[]> childArrayPos = child.getTransformChildArrayPos();
		ArrayList<double[]> sensorPos = child.getTransformSensorPos();

		
		//now need to convert to reference frame of parent
		for (int i=0; i<hydrophones.size(); i++){
			hydrophones.get(i)[0]=hydrophones.get(i)[0]+referencePos[0];
			hydrophones.get(i)[1]=hydrophones.get(i)[1]+referencePos[1];
			hydrophones.get(i)[2]=hydrophones.get(i)[2]+referencePos[2];
		}
		
		for (int i=0; i<childArrayPos.size(); i++){
			childArrayPos.get(i)[0]=childArrayPos.get(i)[0]+referencePos[0];
			childArrayPos.get(i)[1]=childArrayPos.get(i)[1]+referencePos[1];
			childArrayPos.get(i)[2]=childArrayPos.get(i)[2]+referencePos[2];
		}
	
		for (int i=0; i<sensorPos.size(); i++){
			sensorPos.get(i)[0]=sensorPos.get(i)[0]+referencePos[0];
			sensorPos.get(i)[1]=sensorPos.get(i)[1]+referencePos[1];
			sensorPos.get(i)[2]=sensorPos.get(i)[2]+referencePos[2];
		}
		
		ArrayList<ArrayList<Point3D>> transformStreamer=new ArrayList<ArrayList<Point3D>>();
		for (int i=0; i<child.getTransformStreamerPositions().size(); i++){
			ArrayList<Point3D> transLine=new ArrayList<Point3D>(); 
			for (int j=0; j<child.getTransformStreamerPositions().get(i).size(); j++){
				Point3D transPoint=new Point3D(
						child.getTransformStreamerPositions().get(i).get(j).getX()+referencePos[0],
						child.getTransformStreamerPositions().get(i).get(j).getY()+referencePos[1],
						child.getTransformStreamerPositions().get(i).get(j).getZ()+referencePos[2]
						); 
				transLine.add(transPoint);
			} 
			transformStreamer.add(transLine);
		}
		
		//now set
		child.setTransformHydrophonePos(hydrophones);
		child.setTransformChildArrayPos(childArrayPos);
		child.setTransformSensorPos(sensorPos);
		child.setStreamerPositions(transformStreamer);
		
		return child; 

	}
	
	/**
	 * Find the children of an array within a list
	 * @param arrayPosParent - parent array
	 * @param childTier - list of potential child arrays
	 * @return - the children which belong to the parent array. 
	 */
	private ArrayList<ArrayPos> findChildren(ArrayPos arrayPosParent, ArrayList<ArrayPos> childTier){
		 ArrayList<ArrayPos> children=new ArrayList<ArrayPos>(); 
		for (int i=0; i<childTier.size(); i++){
			if (childTier.get(i).getHArray().getParentHArray()==arrayPosParent.getHArray()) children.add(childTier.get(i));
		}
		return children;
	}
	
	public ArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

	public ArrayList<ArrayList<ArrayPos>> getArrayPos() {
		return currentArrayPos;
	}
	
}
