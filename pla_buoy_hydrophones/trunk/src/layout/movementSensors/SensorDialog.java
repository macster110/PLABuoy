package layout.movementSensors;

import layout.ParentArrayComboBox;
import main.ArrayModelControl;
import main.SensorManager;
import main.SensorManager.SensorType;
import dataUnits.movementSensors.AbstractMovementSensor;
import dataUnits.movementSensors.MovementSensor;
import dataUnits.movementSensors.OpenTagSensor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SensorDialog extends Dialog<MovementSensor>{
	
	private static SensorDialog singleInstance;
	
	TextField nameField; 

	/**
	 * The current movement sensor the dialog is showing. 
	 */
	private MovementSensor movementSensor; 
	
	/**
	 * The pane shows custom controls for specific types of sensor. 
	 */
	BorderPane customSensorPane= new BorderPane();
	
	/**
	 * Reference to the sensor manager. 
	 */
	SensorManager sensorManager=ArrayModelControl.getInstance().getSensorManager();

	private ParentArrayComboBox parentArrayComboBox; 

	
	public SensorDialog(){
		this.setTitle("Sensor Dialog");
		this.getDialogPane().setContent(createDialogPane());
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return movementSensor;
			}
			return null;
		});

		final Button btOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK); 
		btOk.addEventFilter(ActionEvent.ACTION, (event) -> { 
			if (getParams());
			else {
				//do not close
				event.consume();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");			
				alert.setContentText("Invalid data enetered in Movement Sensor Dialog");
				alert.showAndWait();
			}
		}); 
	}

	public static Dialog<MovementSensor> createDialog(MovementSensor sensor){
		if (singleInstance==null) {
			singleInstance=new SensorDialog();
		}
	
		singleInstance.setParams(sensor);
	
		return singleInstance; 
	}
	
	private boolean getParams(){
		return true;
	}
	
	private void setParams(MovementSensor movementSensor){
		this.movementSensor=movementSensor;
	}; 
	
	/**
	 * Create the dialog pane. 
	 * @return the main pane which sits inside dialog. 
	 */
	private Pane createDialogPane(){
		
		double sectionPadding=10;
		
		BorderPane mainPane=new BorderPane();
		
		Label nameLabel=new Label("Sensor Name"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		nameField=new TextField();
		
		Label parentArrayLabel=new Label("Parent Array");
		parentArrayLabel.setPadding(new Insets(sectionPadding,0,0,0));
		parentArrayComboBox = new ParentArrayComboBox();
		
		Label sensorLabel=new Label("Select Sensor");
		ComboBox<SensorType> sensorBox=new ComboBox<SensorType>();
		sensorBox.setItems(FXCollections.observableArrayList(SensorType.values()));
		/**
		 * Unlike arrays and hydrophones, sensors are very different from each other and made
		 * up of multiple sub classes. Therefore when a new sensor type is selected, that sensor 
		 * requires a specific pane and a new instance of the subclass to be created. 
		 */
		sensorBox.valueProperty().addListener((obs, t, t1)->{
			if (sensorManager.getSensorType(movementSensor)!=obs.getValue()){
				System.out.println("Create new sensor"); 
				movementSensor=sensorManager.createNewSensor(obs.getValue());
				createSensorPane(movementSensor); 
			}
		}); 
		

		
//		sensorBox.setConverter(new StringConverter<SensorType>() {
//            @Override
//            public String toString(SensorType user) {
//              if (user == null){
//                return null;
//              } else {
//                return user.sensorNameProperty().get();
//              }
//            }
//
//          @Override
//          public MovementSensor fromString(String userId) {
//              return null;
//          }
//		});
		
		
		sensorBox.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(sensorBox, Priority.ALWAYS);
		
		VBox selectSensor=new VBox();
		selectSensor.getChildren().addAll(nameLabel, nameField, parentArrayLabel, parentArrayComboBox,sensorLabel, sensorBox);
		
		mainPane.setTop(selectSensor);
		mainPane.setBottom(customSensorPane); 

		return mainPane; 
		
	}
	
	/**
	 * Create the specific pane in the dialog for the selected sensor. 
	 */
	public void createSensorPane(MovementSensor sensor){
		customSensorPane.setCenter(null); 
		if (sensor instanceof AbstractMovementSensor){
			customSensorPane.setCenter(	((AbstractMovementSensor) sensor).getSettingsPane());
		}
	}

	
}
