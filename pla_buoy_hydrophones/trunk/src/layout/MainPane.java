package layout;

import main.HydrophoneModelControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * The main pane holds all controls for modelling program. 
 * @author Jamie Macaulay
 *
 */
public class MainPane extends BorderPane {
	
	public MainPane (HydrophoneModelControl hydrophoneModelControl){
		
		VBox sensorBox=new VBox(); 
		
		ArrayPane arrayPane=new ArrayPane(); 
		HydrophonePane hydrophonePane=new HydrophonePane(); 
		SensorPane sensorPane=new SensorPane(); 
		
		sensorBox.getChildren().addAll(arrayPane, hydrophonePane, sensorPane);
		
		this.setLeft(sensorBox);
		
	}

}
