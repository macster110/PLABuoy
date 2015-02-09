package dataUnits.movementSensors;

import java.io.File;

import main.SensorManager.SensorType;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public class InclinometerSensor extends AbstractMovementSensor {

	public InclinometerSensor(String sensorName, SensorType sensorType) {
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
	public void loadData(long time1, long time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SensorPane getSettingsPane() {
		// TODO Auto-generated method stub
		return null;
	}





}
