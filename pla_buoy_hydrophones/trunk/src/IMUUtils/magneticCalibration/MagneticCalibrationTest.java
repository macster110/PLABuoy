package IMUUtils.magneticCalibration;

import java.io.File;

import IMUUtils.openTag.ReadDSG;
import IMUUtils.openTag.ReadDSG.OTData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test extended magnetic calibration. 
 * @author Jamie Macaulay
 *
 */
public class MagneticCalibrationTest extends Application {
	
	//Some test files from and OpenTag
//	private static String filename="F:/Wales_2015/pla_buoy/20150422/Open_Tags/op7/14.DSG"; 
	private static String filename="C:/Users/jamie/Google Drive/SMRU_research/Open_Tag/Open Tag Madgwick Test/Heading Test/42.DSG";
	private static 	String pTCalFilename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/PRESSTMP.CAL";

	@Override
	public void start(Stage primaryStage) throws Exception {

		//open OT file 
		ReadDSG readDSG=new ReadDSG(); 
		OTData otData=readDSG.otLoadDat(new File(filename), new File(pTCalFilename));

		MagneticCalibration magCal=new MagneticCalibration(); 
		MagneticCalibrationPane magCalPane=new MagneticCalibrationPane(); 
		magCalPane.setPercentileKeep(0.9);
		
//		int start=6500; 
//		int end=15000; 
		int start=0; 
		int end=80000; 
		double[][] magCalSection=new double[end-start][3];
		for (int i=0; i<end-start; i++){
			magCalSection[i]=otData.magnotometer[i+start]; 
		}
		
		magCalPane.setPercentileKeep(0.75);
		magCalPane.setMagnetomterData(magCalSection);
		
		FitPoints fitPoints=new FitPoints();
		fitPoints.fitEllipsoid( FitPoints.convertToThreeSpace(otData.magnotometer));
		
		primaryStage.setScene(new Scene(magCalPane, 1000,800));
		primaryStage.show();
	}
	
	 public static void main(String[] args) {
	        launch(args);
	    }

}
