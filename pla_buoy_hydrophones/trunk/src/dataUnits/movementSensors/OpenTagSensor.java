package dataUnits.movementSensors;

import java.io.File;

import layout.movementSensors.OpenTagPane;
import layout.movementSensors.SensorPane;
import main.SensorManager.SensorType;

public class OpenTagSensor extends AbstractMovementSensor {
	
	private File dataPath=null;  
	
	private SensorPane otSensorPane; 
	
	/**
	 * List of sensor types the open tag has. Ignore the depth sensor as this is an add on to open tags.
	 * If depth sensor is ever used can create an 'OPEN_TAG_DEPTH' sensor type. 
	 */
	private boolean[]  sensorTypes={true,true,true, false, false, false};

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

	@Override
	public boolean[] getHasSensors() {
		return sensorTypes;
	}

	

}
