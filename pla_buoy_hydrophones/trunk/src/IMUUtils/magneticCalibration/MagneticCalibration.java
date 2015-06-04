package IMUUtils.magneticCalibration;

import Jama.Matrix;

/**
 * Extended Magnetic Calibration. Includes utils to calibrate and an interface in JavaFX. 
 * @author Jamie Macaulay
 *
 */
public class MagneticCalibration {
	
	/**
	 * The current ellipsoid fit for the magnetic calibration. 
	 */
	public FitPoints fitPoints=new FitPoints();
	
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
		fitPoints.fitEllipsoid( FitPoints.convertToThreeSpace(magCal));
		return fitPoints; 
	}
	
	/**
	 * Get current calibration values. 
	 * @return the current magnetic calibration values. If no calibration has been performed the n 
	 */
	public FitPoints getCurrentCalValues() {
		return fitPoints;
	}
	
	public double[][] calibrateMagData(double[][] magnetomterData){
		return calibrateMagData(magnetomterData,  fitPoints) ;
		
	}
	
	/**
	 * Use ellipsoid fit data to calibrate raw magnetometer measurements. Based on;
	 * 	% Magnetometer Calibration Skript for Razor AHRS v1.4.2
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
	 * @param magnetomterData raw magnetometer measurements 
	 * @return calibrated magnetometer measurments,. 
	 */
	public double[][] calibrateMagData(double[][] magnetomterData, FitPoints points) {
		
		if (points.radii==null){
			System.err.println("MagneticCalibration: No ellipsoid fit has been performed or fit has not worked"); 
			return null; 
		}
		
		//create matrix
		double[][] scale={{points.radii.getEntry(2), 0. , 0},
				{0.,points.radii.getEntry(1), 0. },
				{0.,0.,points.radii.getEntry(0)} };
//		System.out.println("Entry 1: "+points.radii.getEntry(0)+" Entry 2: "+points.radii.getEntry(1)+" Entry 3: "+points.radii.getEntry(2));
		
		
		Matrix scaleM=new Matrix(scale).inverse().times(points.radii.getMinValue());
//		System.out.println("scale");
//		scaleM.print(3, 4);

		
		double[][] eigenVecs={{points.evecs2.getEntry(0), points.evecs1.getEntry(0) , -points.evecs.getEntry(0)},
				{points.evecs2.getEntry(1), points.evecs1.getEntry(1) , -points.evecs.getEntry(1)},
				{points.evecs2.getEntry(2), points.evecs1.getEntry(2) , -points.evecs.getEntry(2)} };
		
		
		Matrix map=new Matrix(eigenVecs).transpose();
		map.print(3, 4);
		
		Matrix invmap=new Matrix(eigenVecs);
		
//		System.out.println("eigenvecs");
//		invmap.print(3, 4);

		//compensation factor. 
		Matrix comp=invmap.times(scaleM).times(map); 
		
//		System.out.println("comp");
//		comp.print(3, 4);
		//this now needs multiply by all results 
		
		double[][] magnetomterDataCal=new double[magnetomterData.length][3]; 
		Matrix sM;
		Matrix result;
		for (int i=0; i<magnetomterData.length; i++){
			double[][] s= {{magnetomterData[i][0]-points.center.getEntry(0)},{magnetomterData[i][1]-points.center.getEntry(1)},{magnetomterData[i][2]-points.center.getEntry(2)}};
			sM=new Matrix(s);
			result=comp.times(sM).transpose();
			magnetomterDataCal[i][0]=result.getArray()[0][0]; 
			magnetomterDataCal[i][1]=result.getArray()[0][1]; 
			magnetomterDataCal[i][2]=result.getArray()[0][2]; 
		}

		return magnetomterDataCal;
		
	}


	
	



}
