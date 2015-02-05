package dataUnits;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBoxBase;

public class Hydrophone {

	/**
	 * Channel of the hydrophone. 
	 */
	public final SimpleIntegerProperty channel= new SimpleIntegerProperty(0, "channel"); 
	/**
	 *The array the hydrophone belong to. 
	 */
	private final ObjectProperty<Array> parentArray=new SimpleObjectProperty<Array>(); 

	/**
	 *x Position of hydrophone relative to the array (meters)
	 */
	private final DoubleProperty xPos=new SimpleDoubleProperty(0.); 
	/**
	 *y Position of hydrophone relative to the array (meters)
	 */
	private final DoubleProperty yPos=new SimpleDoubleProperty(0.); 
	
	/**
	 *z Position of hydrophone relative to the array (meters)
	 */
	private final DoubleProperty zPos=new SimpleDoubleProperty(0.); 
	
	/**
	 * Error in x position of hydrophone (meters)
	 */
	private final DoubleProperty xPosErr=new SimpleDoubleProperty(0.01); 
	/**
	 *  Error in y position of hydrophone (meters)
	 */
	private final DoubleProperty yPosErr=new SimpleDoubleProperty(0.01); 
	
	/**
	 *  Error in z position of hydrophone (meters)
	 */
	private final DoubleProperty zPosErr=new SimpleDoubleProperty(0.01); 
	
	/**
	 * Create a hydrophone. 
	 */
	public Hydrophone(double x, double y, double z){
		xPos.setValue(x);
		yPos.setValue(y);	
		zPos.setValue(z);		
	}
	
	/**
	 * Get the position of the hydrophone relative to it's array. 
	 * @return the x, y, z posiiton of the hydrophone in meters. 
	 */
	public double[] getPos() {
		double[] pos={xPos.getValue(), yPos.getValue(), zPos.getValue()}; 
		return pos;
	}

	/**
	 * Set the hydrophone position relative to it's array. 
	 * @param pos x, y, z position of the hydrophone in meters. 
	 */
	public void setPos(double[] pos) {
		xPos.setValue(pos[0]);
		yPos.setValue(pos[1]);
		zPos.setValue(pos[2]);
	}

	/**
	 * Get the position error. 
	 * @return the error in x, y and positions of hydrophone in meters. 
	 */
	public double[] getPosError() {
		double[] posErr={xPosErr.getValue(), yPosErr.getValue(), zPosErr.getValue()}; 
		return posErr;
	}

	public void setPosError(double[] posError) {
		xPosErr.setValue(posError[0]);
		yPosErr.setValue(posError[1]);
		zPosErr.setValue(posError[2]);
	}

	public Array getParentArray() {
		return parentArray.getValue();
	}

	public void setParentArray(Array parentArray) {
		this.parentArray.setValue(parentArray);
	}
	
	/**
	 * Get the channel property. This is the hardware channel that the hydrophone represents. 
	 * @return the hardware channle associated with the hydrophone. 
	 */
	public SimpleIntegerProperty channelProperty() {
		return channel;
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

	public DoubleProperty xErrProperty() {
		return xPosErr;
	}

	public DoubleProperty yErrProperty() {
		return yPosErr;
	}

	public DoubleProperty zErrProperty() {
		return zPosErr;
	}

	public ObjectProperty<Array> parentArrayProperty() {
		return parentArray;
	}


	
	
	

}
