package plaProcess;

import javafx.scene.layout.Pane;

/**
 * The cRio is based around process, much like the modular system of PAMGAURD. 
 * <p>
 * The PLAProcessInterface allows users to create a custom pane for each different process in the PLABuoy. 
 * The pane for each process should show some kind of summary data. The pane is updated through updatePane function. 
 * @author Jamie Macaulay.
 *
 */
public interface PLAProcessInterface {
	
	/**
	 * The name of the process. This is the "processName" variable in the PLAProcess constructor. It is used to find the process through 
	 * network;  
	 * @return the process name 
	 */
	public String getProcessTypeName(); 
	
	/**
	 * Pane showing summary data for process. 
	 * @return a pane shwoing summary data for process. 
	 */
	public Pane getProcessPane();
	
	/**
	 * Updates the pane.
	 * @param command- command sent through network
	 * @param response-data which has returned. null if no data has returne d
	 */
	public void updatePane(String command, String response);
	
}
