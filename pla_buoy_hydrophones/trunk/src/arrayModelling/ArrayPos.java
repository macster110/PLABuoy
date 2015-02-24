package arrayModelling;

import java.util.ArrayList;

import org.fxyz.geometry.Point3D;

import dataUnits.hArray.HArray;

/**
 * Utility class to store calculated hydrophone positions and the modelled streamer. 
 * @author Jamie Macaulay
 *
 */
public class ArrayPos {
	
	/**
	 * Reference to the array to which these results belong
	 */
	private HArray hydrophoneArray; 
	
	/**
	 * Reference to child arrays for which attachment points have been calulated. 
	 */
	private ArrayList<HArray> childArrays;

	/**
	 * Hydrophone positions relative to the 0,0,0 point of the array. 
	 */
	public ArrayList<double[]> transfromHydrophonePos;
	
	/**
	 * List of child array attachment points to parent after movement transforms have been applied. 
	 */
	public ArrayList<double[]> transfromChildArrayPos;

	/**
	 * Describes streamer or streamer. 
	 */
	public  ArrayList<ArrayList<Point3D>> streamerPositions; 
	
	/**
	 * Get hydrophone positions. 
	 * @return the hydrophone positons in x,y and z;
	 */
	public ArrayList<double[]> getHydrophonePos() {
		return transfromHydrophonePos;
	}

	/**
	 * Set hydrophone positions
	 * @param hydrophonePositions - list of x,y,z hydrophone postions. 
	 */
	public void setHydrophonePositions(ArrayList<double[]> hydrophonePositions) {
		this.transfromHydrophonePos = hydrophonePositions;
	}

	/**
	 * Get streamer positions - used to generate visualisation of array in 3D map. 
	 * @return streamer lines - could be individual lines or a series of lines which, when plotted
	 * form a continous curving streamer. Each <ArrayList<Point3D> is one continous line - multiple lines can be used
	 * to visualise more complex array setups. 
	 */
	public ArrayList<ArrayList<Point3D>> getStreamerPositions() {
		return streamerPositions;
	}

	/**
	 * Set streamer positions. 
	 * @param streamerPositions  - could be individual lines or a series of lines which, when plotted
	 * form a continous curving streamer. 
	 */
	public void setStreamerPositions( ArrayList<ArrayList<Point3D>> streamerPositions) {
		this.streamerPositions = streamerPositions;
	}
	
	public ArrayList<double[]> getChildArrayPos() {
		return transfromChildArrayPos;
	}

	public void setArrayPositions(ArrayList<double[]> arrayPositions) {
		this.transfromChildArrayPos = arrayPositions;
	}
	
	public HArray getHArray() {
		return hydrophoneArray;
	}

	public void setHArray(HArray hydrophoneArray) {
		this.hydrophoneArray = hydrophoneArray;
	}
	
	public ArrayList<HArray> getChildArrays() {
		return childArrays;
	}

	public void setChildArrays(ArrayList<HArray> childArrays) {
		this.childArrays = childArrays;
	}

	

}
