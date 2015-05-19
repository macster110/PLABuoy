package IMUUtils.openTag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.controlsfx.glyphfont.Glyph;

import IMUUtils.magneticCalibration.MagneticCalibration;
import IMUUtils.magneticCalibration.MagneticCalibrationPane;
import IMUUtils.openTag.ReadDSG.OTData;
import dataUnits.movementSensors.AbstractMovementSensor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Pane which allows a user to load a folder of open tag data and perform magnetic plus gyroscope calibrations for an OpenTag  
 * @author Jamie Macaulay
 */
public class OpenTagPane extends VBox {


	/**
	 * Settings for the OpenTag
	 */
	OpenTagSettings openTagSettings=new OpenTagSettings(); 
	
	/**
	 * File extendsion allowed for open tags. 
	 */
	private final ArrayList<String> oTFileExtensions=new ArrayList<String>();

	/**
	 * Text field showing the file path. 
	 */
	private TextField filePath;

	
	private TextField magCalX;


	private TextField magCalY;


	private TextField magCalZ;


	private TextField gyroCalX;


	private TextField gyroCalZ;


	private TextField gyroCalY;


	private TextField mTime;


	private TextField constantTime; 
	
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
	
	/**
	 * Class for reading in .DSG binaries. 
	 */
	private final ReadDSG readDSG=new ReadDSG(); 
	
	/**
	 * File chooser for opening a list of .DSG files. 
	 */
    private final FileChooser fileChooser = new FileChooser();
    
    /**
     * Pane for showing magentic calibration 
     */
	private MagneticCalibrationPane magCalPane=new MagneticCalibrationPane(); 
    
    /**
     * Directory chooser for opening folder of DSG files. 
     */
	final DirectoryChooser directoryChooser =
            new DirectoryChooser();


