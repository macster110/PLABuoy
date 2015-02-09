package dataUnits.movementSensors;

import java.io.File;

import layout.movementSensors.OpenTagPane;
import main.SensorManager.SensorType;

public class OpenTagSensor extends AbstractMovementSensor {
	
	private File dataPath=null;  
	
	private SensorPane otSensorPane; 

	public OpenTagSensor() {
		super("", SensorType.OPEN_TAG);
		otSensorPane=new OpenTagPane(); 
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
	public File getDataPath() {
		return dataPath;
	}

	@Override
	public void loadData(long time1, long time2) {
		// TODO load open tag data; 
		
	}

	@Override
	public SensorPane getSettingsPane() {
		return otSensorPane;
	}
	
	@Override
	public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDataPath(File dataPath) {
		this.dataPath=dataPath; 		
	}

	

}
