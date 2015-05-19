package IMUUtils.openTag;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test extended magnetic calibration. 
 * @author Jamie Macaulay
 *
 */
public class OpenTagTest extends Application {
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Create OpenTag pane. 
		OpenTagSettings openTagSettings=new OpenTagSettings(); 
		
		OpenTagPane openTagPane=new OpenTagPane(); 
		openTagPane.setParams(openTagSettings);
		openTagPane.setPadding(new Insets(5,5,5,5));
		
		primaryStage.setTitle("Open Tag DSG to CSV");
		primaryStage.setScene(new Scene(openTagPane, 305,500));
		primaryStage.show();
	}
	
	 public static void main(String[] args) {
	        launch(args);
	    }

}