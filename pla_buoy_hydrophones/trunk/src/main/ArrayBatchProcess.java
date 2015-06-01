package main;

import java.io.File;

import dataUnits.movementSensors.MovementSensor;
import arrayModelling.ArrayPos;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Manages batch processing of array data. The batch processing loads data from sensors, and create a time series of hydrophone positions. 
 * <p>
 * The data is output into a selected file format. 
 * @author Jamie Macaulay
 *
 */
public class ArrayBatchProcess {
	
	/**
	 * Reference to the array model control. 
	 */
	private ArrayModelControl arrayModelControl;
	
	/**
	 * The batch process task 
	 */
	private BatchTask batchTask;

	/**
	 * The thread for running task on.  
	 */
	private Thread currentBatchThread; 
	
	/**
	 * Types of output file. 
	 */
	private String[] outputFileExtensions={
			".csv"
	};

	public ArrayBatchProcess(ArrayModelControl arrayModelControl){
		this.arrayModelControl=arrayModelControl; 
		//create task
		batchTask=new BatchTask();
	}
	

	/**
	 * Determine the position of hydrophones. 
	 * @param task - the task currently processing data. 
	 * @param timeStart - time bin start. 
	 * @param timeEnd - time bin end. 
	 * @param -transformed array positions 
	 */
	private ArrayPos processPosition(Task task, long timeStart, long timeEnd){
		//batch processing takes place on a separate thread.
		long time=(timeEnd+timeStart)/2; 
		int error=arrayModelControl.getArrayModelManager().calculateHydrophonePositions(time);
		if (error!=0) {
			System.out.println("Batch Porcess: Warning: ");
		}
		//work out total data range
		return null; 
	}
	
	/**
	 * Perform pre checks to check that sensor can be loaded and there are no errors. 
	 * @return. true if pre check is successfully completed. False otherwise. 
	 */
	private boolean performPreChecks(){
		try{
		ObservableList<MovementSensor> sensors=arrayModelControl.getSensorManager().getSensorList();
		Double[] orientation; 
		Double[] position; 
		for (int i=0; i<sensors.size(); i++){
			//try and load up some data from sensor
			orientation=sensors.get(i).getOrientationData(arrayModelControl.getArrayManagerSettings().startBatch);
			position=sensors.get(i).getLatLong(arrayModelControl.getArrayManagerSettings().startBatch);
			
			//now check if there are any null values for sensor reading the movement sensor should have
			for (int j=0; j<sensors.get(i).getHasSensors().length; j++){
				final int n=i; 
				//orientation data
				if (j<=2){
					if (sensors.get(i).getHasSensors()[j] && (orientation==null || orientation[j]==null)){
						Platform.runLater(()->{
							showPreCheckWarning(sensors.get(n).sensorNameProperty().getValue()); 
						});
						return false; 
					}
				}
				//position data
				if (j>2){
					if (sensors.get(i).getHasSensors()[j] && (position==null || position[j]==null)){
						Platform.runLater(()->{
							showPreCheckWarning(sensors.get(n).sensorNameProperty().getValue()); 
						});						
						return false; 
					}
				}
			}
		}}
		catch(Exception e){
			e.printStackTrace();
		}
		return true; 
	}
	
	/**
	 * Stop any running batch processes. 
	 * @return true if successfully stopped. 
	 */
	public boolean stop(){

		if (currentBatchThread!=null){

			synchronized (currentBatchThread){
				//cancel task
				batchTask.cancel();
				try {
					//wait for thread to finish. 
					currentBatchThread.wait(1000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			currentBatchThread=null; 
		}
		arrayModelControl.getMainView().unbindProgressTask(); 

		return true; 
		
	}
	
	/**
	 * Get the current file for saving data. For long processing run, new files are created. 
	 * @return current file for saving data. 
	 */
	private File getCurrentFile(){
		return null; //TODO
	}
	
	private void saveData(File file, ArrayPos arrayPos){
		//TODO
	}

	public String[] getOutputFileExtensions() {
		return outputFileExtensions;
	}

	public void start(File file) {
		
		stop(); 

		//create new batch task. (Need to this in case previous task has thrown an error)
		batchTask=new BatchTask(); 

		//bind progress bar to batch progress bar to task progress. Have to unbine each time but worth it incase batch task changes in future. 
		arrayModelControl.getMainView().bindProgressTask(batchTask); 

		//start a new thread
		currentBatchThread=new Thread(batchTask);
		currentBatchThread.setDaemon(true);

		currentBatchThread.start();

	}
	
	/**
	 * Show a warning dialog if sensor has not successfully loaded data. 
	 * @param sensor - sensor has not loaded data. 
	 */
	private void showPreCheckWarning(String sensor){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Sensor Pre Check Error");
		alert.setHeaderText(("Error in "+sensor));
		alert.setContentText("An occured during "+sensor + " sensor pre check. ");
		alert.showAndWait();
	}
	
	
	/**
	 * Task for batch processing. 
	 * @author Jamie Macaulay
	 *
	 */
	protected class BatchTask extends Task<Integer>{

		@Override 
		protected Integer call() {

			updateMessage("Batch process: Performing sensor pre-checks");
			boolean ok=performPreChecks();
			if  (!ok){
				updateMessage("Batch process: Error in pre check");
				return null; 
			}

			//run the batch process. 

			ArrayManagerSettings settings = arrayModelControl.getArrayManagerSettings(); 
			int n=0; 
			long totalTime =settings.endBatch-settings.startBatch;
			ArrayPos arrayPos; 
			for (long time = settings.startBatch; time <= settings.endBatch-settings.timeBin; time=time+settings.timeBin) {

				if (isCancelled()) {
					break;
				}

				try {
					//arrayPos=processPosition(this, time, time+settings.timeBin); 
					//saveData(getCurrentFile(),  arrayPos);	

					//TEMP//

					Thread.sleep(1);
					if (isCancelled()) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//TEMP//

				if (n%100==0) updateMessage("Progress: "+String.format( "%.2f",((time-settings.startBatch)/1000.))+" of "+totalTime/1000.); 
				updateProgress(time-settings.startBatch, totalTime);
			}


			return null;
		}

		@Override 
		protected void cancelled() {
			super.cancelled();
			try {
			Platform.runLater(()->{
				arrayModelControl.getMainView().unbindProgressTask(); 
				arrayModelControl.getMainView().showProgessPane(false); 
			});
			updateMessage("Batch process cancelled");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		@Override 
		protected void succeeded() {
			super.succeeded();
			System.out.println("The threrad has succeeded"); 
			try {
				Platform.runLater(()->{
					arrayModelControl.getMainView().unbindProgressTask(); 
					arrayModelControl.getMainView().showProgessPane(false); 
				});
				updateMessage("Batch process complete");
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
	};

}

