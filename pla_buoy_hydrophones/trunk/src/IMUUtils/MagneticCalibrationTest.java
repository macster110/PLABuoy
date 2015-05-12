package IMUUtils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test extended magnetic calibration. 
 * @author Jamie Macaulay
 *
 */
public class MagneticCalibrationTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MagneticCalibration magCal=new MagneticCalibration(); 
		MagneticCalibrationPane magCalPane=new MagneticCalibrationPane(magCal); 
		
		primaryStage.setScene(new Scene(magCalPane, 1000,800));
		primaryStage.show();
	}
	
	 public static void main(String[] args) {
	        launch(args);
	    }

}
