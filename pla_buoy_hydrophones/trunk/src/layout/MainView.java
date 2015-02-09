package layout;


import layout.arrays.ArrayTablePane;
import layout.hydrophones.HydrophoneTablePane;
import layout.movementSensors.SensorTablePane;
import main.ArrayModelControl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The main pane holds all controls for modelling program. 
 * @author Jamie Macaulay
 *
 */
public class MainView extends BorderPane {
	
	/**
	 *Reference to the a ArrayModelControl. 
	 */
	private ArrayModelControl arrayModelControl;
	
	/**
	 * The primary stage. 
	 */
	private Stage primaryStage;

	public MainView (ArrayModelControl arrayModelControl, Stage primaryStage){
		
		this.arrayModelControl=arrayModelControl; 
		this.primaryStage=primaryStage; 
		
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

		
		ArrayTablePane arrayPane=new ArrayTablePane(this); 
		HydrophoneTablePane hydrophonePane=new HydrophoneTablePane(this); 
		SensorTablePane sensorPane=new SensorTablePane(this); 
		
		sensorBox.getChildren().addAll(arrayLabel, arrayPane, hydrophoneLabel, hydrophonePane, sensorLabel, sensorPane);
		sensorBox.setSpacing(10);
		
		this.setLeft(sensorBox);
		
	}
	
	/**
	 * Get the primary stage for the application
	 * @return the primary stage. 
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Get the array control model. This holds data on arrays, hydrophones and sensors. 
	 * @return
	 */
	public ArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

}
