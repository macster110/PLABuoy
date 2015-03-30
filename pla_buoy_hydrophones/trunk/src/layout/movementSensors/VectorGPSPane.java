package layout.movementSensors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.ArrayModelControl;

import org.controlsfx.glyphfont.Glyph;

import dataUnits.movementSensors.AbstractMovementSensor;
import dataUnits.movementSensors.VectorGPS;

public class VectorGPSPane extends SensorPane<VectorGPS>{

	
	private  List<File> filePaths;

	private final TextField filePathField; 
	
	/**
	 * File extendsion allowed for GPS data. 
	 */
	private final ArrayList<String> vGPSFileExtensions=new ArrayList<String>();


	/**Create the pane*/ 
	public VectorGPSPane(){
		
		vGPSFileExtensions.add("txt"); 
		vGPSFileExtensions.add("nmea"); 
		vGPSFileExtensions.add("gpx"); 

		
		Button browseFolderButton =new Button("", Glyph.create("FontAwesome|FOLDER"));
		browseFolderButton.setOnAction((action)->{
			final FileChooser fileChooser  =
	                new FileChooser ();
			
	            File filePath = fileChooser.showOpenMultipleDialog(ArrayModelControl.getInstance().getPrimaryStage());
	           
	    		if (filePath!=null){
		    		if (AbstractMovementSensor.getFiles(vGPSFileExtensions, folderPath, true).size()<=0) showErrorWarning(NO_FILES_ERROR);
		    		filePathField.setText(folderPath.getAbsolutePath());
		    		filePathField.setTooltip(new Tooltip(folderPath.getAbsolutePath()));
	    		}
	    		else{
	    			filePathField.setText("");
	    			filePathField.setTooltip(new Tooltip("No folder set"));
	    		}
		});
		
		Button browseFileButton =new Button("", Glyph.create("FontAwesome|FILE"));
		browseFileButton.setOnAction((action)->{
			final FileChooser fileChooser  =
	                new FileChooser ();
			
	            filePaths = fileChooser.showOpenMultipleDialog(ArrayModelControl.getInstance().getPrimaryStage());
	           
	    		if (filePaths!=null){
		    		if (showErrorWarning(NO_FILES_ERROR));
		    		filePathField.setText(folderPath.getAbsolutePath());
		    		filePathField.setTooltip(new Tooltip(folderPath.getAbsolutePath()));
	    		}
	    		else{
	    			filePathField.setText("");
	    			filePathField.setTooltip(new Tooltip("No folder set"));
	    		}
		});
		
	}

	@Override
	public int getParams(VectorGPS movementSensor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParams(VectorGPS movementSensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean showErrorWarning(int errorType) {
		// TODO Auto-generated method stub
		return false;
	}

}
