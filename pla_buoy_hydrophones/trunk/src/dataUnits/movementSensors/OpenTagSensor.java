package dataUnits.movementSensors;

import java.io.File;

import javafx.scene.layout.Pane;

public class OpenTagSensor extends AbstractMovementSensor {
	
	public File dataPath=null;  

	public OpenTagSensor() {
		super("", "Open Tag");
	}

	@Override
	public double[] getOrientationData(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	File getDataPath() {
		return dataPath;
	}

	@Override
	void loadData(long time1, long time2) {
		// TODO load open tag data; 
		
	}

	@Override
	Pane getSettingsPane() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
