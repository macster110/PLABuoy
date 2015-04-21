package plaProcess;

import pla_buoy_interface.PLAInterfaceView;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SerialProcessInterface implements PLAProcessInterface {

	private TextArea serialTextField;
	
	private Pane serialPane; 

	@Override
	public String getProcessTypeName() {
		return "serialRead";
	}

	@Override
	public Pane getProcessPane() {
		if (serialPane==null) serialPane=createSerialPane();
		return serialPane;
	}
	
	/**
	 * Create pane which shows incoming serial data. 
	 * @return pane showing incoming serial data from device. 
	 */
	private Pane createSerialPane(){
		
		VBox serialPane=new VBox(); 

		Label serialLabel=new Label("Serial Data");
		serialLabel.setPadding(PLAInterfaceView.standardPadding);
		
		HBox serialDataPane=new HBox(); 
		serialDataPane.setSpacing(10);
		serialDataPane.setPadding(PLAInterfaceView.standardPadding);
		
		serialTextField = new TextArea(); 
		serialTextField.setPrefRowCount(3);
		serialTextField.setWrapText(true);
		serialTextField.setPadding(PLAInterfaceView.standardPadding);
		serialTextField.setEditable(false);
	    HBox.setHgrow(serialTextField, Priority.ALWAYS);
		serialDataPane.getChildren().add(serialTextField);
		
		serialPane.getChildren().addAll(serialLabel,serialDataPane); 
		
		return serialPane; 
	}

	@Override
	public void updatePane(String command, String response) {
		//only summarydataa so far but could change 
		if (response!=null) serialTextField.setText(response);

	}

}
