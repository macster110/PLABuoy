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
import javafx.scene.layout.VBox;
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
		
		Label dataAquisition=new Label("Data Aquisition");
		dataAquisition.setPadding(standardPadding);
		
		Label serialLabel=new Label("Serial Data");
		serialLabel.setPadding(standardPadding);
		
		Label levelMeterLabel=new Label("Level Meters");
		levelMeterLabel.setPadding(standardPadding);

		mainControls.getChildren().addAll(dataAquisition, createStartStopPane(), serialLabel , createSerialPane() ,
				levelMeterLabel,  createLevelMeterPane());
		
		this.setTop(mainControls);
	
	}
	
	/**
	 * Create pane with start and stop controls. 
	 * @return pane with start and stop buttons. 
	 */
	private Node createStartStopPane(){
		HBox startStop=new HBox(); 
		startStop.setSpacing(10);
		startStop.setPadding(standardPadding);

		Button start = new Button();
		start.setText("Start");
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//plaControl.getNetworkManager().startDAQ();
			}
		});



		Button stop = new Button();
		stop.setText("Stop");
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//plaControl.getNetworkManager().stopDAQ();
			}
		});
		
		startStop.getChildren().addAll(start, stop);

		return startStop;
	}
	
	private Node createSerialPane(){
		serialTextField = new TextField(); 
		serialTextField.setPadding(standardPadding);
		serialTextField.setEditable(false);
		return serialTextField; 
	}
	
	private Pane createStoredDataPane(){
		//TODO
		return null; 
	}
	
	
	private Node createLevelMeterPane(){
		leveMeterPane=new LevelMeterPane(Orientation.VERTICAL, 8); 
		leveMeterPane.setPadding(standardPadding);
		return leveMeterPane;
	}




	
	


}
