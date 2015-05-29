package dataUnits.hArray;

import layout.arrays.FlexVertPane;
import layout.arrays.HArrayPane;
import layout.movementSensors.SensorPane;
import main.ArrayModelControl;
import main.ArrayManager.ArrayType;
import arrayModelling.ArrayModel;
import arrayModelling.LinearFlexibleModel;
import arrayModelling.LinearFlexibleModel2;
import arrayModelling.LinearFlexibleModel3;
import arrayModelling.RigidModel;

public class FlexibleVerticalArray extends HArray {
	
	private FlexVertPane flexVertPane; 
	
	private boolean[] hydrophoneDim={false, false, true};

	private LinearFlexibleModel flexModel;
	

	public FlexibleVerticalArray(){
		super();
    	this.nameProperty().setValue("New Flexible Array ");
		this.hArrayTypeProperty().setValue(ArrayType.FLEXIBLE_VERTICAL_ARRAY);
		flexVertPane=new FlexVertPane(); 
		//flexModel=new LinearFlexibleModel2(this); 
		flexModel=new LinearFlexibleModel3(this); 

	}

	@Override
	public ArrayModel getArrayModel() {
		return flexModel;
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
