package IMUUtils.MadwickAlgorithm;

import java.io.File;

import IMUUtils.openTag.ReadDSG;
import IMUUtils.openTag.ReadDSG.OTData;

/**
 * Test class for IMU.
 * @author Jamie Macaulay
 *
 */
public class MadwickTest extends MadwickAlgorithm {

	//Some test files. 
	private static String filename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/33.DSG"; 
	private static 	String pTCalFilename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/PRESSTMP.CAL";

	public static void main(String[] args) {

		//open OT file 
		ReadDSG readDSG=new ReadDSG(); 
		OTData otData=readDSG.otLoadDat(new File(filename), new File(pTCalFilename));
		
		//Madwick algorithm
		MadwickAlgorithm madwickAlgorithm=new MadwickAlgorithm();
		Quaternion[] quaternion=madwickAlgorithm.runMadWickAglorithm(otData.times, otData.magnotometer, otData.accelerometer, otData.gyroscope, null, null);
		//print out Eular angles
		for (int i=0; i<quaternion.length; i++){
			double[] euler=quaternion[i].toEulerAngles();
			System.out.println("Euler: heading: "+ Math.toDegrees(euler[0]) + " pitch: "+ Math.toDegrees(euler[1])+ " roll: "+ Math.toDegrees(euler[2])+
					"   Quaternion: "+i+": "+quaternion[i].getW() +" "+quaternion[i].getX()+ " "+quaternion[i].getY()+" "+quaternion[i].getZ()); 
		}
		
		double[] euler=quaternion[quaternion.length-1].toEulerAngles();
		System.out.println("Euler: heading: "+ Math.toDegrees(euler[0]) + " pitch: "+ Math.toDegrees(euler[1])+ " roll: "+ Math.toDegrees(euler[2])); 

	}

}
