package layout.movementSensors;

import dataUnits.movementSensors.MovementSensor;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class SensorDialog extends Dialog<MovementSensor>{
	
	private static SensorDialog singleInstance;

	/**
	 * The current movement sensor the dialog is showing. 
	 */
	MovementSensor currentMovementSensor; 
	
	/**
	 * The pane shows custom controls for specific types of sensor. 
	 */
	BorderPane customSensorPane= new BorderPane(); 
	
	public SensorDialog(){
		this.setTitle("Sensor Dialog");
		this.getDialogPane().setContent(createDialogPane());
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return currentMovementSensor;
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
		
	}; 
	
	private Pane createDialogPane(){
		
		BorderPane mainPane=new BorderPane();
		
		Label sensorLabel=new Label("Select Sensor");
		ComboBox<MovementSensor> sensorBox=new ComboBox<MovementSensor>();
		sensorBox.setConverter(new StringConverter<MovementSensor>() {
            @Override
            public String toString(MovementSensor user) {
              if (user == null){
                return null;
              } else {
                return user.sensorNameProperty().get();
              }
            }

          @Override
          public MovementSensor fromString(String userId) {
              return null;
          }
		});
		
		VBox selectSensor=new VBox();
		selectSensor.getChildren().addAll(sensorLabel, sensorBox);
		
		mainPane.setTop(selectSensor);
		mainPane.setBottom(customSensorPane); 

		return mainPane; 
		
	}

	
}
