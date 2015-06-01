package main;

/**
 * Holds settings. 
 * @author Jamie Macaulay
 *
 */
public class ArrayManagerSettings {

	/**
	 * When to start batch processing data from
	 */
	public long startBatch=0;
	
	/**
	 * When to end batch processing of data. 
	 */
	public long endBatch=24*3600000; //1 day after 0 time
	
	/**
	 * The the time bin for which to process data. e.g. if 0.25 seconds then the position of hydrophones 
	 * will be calculated every 0.25 seconds. timeBin is in millis. 
	 */
	public long timeBin=250; 
	
}
