package pla_buoy_interface;

/**
 * Holds settings and controls network 
 * @author jamie
 *
 */
public class PLAControl {
	
	PLAInterfaceSettings plaInterfaceSettings; 

	public PLAControl(){
		plaInterfaceSettings=new PLAInterfaceSettings();
		//TODO -load settings here. 
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

}
