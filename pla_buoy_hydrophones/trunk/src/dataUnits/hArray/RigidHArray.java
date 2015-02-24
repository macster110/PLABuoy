package dataUnits.hArray;

import layout.arrays.HArrayPane;
import layout.arrays.RigidArrayPane;
import main.HArrayManager.ArrayType;
import arrayModelling.ArrayModel;

public class RigidHArray extends HArray{
	
	private RigidArrayPane rigidArrayPane;
	
	private boolean[] hydrophoneDim={true, true, true};
	
	public RigidHArray(){
		super();
    	this.nameProperty().setValue("New Rigid Array ");
		this.hArrayTypeProperty().setValue(ArrayType.RIGID_ARRAY);
		rigidArrayPane=new RigidArrayPane(); 
	}
	
	@Override
	public ArrayModel getArrayModel() {
		// TODO Auto-generated method stub
		return null;
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
