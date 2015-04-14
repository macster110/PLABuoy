package pla_buoy_interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PLAInterfaceView extends BorderPane {
	
	/**
	 * Holds information on cRio IP address. 
	 */
	TextField ipTextField; 
	
	/**
	 * Reference to PLA Control class. 
	 */
	PLAControl plaControl;
	
	
	public PLAInterfaceView(PLAControl plaControl){
		this.plaControl=plaControl; 
		//create GUI nodes. 
		VBox mainControls=new VBox(); 
		
		Label dataAquisition=new Label("Data Aquisition");
		dataAquisition.setPadding(new Insets(10,10,0,10));
		
		Label serialLabel=new Label("Serial Data");
		serialLabel.setPadding(new Insets(10,10,0,10));

		mainControls.getChildren().addAll(dataAquisition, createStartStop(), serialLabel ,createSerialPane());
		
		this.setTop(mainControls);
	
	}
	
	/**
	 * Create pane with start and stop controls. 
	 * @return
	 */
	private Pane createStartStop(){
		HBox startStop=new HBox(); 
		startStop.setSpacing(10);
		startStop.setPadding(new Insets(10,10,0,10));

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
		TextField textField = new TextField(); 
		textField.setPadding(new Insets(10,10,0,10));
		textField.setEditable(false);
		return textField; 
	}
	
	private Pane createStoredDataPane(){
		//TODO
		return null; 
	}
	
	private Pane createLevelMeterPane(){
		//TODO
		return null; 
	}




	
	


}
