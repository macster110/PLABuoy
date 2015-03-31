package layout.movementSensors;

import dataUnits.movementSensors.MovementSensor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Pane which allows users to set the reference orientation and positions of a sensor. 
 * @author Jamie Macaulay
 *
 */
public class PositionRefPane extends VBox {
	
	/**
	 * Error occured in position/orientation text fields. 
	 */
	public final static int TEXT_FIELD_POS_ERROR=1; 
	
	/**
	 * Angle out of bounds e.g. heading != 361 degrees.
	 */
	public final static int ANGLE_ERROR=2; 

	
	/**
	 * The prefferred width in pixels of text fields. 
	 */
	private double prefTextWidth=50; 

	/**
	 * Position of sensor relative to it's parent array. 
	 */
	private TextField xPos;
	private TextField yPos;
	private TextField zPos;
	
	/**
	 * The reference heading, pitch and roll of sensor when no movement has coccured. 
	 */
	private TextField heading;
	private TextField pitch;
	private TextField roll;

	public PositionRefPane(){
		createPane();
	}
	
	/**
	 * Create the pane to set position and orientation. 
	 */
	private void createPane(){
		
		GridPane gridPane=new GridPane(); 
		gridPane.setPrefWidth(300);
		gridPane.setVgap(5); //vertical gap in pixels
		
		xPos=new TextField();
		xPos.setPrefWidth(prefTextWidth);
		yPos=new TextField();
		yPos.setPrefWidth(prefTextWidth);
		zPos=new TextField();
		zPos.setPrefWidth(prefTextWidth);
		
		gridPane.add(new Label(" x (m)"),1,1);
		gridPane.add(xPos,2,1);
		gridPane.add(new Label(" y (m)"),3,1);
		gridPane.add(yPos,4,1);
		gridPane.add(new Label(" z (m)"),5,1);
		gridPane.add(zPos,6,1);
	
		heading=new TextField();
		heading.setPrefWidth(prefTextWidth);
		pitch=new TextField();
		pitch.setPrefWidth(prefTextWidth);
		roll=new TextField();
		roll.setPrefWidth(prefTextWidth);
		
		gridPane.add(new Label(" Heading "+"\u00B0"),1,2);
		gridPane.add(heading,2,2);
		gridPane.add(new Label(" Pitch"+"\u00B0"),3,2);
		gridPane.add(pitch,4,2);
		gridPane.add(new Label(" Roll"+"\u00B0"),5,2);
		gridPane.add(roll,6,2);
		
		this.getChildren().addAll(new Label("Sensor Position and Orientation"),gridPane); 
		this.setSpacing(2);

		
//		for (int i=0; i<6; i++){
//		     ColumnConstraints column1 = new ColumnConstraints();
//		     column1.setPercentWidth(100);
//		     this.getColumnConstraints().add(column1);
//		}
		
	}
	
	/**
	 * Set controls to value of MovementSensor. 
	 * @param movmentSensor the movement sensor to get data from. 
	 */
	public void setParams(MovementSensor movmentSensor){
		System.out.println("Position pane set params: "+ movmentSensor.xRefPositionProperty().get());
		xPos.setText(Double.toString(movmentSensor.xRefPositionProperty().get()));
		yPos.setText(Double.toString(movmentSensor.yRefPositionProperty().get()));
		zPos.setText(Double.toString(movmentSensor.zRefPositionProperty().get()));
		heading.setText(Double.toString(movmentSensor.headingRefProperty().get()));
		pitch.setText(Double.toString(movmentSensor.pitchRefProperty().get()));
		roll.setText(Double.toString(movmentSensor.rollRefProperty().get()));
	}
	
	/**
	 * Get data from controls and set value in MovmentSnsor. Returns an error flag if error occurs. 
	 * @param movmentSensor - the movement sensor to set values for. 
	 * @return error flag. 0 if no error. >0 if there is an error.
	 */
	public int getParams(MovementSensor movmentSensor){

		double headingVal;
		double pitchVal;
		double rollVal;

		double xVal;
		double yVal;
		double zVal;

		try{

			xVal=Double.valueOf(xPos.getText());
			yVal=Double.valueOf(yPos.getText());
			zVal=Double.valueOf(zPos.getText());

			headingVal=Double.valueOf(heading.getText());
			pitchVal=Double.valueOf(pitch.getText());
			rollVal =Double.valueOf(roll.getText());

		}
		catch (Exception e){
			return TEXT_FIELD_POS_ERROR; 
		}

		if (headingVal<0 || headingVal>360) return ANGLE_ERROR;
		if (pitchVal<-90 || pitchVal>90) return ANGLE_ERROR;
		if (rollVal<-180 || rollVal>180) return ANGLE_ERROR;


		movmentSensor.headingRefProperty().setValue(Math.toRadians(headingVal));
		movmentSensor.pitchRefProperty().setValue(Math.toRadians(pitchVal));
		movmentSensor.rollRefProperty().setValue(Math.toRadians(rollVal));

		movmentSensor.xRefPositionProperty().setValue(xVal);
		movmentSensor.yRefPositionProperty().setValue(yVal);
		movmentSensor.zRefPositionProperty().setValue(zVal);

		return 0; 
	}
	
	
	public TextField getxPosField() {
		return xPos;
	}

	public TextField getyPosField() {
		return yPos;
	}

	public TextField getzPosField() {
		return zPos;
	}

	public TextField getHeadingField() {
		return heading;
	}

	public TextField getPitchField() {
		return pitch;
	}

	public TextField getRollField() {
		return roll;
	}

	

}
