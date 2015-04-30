package layout.movementSensors;

import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import main.ArrayModelControl;

import org.controlsfx.glyphfont.Glyph;

import dataUnits.movementSensors.VectorGPS;

public class VectorGPSPane extends SensorPane<VectorGPS>{

	/**
	 * A list of GPS files to load. 
	 */
	private List<File> filePaths;

	/**
	 * Text field showing file paths. 
	 */
	private final TextField filePathField;

	/**
	 * Reference position. 
	 */
	private PositionRefPane positionRefPane; 
	
	/**
	 * No error has been found.
	 */
	public final static int NO_ERROR=0;
	
	/**
	 * Error occured in position/orientation text fields. 
	 */
	public final static int TEXT_FIELD_POS_ERROR=1; 
	
	/**
	 * Angle out of bounds e.g. heading != 361 degrees.
	 */
	public final static int ANGLE_ERROR=2; 

	/**
	 * No file with right extension was found. 
	 */
	public final static int NO_FILES_ERROR=3; 


	/**Create the pane*/ 
	public VectorGPSPane(){
		
		Label nameLabel=new Label(".GPS file(s)"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		
		//create HBox with text field and file path and browse button. 
		HBox gpsFilePath=new HBox();  
		gpsFilePath.setMaxWidth(Double.MAX_VALUE);
		gpsFilePath.setSpacing(10);
		filePathField=new TextField(); 
		filePathField.setEditable(false);
		filePathField.setMaxWidth(Double.MAX_VALUE);
		//filePath.setEditable(false);
		HBox.setHgrow(filePathField, Priority.ALWAYS);
		HBox.setHgrow(gpsFilePath, Priority.ALWAYS);


		Button browseFileButton =new Button("", Glyph.create("FontAwesome|FILE"));
		browseFileButton.setOnAction((action)->{
			final FileChooser fileChooser  =
					new FileChooser ();

			filePaths = fileChooser.showOpenMultipleDialog(ArrayModelControl.getInstance().getPrimaryStage());
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("TXT", "*.txt"),
					new FileChooser.ExtensionFilter("NMEA", "*.nmea"),
					new FileChooser.ExtensionFilter("GPX", "*.gpx")
					);

			if (filePaths!=null && filePaths.size()>0){
				filePathField.setText(filePaths.get(0).getAbsolutePath());
				filePathField.setTooltip(new Tooltip(filePaths.get(0).getAbsolutePath()));
			}
			else{
				filePathField.setText("");
				filePathField.setTooltip(new Tooltip("No file set"));
			}
		});
		
		gpsFilePath.getChildren().addAll(filePathField, browseFileButton);
		
		positionRefPane=new PositionRefPane();
		positionRefPane.getPitchField().setDisable(true);
		positionRefPane.getRollField().setDisable(true);
	
		this.setSpacing(2);
		this.getChildren().addAll(nameLabel, gpsFilePath, positionRefPane);
		
	}

	@Override
	public int getParams(VectorGPS movementSensor) {
		int error=0; 
		//System.out.println("Folder path "+ folderPath + "  file path text"+filePath.getText());
		if (filePaths==null || filePaths.size()<=0) error= NO_FILES_ERROR;
		
	    movementSensor.setDataPath(filePaths); 
	    
		error = positionRefPane.getParams(movementSensor);

		return error;		
		
	}

	@Override
	public void setParams(VectorGPS movementSensor) {
		
	    positionRefPane.setParams(movementSensor);
		
	    filePaths=movementSensor.getDataPath();
		
		if (movementSensor.getDataPath()!=null) setTextFieldText(movementSensor.getDataPath());
		else{
			filePathField.setText("");
		}
	}
	
	/**
	 * Get a string list of names from list of files. 
	 * @param files - list of files. 
	 * @return string representing a list of files. 
	 */
	private String setTextFieldText(List<File> files){
		String filesString="";
		for (int i=0; i<files.size(); i++){
			filesString=filesString+files.get(i).getPath()+"; "; 
		}
		return filesString;
	}

	@Override
	public boolean showErrorWarning(int errorType) {
		String warning=null; 
		boolean returnType=false; 
		switch (errorType){
		case TEXT_FIELD_POS_ERROR:
			warning="Error in the position or orinetation text fields. Check only numeric characters have been entered";
			break; 
		case ANGLE_ERROR:
			warning="Error in angle range. Heading must be between 0 and 360 degrees";
			break; 
		case NO_FILES_ERROR:
			warning="Error. No files were selected";
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
