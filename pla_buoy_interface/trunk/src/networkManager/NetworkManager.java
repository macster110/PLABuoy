package networkManager;

import pla_buoy_interface.PLAControl;

/**
 * Manages network for the 
 * @author Jamie Macaulay
 *
 */
public class NetworkManager {
	
	/**
	 * The UDP interface. 
	 */
	private NIUDPInterface niUdpInterface;
	
	/**
	 * Reference to plaControl. 
	 */
	private PLAControl plaControl;

	public NetworkManager(PLAControl plaControl){
		this.plaControl=plaControl; 
		niUdpInterface=new NIUDPInterface(this); 
	}
	
	/**
	 * Start the processes and DAQ aquisition.
	 */
	public String startDAQ(){
		return niUdpInterface.sendCommand("start");
	}
	
	/**
	 * Stop the DAQ and processes. 
	 */
	public String stopDAQ(){
		return niUdpInterface.sendCommand("stop");
	}
	
	/**
	 * Ping to check connection
	 */
	public String pingDAQ() {
		return niUdpInterface.sendCommand("ping");
	};
	
	/**
	 * Get summary data from a process. 
	 * @param processName- the process name
	 * @return returned summary data or null. 
	 */
	public synchronized String getSummaryData(String processName) {
		String command=processName+ " summarydata";
		return niUdpInterface.sendCommand(command);
	}; 
	
	/**
	 * Start the processes and DAQ aquisition.
	 */
	public double[] getLevels(){
		
		String channels=niUdpInterface.sendCommand("start");
		double[] levels=new double[8]; //TODO- dynamic channels
		
		return levels;
	}
	
	/**
	 * Get current IP address
	 */
	public String getCurrentIPaddress(){
		return plaControl.getPlaInterfaceSettings().ipAddress;
	}

	
	
	

}
