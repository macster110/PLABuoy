package levelMeter;

import java.util.ArrayList;




import pla_buoy_interface.Utils;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Create a level meter node. Shows current levels. 
 * @author Jamie Macaulay
 *
 */
public class LevelMeterPane extends BorderPane {
	
	private ArrayList<ProgressBar> progressBars=new ArrayList<ProgressBar>();
	
	private ArrayList<Label> dBLabels=new ArrayList<Label>();

	private GridPane levelsPane; 
	
	//min dB  for the level. 
	double min=100; 
	
	//max dB for each level
	double max=230;
	
	public LevelMeterPane(Orientation orientation, int nChannels){
		
		levelsPane=createLevelsPane(nChannels);
		this.setCenter(levelsPane);
	}
	
	/**
	 * Create the level pane. 
	 * @param nChannels - number of channels/level meters. 
	 * @return pane containing level meters
	 */
	private GridPane createLevelsPane(int nChannels){
		
		levelsPane = new GridPane();
		levelsPane.setVgap(10);
		levelsPane.setHgap(5);

		//create progress bars. 
		StackPane stackPane;
		for (int i=0; i< nChannels; i++){
			
			levelsPane.add(new Label("ch: "+i),0,i);

			stackPane=new StackPane();
			
			progressBars.add(new ProgressBar(0));
			progressBars.get(i).setPrefWidth(300);
			
			dBLabels.add(new Label("0dB")); 
		    StackPane.setAlignment(dBLabels.get(i), Pos.CENTER);

			stackPane.getChildren().addAll(progressBars.get(i), dBLabels.get(i)); 
			levelsPane.add(stackPane,1,i);
			
		}
		
		return levelsPane;
	
	}
	
	/**
	 * Set levels
	 * @param unitLevels - amplitude in units. e.g. if 16 bit sound card max would 
	 * @param soundCardBits - the sound card bit. 
	 * @param ptopV - the peak to peak voltage in volts
	 * @param gaindB - the total gain of the system in dB
	 * @param recieverCal - the calibration value of the reciever in uPa.s
	 */
	public void setUnitLevels(int[] unitLevels, int soundCardBits, double gaindB, double ptopV, double recieverCal){
		
		double[] levelsdB=new double[unitLevels.length];
		for(int i=0;  i< unitLevels.length; i++){
			
			levelsdB[i]=Utils.calcTruedB( unitLevels[i],  soundCardBits,  gaindB,  ptopV,  recieverCal);
			
			if (levelsdB[i]>max) levelsdB[i]=max; 
			if (levelsdB[i]<min) levelsdB[i]=min;
			
			//calc value between 0 and 1 to set level meter. 
			double value=(levelsdB[i]-min)/(max-min);
			
			//run on JavaFX thread. 
			final int n=i; 
			Platform.runLater( ()->{
				progressBars.get(n).setProgress(value);
				dBLabels.get(n).setText(String.format("%.1f", levelsdB[n])+"dB");
			});
			

			System.out.println("Levels dB "+levelsdB[i] + " units: " +unitLevels[i]); 
		}
		
	}
	
	
	
	

}
