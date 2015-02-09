package main;

import layout.MainView;
import main.ArrayManager.ArrayType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import dataUnits.Array;
import dataUnits.movementSensors.MovementSensor;
import dataUnits.Hydrophone;

/**
 * Holds all information and functions for modelling hydrophone positions. 
 * @author Jamie Macaulay
 *
 */
public class ArrayModelControl {

	/**
	 * Static reference to the ArrayModelControl. 
	 */
	private static ArrayModelControl arrayControlInstance; 
	
	/**
	 * All hydrophones within the different arrays. No need to have a manager for hydrophones as there's only one type of hydrophone. 
	 */
	ObservableList<Hydrophone> hydrophones = FXCollections.observableArrayList();
	
	/**
	 * The reference array. Sits at 0,0,0 and can't be deleted
	 */
	private Array referenceArray;
	
	/**
	 * Manages different types of arrays
	 */
	private ArrayManager arrayManager; 
	
	
	/**
	 * Manages different types of sensors. 
	 */
	private SensorManager sensorManager;

	/**
	 * Reference to the MainView. 
	 */
	private MainView mainView; 
	
	public ArrayModelControl(){
		arrayControlInstance=this; 
		
		//create sensor and array managers. 
		arrayManager=new ArrayManager(); 
		sensorManager=new SensorManager(); 
		
		//create a non deleteable reference array. 
		referenceArray=new Array(); 
		referenceArray.nameProperty().setValue("Reference Array");
		referenceArray.arrayTypeProperty().setValue(ArrayType.RIGID_ARRAY);
		referenceArray.parentArrayProperty().setValue(null);
	
		arrayManager.getArrayList().add(referenceArray);
		
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
			if (hydrophones.get(i).channelProperty().get()== channel) return true; 
		}
		return false; 
	}
	
	/**
	 * Get a list of all current arrays
	 * @return a list iof all current arrays
	 */
	public ObservableList<Array> getArrays() {
		return arrayManager.getArrayList();
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
		return sensorManager.getSensorList();
	}
	
	public static ArrayModelControl getInstance(){
		return arrayControlInstance; 
	}

	public static void create() {
		if (arrayControlInstance==null){
			new  ArrayModelControl(); 
		}
		
	}

	/**
	 * Convenience function to get the reference array, 
	 * @return the reference array. This is a rigid array set at (0,0,0) whihc cannot be deleted. 
	 */
	public Array getReferenceArray() {
		return referenceArray;
	}

	/**
	 * Get the sensor mamanger. This organises sensors for all arrays.
	 * @return the sensor manager. 
	 */
	public SensorManager getSensorManager() {
		return sensorManager;
	}
	
	/**
	 * Convenience function to get main stage of application. 
	 * @return the application main stage. Can return null if MainView has not been set/created. 
	 */
	public Stage getPrimaryStage(){
		if (mainView==null) return null; 
		return mainView.getPrimaryStage();
	}

	/**
	 * Set the MainView. This is only called once on application start up. 
	 * @param mainView - MainView class to set. 
	 */
	protected void setMainView(MainView mainView) {
		this.mainView=mainView; 
	}
	

}
