package dataUnits;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public class Hydrophone {

	/**
	 * Channel of the hydrophone. 
	 */
	public SimpleIntegerProperty channel= new SimpleIntegerProperty(0, "channel"); 
	
	/**
	 * Position of hydrophone relative to the attachment point of the array
	 */
	private double[] pos=new double[3]; 

	/**
	 * The error in hydrophone positions. 
	 */
	private double[] posError=new double[3]; 
	
	/*
	 *The array the hydrophone belong to. 
	 */
	private Array parentArray; 
	
	/**
	 * Create a hydrophone. 
	 */
	public Hydrophone(double x, double y, double z){
		pos[0]=x;
		pos[1]=y;
		pos[2]=z;
	}
	
	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public double[] getPosError() {
		return posError;
	}

	public void setPosError(double[] posError) {
		this.posError = posError;
	}

	public Array getParentArray() {
		return parentArray;
	}

	public void setParentArray(Array parentArray) {
		this.parentArray = parentArray;
	}
	
	public SimpleIntegerProperty channelProperty() {
		return channel;
	}


	
	
	

}
