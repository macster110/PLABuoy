package dataUnits;

import dataUnits.movementSensors.MovementSensor;
import main.ArrayManager.ArrayType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;

public class Array  {
	
	/**
	 * The hydrophones associated with the array. 
	 */
	private final ObservableList<Hydrophone> hydrophones=FXCollections.observableArrayList();

		
	/**
	 * List of movement sensors associated with the array. 
	 */
	private final ObservableList<MovementSensor> movementSensors=FXCollections.observableArrayList();

	/**
	 * The array type 
	 */
    private final ObjectProperty<ArrayType> arrayType = new SimpleObjectProperty<ArrayType>(ArrayType.RIGID_ARRAY);
    
    /**
     * Orientation of the array- only applies to linear arrays.
     */
    private ObjectProperty<Orientation> orientationProperty=new SimpleObjectProperty<Orientation>(Orientation.VERTICAL); 
    
    /**
     * The name of the array 
     */
    private final StringProperty arrayNameProperty= new SimpleStringProperty("");
    
    /**
     * The array must be attached to another array, either the reference array or another array e.g for SMRU pla buoy the cluster 
     * array is attached to the vertical array. However for the standard cluster and vertical array on a vessel, the cluster and vertical array 
     * would be unrelated and individually attcahed to the reference array or another rigid array. 
     */
    private final ObjectProperty<Array> parentArrayProperty= new SimpleObjectProperty<Array>(); 
    
    /**
     * x position of attachment point. The attachment point represents (0,0,0) of this array. xPos, yPos, zPos represent the attachment point on relative to the ATTACHMENT ARRAY. 
     */
    private final DoubleProperty xPos=new SimpleDoubleProperty(0,"xPos");
   

	/**
     * y position of attachment point. 
     */
    private final DoubleProperty yPos=new SimpleDoubleProperty(0,"yPos");
    
    /**
     * z position of attachment point. 
     */
    private final DoubleProperty zPos=new SimpleDoubleProperty(0,"zPos");


	/**
     * Create a new array. 
     */
    public Array(){
    
    }
    
    /**
     * Get the parent attachment point of the array. The attachment point is the point on the PARENT ARRAY at which this array attaches. In this array's frame the attachment point is always (0,0,0); 
     * @return the attachment point of the array on its parent. 
     */
    public double[] getParentAttachPoint(){
    	double[] attachmentPoint={xPos.get(), yPos.get(), zPos.get()};
    	return attachmentPoint;
    }
    

    /**
     * Create a new array. 
     */
    public Array(String name, ArrayType type){
    	arrayNameProperty.setValue(name);
    	arrayType.setValue(type);
    }
    
    /**
     * Set the parent attachment point of the array. The attachment point is the point on the PARENT ARRAY at which this array attaches. In this array's frame the attachment point is always (0,0,0); 
     * @param the attachment point of the array on its parent. 
     */
    public void setParentAttachPoint(double[] atachmentPoint){
    	xPos.setValue(atachmentPoint[0]);
    	yPos.setValue(atachmentPoint[1]);
    	zPos.setValue(atachmentPoint[2]);
    }
    
    /**
     * Get a list of hydrophones which belong to the Array. The positions of the hydrophones are relative to the array. 
     * @return a list of hydrophones which belong to the array. 
     */
	public ObservableList<Hydrophone> getHydrophones() {
		return hydrophones;
	}

	 /**
     * Get a list of movement sensors which belong to the Array. The positions of the movement sensors are relative to the array. 
     * @return a list of movement sensors which belong to the array. 
     */
	public ObservableList<MovementSensor> getMovementSensors() {
		return movementSensors;
	}

	/**
	 * Get the property  for the name of the array. 
	 * @return the name property value. 
	 */
	public StringProperty nameProperty() {
		return arrayNameProperty;
	}

	/**
	 * Get the property for the array type. 
	 * @return the ArrayType propoerty. 
	 */
	public ObjectProperty<ArrayType> arrayTypeProperty() {
		return arrayType;
	}

    public ObjectProperty<Array> parentArrayProperty() {
		return parentArrayProperty;
	}
    
    public DoubleProperty xPosProperty() {
		return xPos;
	}

	public DoubleProperty yPosProperty() {
		return yPos;
	}

	public DoubleProperty zPosProperty() {
		return zPos;
	}
	
	public ObjectProperty<Orientation> orientationProperty(){
		return orientationProperty; 
	}

	
	

 

}
