package dataUnits.movementSensors;

import java.io.File;
import java.util.ArrayList;

import main.ArrayModelControl;
import main.SensorManager.SensorType;
import dataUnits.Array;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public abstract class AbstractMovementSensor implements MovementSensor {
	
	/**
	 * The x position of the sensor relative to it's parent Array
	 */
	private DoubleProperty xRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The y position of the sensor relative to it's parent Array
	 */
	private DoubleProperty yRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The z (-depth) position of the sensor relative to it's parent Array
	 */
	private DoubleProperty zRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The reference heading i.e. the heading when the array is not moving and facing north
	 */
	private DoubleProperty headingRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The reference pitch i.e. the pitch when the array is not moving.
	 */
	private DoubleProperty pitchRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The reference roll i.e. the roll when the array is not moving.
	 */
	private DoubleProperty rollRefProperty=new SimpleDoubleProperty(0.); 
	
	/**
	 * The array the sensor belongs to. 
	 */
	private ObjectProperty<Array> parentArray= new SimpleObjectProperty<Array>(ArrayModelControl.getInstance().getReferenceArray()); 
	
	/**
	 * The name of the sensor
	 */
	public StringProperty sensorNameProperty= new SimpleStringProperty(""); 
	
	/**
	 * The sensor type e.g. "Open Tag". 
	 */
	public ObjectProperty<SensorType> sensorTypeProperty= new SimpleObjectProperty<SensorType>(); 
	
	public AbstractMovementSensor(String sensorName, SensorType sensorType){
		sensorNameProperty.setValue(sensorName);
		sensorTypeProperty.setValue(sensorType);
	}

	/**
	 * Get the path to the data 
	 * @return the path to the folder which contains data
	 */
	abstract File getDataPath(); 
	
	/**
	 * Load sensor data between two times so available in memory. 
	 * @param time1 - start time in millis to load data from. 
	 * @param time2 - end time in millis to load data to.
	 */
	public abstract void loadData(long time1, long time2);
	
	/**
	 * Get pane with GUI to allow users to change sensor settings
	 * @return pane with GUI to allow users to change settings of sensor. 
	 */
	public abstract SensorPane getSettingsPane();
	
	
	@Override
	public StringProperty sensorNameProperty() {
		return sensorNameProperty;
	}

	@Override
	public ObjectProperty<SensorType> sensorTypeProperty() {
		return sensorTypeProperty;
	}

	@Override
	public double[] getReferencePosition() {
		double[] position={xRefProperty.get(), yRefProperty.get(), zRefProperty.get(),};
		return position;
	}
	
	@Override
	public double[] getOrientationReference() {
		double[] orientation={headingRefProperty.get(), pitchRefProperty.get(), rollRefProperty.get(),};
		return orientation;
	}


	@Override
	public DoubleProperty xRefPositionProperty(){
		return xRefProperty;
	}
	
	@Override
	public DoubleProperty yRefPositionProperty(){
		return yRefProperty;
	}

	@Override
	public DoubleProperty zRefPositionProperty(){
		return zRefProperty;
	}
	
	@Override
	public DoubleProperty headingRefProperty(){
		return headingRefProperty;
	}
	
	@Override
	public DoubleProperty pitchRefProperty(){
		return pitchRefProperty;
	}

	@Override
	public DoubleProperty rollRefProperty(){
		return rollRefProperty;
	}
	
	@Override
	public ObjectProperty<Array> parentArrayProperty(){
		return parentArray;
	}
	
	/**
	 * Get all files with specified extensions within a folder. 
	 * @param fileExtensions - the extendsion to find e.g. "DSG"
	 * @param folderPath - the folder to look in. 
	 * @param subFolders - true to include sub folders. 
	 */
	public static ArrayList<File> getFiles(ArrayList<String> fileExtensions, File folderPath, boolean subFolders){
		ArrayList<File> foundFiles=new ArrayList<File>(); 
		//check the folder contains some .DSG files. 
		File[] listOfFiles = folderPath.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String ext=getExtension(listOfFiles[i]);
				for (int j=0; j<fileExtensions.size(); j++){
					//System.out.println("Extension is: "+ext + " possible extension is: "+fileExtensions.get(j) + " which is the same: "+ext.equals(fileExtensions.get(j))); 
					if (ext!=null && ext.equals(fileExtensions.get(j))){
						foundFiles.add(listOfFiles[i]);
					}
				}
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
				if (subFolders) foundFiles.addAll(getFiles(fileExtensions, listOfFiles[i], subFolders));
			}
		}
		
		System.out.println(foundFiles.size() + " files found");

		return foundFiles;
	}
	
	/*
	 * Get the extension of a file.
	 */  
	public static String getExtension(File f) {
	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');

	    if (i > 0 &&  i < s.length() - 1) {
	        ext = s.substring(i+1).toLowerCase();
	    }
	    return ext;
	}
	

}
