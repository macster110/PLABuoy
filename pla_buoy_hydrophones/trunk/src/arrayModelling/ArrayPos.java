package arrayModelling;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import dataUnits.hArray.HArray;

/**
 * Utility class to store transformed hydrophone, sensor and the modelled streamer positions. 
 * <p>
 * In this class a list of the original positions are kept in the HArray classes whilst transfromHydrophonePos, transfromSensorPos and transfromChildArrayPos
 * represent the transformed hydrophone, sensor and reference positions of 
 * child arrays respectively. 
 * <p>
 * In this all co-ordinates are kept in the 
 * @author Jamie Macaulay
 *
 */
public class ArrayPos {
	
	/**
	 * Time for which array was modelled -1 for simulated data. 
	 */
	long time=-1; 

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
	public ArrayList<double[]> transformHydrophonePos;
	
	/**
	 * List of child array attachment points after movement transforms have been applied. 
	 */
	public ArrayList<double[]> transformChildArrayPos;
	
	
	/**
	 * List of  sensor positions after transform has been applied. 
	 */
	public ArrayList<double[]> transformSensorPos;
	
	/**
	 * Describes streamer or streamer. 
	 */
	public  ArrayList<ArrayList<Point3D>> streamerPositions; 
	
	
	/**
	 * Get transformed sensor positions. 
	 * @return list of {x,y,z} sensor positions in this arrays' own co-ordinate frame; 
	 */
	public ArrayList<double[]> getTransformSensorPos() {
		return transformSensorPos;
	}

	/**
	 * Set transformed sensor positions. 
	 * @param list of {x,y,z} sensor positions in this arrays' own co-ordinate frame; 
	 */
	public void setTransformSensorPos(ArrayList<double[]> transfromSensorPos) {
		this.transformSensorPos = transfromSensorPos;
	}
	
	/**
	 * Set transformed sensor positions. 
	 * @param list of {x,y,z} sensor positions in this arrays' own co-ordinate frame; 
	 */
	public void setTransformSensorPos(double[][] transfromSensorPos) {
		ArrayList<double[]> sensorPosList=new ArrayList<double[]>();
		for (int i=0; i<transfromSensorPos.length; i++){
			sensorPosList.add(transfromSensorPos[i]);
		}
		this.transformSensorPos = sensorPosList;
	}

	/**
	 * Get transformed hydrophone positions. 
	 * @param hydrophonePositions - list of {x,y,z} hydrophone positions in this arrays' own co-ordinate frame; 
	 */
	public ArrayList<double[]> getTransformHydrophonePos() {
		return transformHydrophonePos;
	}

	/**
	 * Set transformed hydrophone positions
	 * @param hydrophonePositions - list of {x,y,z} hydrophone positions in this arrays' own co-ordinate frame; 
	 */
	public void setTransformHydrophonePos(ArrayList<double[]> hydrophonePositions) {
		this.transformHydrophonePos = hydrophonePositions;
	}
	
	/**
	 * Set hydrophone positions
	 * @param hydrophonePositions - list of {x,y,z} hydrophone postions. 
	 */
	public void setTransformHydrophonePos(double[][] hydrophonePositions) {
		ArrayList<double[]> hydrophonePosList=new ArrayList<double[]>();
		for (int i=0; i<hydrophonePositions.length; i++){
			hydrophonePosList.add(hydrophonePositions[i]);
		}
		this.transformHydrophonePos = hydrophonePosList;
	}


	/**
	 * Get streamer positions - used to generate visualisation of array in 3D map. 
	 * @return streamer lines - could be individual lines or a series of lines which, when plotted
	 * form a continous curving streamer. Each <ArrayList<Point3D> is one continous line - multiple lines can be used
	 * to visualise more complex array setups. 
	 */
	public ArrayList<ArrayList<Point3D>> getTransformStreamerPositions() {
		return streamerPositions;
	}

	/**
	 * Set streamer positions. 
	 * @param streamerPositions  - could be individual lines or a series of lines which, when plotted
	 * form a continuous curving streamer. 
	 */
	public void setStreamerPositions( ArrayList<ArrayList<Point3D>> streamerPositions) {
		this.streamerPositions = streamerPositions;
	}
	
	/**
	 * Get transformed positions of child arrays/ 
	 * @return
	 */
	public ArrayList<double[]> getTransformChildArrayPos() {
		return transformChildArrayPos;
	}

	/**
	 * Set transformed positions of child arrays. 
	 * @param arrayPositions list of {x,y,z} in arrays own co-ordinate frame; 
	 */
	public void setTransformChildArrayPos(ArrayList<double[]> arrayPositions) {
		this.transformChildArrayPos = arrayPositions;
	}
	
	/**
	 * Set transformed positions of child arrays. 
	 * @param arrayPositions list of {x,y,z} in arrays own co-ordinate frame; 
	 */
	public void setTransformChildArrayPos(double[][] arrayPositions) {
		ArrayList<double[]> childArrayPosList=new ArrayList<double[]>();
		for (int i=0; i<arrayPositions.length; i++){
			childArrayPosList.add(arrayPositions[i]);
		}
		this.transformChildArrayPos = childArrayPosList;
	}
	
	
	/**
	 * Get the parent array. this is the array to which hydrophones belong. 
	 * @return the parent array. 
	 */
	public HArray getHArray() {
		return hydrophoneArray;
	}

	/**
	 * Set the parent array. This is the array to which hydrophones belong. 
	 * @param hydrophoneArray - the array to set as parent. 
	 */
	public void setHArray(HArray hydrophoneArray) {
		this.hydrophoneArray = hydrophoneArray;
	}
	
	/**
	 * Get all arrays which are children of the parent arrays. 
	 * @return list of parent array's children. 
	 */
	public ArrayList<HArray> getChildArrays() {
		return childArrays;
	}

	/**
	 * Set the child arrays of the parent. 
	 * @param childArrays - child arrays of the parent. 
	 */
	public void setChildArrays(ArrayList<HArray> childArrays) {
		this.childArrays = childArrays;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	@Override 
	public String toString(){
		
		String string="";
		String hydrophoneString; 
		for (int i=0; i<transformHydrophonePos.size(); i++){
			hydrophoneString=("Hydrophone channel " + hydrophoneArray.getHydrophones().get(i).channelProperty().get() + 
					" transfromHydrophonePos.get(i)[0]"+ " y: "+transformHydrophonePos.get(i)[1] +" z: "+transformHydrophonePos.get(i)[2]);
			string=string+hydrophoneString+ " ";
		}
//		string=string+"\n";
		
		String childArrayString;
		for (int i=0; i<transformChildArrayPos.size(); i++){
			childArrayString=(childArrays.get(i).nameProperty().get() + " child array x: "+transformChildArrayPos.get(i)[0]+ " y: "+transformChildArrayPos.get(i)[1]+" z: "+transformChildArrayPos.get(i)[2]);
			string=string+childArrayString+" ";
		}

//		string=string+"\n";

		return string;
	}

	

}
