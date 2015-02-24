package main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import dataUnits.hArray.FlexibleVerticalArray;
import dataUnits.hArray.RigidHArray;
import dataUnits.hArray.HArray;
import layout.ControlPane.ChangeType;

/**
 * Manages different types of Arrays
 * @author Jamie Macaulay 
 *
 */
public class HArrayManager {
	
	/**
	 * Enum of array types
	 * RIGID_ARRAY-A rigid array 
	 * LINEAR_FILEXIBLE_ARRAY - a flexible linear array, e.g. towed array or vertical array. 
	 */
	public enum ArrayType {
		RIGID_ARRAY, FLEXIBLE_VERTICAL_ARRAY
	}
	
	/**
	 * Holds all array components. This list contains one non editable array. The 'master array' is the attachment point for all other arrays. It's a rigid array centered on (0,0,0)
	 */
	ObservableList<HArray> arrays = FXCollections.observableArrayList();
	
	/**
	 * Reference to the array model control. 
	 */
	private HArrayModelControl arrayModelControl;
	
	/**
	 * Create the array manager.
	 */
	public HArrayManager(HArrayModelControl arrayModelControl){
		this.arrayModelControl=arrayModelControl;
				
		arrays.addListener((ListChangeListener.Change<? extends HArray> c) ->{
			arrayModelControl.notifyModelChanged(ChangeType.ARRAY_CHANGED); 
		});
	}
	
	/**
	 * Create a new sensor. 
	 * @return the new sensor. 
	 */
	public HArray createNewArray(ArrayType type){
		switch(type){
		case FLEXIBLE_VERTICAL_ARRAY:
			return new FlexibleVerticalArray(); 
		case RIGID_ARRAY:
			return new RigidHArray(); 
		default:
			break;
		}

		return null;
	}
	
	/**
	 * Get the list of current arrays.
	 * @return list of current arrays. 
	 */
	public ObservableList<HArray> getHArrayList(){
		return arrays; 
	}

	
	 
	

}
