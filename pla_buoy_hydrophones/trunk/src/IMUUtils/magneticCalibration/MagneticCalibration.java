package IMUUtils.magneticCalibration;

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
	 * @param yMag - y magnetometer values
	 * @param zMag - z magnetometer values 
	 * @return A fit points class with calibration value calculated. The values are [center, radii, eigenvector 1, eigenvector 2, eigenvector 3]
	 */
	public FitPoints calcCalValues(double[][] magCal){
		//to  extended calibration
		FitPoints fitPoints=new FitPoints();
		fitPoints.fitEllipsoid( FitPoints.convertToThreeSpace(magCal));
		return fitPoints; 
	}



}
