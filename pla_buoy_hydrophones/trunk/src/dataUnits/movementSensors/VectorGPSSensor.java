package dataUnits.movementSensors;

import java.io.File;

import javafx.scene.layout.Pane;

public class VectorGPSSensor extends AbstractMovementSensor {

	public VectorGPSSensor(String sensorName, String sensorType) {
		super(sensorName, sensorType);
		// TODO Auto-generated constructor stub
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
	public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	File getDataPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void loadData(long time1, long time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	Pane getSettingsPane() {
		// TODO Auto-generated method stub
		return null;
	}

}
