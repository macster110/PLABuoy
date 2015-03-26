package layout;

import java.util.ArrayList;

import dataUnits.movementSensors.MovementSensor;
import main.SensorManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Pane which allows users to change sensor orientation and position settings in order to simulate what the corresponding 
 * hydrophone positions would look like. 
 * @author Jamie Macaulay	
 *
 */
public class SensorsSimPane extends BorderPane implements ControlPane {


	private ArrayModelView mainView;
	
	private VBox sensorHolderPane; 
	
	/**
	 * List of current sensors panes. 
	 */
	private ArrayList<SimSensorPane> currentSensorPanes; 

	public SensorsSimPane(ArrayModelView mainView) {
		this.mainView=mainView;
		currentSensorPanes=new ArrayList<SimSensorPane>(); 
		
		Label arrayLabel=new Label("Sensor Simulation");
		arrayLabel.setPadding(new Insets(10,0,0,10));
		arrayLabel.setFont(new Font("Ubuntu", 20));
		
		sensorHolderPane=new VBox(); 
		sensorHolderPane.setPadding(new Insets(10,10,10,10));
		sensorHolderPane.setPrefWidth(200);
		
		this.setTop(arrayLabel);
		this.setCenter(sensorHolderPane);
		
		populateSensorPane();
	}
	
