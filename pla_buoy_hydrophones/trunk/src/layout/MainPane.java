package layout;

import main.HydrophoneModelControl;
import javafx.scene.layout.BorderPane;

/**
 * The main pane holds all controls for modelling program. 
 * @author Jamie Macaulay
 *
 */
public class MainPane extends BorderPane {
	
	public MainPane (HydrophoneModelControl hydrophoneModelControl){
		
		ArrayPane arrayPane=new ArrayPane(); 
		this.setLeft(arrayPane);
		
	}

}
