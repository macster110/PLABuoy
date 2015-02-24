package main;

import arrayModelling.ArrayModelManager;
import layout.ArrayModelView;
import layout.ControlPane.ChangeType;
import main.HArrayManager.ArrayType;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import dataUnits.hArray.HArray;
import dataUnits.hArray.RigidHArray;
import dataUnits.movementSensors.MovementSensor;
import dataUnits.Hydrophone;

/**
 * Holds all information and functions for modelling hydrophone positions. 
 * @author Jamie Macaulay
 *
 */
public class HArrayModelControl {

	/**
	 * Static reference to the ArrayModelControl. 
	 */
	private static HArrayModelControl arrayControlInstance; 
	
	/**
	 * All hydrophones within the different arrays. No need to have a manager for hydrophones as there's only one type of hydrophone. 
	 */
	ObservableList<Hydrophone> hydrophones = FXCollections.observableArrayList();
	
	/**
	 * The reference array. Sits at 0,0,0 and can't be deleted
	 */
	private HArray referenceArray;
	
	/**
	 * Manages different types of arrays
	 */
	private HArrayManager arrayManager; 
	
	/**
	 * Manages different types of sensors. 
	 */
	private SensorManager sensorManager;

	/**
	 * Reference to the MainView. 
	 */
	private ArrayModelView mainView;

	/**
	 * Reference to the array model manager. This manages the algorithms which calculate hydrophones
	 */
	private ArrayModelManager arrayModelManager; 
	
	public HArrayModelControl(){
		arrayControlInstance=this; 
		
		//create sensor and array managers. 
		arrayManager=new HArrayManager(this);
		sensorManager=new SensorManager(this); 
		
		//manages modelling algorithms 
		arrayModelManager=new ArrayModelManager(this);
		
		//create a non deleteable reference array. 
		referenceArray=new RigidHArray(); 
		referenceArray.nameProperty().setValue("Reference Array");
		referenceArray.hArrayTypeProperty().setValue(ArrayType.RIGID_ARRAY);
		referenceArray.parentHArrayProperty().setValue(null);
	
		arrayManager.getHArrayList().add(referenceArray);
		
		//add notification listener to hydrophone list
		hydrophones.addListener((Change<? extends Hydrophone> c) ->{
			this.notifyModelChanged(ChangeType.HYDROPHONE_CHANGED);
		});
		
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
	 * Check all array data units have correct associated hydrophone within internal lists. 
	 * Note: this is needed to 1) update tables and 2)update array for modelling. 
	 */
	public void updateArrayHydrophones(){
		for (int i=0; i<arrayManager.getHArrayList().size(); i++){
			
		}
	}
	
	/**
	 * Get a list of all current arrays
	 * @return a list iof all current arrays
	 */
	public ObservableList<HArray> getArrays() {
		return arrayManager.getHArrayList();
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
	
	public static HArrayModelControl getInstance(){
		return arrayControlInstance; 
	}

	public static void create() {
		if (arrayControlInstance==null){
			new  HArrayModelControl(); 
		}
		
	}

	/**
	 * Convenience function to get the reference array, 
	 * @return the reference array. This is a rigid array set at (0,0,0) whihc cannot be deleted. 
	 */
	public HArray getReferenceArray() {
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
	protected void setMainView(ArrayModelView mainView) {
		this.mainView=mainView; 
	}
	
	/**
	 * Notify all control panes that something has changed
	 * @param type - type of change
	 */
	public void notifyModelChanged(ChangeType type){
		if (mainView!=null) this.mainView.notifyModelChanged(type);
		
	}

	/**
	 * Get the array manager. This holds the list of current arrays. 
	 * @return the array manager. 
	 */
	public HArrayManager getHArrayManager() {
		return this.arrayManager;
	}
	
	public ArrayModelManager getArrayModelManager(){
		return this.arrayModelManager;
	}
	

}
