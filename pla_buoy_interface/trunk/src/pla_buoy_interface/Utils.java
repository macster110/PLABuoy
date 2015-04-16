package pla_buoy_interface;

/**
 * Useful classes
 * @author Jamie Macaulay
 *
 */
public class Utils {

	/**
	 * Calculate the true dB re 1uP a value of single from it's recieved unit amplitude. 
	 * @param unitLevels - amplitude in units. e.g. if 16 bit sound card max would 
	 * @param soundCardBits - the sound card bit. 
	 * @param ptopV - the peak to peak voltage in volts
	 * @param gaindB - the total gain of the system in dB
	 * @param recieverCal - the calibration value of the reciever in dB re 1V/uPa
	 * @return true recieved amplitude in dB re 1uP
	 */
	public static double calcTruedB(int unitLevel, int soundCardBits, double gaindB, double ptopV, double recieverCal){
		
		if (unitLevel==0) return -20; 
		
		//get amplitud ein units of volt
		double voltPerUnit=ptopV/Math.pow(2,soundCardBits);
		double vdB=20*Math.log10(voltPerUnit*Math.abs(unitLevel));
		vdB=vdB-gaindB;
		//convert back to linear voltage
		double vAmp=Math.pow(10,vdB/20.);
		
		//find volts per Pa
		double paPerV=1/(Math.pow(10,(recieverCal/20))*Math.pow(10, 6));
		
		double paAmp=vAmp*paPerV;
		
		//convert to dB
		return 20*Math.log10(paAmp/Math.pow(10,-6));
		
	}
	

//    public static void main(String[] args) {
//    	calcTruedB(26215, 16, 18, 20, -201);
//    }
//    
}
