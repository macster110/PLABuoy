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
	 * TODO If depth sensor is ever used can create an 'OPEN_TAG_DEPTH' sensor type. 
	 */
	private boolean[]  sensorTypes={true,true,true, false, false, false};

	public OpenTagSensor() {
		super("", SensorType.OPEN_TAG);
		otSensorPane=new OpenTagPane(); 
	}

	@Override
	public Double[] getOrientationData(long time) {
		//return sim data. 
		if (time<0) return super.getSimOrientationData();
		//try return true data. 
		return null;
	}

	@Override
	public Double[] getPosition(long time) {
		return getReferencePosition();
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

	@Override
	public Double[] getLatLong(long time) {
		//open tag has no lat long
		return null ;
	}

	

}
