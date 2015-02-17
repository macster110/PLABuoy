package arrayModelling;

import java.util.ArrayList;

import dataUnits.hArray.HArray;
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
		 * level. Once no array have been found in a level then that must be the lowest level. Ot
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
		
		/**
		 * now sanity check - are the number of arrays in the tiered array the same as the 
		 * number of arrays in the observable list within the array manager?
		 */
		if (arrayCount!=arrayModelControl.getHArrayManager().getHArrayList().size()) return INCONSISTANT_TIER_ERROR;
		
		//Now we have the tier structure can calculate hydrophone positions.
		//first send array, associated movement sensor and associated hydrophones to algorithm. 
		
		
		
		return 0; 
	}
	
	
	
	public HArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}
	
	


}