	/**
	 * Populate the pane with a titled pane for each sensor. 
	 */
	private void populateSensorPane(){
		
		//remove all previous sensors. 
		sensorHolderPane.getChildren().removeAll(sensorHolderPane.getChildren());

		//get sensor data
		SensorManager sensorManager=this.mainView.getArrayModelControl().getSensorManager();
		ObservableList<MovementSensor> sensors=sensorManager.getSensorList();
		
		//create temp array
		ArrayList<SimSensorPane> tempSensorPanes=new ArrayList<SimSensorPane>(); 
		
		//now iterate through all sensors- remove any that are not here any more and add new sensor.
		TitledPane[] titledPane=new TitledPane[sensors.size()]; 
		for (int i=0; i<sensors.size(); i++){
			SimSensorPane simSensorPane = null;
			for (int j=0; j<currentSensorPanes.size(); j++){
				if (currentSensorPanes.get(j).getMovementSensor()==sensors.get(i)){
					simSensorPane=currentSensorPanes.get(j);
					continue; 
				}
			}
			if (simSensorPane==null){
				simSensorPane=new SimSensorPane(sensors.get(i));
			}
			
			tempSensorPanes.add(simSensorPane);
			
			//now create a titled pane for this 
			titledPane[i]=new TitledPane();
			titledPane[i].setText(simSensorPane.getMovementSensor().sensorNameProperty().get());
			titledPane[i].setContent(simSensorPane);
			sensorHolderPane.getChildren().add(titledPane[i]);
		}
		
		currentSensorPanes=tempSensorPanes;

	}
	
	
	@Override
	public void notifyChange(ChangeType type) {
		switch (type){
		case ARRAY_CHANGED:
			System.out.println("SensorSimPane: ARRAY_CHANGED");
			break;
		case HYDROPHONE_CHANGED:
			System.out.println("SensorSimPane: HYDROPHONE_CHANGED");
			break;
		case SENSOR_CHANGED:
			System.out.println("SensorSimPane: SENSOR_CHANGED");
			populateSensorPane();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Called when user changed sim sensor control. 
	 */
	public void simDataControlChanged(){
		mainView.getArrayModelControl().notifyModelChanged(ChangeType.SIM_SENSOR_TRIGGERED);
		mainView.getArrayModelControl().getArrayModelManager().calculateHydrophonePositions(-1);
	}
	
	/**
	 * Get simulated data for a sensor.  
	 * @param movementSensor - the movement sensor to find sim data for. 
	 * @return heading, pitch , roll, (all in RADIANS) latitude, longitude (in DECIMAL), depth (in METERS). 
	 */
	public Double[] getSimSensorData(MovementSensor movementSensor){
		Double[] simSensorData=null; 
		for (int i=0; i<currentSensorPanes.size(); i++){
			if (movementSensor == currentSensorPanes.get(i).getMovementSensor()){
				simSensorData=currentSensorPanes.get(i).getSimSensorData(); 
			}
		}
		
		return simSensorData; 
	}

	
	/**
	 * Pane which represents a sensor on the array. Controls allow the orientation of the sensor to be simulated. 
	 * @author Jamie Macaulay	
	 */
	class SimSensorPane extends VBox{
		 
		private MovementSensor movementSensor;
		
		private Slider headingSlider; 
		
		private Slider pitchSlider;
		
		private Slider rollSlider;
		
		private Slider depthSlider;
		
		private Spinner<Double> latitude; 
		
		private Spinner<Double> longitude; 

		public SimSensorPane(MovementSensor movementSensor){
			this.movementSensor=movementSensor;
			//TODO- at the moment only deals with orientation and depth. GPS should be needed as we would
			//onyl ever have one GPS sensor to show abs positions- if a situation arises where we might 
			//two gps sensor can add lat long sliders. 
			
			this.setSpacing(2);
			this.setPadding(new Insets(5,5,5,5));
			
			if (movementSensor.getHasSensors()[0]){
				this.getChildren().add(new Label("Heading "+"\u00B0"));
				this.getChildren().add(headingSlider= new Slider(0,360, 0));
				headingSlider.setPadding(new Insets(0,0,10,0));
				headingSlider.setShowTickMarks(true);
				headingSlider.setShowTickLabels(true);
				headingSlider.setValue(Math.toDegrees(movementSensor.headingRefProperty().get()));
				headingSlider.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});
			}
				
			if (movementSensor.getHasSensors()[1]){
				this.getChildren().add(new Label("Pitch "+"\u00B0"));
				this.getChildren().add(pitchSlider= new Slider(-90,90, 0));
				pitchSlider.setPadding(new Insets(0,0,10,0));
				pitchSlider.setShowTickMarks(true);
				pitchSlider.setShowTickLabels(true);
				pitchSlider.setValue(Math.toDegrees(movementSensor.pitchRefProperty().get()));
				pitchSlider.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});
			}
			if (movementSensor.getHasSensors()[2]){
				this.getChildren().add(new Label("Roll "+"\u00B0"));
				this.getChildren().add(rollSlider= new Slider(-180,180, 0));
				rollSlider.setPadding(new Insets(0,0,10,0));
				rollSlider.setShowTickMarks(true);
				rollSlider.setShowTickLabels(true);
				rollSlider.setValue(Math.toDegrees(movementSensor.rollRefProperty().get()));
				rollSlider.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});
			}
			
			if (movementSensor.getHasSensors()[3]){
				this.getChildren().add(new Label("Latitude N (m)"));
				this.getChildren().add(latitude= new Spinner<Double>(-90., 90., 0.0, 0.0001));
				latitude.setEditable(false);
				latitude.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});

			}
			
			if (movementSensor.getHasSensors()[4]){
				this.getChildren().add(new Label("Longitude W (m)"));
				this.getChildren().add(longitude=new Spinner<Double>(-180., 180., 0.0, 0.0001));
				longitude.setEditable(true);
				longitude.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});
			}
			
			if (movementSensor.getHasSensors()[5]){
				this.getChildren().add(new Label("Depth (m)"));
				this.getChildren().add(depthSlider= new Slider(0,100, 0));
				depthSlider.valueProperty().addListener(( ov, ol_val ,new_val)->{
					simDataControlChanged();
				});
			}
			
		}
	
		/**
		 * Get the current simulated data ceneter 
		 * @return heading, pitch , roll, (all in RADIANS) latitude, longitude (in DECIMAL), depth (in METERS). 
		 */
		public Double[] getSimSensorData() {
			
			Double[] sensorData=new Double[6];  
			
			if (movementSensor.getHasSensors()[0]){
				sensorData[0]=Math.toRadians(headingSlider.getValue());
			}
			if (movementSensor.getHasSensors()[1]){
				sensorData[1]=Math.toRadians(pitchSlider.getValue());
			}
			if (movementSensor.getHasSensors()[2]){
				sensorData[2]=Math.toRadians(rollSlider.getValue());
			}
			if (movementSensor.getHasSensors()[3]){
				sensorData[3]=latitude.getValue();
			}
			if (movementSensor.getHasSensors()[4]){
				sensorData[4]=longitude.getValue();
			}
			if (movementSensor.getHasSensors()[5]){
				sensorData[5]=depthSlider.getValue();
			}
			
			return sensorData;
		}


		/**
		 * Get the movement sensor. 
		 * @return the movement sensor. 
		 */
		public MovementSensor getMovementSensor() {
			return movementSensor;
		}
	}

}
