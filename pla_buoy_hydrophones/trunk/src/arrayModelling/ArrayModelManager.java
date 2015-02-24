package arrayModelling;

import java.util.ArrayList;

import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;
import dataUnits.movementSensors.MovementSensor;
import layout.ControlPane.ChangeType;
import main.HArrayModelControl;

/**
 * Manages algorithms to calculate hydrophone positions. 
 * @author Jamie Macaulay
 *
 */
public class ArrayModelManager {
	
	public final static int NO_ERROR=0; 
	
	/**
	 * Error due to the fact the parent-> child tier structure has inconsistencies
	 * with the list of arrays. This occurs if say an array is added which is a child of another
	 *  array which itself is a child of that array. For any array you should be able to trace
	 *  parent back to the reference array. 
	 */
	public final static int INCONSISTANT_TIER_ERROR=1; 
	
	/**
	 * The current array position. Note this is the position after transformation have taken place and all 
	 * transformed co-ordinates are relative to reference array, not to parent arrays. The 2D array is a pyramid s
	 * structure with currentArrayPos(0)(0) the reference array currentArrayPos(1)[.....] all children of that array abd so on. 
	 * 
	 */
	private ArrayList<ArrayList<ArrayPos>> currentArrayPos; 

	private HArrayModelControl arrayModelControl;

	
	public ArrayModelManager(HArrayModelControl arrayModelControl) {
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
		
		System.out.println("tieredArray: "+tieredArray + " n "+n);
		
		/**
		 * Now sanity check - are the number of arrays in the tiered array the same as the 
		 * number of arrays in the observable list within the array manager?
		 */
		if (arrayCount!=arrayModelControl.getHArrayManager().getHArrayList().size()) return INCONSISTANT_TIER_ERROR;
		
		/**
		 * Now we have the tier structure can calculate hydrophone positions.
		 * first send array, associated movement sensor and associated hydrophones to algorithm. 
		 */
		ArrayList<ArrayList<ArrayPos>> tierredArrayResults=new ArrayList<ArrayList<ArrayPos>>(); 
		
		for (int i=0; i<tieredArray.size(); i++){
			ArrayList<ArrayPos> arrayResults=new ArrayList<ArrayPos>(); 
			for (int j=0; j<tieredArray.get(i).size(); j++){
				HArray array=tieredArray.get(i).get(j); 
				arrayResults.add(array.getArrayModel().getTransformedPositions(array.getChildArrays(), 
						new ArrayList<Hydrophone>(array.getHydrophones()), new ArrayList<MovementSensor>(array.getMovementSensors()), timeMillis));
			}
			tierredArrayResults.add(arrayResults);
		}
		
		/**
		 * Now we have a set of array results. Next thing to do is to combine these into one result which can be sent to the 3D map and saved. 
		 * Need to be careful here as we have to propagate up the reference positions of each array and compensate hydrophone array positions by adding
		 *attachment points. Note: this could have been integrated into previous nested loop but was kept separate for readability purposes. 
		 */
		for (int i=0; i<tierredArrayResults.size()-2; i++){
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
		arrayModelControl.notifyModelChanged(ChangeType.NEW_ARRAY_POS_CALCULATED);
		
		currentArrayPos=tierredArrayResults;
		
		//print results.
		System.out.println("Print transformed array positions "+currentArrayPos);
		printCurrentArrayPos(); 
	
		return 0; 
	}
	
	public void printCurrentArrayPos(){
		for (int i=0; i<currentArrayPos.size(); i++){
			for (int j=0; j<currentArrayPos.size(); j++){
				System.out.println(currentArrayPos.get(i).get(j).toString());
			}
		}
	}
	
	/**
	 * Transform arrayPos results 
	 * @param parent
	 * @param child
	 */
	private void transfromResults(ArrayPos parent, ArrayPos child){
		//find referencePos
		int index=parent.getChildArrays().indexOf(child.getParentHArray());
		if (index<0){
			System.err.println("Warning: ArrayModelManager: The parent ArrayPos did not contain child. ");
			return;
		}
		
		double[] referencePos=parent.getChildArrayPos().get(index); 
		
		//get data in terms of array reference point. 
		ArrayList<double[]> hydrophones=child.getHydrophonePos();
		ArrayList<double[]> childArrayPos = child.getChildArrayPos();
		
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
		
		//now set
		child.setHydrophonePositions(hydrophones);
		child.setChildArrayPositions(childArrayPos);

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
			if (childTier.get(i).getParentHArray().getParentHArray()==arrayPosParent.getParentHArray()) children.add(childTier.get(i));
		}
		return children;
	}
	
	public HArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

	public ArrayList<ArrayList<ArrayPos>> getArrayPos() {
		return currentArrayPos;
	}
	
}
