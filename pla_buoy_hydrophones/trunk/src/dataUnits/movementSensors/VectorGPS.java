package dataUnits.movementSensors;

import java.io.File;

import layout.movementSensors.SensorPane;
import main.SensorManager.SensorType;

/**
 * Vector GPS
 * @author Jamie Macaulay
 *
 */
public class VectorGPS extends AbstractMovementSensor {
	
	private boolean[] hasSensors={true, false, false, false, true, true};
	
	/**
	 * Data path for vector GPS data. 
	 */
	private File dataPath=null;  


	public VectorGPS(String sensorName, SensorType sensorType) {
		super(sensorName, sensorType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double[] getOrientationData(long time) {
		if (time<0) return super.getSimOrientationData();
		return null;
	}

	@Override
	public Double[] getPosition(long time) {
		return getReferencePosition();

	}

	@Override
	public Double[] getLatLong(long time) {
		if (time<0) return super.getSimLatLongData(); 
		return null;
	}

	@Override
	public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] getHasSensors() {
		return hasSensors;
	}

	@Override
	File getDataPath() {
		return dataPath;
	}

	@Override
	public void loadData(long time1, long time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SensorPane getSettingsPane() {
		// TODO Auto-generated method stub
		return null;
	}

}
