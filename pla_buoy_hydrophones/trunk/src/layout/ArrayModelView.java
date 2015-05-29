package layout;


import java.util.ArrayList;

import org.controlsfx.glyphfont.Glyph;

import layout.ControlPane.ChangeType;
import layout.arrays.ArrayTablePane;
import layout.hydrophones.HydrophoneTablePane;
import layout.movementSensors.SensorTablePane;
import main.ArrayModelControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
	private ArrayModelControl arrayModelControl;
	
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

	/**
	 * Pane for importing and saving array configurations
	 */
	private ImportArrayPane processPane;
	
	public 	Image settingsIcon=new Image(getClass().getResourceAsStream("/resources/SettingsButtonMediumGrey.png"));

	

	public ArrayModelView (ArrayModelControl arrayModelControl, Stage primaryStage){
		
		this.arrayModelControl=arrayModelControl; 
		this.primaryStage=primaryStage; 
		
		VBox sensorBox=new VBox(); 
		sensorBox.setPadding(new Insets(10,0,0,0));
		

		Label importLabel=new Label("Import Array");
		importLabel.setPadding(new Insets(0,0,0,10));
		importLabel.setFont(new Font("Ubuntu", 20));
		
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
		
		sensorBox.getChildren().addAll(importLabel, processPane=new ImportArrayPane(),arrayLabel, arrayPane, hydrophoneLabel, hydrophonePane, sensorLabel, sensorPane);
		processPane.prefWidthProperty().bind(sensorBox.widthProperty());
		sensorBox.setSpacing(10);
		
		//create array pane with some controls on top
		array3DPane=new Array3DPane(this);
		StackPane stackPane=new StackPane(); 
		//stackPane.setMouseTransparent(true);
		stackPane.getChildren().add(array3DPane);
		addProcessButtons( stackPane);
		
		SplitPane sp = new SplitPane();
		sp.getItems().add(sensorBox);
		sp.getItems().add(stackPane);
		sp.getItems().add(sensorSimPane= new SensorsSimPane(this));
		SplitPane.setResizableWithParent(sensorSimPane, false);
		sp.setDividerPositions(0.4f, 0.8f, 0.9f);
		 
		this.setCenter(sp);
		
		//add all to list. 
		controlPanes=new ArrayList<ControlPane>();
		controlPanes.add(processPane); 
		controlPanes.add(arrayPane); 
		controlPanes.add(hydrophonePane); 
		controlPanes.add(sensorPane); 
		controlPanes.add(array3DPane); 
		controlPanes.add(sensorSimPane); 
	}
	
	private void addProcessButtons(StackPane stackPane){
//		
		Button batchProcess=new Button("", Glyph.create("FontAwesome|PLAY")); 
		batchProcess.setOnAction((action)->{
			//browse for settings file. 
		});
		StackPane.setAlignment(batchProcess, Pos.TOP_LEFT);
	    StackPane.setMargin(batchProcess, new Insets(10,0,0,10));

		Button stopProcess=new Button("", Glyph.create("FontAwesome|STOP")); 
		stopProcess.setOnAction((action)->{
			//browse for settings file. 
		});
		stopProcess.prefHeightProperty().bind(batchProcess.heightProperty());
		StackPane.setAlignment(stopProcess, Pos.TOP_LEFT);
	    StackPane.setMargin(stopProcess, new Insets(10,0,0,60));
		
		Button settingsBatch=new Button(); 
		settingsBatch.setGraphic(new ImageView(settingsIcon));
		settingsBatch.prefHeightProperty().bind(batchProcess.heightProperty());
		StackPane.setAlignment(settingsBatch, Pos.TOP_LEFT);
	    StackPane.setMargin(settingsBatch, new Insets(10,0,0,110));

		stackPane.getChildren().addAll(batchProcess, stopProcess, settingsBatch);
		
	}
	
	/**
	 * Get the primary stage for the application
	 * @return the primary stage. 
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Get the SensorSimPane. This contains controls to allow users to simulate different sensor readings. 
	 * @return the SensorSimPane.
	 */
	public SensorsSimPane getSensorSimPane(){
		return sensorSimPane;
	}
	
	/**
	 * Get the array control model. This holds data on arrays, hydrophones and sensors. 
	 * @return
	 */
	public ArrayModelControl getArrayModelControl() {
		return arrayModelControl;
	}

	public void notifyModelChanged(ChangeType type) {
		for (int i=0; i<this.controlPanes.size(); i++){
			this.controlPanes.get(i).notifyChange(type);
		}
	}

}
