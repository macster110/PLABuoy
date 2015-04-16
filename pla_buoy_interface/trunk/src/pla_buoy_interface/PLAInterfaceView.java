package pla_buoy_interface;

import java.awt.TextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

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
	 * Shows last status update. 
	 */
	private Label statusLabel=new Label("");
	
	/**
	 * Formatted text field which allows users to set IP address. 
	 */
	private FormattedTextField<Object> ipAddressField;
	
	/**
	 * Holds process panes
	 */
	private VBox processPane;
	
	/**
	 * Default insets for different panes. 
	 */
	public static Insets standardPadding=new Insets(10,10,0,10);

	
	public PLAInterfaceView(PLAControl plaControl){
		this.plaControl=plaControl; 
		
		//create main GUI nodes. 
		VBox mainControls=new VBox(); 
		
		Label connectionLabel=new Label("Connection");
		connectionLabel.setPadding(standardPadding);
		
		Label dataAquisition=new Label("Data Aquisition");
		dataAquisition.setPadding(standardPadding);
		
		mainControls.getChildren().addAll(connectionLabel, createConnectionPane(), createStatusPane(), dataAquisition, 
				createStartStopPane(), createRealTimePane());
		
		//now add process nodes. 
		processPane=new VBox(); 
		mainControls.getChildren().add(processPane);

		layoutProcessPanes(); 
		
		mainControls.setMaxWidth(500); //TODO- need dynamic layout- external libraries might be useful. 
		this.setLeft(mainControls);
	
	}
	

	/**
	 * Set status
	 * @param input string- data string recieved from cRio; 
	 */
	public void setStatus(String inputstring){
		boolean isError=false; 
		String outputString; 
		if (inputstring==null){
			outputString="Error: Nothing recieved";
			isError=true; 
		}
		else outputString =inputstring;
	
		statusLabel.setText(outputString);
		if (isError) statusLabel.setTextFill(Color.RED);
		else statusLabel.setTextFill(Color.LIMEGREEN);
		
	}
	
	/**
	 * Add all current processInterface panes. If there are already process panes these are 
	 * removed and new ones added. 
	 */
	public void layoutProcessPanes(){
		//remove all current processInterface panes. 
		processPane.getChildren().removeAll(processPane.getChildren());
		//add new panes. 
		for (int i=0; i<plaControl.getPLAProcessInterfaces().size(); i++){
			processPane.getChildren().add(plaControl.getPLAProcessInterfaces().get(i).getProcessPane()); 
		}
	}
	
	
	/******************Create Controls*************************/ 
	
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
				setStatus(plaControl.getNetworkManager().startDAQ());
			}
		});



		Button stop = new Button();
		stop.setText("Stop");
	    HBox.setHgrow(stop, Priority.ALWAYS);
		stop.setMaxWidth(Double.MAX_VALUE);
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setStatus(plaControl.getNetworkManager().stopDAQ());
			}
		});
		
		startStop.getChildren().addAll(start, stop);

		return startStop;
	}
	

	/**
	 * Create pane to set IP address and attempt connection. 
	 * @return pane allowing users to change IP address settings and test connection. 
	 */
	private Pane createConnectionPane(){

		HBox ipSettings=new HBox(); 
		ipSettings.setSpacing(10);
		ipSettings.setPadding(standardPadding);

		ipAddressField = new FormattedTextField<>();
		ipAddressField.setPattern("h.h.h.h");
		ipAddressField.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0, 255));
		HBox.setHgrow(ipAddressField, Priority.ALWAYS);

		Button connect=new Button("Connect");
		connect.setOnAction((action)->{
			plaControl.getPlaInterfaceSettings().ipAddress=ipAddressField.getText();
			String string=plaControl.getNetworkManager().pingDAQ();
			setStatus(string);
		});

		ipSettings.getChildren().addAll(ipAddressField, connect); 

		return ipSettings;
	}

	/**
	 * Creates a pane which shows current status updates. 
	 * @return pane which shows current status updates. 
	 */
	private Pane createStatusPane(){
		
		HBox ipSettings=new HBox(); 
		ipSettings.setSpacing(10);
		ipSettings.setPadding(standardPadding);
				
		ipSettings.getChildren().addAll(new Label("Status: "), statusLabel); 
		
		return ipSettings;

	}
	
	/**
	 * Creates a pane which allows users to set whether real time polling should be on or off.  
	 * @return
	 */
	private  Pane createRealTimePane(){
		
		HBox realTime=new HBox(); 
		realTime.setSpacing(10);
		realTime.setPadding(standardPadding);
		
		CheckBox cb = new CheckBox("Real time data");
		cb.setOnAction((action)->{
			plaControl.setPolling(cb.isSelected());
		});

		realTime.getChildren().add(cb);
				
		return realTime;
	}
	
	

	/**
	 * Set params for the nodes. 
	 */
	public void setParams(PLAInterfaceSettings settings){
		ipAddressField.setText(settings.ipAddress);
	}
	

}
