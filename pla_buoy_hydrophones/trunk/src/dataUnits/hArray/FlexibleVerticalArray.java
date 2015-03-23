package dataUnits.hArray;

import layout.arrays.FlexVertPane;
import layout.arrays.HArrayPane;
import layout.movementSensors.SensorPane;
import main.ArrayModelControl;
import main.ArrayManager.ArrayType;
import arrayModelling.ArrayModel;

public class FlexibleVerticalArray extends HArray {
	
	private FlexVertPane flexVertPane; 
	
	private boolean[] hydrophoneDim={false, false, true};
	
	

	public FlexibleVerticalArray(){
		super();
    	this.nameProperty().setValue("New Flexible Array ");
		this.hArrayTypeProperty().setValue(ArrayType.FLEXIBLE_VERTICAL_ARRAY);
		flexVertPane=new FlexVertPane(); 
	}

	@Override
	public ArrayModel getArrayModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HArrayPane<FlexibleVerticalArray> getArrayPane() {
		return flexVertPane;
	}

	@Override
	public boolean[] getHydrophoneDimPos() {
		return hydrophoneDim;
	}

}
