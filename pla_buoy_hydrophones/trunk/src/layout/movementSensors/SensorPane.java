package layout.movementSensors;

import dataUnits.movementSensors.MovementSensor;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import layout.MainView;
import layout.TablePane;
import main.ArrayModelControl;
import main.SensorManager.SensorType;

public class SensorPane extends TablePane<MovementSensor> {
		
	public SensorPane(MainView mainPane){
		
		super(mainPane.getArrayModelControl().getSensors());
		
		TableColumn<MovementSensor,String>  sensorName = new TableColumn<MovementSensor,String>("Sensor Name");
		sensorName.setCellValueFactory(cellData -> cellData.getValue().sensorNameProperty());
		
		TableColumn<MovementSensor,String>  sensorType = new TableColumn<MovementSensor,String>("Sensor Type");
		sensorType.setCellValueFactory(cellData -> cellData.getValue().sensorNameProperty());
		
		TableColumn<MovementSensor,String> array = new TableColumn<MovementSensor,String>("Array");
		array.setCellValueFactory(cellData -> cellData.getValue().parentArrayProperty().getValue().nameProperty());
		
		//orientation reference
		TableColumn<MovementSensor,Number>  headingRef = new TableColumn<MovementSensor,Number>("Heading");
		headingRef.setCellValueFactory(cellData -> cellData.getValue().headingRefProperty().multiply(180/Math.PI));
		
		TableColumn<MovementSensor,Number>  pitchRef = new TableColumn<MovementSensor,Number>("Pitch");
		pitchRef.setCellValueFactory(cellData -> cellData.getValue().pitchRefProperty().multiply(180/Math.PI));
		
		TableColumn<MovementSensor,Number>  rollRef = new TableColumn<MovementSensor,Number>("Roll");
		rollRef.setCellValueFactory(cellData -> cellData.getValue().rollRefProperty().multiply(180/Math.PI));
		
		TableColumn orientationReference = new TableColumn("Orientation Reference");
		orientationReference.getColumns().addAll(headingRef, pitchRef, rollRef);

		
		//position reference
		TableColumn<MovementSensor,Number>  xRef = new TableColumn<MovementSensor,Number>("x (m)");
		xRef.setCellValueFactory(cellData -> cellData.getValue().xRefPositionProperty());
		
		TableColumn<MovementSensor,Number>  yRef = new TableColumn<MovementSensor,Number>("y (m)");
		yRef.setCellValueFactory(cellData -> cellData.getValue().yRefPositionProperty());
		
		TableColumn<MovementSensor,Number>  zRef = new TableColumn<MovementSensor,Number>("z (m)");
		zRef.setCellValueFactory(cellData -> cellData.getValue().zRefPositionProperty());
		
		TableColumn posReference = new TableColumn("Position Reference");
		posReference.getColumns().addAll(xRef, yRef, zRef);

		getTableView().getColumns().addAll(sensorName, sensorType, array, orientationReference, posReference);

	}
	

	@Override
	public Dialog<MovementSensor> createSettingsDialog(MovementSensor data) {
		if (data==null) {
			//create a new open tag sensor as a default. 
			return SensorDialog.createDialog(ArrayModelControl.getInstance().getSensorManager().createNewSensor(SensorType.Open_TAG));
		}
		else return SensorDialog.createDialog(data);
	}

}
