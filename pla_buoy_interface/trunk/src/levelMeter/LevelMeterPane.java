package levelMeter;

import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Create a level meter node. Shows current levels. 
 * @author Jamie Macaulay
 *
 */
public class LevelMeterPane extends BorderPane {
	
	private ArrayList<ProgressBar> progressBars=new ArrayList<ProgressBar>();
	
	private ArrayList<Label> dBLabels=new ArrayList<Label>();

	private GridPane levelsPane; 
	
	public LevelMeterPane(Orientation orientation, int nChannels){
		
		levelsPane = new GridPane();
		levelsPane.setVgap(10);
		levelsPane.setHgap(5);


		//create progress bars. 
		StackPane stackPane;
		Label dBLabel;
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
		
		this.setCenter(levelsPane);
		
	}
	
	
	
	

}
