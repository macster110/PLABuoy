package layout;


import java.util.ArrayList;

import layout.ControlPane.ChangeType;
import layout.arrays.ArrayTablePane;
import layout.hydrophones.HydrophoneTablePane;
import layout.movementSensors.SensorTablePane;
import main.HArrayModelControl;
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
public class ArrayModelView extends BorderPane {
	
	/**
	 *Reference to the a ArrayModelControl. 
	 */
	private HArrayModelControl arrayModelControl;
	
	/**
	 * The primary stage. 
	 */
	private Stage primaryStage;
	
	/**
	 * Reference to the array pane. 
	 */
	private Array3DPane array3DPane; 
	
	/**
	 * Reference to the pane which allows users to simulate sensor movement. 
	 */
	private SensorsSimPane sensorSimPane; 
	
	/**
	 * List of control panes. Notification are passed between these panes to indicate chnages such as sensors being added, array types being chnaged etc. 
	 */
	private ArrayList<ControlPane> controlPanes;
	

	public ArrayModelView (HArrayModelControl arrayModelControl, Stage primaryStage){
		
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
		this.setCenter(array3DPane=new Array3DPane(this));
		this.setRight(sensorSimPane= new SensorsSimPane(this));
		
		//add all to list. 
		controlPanes=new ArrayList<ControlPane>();
		controlPanes.add(arrayPane); 
		controlPanes.add(hydrophonePane); 
		controlPanes.add(sensorPane); 
		controlPanes.add(array3DPane); 
		controlPanes.add(sensorSimPane); 
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
	public HArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

	public void notifyModelChanged(ChangeType type) {
		for (int i=0; i<this.controlPanes.size(); i++){
			this.controlPanes.get(i).notifyChange(type);
		}
	}

}
