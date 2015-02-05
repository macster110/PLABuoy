package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dataUnits.Array;

/**
 * Manages different types of Arrays
 * @author Jamie Macaulay 
 *
 */
public class ArrayManager {
	
	/**
	 * Enum of array types
	 * RIGID_ARRAY-A rigid array 
	 * LINEAR_FILEXIBLE_ARRAY - a flexible linear array, e.g. towed array or vertical array. 
	 */
	public enum ArrayType {
		RIGID_ARRAY, LINEAR_FILEXIBLE_ARRAY
	}
	
	/**
	 * Holds all array components. This list contains one non editable array. The 'master array' is the attachment point for all other arrays. It's a rigid array centered on (0,0,0)
	 */
	ObservableList<Array> arrays = FXCollections.observableArrayList();
	
	/**
	 * Create the array manager.
	 */
	public ArrayManager(){
		
	}
	
	/**
	 * Get the list of current arrays.
	 * @return list of current arrays. 
	 */
	public ObservableList<Array> getArrayList(){
		return arrays; 
	}

	
	 
	

}
