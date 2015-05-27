package layout.movementSensors;

import IMUUtils.openTag.OpenTagPane;
import dataUnits.movementSensors.OpenTagSensor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Pane for changing settings of Open Tag
 * @author Jamie Macaulay
 *
 */
public class OpenTagSensorPane extends SensorPane<OpenTagSensor> {
	
	/**
	 * POane for changing settings one open tag. 
	 */
	private OpenTagPane openTagPane;
	
	/*
	 * Pane for settings reference position of tag on array. 
	 */
	private PositionRefPane positionRefPane;

	public OpenTagSensorPane(){
		openTagPane=new OpenTagPane(); 
		positionRefPane=new PositionRefPane();
		
		this.setSpacing(2);
		this.getChildren().addAll(openTagPane, positionRefPane);
		
	}

	@Override
	public int getParams(OpenTagSensor movementSensor) {
		
		//get params for open tag settings
		int error=openTagPane.getParams(movementSensor.getOpenTagSettings());
		
		//get params for position sensor
		error = positionRefPane.getParams(movementSensor);

		return error;
	}

	@Override
	public void setParams(OpenTagSensor movementSensor) {
		
		//set params for open tag sensor
		openTagPane.setParams(movementSensor.getOpenTagSettings());
		
		//set params for position pane. 
		positionRefPane.setParams(movementSensor);
	}

	@Override
	public boolean showErrorWarning(int errorType) {
		String warning=null; 
		boolean returnType=false; 
		switch (errorType){
		case OpenTagPane.TEXT_FIELD_POS_ERROR:
			warning="Error in the position or orinetation text fields. Check only numeric characters have been entered";
			break; 
		case OpenTagPane.ANGLE_ERROR:
			warning="Error in angle range. Heading must be between 0 and 360 degrees. Pitch must be between -90 to 90 degrees. Roll must be between -180 to 180 degrees.";
			break; 
		case OpenTagPane.NO_FILES_ERROR:
			warning="Error in file search. No OpenTag files were found in folder or folder does not exist. OpenTag files have extensions .DSG";
//			String extensions="";  
//			for (int i=0; i<this.oTFileExtensions.size(); i++){
//				extensions=(extensions+oTFileExtensions.get(i)+" ");
//			}
//			warning=warning+extensions; 
			//let person select wrong folder- will flagged during batch processing. 
			returnType=true;
			break; 
		}
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");		
		alert.setContentText(warning);
		alert.showAndWait();
		
		return returnType;
	}



}
