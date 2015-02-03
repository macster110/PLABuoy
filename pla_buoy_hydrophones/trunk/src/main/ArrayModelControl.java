package main;

import sensorInput.MovementSensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dataUnits.Array;
import dataUnits.Array.ArrayType;
import dataUnits.Hydrophone;

/**
 * Holds all information for modelling hydrophones
 * @author Jamie Macaulay
 *
 */
public class ArrayModelControl {

	private static ArrayModelControl arrayControlInstance; 
	
	/**
	 * Holds all array components. This list contains one non editable array. The 'master array' is the attachment point for all other arrays. It's a rigid array centered on (0,0,0)
	 */
	ObservableList<Array> arrays = FXCollections.observableArrayList();
	
	/**
	 * All hydrophones within the different arrays
	 */
	ObservableList<Hydrophone> hydrophones = FXCollections.observableArrayList();

	/**
	 * All sensors attached to the  arrays. 
	 */
	ObservableList<MovementSensor> sensors = FXCollections.observableArrayList();

	
	public ArrayModelControl(){
		arrayControlInstance=this; 
		//create a non delteable reference array. 
		Array mainArray=new Array(); 
		mainArray.nameProperty().setValue("Reference Array");
		mainArray.arrayTypeProperty().setValue(ArrayType.RIGID_ARRAY);
		mainArray.parentArrayProperty().setValue(null);
		
		arrays.add(mainArray);
		
	} 
	
	/**
	 * Run the model. 
	 */
	public void runModel(){
		
	}
	
	/**
	 * Check whether a hydrophone channel is already used by another hydrophone.
	 * @param channel - the channel to check.  
	 * @return true if another hydrophone already sues the channel 
	 */
	public boolean checkHydrophoneChannels(int channel){
		for (int i=0; i<hydrophones.size(); i++ ){
			if (hydrophones.get(i).equals(channel)) return true; 
		}
		return false; 
	}
	
	/**
	 * Get a list of all current arrays
	 * @return a list iof all current arrays
	 */
	public ObservableList<Array> getArrays() {
		return arrays;
	}

	/**
	 * Get a list of all current hydrophones.
	 * @return a list of all current hydrophones
	 */
	public ObservableList<Hydrophone> getHydrophones() {
		return hydrophones;
	}

	/**
	 * A list of all current movement sensors. 
	 * @return a list of all current movement sensors. 
	 */
	public ObservableList<MovementSensor> getSensors() {
		return sensors;
	}
	
	public static ArrayModelControl getInstance(){
		return arrayControlInstance; 
	}

	public static void create() {
		if (arrayControlInstance==null){
			new  ArrayModelControl(); 
		}
		
	}
	

}
