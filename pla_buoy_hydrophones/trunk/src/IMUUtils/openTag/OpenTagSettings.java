package IMUUtils.openTag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import IMUUtils.magneticCalibration.FitPoints;

/**
 * Holds settings for an OpenTag 
 * @author Jamie Macaulay 
 *
 */
public class OpenTagSettings {
	
	/**
	 * Magnetic calibration, x,y,z
	 */
	FitPoints magneticCal=new FitPoints(); 
	
	/**
	 * Gyroscope calibration x,y,z
	 */
	double[] gyroCal={0,0,0};
	
	/**
	 * Time offset for the open tag-> linear straight line equation. -> true time = timeOffset[0]*open tag time+timeOffset[1]
	 */
	double[] timeOffset={1,0};
	
	/**
	 * Path to open tag data. Note that the here element[0] is the last selected path. 
	 */
	public ArrayList<File> folderPaths=new 	ArrayList<File>(); 
	
	/**
	 * Path to open tag calibration file for temperature and pressure. 
	 */
	public File calFilePath=null; 
	
	
	/**
	 * Files for magnetic calibration
	 */
	public List<File> magCalFiles=new ArrayList<File>(); 
	
	/**
	 * Files for gyroscope calibration
	 */
	public List<File> gyroscopeCalFiles=new ArrayList<File>(); 


}
