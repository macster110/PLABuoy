package layout.movementSensors;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * Pane for changing settings of Open Tag
 * @author Jamie Macaulay
 *
 */
public class OpenTagPane extends BorderPane {
	
	public OpenTagPane(){
		
		HBox openFilePath=new HBox();  
		TextField filePath=new TextField(); 
		Button 
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(stage);
		
	}
	

}
