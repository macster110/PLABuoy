package pla_buoy_interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;
import levelMeter.LevelMeterPane;

public class PLAInterfaceView extends BorderPane {
	
	/**
	 * Holds information on cRio IP address. 
	 */
	TextField ipTextField; 
	
	/**
	 * Reference to PLA Control class. 
	 */
	PLAControl plaControl;

	/**
	 * Serial text field 
	 */
	private TextField serialTextField;
	
	/**
	 * Serial text field 
	 */
	private LevelMeterPane leveMeterPane;
	
	private Insets standardPadding=new Insets(10,10,0,10);
	
	
	public PLAInterfaceView(PLAControl plaControl){
		this.plaControl=plaControl; 
		//create GUI nodes. 
		VBox mainControls=new VBox(); 
		
		
		Label connectionLabel=new Label("Connection");
		connectionLabel.setPadding(standardPadding);
		
		Label dataAquisition=new Label("Data Aquisition");
		dataAquisition.setPadding(standardPadding);
		
		Label serialLabel=new Label("Serial Data");
		serialLabel.setPadding(standardPadding);
		
		Label levelMeterLabel=new Label("Level Meters");
		levelMeterLabel.setPadding(standardPadding);

		mainControls.getChildren().addAll(connectionLabel, createStoredDataPane(), dataAquisition, 
				createStartStopPane(), serialLabel , createSerialPane() ,
				levelMeterLabel,  createLevelMeterPane());
		
		mainControls.setMaxWidth(500);
		this.setLeft(mainControls);
	
	}
	
	/**
	 * Create pane with start and stop controls. 
	 * @return pane with start and stop buttons. 
	 */
	private Node createStartStopPane(){
		HBox startStop=new HBox(); 
		startStop.setSpacing(10);
		startStop.setMaxWidth(Double.MAX_VALUE);
		startStop.setPadding(standardPadding);

		Button start = new Button();
		start.setText("Start");
	     HBox.setHgrow(start, Priority.ALWAYS);
		start.setMaxWidth(Double.MAX_VALUE);
		start.setMaxWidth(Double.MAX_VALUE);
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				plaControl.getNetworkManager().startDAQ();
			}
		});



		Button stop = new Button();
		stop.setText("Stop");
	    HBox.setHgrow(stop, Priority.ALWAYS);
		stop.setMaxWidth(Double.MAX_VALUE);
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				plaControl.getNetworkManager().stopDAQ();
			}
		});
		
		startStop.getChildren().addAll(start, stop);

		return startStop;
	}
	
	private Node createSerialPane(){
		HBox serialPane=new HBox(); 
		serialPane.setSpacing(10);
		serialPane.setPadding(standardPadding);
		
		serialTextField = new TextField(); 
		serialTextField.setPadding(standardPadding);
		serialTextField.setEditable(false);
	    HBox.setHgrow(serialTextField, Priority.ALWAYS);

		
		serialPane.getChildren().add(serialTextField);
		
		return serialPane; 
	}
	
	private Pane createStoredDataPane(){
		
		HBox ipSettings=new HBox(); 
		ipSettings.setSpacing(10);
		ipSettings.setPadding(standardPadding);
		
		FormattedTextField field = new FormattedTextField<>();
		field.setPattern("h.h.h.h");
		field.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0, 255));
	     HBox.setHgrow(field, Priority.ALWAYS);

		
		Button connect=new Button("Connect");
		
		ipSettings.getChildren().addAll(field, connect); 
		
		return ipSettings;
		
			
	}
	
	
	private Node createLevelMeterPane(){
		leveMeterPane=new LevelMeterPane(Orientation.VERTICAL, 8); 
		leveMeterPane.setPadding(standardPadding);
		leveMeterPane.setMaxWidth(Double.MAX_VALUE);
		return leveMeterPane;
	}




	
	


}
