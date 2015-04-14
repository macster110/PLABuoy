package pla_buoy_interface;

import networkManager.NetworkManager;

/**
 * Holds settings and controls network 
 * @author Jamie Macaulay
 *
 */
public class PLAControl {
	
	/**
	 * Settings info for program
	 */
	private PLAInterfaceSettings plaInterfaceSettings;
	
	/**
	 * Manages sending commands and recieving data. 
	 */
	private NetworkManager netWorkManager; 

	public PLAControl(){
		plaInterfaceSettings=new PLAInterfaceSettings();
		netWorkManager=new NetworkManager(); 
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

}
