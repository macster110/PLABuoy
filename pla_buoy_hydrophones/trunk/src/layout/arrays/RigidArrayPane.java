package layout.arrays;

import javafx.scene.control.Label;
import dataUnits.hArray.RigidHArray;

public class RigidArrayPane extends HArrayPane<RigidHArray> {
	
	public RigidArrayPane(){
		super();
		//this.getChildren().add(new Label("Rigid"));
	}

	@Override
	public int getParams(RigidHArray array) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParams(RigidHArray array) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean showErrorWarning(int errorType) {
		// TODO Auto-generated method stub
		return false;
	}

}
