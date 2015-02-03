package layout;

import sensorInput.MovementSensor;
import javafx.scene.control.Dialog;

public class SensorPane extends TablePane<MovementSensor> {
	
	public SensorPane(MainView mainPane){
		super(mainPane.getArrayModelControl().getSensors());
	}

	@Override
	Dialog<MovementSensor> createSettingsDialog(MovementSensor data) {
		// TODO Auto-generated method stub
		return null;
	}

}
