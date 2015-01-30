package dataUnits;

import sensorInput.MovementSensor;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Array  {
	
	/**
	 * A rigid array 
	 */
	public final static int RIGID_ARRAY=0; 
	
	/**
	 * A linear flexible array 
	 */
	public final static int LINEAR_FILEXIBLE_ARRAY=1;

	/**
	 * The hydrophones associated with the array. 
	 */
	private ObservableList<Hydrophone> hydrophones=FXCollections.observableArrayList();

		
	/**
	 * List of movement sensors associated with the array. 
	 */
	private ObservableList<MovementSensor> movementSensors=FXCollections.observableArrayList();

	/**
	 * The array type 
	 */
    private final IntegerProperty arrayTypeProperty= new SimpleIntegerProperty(0);
    
    /**
     * The name of the array 
     */
    private final StringProperty arrayNameProperty= new SimpleStringProperty("");

    
    
    /**
     * Create a new array. 
     */
    public Array(String name, int type){
    	arrayNameProperty.setValue(name);
    	arrayTypeProperty.setValue(type);
    }
    
    
	public ObservableList<Hydrophone> getHydrophones() {
		return hydrophones;
	}

	
	public ObservableList<MovementSensor> getMovementSensors() {
		return movementSensors;
	}

	public IntegerProperty getArrayType() {
		return arrayTypeProperty;
	}


	public StringProperty getNameProperty() {
		// TODO Auto-generated method stub
		return arrayNameProperty;
	}
 

}
