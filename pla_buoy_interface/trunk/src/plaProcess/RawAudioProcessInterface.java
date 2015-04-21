package plaProcess;

import pla_buoy_interface.PLAInterfaceView;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import levelMeter.LevelMeterPane;

public class RawAudioProcessInterface implements PLAProcessInterface {

	/**
	 * Level meter pane. 
	 */
	private LevelMeterPane leveMeters;
	
	/**
	 * Main pane for raw audio data. 
	 */
	private Pane rawAudioPane;  

	@Override
	public String getProcessTypeName() {
		return "Audio";
	}

	@Override
	public Pane getProcessPane() {
		if (rawAudioPane==null) rawAudioPane=createLevelMeterPane();
		return rawAudioPane;
	}
	
	private Pane createLevelMeterPane(){
		
		VBox vBox=new VBox(); 

		Label levelMeterLabel=new Label("Level Meters");
		levelMeterLabel.setPadding(PLAInterfaceView.standardPadding);		
		
		leveMeters=new LevelMeterPane(Orientation.VERTICAL, 8); 
		leveMeters.setPadding(PLAInterfaceView.standardPadding);
		leveMeters.setMaxWidth(Double.MAX_VALUE);
		
		vBox.getChildren().addAll(levelMeterLabel,leveMeters);
		
		return vBox;
	}

	@Override
	public void updatePane(String command, String response) {
		//need to surround with try catch in case corrupted string won't convert to number
		try{

			String[] splitArray = null;
			//only summary data so far so don't need to comman string but could change 
			if (response!=null)	splitArray = response.split(",");
			if (response==null || splitArray.length==0){
				System.err.println("RawAudioProcessInterface: No level information recieved"); 
				return; 
			}
			if (splitArray.length-1!=Integer.valueOf(splitArray[0])){
				System.err.println("RawAudioProcessInterface: Channels do not correspond to recieved data"); 
				return; 
			}

			int[] amplitudeData=new int[splitArray.length-1]; 
			//create an array of amplitude data
			for (int i=1; i<splitArray.length; i++){
				amplitudeData[i-1]=Integer.valueOf(splitArray[i]);
				//System.out.println("Amplitude Data: ch: "+i+" amplitude: "+amplitudeData[i-1]);
			}
			
			leveMeters.setUnitLevels(amplitudeData, 16, 18,20, -201);
			
		}
		catch(Exception e){
			e.printStackTrace();
			return; 
		}
		
	}
	
	public String[] stringSplit(String string){
	    String[] splitArray = string.split(",");
	    return splitArray;
	}

}
