package layout.movementSensors;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Pane which allows users to set the reference orientation and positions of a sensor. 
 * @author Jamie Macaulay
 *
 */
public class PositionRefPane extends BorderPane {
	
	private TextField xPos;
	private TextField yPos;
	private TextField zPos;

	public PositionRefPane(){
		this.setCenter(createPane());
	}
	
	
	private Pane createPane(){
		
		
		HBox arrayPos= new HBox(); 
		xPos=new TextField();
		yPos=new TextField();
		zPos=new TextField();
		arrayPos.setSpacing(10);
		arrayPos.getChildren().addAll(new Label("x (m)"), xPos, new Label("y (m)"), yPos, new Label("z (m)"), zPos);
		
		return null; 
		
	}
	
	

}
