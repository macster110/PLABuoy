package layout.movementSensors;

import java.io.File;
import java.util.ArrayList;

import main.ArrayModelControl;

import org.controlsfx.glyphfont.Glyph;

import dataUnits.movementSensors.AbstractMovementSensor;
import dataUnits.movementSensors.MovementSensor;
import dataUnits.movementSensors.OpenTagSensor;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Pane for changing settings of Open Tag
 * @author Jamie Macaulay
 *
 */
public class OpenTagPane extends SensorPane<OpenTagSensor> {

	/**
	 * The folder contianing OpenTag sensor data. 
	 */
	private File folderPath; 
	
	/**
	 * Pane to set position and orientation of open tag. 
	 */
	private final PositionRefPane positionRefPane; 
	
	/**
	 * File extendsion allowed for open tags. 
	 */
	private final ArrayList<String> oTFileExtensions=new ArrayList<String>();

	private final TextField filePath; 
	
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

	
	
	public OpenTagPane(){
		
		
		Label nameLabel=new Label(".DSG file(s)"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		
		oTFileExtensions.add("dsg"); 
		
		
		//create HBox with text field and file path and browse button. 
		HBox openFilePath=new HBox();  
		openFilePath.setMaxWidth(Double.MAX_VALUE);
		openFilePath.setSpacing(10);
		filePath=new TextField(); 
		filePath.setMaxWidth(Double.MAX_VALUE);
		//filePath.setEditable(false);
		HBox.setHgrow(filePath, Priority.ALWAYS);
		HBox.setHgrow(openFilePath, Priority.ALWAYS);

		
		Button browseButton =new Button("", Glyph.create("FontAwesome|FOLDER"));
		browseButton.setOnAction((action)->{
			final DirectoryChooser directoryChooser =
	                new DirectoryChooser();
			
	            folderPath = directoryChooser.showDialog(ArrayModelControl.getInstance().getPrimaryStage());
	           
	    		if (folderPath!=null){
		    		if (AbstractMovementSensor.getFiles(oTFileExtensions, folderPath, true).size()<=0) showErrorWarning(NO_FILES_ERROR);
	    			filePath.setText(folderPath.getAbsolutePath());
	    			filePath.setTooltip(new Tooltip(folderPath.getAbsolutePath()));
	    		}
	    		else{
	    			filePath.setText("");
	    			filePath.setTooltip(new Tooltip("No folder set"));
	    		}

		});
		
		
		openFilePath.getChildren().addAll(filePath, browseButton); 
		
		
		this.setSpacing(2);
		this.getChildren().addAll(nameLabel, openFilePath, positionRefPane=new PositionRefPane());
				
	}

	@Override
	public int getParams(OpenTagSensor movementSensor) {
					
		System.out.println("Folder path "+ folderPath + "  file path text"+filePath.getText());
		if (folderPath==null ) return NO_FILES_ERROR;
		
		//check at least some files exist. 
		if (AbstractMovementSensor.getFiles(oTFileExtensions, folderPath, true).size()<=0) return NO_FILES_ERROR;

	    movementSensor.setDataPath(folderPath); 
	    
		int error = positionRefPane.getParams(movementSensor);

		return error;
	}
	

	@Override
	public void setParams(OpenTagSensor movementSensor) {

		positionRefPane.setParams(movementSensor);
		
		folderPath=movementSensor.getDataPath();
		
		if (movementSensor.getDataPath()!=null) filePath.setText(movementSensor.getDataPath().getAbsolutePath());
		else{
			filePath.setText("");
		}

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
			warning="Error in angle range. Heading must be between 0 and 360 degrees. Pitch must be between -90 to 90 degrees. Roll must be between -180 to 180 degrees.";
			break; 
		case NO_FILES_ERROR:
			warning="Error in file search. No OpenTag files were found in folder or folder does not exist. OpenTag files have extensions ";
			String extensions="";  
			for (int i=0; i<this.oTFileExtensions.size(); i++){
				extensions=(extensions+oTFileExtensions.get(i)+" ");
			}
			warning=warning+extensions; 
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
