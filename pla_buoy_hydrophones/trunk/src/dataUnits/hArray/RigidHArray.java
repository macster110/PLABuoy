package dataUnits.hArray;

import layout.arrays.HArrayPane;
import layout.arrays.RigidArrayPane;
import main.ArrayManager.ArrayType;
import arrayModelling.ArrayModel;
import arrayModelling.RigidModel;

public class RigidHArray extends HArray{
	
	private RigidArrayPane rigidArrayPane;
	
	private boolean[] hydrophoneDim={true, true, true};
	
	private RigidModel rigidModel; 
	
	public RigidHArray(){
		super();
    	this.nameProperty().setValue("New Rigid Array ");
		this.hArrayTypeProperty().setValue(ArrayType.RIGID_ARRAY);
		rigidArrayPane=new RigidArrayPane(); 
		rigidModel=new RigidModel(this); 
	}
	
	@Override
	public ArrayModel getArrayModel() {
		return rigidModel;
	}

	@Override
	public HArrayPane<? extends HArray> getArrayPane() {
		return rigidArrayPane;
	}

	@Override
	public boolean[] getHydrophoneDimPos() {
		return hydrophoneDim;
	}

}
