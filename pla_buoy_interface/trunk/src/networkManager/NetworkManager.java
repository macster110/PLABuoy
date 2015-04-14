package networkManager;

/**
 * Manages network for the 
 * @author Jamie Macaulay
 *
 */
public class NetworkManager {
	
	
	private NIUDPInterface niUdpInterface;

	public NetworkManager(){
		niUdpInterface=new NIUDPInterface(this); 
	}
	
	/**
	 * Start the processes and DAQ aquisition.
	 */
	public void startDAQ(){
		niUdpInterface.sendCommand("start");
	}
	
	/**
	 * Stiop the DAQ and processes. 
	 */
	public void stopDAQ(){
		niUdpInterface.sendCommand("stop");
	}
	
	

}
