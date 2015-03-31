package dataUnits.movementSensors;

import java.io.File;
import java.util.List;

import layout.movementSensors.SensorPane;
import layout.movementSensors.VectorGPSPane;
import main.SensorManager.SensorType;

/**
 * Vector GPS
 * @author Jamie Macaulay
 *
 */
public class VectorGPS extends AbstractMovementSensor {
	
	private boolean[] hasSensors={true, false, false, true, true, true};
	
	/**
	 * Data path for vector GPS data. 
	 */
	private List<File> dataPaths=null;

	private VectorGPSPane vectorGPSPane;  

	public VectorGPS() {
		super("Vector GPS", SensorType.VECTOR_GPS);
		vectorGPSPane=new VectorGPSPane(); 
	}

	@Override
	public Double[] getOrientationData(long time) {
		if (time<0) return super.getSimOrientationData();
		return null;
	}

	@Override
	public Double[] getPosition(long time) {
		if (time<0) {
			Double[] pos={getReferencePosition()[0], getReferencePosition()[1],  super.getSimOrientationData()[5]};
			return pos; 
		}
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
	public void loadData(long time1, long time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SensorPane getSettingsPane() {
		return vectorGPSPane;
	}

	public List<File> getDataPath() {
		return dataPaths;
	}

	public void setDataPath(List<File> filePaths) {
		this.dataPaths=filePaths;
	}

}
