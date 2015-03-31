package layout.movementSensors;

import dataUnits.movementSensors.MovementSensor;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import layout.ArrayModelView;
import layout.ControlPane;
import layout.ControlPane.ChangeType;
import layout.utils.TablePane;
import main.ArrayModelControl;
import main.SensorManager.SensorType;

public class SensorTablePane extends TablePane<MovementSensor> implements ControlPane {
		
	/**
	 * Reference to the main view.
	 */
	private ArrayModelView mainPane;


	public SensorTablePane(ArrayModelView mainPane){
		super(mainPane.getArrayModelControl().getSensors());

		this.mainPane=mainPane; 
		
		TableColumn<MovementSensor,String>  sensorName = new TableColumn<MovementSensor,String>("Sensor Name");
		sensorName.setCellValueFactory(cellData -> cellData.getValue().sensorNameProperty());
		
		TableColumn<MovementSensor,SensorType>  sensorType = new TableColumn<MovementSensor,SensorType>("Sensor Type");
		sensorType.setCellValueFactory(cellData -> cellData.getValue().sensorTypeProperty());
		
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
		System.out.println("data "+data); 
		if (data==null) {
			//create a new open tag sensor as a default. 
			MovementSensor movementSensor=ArrayModelControl.getInstance().getSensorManager().createNewSensor(SensorType.OPEN_TAG);
			movementSensor.parentArrayProperty().setValue(ArrayModelControl.getInstance().getReferenceArray());
			return SensorDialog.createDialog(movementSensor);
		}
		else return SensorDialog.createDialog(data);
	}


	@Override
	public void notifyChange(ChangeType type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dialogClosed(MovementSensor data) {
		mainPane.getArrayModelControl().getArrayModelManager().calculateHydrophonePositions(-1);
		mainPane.getArrayModelControl().notifyModelChanged(ChangeType.SENSOR_CHANGED);
		this.mainPane.getArrayModelControl().updateArraySensors();
		
	}

}
