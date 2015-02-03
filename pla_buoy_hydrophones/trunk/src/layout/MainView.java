package layout;


import main.ArrayModelControl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The main pane holds all controls for modelling program. 
 * @author Jamie Macaulay
 *
 */
public class MainView extends BorderPane {
	
	ArrayModelControl arrayModelControl;

	public MainView (ArrayModelControl arrayModelControl){
		
		this.arrayModelControl=arrayModelControl; 
		
		VBox sensorBox=new VBox(); 
		sensorBox.setPadding(new Insets(10,0,0,0));
		
		Label arrayLabel=new Label("Array");
		arrayLabel.setPadding(new Insets(0,0,0,10));
		arrayLabel.setFont(new Font("Ubuntu", 20));
		
		Label hydrophoneLabel=new Label("Hydrophones");
		hydrophoneLabel.setPadding(new Insets(0,0,0,10));
		hydrophoneLabel.setFont(new Font("Ubuntu", 20));
		
		Label sensorLabel=new Label("Movement Sensors");
		sensorLabel.setPadding(new Insets(0,0,0,10));
		sensorLabel.setFont(new Font("Ubuntu", 20));

		
		ArrayPane arrayPane=new ArrayPane(this); 
		HydrophonePane hydrophonePane=new HydrophonePane(this); 
		SensorPane sensorPane=new SensorPane(this); 
		
		sensorBox.getChildren().addAll(arrayLabel, arrayPane, hydrophoneLabel, hydrophonePane, sensorLabel, sensorPane);
		sensorBox.setSpacing(10);
		
		this.setLeft(sensorBox);
		
	}
	
	/**
	 * Get the array control model. This holds data on arrays, hydrophones and sensors. 
	 * @return
	 */
	public ArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

}