	private TextField calfilePath;
	

	
	public OpenTagPane(){
		
		Label nameLabel=new Label(".DSG file(s)"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		Pane dsgFilesPane=createDSGFilePane();
		
		Label ptCalNameLabel=new Label("PT Cal file path "); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		Pane ptCalPane=createPTFilePane();
		
		//create magnetometer calibration pane. 
		Label magCalLabel=new Label("Magnetometer calibration"); 
		magCalLabel.setPadding(new Insets(5,0,0,0));
		Pane magCalPane=createMagCalPane(); 
		
		//create gyroscope calibration pane. 
		Label gyroCalLabel=new Label("Gyroscope calibration"); 
		gyroCalLabel.setPadding(new Insets(5,0,0,0));
		Pane gyroCalPane=createGyroCalPane(); 
		
		//create time offset pane
		Label timeOffsetLabel=new Label("Time drift calibration"); 
		timeOffsetLabel.setPadding(new Insets(5,0,0,0));
		Pane timeOffsetPane=createtimeOffsetPane(); 

		this.setSpacing(2);
		this.getChildren().addAll(nameLabel, dsgFilesPane ,ptCalNameLabel, ptCalPane, magCalLabel, magCalPane, gyroCalLabel, gyroCalPane, timeOffsetLabel, timeOffsetPane);
				
	}
	
	private Pane createPTFilePane() {

		//create HBox with text field and file path and browse button. 
		HBox openFileCalPath=new HBox();  
		//openFilePath.setMaxWidth(Double.MAX_VALUE);
		openFileCalPath.setSpacing(10);
		calfilePath=new TextField(); 
		calfilePath.setMaxWidth(Double.MAX_VALUE);
		//filePath.setEditable(false);
		HBox.setHgrow(calfilePath, Priority.ALWAYS);
		HBox.setHgrow(openFileCalPath, Priority.ALWAYS);

		
		Button browseButton =new Button("", Glyph.create("FontAwesome|FILE"));
		browseButton.setOnAction((action)->{

			configureFileChooserPT(fileChooser);
			File calFile=fileChooser.showOpenDialog(this.getScene().getWindow()); 
			if (calFile!=null){
				calfilePath.setText(calFile.getName());
			}
			
		});
		
		openFileCalPath.getChildren().addAll(calfilePath, browseButton); 

		return openFileCalPath;
	}

	/**
	 * Create pane to allow users to select a folder of .DSG files to process. 
	 * @return pane allowing users to select folder of .dsg files. 
	 */
	private Pane createDSGFilePane(){
		
		oTFileExtensions.add("dsg"); 
		
		//create HBox with text field and file path and browse button. 
		HBox openFilePath=new HBox();  

		//openFilePath.setMaxWidth(Double.MAX_VALUE);
		openFilePath.setSpacing(10);
		filePath=new TextField(); 
		filePath.setMaxWidth(Double.MAX_VALUE);
		//filePath.setEditable(false);
		HBox.setHgrow(filePath, Priority.ALWAYS);
		HBox.setHgrow(openFilePath, Priority.ALWAYS);

		
		Button browseButton =new Button("", Glyph.create("FontAwesome|FOLDER"));
		browseButton.setOnAction((action)->{

			File folderPath = directoryChooser.showDialog(null);
	           
	    		if (folderPath!=null){
		    		if (AbstractMovementSensor.getFiles(oTFileExtensions, folderPath, true).size()<=0) showErrorWarning(NO_FILES_ERROR);
	    			filePath.setText(folderPath.getAbsolutePath());
	    			filePath.setTooltip(new Tooltip(folderPath.getAbsolutePath()));
	    			openTagSettings.folderPath=folderPath; 
	    		}
	    		else{
	    			filePath.setText("");
	    			filePath.setTooltip(new Tooltip("No folder set"));
	    		}
	    		
		});
		
		openFilePath.getChildren().addAll(filePath, browseButton); 

		return openFilePath;
	}
	
	/**
	 * Create Pane for magnetic calibration
	 * @return Pane which allows users to perform a magnetic calibration. 
	 */
	private Pane createMagCalPane(){

		///shows HARD IRON values- not the full set of eigenvectors. 
		HBox magCalValues=new HBox(); 
		magCalValues.setAlignment(Pos.CENTER_LEFT);
		magCalValues.setSpacing(5);

		magCalX=new TextField(); 
		magCalX.setPrefColumnCount(5);
		magCalY=new TextField(); 
		magCalY.setPrefColumnCount(5);
		magCalZ=new TextField(); 
		magCalZ.setPrefColumnCount(5);

		magCalValues.getChildren().addAll(new Label("X"), magCalX, 
				new Label("Y"), magCalY,  new Label("Z"), magCalZ);

		Button selectMagCalFiles=new Button(); 
		selectMagCalFiles.setGraphic(Glyph.create("FontAwesome|FILES_ALT"));
		selectMagCalFiles.setOnAction((action)->{
			configureFileChooser(
					fileChooser, "Open files for Magnetic Calibration") ;
			List<File> files =fileChooser.showOpenMultipleDialog(this.getScene().getWindow());
			if (files==null || files.size()<=0) return; 
			else calibrateMag(files);
		});
		magCalValues.getChildren().add(selectMagCalFiles); 

		return magCalValues;

	} 

	/**
	 * Create Pane for gyroscope calibration
	 * @return Pane which allows users to perform a gyroscope calibration. 
	 */
	private Pane createGyroCalPane(){

		HBox gyroCalValues=new HBox(); 
		gyroCalValues.setAlignment(Pos.CENTER_LEFT);
		gyroCalValues.setSpacing(5);

		gyroCalX=new TextField();
		gyroCalX.setPrefColumnCount(5);
		gyroCalY=new TextField(); 
		gyroCalY.setPrefColumnCount(5);
		gyroCalZ=new TextField(); 
		gyroCalZ.setPrefColumnCount(5);

		gyroCalValues.getChildren().addAll(new Label("X"), gyroCalX, 
				new Label("Y"), gyroCalY,  new Label("Z"), gyroCalZ);

		Button selectGyroCalFiles=new Button(); 
		selectGyroCalFiles.setGraphic(Glyph.create("FontAwesome|FILES_ALT"));
		selectGyroCalFiles.setOnAction((action)->{
			configureFileChooser(
					fileChooser, "Open files for Gyroscope Calibration") ;
		});
		gyroCalValues.getChildren().add(selectGyroCalFiles); 

		return gyroCalValues;
	} 
	
	/**
	 * Allows users to set a time offset. 
	 * @return
	 */
	private Pane createtimeOffsetPane(){
		HBox timeCalValues=new HBox(); 
		timeCalValues.setAlignment(Pos.CENTER_LEFT);

		mTime=new TextField(); 
		mTime.setPrefColumnCount(4);
		constantTime=new TextField(); 
		constantTime.setPrefColumnCount(4);

		timeCalValues.getChildren().addAll(new Label("True time (s) = "), mTime, new Label(" time (s) + "),constantTime, new Label(" (s)") ); 
		return timeCalValues;
	}
	
	
	/**
	 * Configure a file chooser to only open .dsg files. 
	 * @param fileChooser - file chooser to configure
	 * @param title - the title of the file chooser. 
	 */
	 private static void configureFileChooser(
		        final FileChooser fileChooser, String title) {      
		            fileChooser.setTitle(title);
		            fileChooser.setInitialDirectory(
		                new File(System.getProperty("user.home"))
		            );                 
		            fileChooser.getExtensionFilters().removeAll( fileChooser.getExtensionFilters());
		            fileChooser.getExtensionFilters().addAll(
		                new FileChooser.ExtensionFilter("DSG", "*.dsg"));
		    }
	 
	 /**
		 * Configure a file chooser to only open .dsg files. 
		 * @param fileChooser - file chooser to configure
		 * @param title - the title of the file chooser. 
		 */
		 private static void configureFileChooserPT(
			        final FileChooser fileChooser) {      
			            fileChooser.setTitle("Select PT Cal file." );
			            fileChooser.setInitialDirectory(
			                new File(System.getProperty("user.home"))
			            );                 
			            fileChooser.getExtensionFilters().removeAll( fileChooser.getExtensionFilters());
			            fileChooser.getExtensionFilters().addAll(
			                new FileChooser.ExtensionFilter("CAL", "*.cal"));
			    }
		
	

	public int getParams(OpenTagSettings otSettinggs) {
					
		//get File path- remember user may have edited it. 
		int error=0; 
		System.out.println("Folder path "+ this.openTagSettings.folderPath.getAbsolutePath() + "  file path text"+filePath.getText());
		if (openTagSettings.folderPath==null ) error= NO_FILES_ERROR;
		
		//check at least some files exist. 
		else if (AbstractMovementSensor.getFiles(oTFileExtensions, openTagSettings.folderPath , true).size()<=0) error= NO_FILES_ERROR;
		
		
		
		otSettinggs=this.openTagSettings;
	    
		return error;
	}
	

	public void setParams(OpenTagSettings otSettings) {
		
		this.openTagSettings=otSettings;
		
		if (otSettings.folderPath!=null){
			filePath.setText(otSettings.folderPath.getAbsolutePath());
		}
		else{
			filePath.setText("");
		}
	}
	
	/**
	 * Calibrate magnetometer using a bunch of files. 
	 * @param files - dsg files. Note that calss assumes .dsg file and will throw exception otherwise. 
	 */
	private void calibrateMag(List<File> files) {
		//first read all files and concat into one big matrix
		OTData otData; 
		double[][] magnetometerData=new double[0][0];
		System.out.println("No. Files: "+files.size());
		for (int i=0; i<files.size(); i++){
			 otData=	readDSG.otLoadDat(files.get(i), openTagSettings.calFIlePath);
			 //now add to an array for magnetometer data 
			 magnetometerData=concat(otData.magnotometer, magnetometerData);
			 //System.out.println("OTData size: "+otData.magnotometer.length+ " concat size: "+magnetometerData.length);
		}
		
		//create dialog with magnetic calibration. 
		Dialog<MagneticCalibration> dialog = new Dialog<>();
		dialog.setTitle("Magnetic Calibration");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		
		//FIXME- had to do this to get magnetic calibration pane working in border pane. 
		BorderPane borderPane=new BorderPane(this.magCalPane);
		borderPane.setPrefSize(1000,800);
		borderPane.setPadding(new Insets(0,0,30,0));
		///////
		
		dialog.getDialogPane().setContent(borderPane);
		dialog.setResizable(true);

		// Convert the result when OK button clicked
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return magCalPane.getMagneticCalibration();
		    }
		    return null;
		});

		
		magCalPane.addMagnetomterData(magnetometerData, false);
		
		Optional<MagneticCalibration> result = dialog.showAndWait();
		result.ifPresent(magCal -> {
		   //TODO do some magCal stuff
		});
	
	}
	
	/**
	 * Combine two arrays. 
	 * @param a - array 1
	 * @param b - array 2
	 * @return new array
	 */
	public static double[][] concat(double[][] a, double[][] b){
        int length = a.length + b.length;
        double[][] result = new double[length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	

	
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
