package IMUUtils;

/**
 * Extended Magnetic Calibration. Includes utils to calibrate and an interface in JavaFX. 
 * @author Jamie Macaulay
 *
 */
public class MagneticCalibration {
	
	/**
	 * Calibrate 3 axis magnetometer data. This is an extends magnetic calibration which takes both hard iron and soft iron
	 * distortions in account. 
	 * @param xMag - x magnetometer values 
	 * @param yMag
	 * @param zMag
	 * @return a double of calibration values. The values are [center, radii, eigenvector 1, eigenvector 2, eigenvector 3]
	 */
	public double[] calcCalValues(double xMag, double[] yMag, double[] zMag){
		return null; 
	}
	
	public static void main(String[] args) {
		
	}
	
	
	

}
