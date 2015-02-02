package dataUnits;

import sensorInput.MovementSensor;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Array  {
	
	/**
	 * Enum of array types
	 * RIGID_ARRAY-A rigid array 
	 * LINEAR_FILEXIBLE_ARRAY - a flexible linear array, e.g. towed array or vertical array. 
	 */
	public enum ArrayType {
		RIGID_ARRAY, LINEAR_FILEXIBLE_ARRAY
	}
	
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
    private ObjectProperty<ArrayType> arrayType = new SimpleObjectProperty<ArrayType>(ArrayType.RIGID_ARRAY);
    
    /**
     * The name of the array 
     */
    private final StringProperty arrayNameProperty= new SimpleStringProperty("");

    
    /**
     * Create a new array. 
     */
    public Array(){
    
    }
    
    /**
     * Create a new array. 
     */
    public Array(String name, ArrayType type){
    	arrayNameProperty.setValue(name);
    	arrayType.setValue(type);
    }

 
    
	public ObservableList<Hydrophone> getHydrophones() {
		return hydrophones;
	}

	
	public ObservableList<MovementSensor> getMovementSensors() {
		return movementSensors;
	}

	public StringProperty nameProperty() {
		// TODO Auto-generated method stub
		return arrayNameProperty;
	}

	public ObjectProperty<ArrayType> arrayTypeProperty() {
		return arrayType;
	}
	
	

 

}
