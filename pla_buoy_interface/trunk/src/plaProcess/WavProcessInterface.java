package plaProcess;

import pla_buoy_interface.PLAInterfaceView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WavProcessInterface implements PLAProcessInterface {

	/**
	 * Label showing wav file infpo. 
	 */
	private Label wavFileInfo;
	
	private Pane wavFilePane;;
	
	@Override
	public String getProcessTypeName() {
		return "wavrecord";
	}

	@Override
	public Pane getProcessPane() {
		if (wavFilePane==null) wavFilePane=createWavPane();
		return wavFilePane;
	}
	
	/**
	 * Create pane which shows incoming serial data. 
	 * @return pane showing incoming serial data fz\rom device. 
	 */
	private Pane createWavPane(){
		
		VBox wavPane=new VBox(); 

		Label wavLabel=new Label("Wav Data");
		wavLabel.setPadding(PLAInterfaceView.standardPadding);
		
		HBox wavDataPane=new HBox(); 
		wavDataPane.setSpacing(10);
		wavDataPane.setPadding(PLAInterfaceView.standardPadding);
		
		wavFileInfo = new Label("-"); 
		
		wavDataPane.getChildren().add(wavFileInfo);
		
		wavPane.getChildren().addAll(wavLabel,wavDataPane); 
		
		return wavPane; 
	}

	@Override
	public void updatePane(String command, String response) {
		
		System.out.println("Wavresponse: "+response);
		
		String[] splitString=null;
		if (response!=null) splitString=response.split(",");
		if (splitString==null || splitString.length!=2){
			System.err.println("WavProcessInterface: Information not recieved or corrupt");
			return;
		}
		
		wavFileInfo.setText(String.format("No. of wav file %s: Total size %s: MB ", splitString[0], splitString[1])); 
		
	}

}
