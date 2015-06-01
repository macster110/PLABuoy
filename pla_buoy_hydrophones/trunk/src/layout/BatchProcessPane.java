package layout;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Shows Progress of batch processing. Contains a progress bar and 
 * @author Jamie Macaulay
 *
 */
public class BatchProcessPane extends VBox implements ControlPane {

	/**
	 * Porgress bar shopwing overall progess of batch update. 
	 */
	private ProgressBar progressBar;
	
	/**
	 * Shows the time value the batch process has reached. 
	 */
	private Label currentTimeLabel;

	/**
	 * Shows custom batch process maessages, 
	 */
	private Label messageLabel;

	public BatchProcessPane(){
		 progressBar =new ProgressBar();
		 currentTimeLabel=new Label("Process time: "); 
		 messageLabel=new Label("Batch process message:"); 
		
		 progressBar.prefWidthProperty().bind(this.widthProperty());
		 
		 this.setPadding(new Insets(5));
		 this.setMaxWidth(Double.MAX_VALUE);
		 HBox.setHgrow(this, Priority.ALWAYS);
		 this.setSpacing(5);
		 this.getChildren().addAll(progressBar, currentTimeLabel, messageLabel); 
	}
	
	/**
	 * Bind a task to the progress bar and label. 
	 * @param task the task to bind. 
	 */
	public void bindTask(Task task){
		unbindTask();
		
		//bind progress bar to batch progress bar to task progress. Have to unbine each time but worth it incase batch task changes in future. 
		progressBar.progressProperty().bind(task.progressProperty());
		
		//bind message label to task 
		messageLabel.textProperty().bind(task.messageProperty());
	}

	@Override
	public void notifyChange(ChangeType type) {
		// TODO Auto-generated method stub
	}
	
	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public Label getCurrentTimeLabel() {
		return currentTimeLabel;
	}

	public Label getMessageLabel() {
		return messageLabel;
	}

	public void unbindTask() {
		//bind progress bar to batch progress bar to task progress. Have to unbine each time but worth it incase batch task changes in future. 
		progressBar.progressProperty().unbind();
		//bind message label to task 
		messageLabel.textProperty().unbind();		
	}

}
