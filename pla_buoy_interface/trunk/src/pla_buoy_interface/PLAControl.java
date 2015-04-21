package pla_buoy_interface;

import java.util.ArrayList;

import plaProcess.PLAProcessInterface;
import plaProcess.RawAudioProcessInterface;
import plaProcess.SerialProcessInterface;
import plaProcess.WavProcessInterface;
import javafx.application.Platform;
import javafx.concurrent.Task;
import networkManager.NetworkManager;

/**
 * Holds settings, process configuration and network manager. 
 * @author Jamie Macaulay
 *
 */
public class PLAControl {
	
	/**
	 * Task which polls
	 */
	Task<Integer> pollTask;
	
	/**
	 * True to poll network continuously for summary data. 
	 */
	private boolean poll=false; 
	
	/**
	 * The current thread polling for data from cRio over network. 
	 */
	private Thread currentPollThread; 
	
	/**
	 * Settings info for program
	 */
	private PLAInterfaceSettings plaInterfaceSettings;

	/**
	 * List of current process for which to poll and display summary data. 
	 */
	private ArrayList<PLAProcessInterface> plaProcessInterfaces; 
	
	/**
	 * Manages sending commands and recieving data. 
	 */
	private NetworkManager netWorkManager; 

	public PLAControl(){
		plaProcessInterfaces=new ArrayList<PLAProcessInterface>();
		populateProcesses(); 

		plaInterfaceSettings=new PLAInterfaceSettings();
		netWorkManager=new NetworkManager(this); 
	}
	
	
	/**
	 * Add relevant processeInterfaces to the plaProcessInterfaces list.
	 */
	private void populateProcesses() {
		
		/**
		 * TODO Ideally this could happen automatically but that will require some structural changes to cRio code. 
		 * For now that means processes are added automatically. 
		 */
		
		plaProcessInterfaces.add(new SerialProcessInterface());
		plaProcessInterfaces.add(new WavProcessInterface());
		plaProcessInterfaces.add(new RawAudioProcessInterface());
		
	}
	

	/**
	 * Get settings for PLA Interface, 
	 * @return settings class. 
	 */
	public PLAInterfaceSettings getPlaInterfaceSettings() {
		return plaInterfaceSettings;
	}

	public void setPlaInterfaceSettings(PLAInterfaceSettings plaInterfaceSettings) {
		this.plaInterfaceSettings = plaInterfaceSettings;
	}

	/**
	 * Get the network manager.
	 * @return the network manager. 
	 */
	public NetworkManager getNetworkManager() {
		return netWorkManager;
	}
	
	/**
	 * Set whether the network is polled ocntinously for summary data from current pla process. 
	 * @param poll - true;
	 */
	public void setPolling(boolean poll){		
		if (currentPollThread!=null){
			this.poll=false;
			synchronized (currentPollThread){
				//cancel task
				pollTask.cancel();
				try {
					//wait for thread to finish. 
					currentPollThread.wait();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentPollThread=null; 
		}
		
		//start a new thread
		if (poll){
			this.poll=poll; 
			pollTask=createRealTimeTask();
			currentPollThread=new Thread(pollTask);
			currentPollThread.setDaemon(true);
			currentPollThread.start();
		}

	}
	
	private Task<Integer> createRealTimeTask(){
		Task<Integer> task = new Task<Integer>() {
	        @Override protected Integer call() throws Exception {
	            while (poll==true){
	            	if (isCancelled()) break; 
	            	 System.out.println("Thread : do something here"); 
	            	 for (int i=0; i<plaProcessInterfaces.size(); i++){
	            		 final int n=i ;
	            		 String data=netWorkManager.getSummaryData(plaProcessInterfaces.get(i).getProcessTypeName());
	            		 Platform.runLater(() -> {
	            			 plaProcessInterfaces.get(n ).updatePane("summarydata", data);
	            		 });
	            		 //if (i==1) System.out.println("Levels: "+data);
	            	 }
	            	 Thread.sleep(500);
	            }
	            
           	 	System.out.println("Poll Thread : finished"); 
	            return 0;
	        }
	    };
	    
	    return task;
	}
	
	/**
	 * Get a list of PLAProcessInterfaces. These show summary data from different process running on the remote device. 
	 * @return list of current processes. 
	 */
	public ArrayList<PLAProcessInterface> getPLAProcessInterfaces(){
		return plaProcessInterfaces; 
	}

}
