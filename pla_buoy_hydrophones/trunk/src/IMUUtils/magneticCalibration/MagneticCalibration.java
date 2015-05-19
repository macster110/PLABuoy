package IMUUtils.magneticCalibration;

import Jama.Matrix;

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

	/**
	 * Use ellipsoid fit data to calibrate raw magnetometer measurements. 
	 * @param magnetomterData raw magnetometer measurements 
	 * @return calibrated magnetometer measurments,. 
	 */
	public double[][] calibrateMagData(double[][] magnetomterData, FitPoints points) {
		
		
	}

	/**
	 * Use ellipsoid fit data to calibrate a raw magnetometer measurement. TRanslated from MATLAB code (see below fro license)
	 * %******************************************************************************************
	% Magnetometer Calibration Skript for Razor AHRS v1.4.2
	% 9 Degree of Measurement Attitude and Heading Reference System
	% for Sparkfun "9DOF Razor IMU" and "9DOF Sensor Stick"
	%
	% Released under GNU GPL (General Public License) v3.0
	% Copyright (C) 2013 Peter Bartz [http://ptrbrtz.net]
	% Copyright (C) 2012 Quality & Usability Lab, Deutsche Telekom Laboratories, TU Berlin
	% Written by Peter Bartz (peter-bartz@gmx.de)
	%
	% Infos, updates, bug reports, contributions and feedback:
	%     https://github.com/ptrbrtz/razor-9dof-ahrs
	%******************************************************************************************
	 * @param magnetomterPoint - a raw magnetometer measurement. 
	 * @return calibrated magnetometer measurement. 
	 */
	private double[] calibrateMagPoint(double[] magnetomterPoint, FitPoints points){
		double[] S= {magnetomterPoint[0]-points.center.getEntry(0),magnetomterPoint[1]-points.center.getEntry(1),magnetomterPoint[1]-points.center.getEntry(1)};
		//create matrix
		double[][] scale={{points.radii.getEntry(0), 0 , 0},
				{0,points.radii.getEntry(1), 0 },
				{0,0,points.radii.getEntry(0)} };
		Matrix scaleM=new Matrix(scale);
		Matrix scaleMinv=scaleM.inverse();
		
		double[][] eigenVecs={{points.evecs.getEntry(0), points.evecs.getEntry(1) , points.evecs.getEntry(2)},
				{points.evecs1.getEntry(0), points.evecs1.getEntry(1) , points.evecs1.getEntry(2)},
				{points.evecs2.getEntry(0), points.evecs2.getEntry(1) , points.evecs2.getEntry(2)} };
		Matrix map=new Matrix(eigenVecs).transpose();
		Matrix invmap=new Matrix(eigenVecs);
		
		Matrix comp=invmap.times(scaleM).times(map);

		
	}

	
	



}
