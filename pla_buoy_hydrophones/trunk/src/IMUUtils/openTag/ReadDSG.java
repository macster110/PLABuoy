package IMUUtils.openTag;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import IMUUtils.MadwickAlgorithm.MadwickAlgorithm;

/**
 * Read DSG file from open tag. 
 * @author Jamie Macaulay
 */
public class ReadDSG {

//	//Some test files. 
//	private static String filename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/33.DSG"; 
//	private static 	String pTCalFilename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/PRESSTMP.CAL";
	
	/**
	 * In order to speed things up need to allocate big arrays and then trim after all data has been added. 
	 */
	private static int maxMemoryAlloc=5000000;

	/**
	 * Load raw binary data and time header from a DSG binary file. 
	 * @param file- the .DSG file. 
	 * @return OTBinaryData class containing header, sensor specification and raw recorded data. 
	 */
	private OTBinaryData oDSG(File file){

		DFHead dfHead=new DFHead(); 
		try {

			long timeStart=System.currentTimeMillis();

			BufferedInputStream bufferedInputStream;

			DataInputStream data_in= new DataInputStream(
					bufferedInputStream = new BufferedInputStream(
							new FileInputStream(file)));

			byte[] buffer = new byte[4];

			//read header
			dfHead.version=		readUINT32(data_in);
			dfHead.userID=		readUINT32(data_in);
			dfHead.sec=			data_in.readUnsignedByte();
			dfHead.min=			data_in.readUnsignedByte();
			dfHead.hour=		data_in.readUnsignedByte();
			dfHead.day=			data_in.readUnsignedByte();
			dfHead.mday=		data_in.readUnsignedByte();
			dfHead.month=		data_in.readUnsignedByte();
			dfHead.year=		data_in.readUnsignedByte();
			dfHead.timezone=	data_in.readByte();

//			System.out.println("DSG file Reader: version: "+ dfHead.version+ " openTagData.userID "+dfHead.userID);
//
//			System.out.println("DSG file Reader: start time: "+" year: "+ dfHead.year+ " month: "+dfHead.month+ " day: "+dfHead.day+
//					" hour: "+dfHead.hour + " min "+dfHead.min+" sec "+dfHead.sec + " timezone: "+ dfHead.timezone );

			//extra info for later versions
			if(dfHead.version>=1010) {
				dfHead.lat=		data_in.readFloat();
				dfHead.lon=		data_in.readFloat();
				dfHead.depth=	data_in.readFloat();
				dfHead.DSGCal=	data_in.readFloat();
				dfHead.hydroCal=data_in.readFloat();
				dfHead.lpFilt=	data_in.readFloat();

//				System.out.println("DSG file Reader: lat: "+dfHead.lat+ " lon: "+dfHead.lon+ " depth: "+dfHead.depth+
//						" DSGCal: "+dfHead.DSGCal + " hydroCal "+dfHead.hydroCal+" lpFilt "+dfHead.lpFilt);
			}

			//read info on sensors used. 
			boolean notDone=true; 
			ArrayList<SIDSpec> sidSpecArray=new ArrayList<SIDSpec>(); 	

			SIDSpec currnetSidSpec;
			while (notDone){
				currnetSidSpec=new SIDSpec();

				char[] SID=new char[4]; 
				SID[0]=(char) data_in.readUnsignedByte();
				SID[1]=(char) data_in.readUnsignedByte();
				SID[2]=(char) data_in.readUnsignedByte();
				SID[3]=(char) data_in.readUnsignedByte();
				currnetSidSpec.SID=SID; 

//				System.out.println(" SID: "+SID[0]+SID[1]+SID[2]+SID[3]); 

				currnetSidSpec.nBytes=		readUINT32(data_in);
				currnetSidSpec.numChan=		readUINT32(data_in);
				currnetSidSpec.storeType=	readUINT32(data_in);
				currnetSidSpec.sensorType=	readUINT32(data_in);
				currnetSidSpec.DForm=		readUINT32(data_in);
				currnetSidSpec.SPus=		readUINT32(data_in);
				currnetSidSpec.RECPTS=		readUINT32(data_in);
				currnetSidSpec.RECINT=		readUINT32(data_in);

				if (currnetSidSpec.nBytes==0){
					notDone=false;
					continue; 
				}
				sidSpecArray.add(currnetSidSpec);
			}

//			System.out.println("DSG file Reader: number of sensors used: "+sidSpecArray.size());
//			for (int i=0; i<sidSpecArray.size(); i++){
//				System.out.println("currnetSidSpec.nBytes: "+sidSpecArray.get(i).nBytes);
//				System.out.println("currnetSidSpec.numChan: "+sidSpecArray.get(i).numChan);
//				System.out.println("currnetSidSpec.storeType: "+sidSpecArray.get(i).storeType);
//				System.out.println("currnetSidSpec.sensorType: "+sidSpecArray.get(i).sensorType);
//				System.out.println("currnetSidSpec.DForm: "+sidSpecArray.get(i).DForm);
//				System.out.println("currnetSidSpec.SPus: "+sidSpecArray.get(i).SPus);
//				System.out.println("currnetSidSpec.RECPTS: "+sidSpecArray.get(i).RECPTS);
//				System.out.println("currnetSidSpec.RECINT: "+sidSpecArray.get(i).RECINT);
//			}

			//now get data
			boolean eofstat=true; 
			ArrayList<SIDRec> sidRecArray=new ArrayList<SIDRec>(); 	

			SIDRec currnetSidRec;
			int n=0; 
			while (eofstat){
				currnetSidRec=new SIDRec(); 

				currnetSidRec.nSID=		data_in.readUnsignedByte();
				currnetSidRec.chan=		data_in.readUnsignedByte();
				currnetSidRec.nbytes=	readUINT32(data_in);

				if (dfHead.version>9999){
					currnetSidRec.nbytes_2=readUINT32(data_in);
				}

				int curSID = (currnetSidRec.nSID);

				if(curSID >=0){
					int nsamples;
					if(sidSpecArray.get(curSID).DForm==2){
						nsamples=(int) ((sidSpecArray.get(curSID).nBytes)/2);  //2 because in bytes
						currnetSidRec.data=new double[nsamples]; 
						for (int i=0; i<nsamples; i++){
							currnetSidRec.data[i]=(double) readINT16(data_in);
						}
					}
					else if(sidSpecArray.get(curSID).DForm==3){
						nsamples=(int) ((sidSpecArray.get(curSID).nBytes)); 
						currnetSidRec.data=new double[nsamples]; 
						for (int i=0; i<nsamples; i++){
							currnetSidRec.data[i]=data_in.readUnsignedByte();
						}
					}
					else {

						if (data_in.available()<=0) eofstat=false; 
						n++;
						continue; 
					}
				}        
				sidRecArray.add(currnetSidRec);

				if (data_in.available()<=0){
					eofstat=false; 
				}
				n++;
			}

			long timeEnd=System.currentTimeMillis();

			//			System.out.println("Number of data measurements: "+ sidRecArray.size());
			//			for (int i=0; i<sidRecArray.get(28124).data.length; i++){
			//				System.out.println(" data index: "+ i+ " "+sidRecArray.get(28124).data[i]);
			//			}

			System.out.println("DSG file closed: time to read: " + ((timeEnd-timeStart)/1000.)+ " seconds");

			data_in.close();

			//hold all this data in a class; 
			OTBinaryData otData = new OTBinaryData(); 
			otData.dfHead=dfHead;
			otData.sidSpecArray=sidSpecArray;
			otData.sidRecArray=sidRecArray;

			return otData;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null; 
	}
	
	/**
	 * Load calibration file.
	 * @param file - the PTMP calibration file
	 */
	private PTMPCalData oPTMPCal(File file){
		
		if (file==null) return null;
		
		PTMPCalData pTMPCal=new PTMPCalData(); 
		
		try {
			BufferedInputStream bufferedInputStream;

			DataInputStream data_in= new DataInputStream(
					bufferedInputStream = new BufferedInputStream(
							new FileInputStream(file)));

			pTMPCal.PSENS=readUINT16(data_in);
			pTMPCal.POFF=readUINT16(data_in);
			pTMPCal.TCSENS=readUINT16(data_in);
			pTMPCal.TCOFF=readUINT16(data_in);
			pTMPCal.TREF=readUINT16(data_in);
			pTMPCal.TEMPSENS=readUINT16(data_in);
	
		}
		catch (Exception e){
			e.printStackTrace();
			return null; 
		}
		return pTMPCal; 
	}
	
	/**
	 * Take raw PTMP sensor measurements and calibrate using calibration values form calibration file. Really this has to be used for PTMP data, otherwise
	 * it makes no sense. 
	 * @param ptmp - data from pressure and temperature sensors. 
	 * @param ptmpCalData - clibration data from PTMP file. 
	 */
	private OTData calibratePTMPData(PTMP ptmp, PTMPCalData ptmpCalData){
		//allocate arrays 
		double[] pressure=new double[maxMemoryAlloc];  
		double[] temp=new double[maxMemoryAlloc]; 
		long[] timePT=new long[maxMemoryAlloc]; 
		//iterate through pressure and temperature data
		int count=0;
		for (int n=0; n<ptmp.data.length; n=n+6){
			//next bit copied direct from MATLAB code. - i'm sure this could be neater. 
			
			//do some programming magic to pressure number
			Long p2bits = (Long.parseUnsignedLong(Long.toString((long) ptmp.data[n])) << 16) | (Long.parseUnsignedLong(Long.toString((long) ptmp.data[n+1])) << 8);
			double pressraw=p2bits | Long.parseUnsignedLong(Long.toString((long) ptmp.data[n+2]));
			
			//do some programming magic to temperature number
			Long t2bits = (Long.parseUnsignedLong(Long.toString((long) ptmp.data[n+3])) << 16) | (Long.parseUnsignedLong(Long.toString((long) ptmp.data[n+4])) << 8);
			double tempraw=t2bits | Long.parseUnsignedLong(Long.toString((long) ptmp.data[n+5]));
			
			//calibrate temperature. 
			double dT=tempraw-ptmpCalData.TREF*256;
			temp[count]= (2000+dT*ptmpCalData.TEMPSENS/8388608)/100;  //celsius 
		
			
			//calibrate pressure (depends on temperature)
			double OFF=ptmpCalData.POFF*65536+(ptmpCalData.TCOFF*dT)/128; 
			double SENS=ptmpCalData.PSENS*32768+(dT*ptmpCalData.TCSENS)/256;
			pressure[count]=(pressraw*SENS/2097152-OFF)/81920; // mbar (i.e. a value of 1 = 1 mbar = ~1 cm depth resolution) 
			
			count++; 
		}
		
		//trim arrays, 
		pressure=Arrays.copyOf(pressure, count);
		temp=Arrays.copyOf(temp, count);
		
		for (int i=0; i<count; i++){
			timePT[i]=(long) (i/(1000/(double) ptmp.SPus));
		} 
		
		//add to OTData class. 
		OTData otData=new OTData(); 
		otData.timesPT=timePT;
		otData.pressure=pressure;
		otData.temperature=temp;

		return otData; 
	}


	/**
	 * Get data from binary file and convert to readable format. Packages data in a OTData class. 
	 * @param file - the .DSG file. 
	 * @return OTData - open tag data containing accelerometer, gyroscope and magnetometer data and, if a cal file is present and tag has sensors, also pressure and temperature data. 
	 */
	public OTData otLoadDat(File file, File calFile){

		//open file and load binary data. 
		OTBinaryData otBinaryDat = oDSG(file);

		OTData otData=new OTData(); //holds IMU and pressure/temp data

		if (otBinaryDat==null){
			System.err.println("DSG otLoadData: Error: File could not be loaded"); 
			return null; 
		}

		double srate=1000000/(otBinaryDat.sidSpecArray.get(0).SPus);
		double accelCal=16/4096.;  //16 g/4096 (13 bit ADC)
		double gyroCal=500/32768.;  // 500 degrees per second (16-bit ADC)
		double magCal=1/1090.;  //1090 LSB/Gauss

		/**
		 * Going to have lots of copying arrays which slows things down massively. So, create massive arrays which
		 * hog memory instead. 
		 */
		double[] dsgdata0=null; int n0=0; 
		double[] dsgdata1=null; int n1=0; 
		double[] dsgdata2=null; int n2=0;
		double[] dsgdata3=null; int n3=0;

		//iterate through recorded and create long list of different data types. 
		for (int i=0; i<otBinaryDat.sidRecArray.size(); i++){ //TODO- can change amount of data loaded here. 
			//otBinaryDat.sidRecArray.size();
			if (i%5000==0) System.out.println("Loading data: "+i+ " of " +otBinaryDat.sidRecArray.size());

			int cur_sid =otBinaryDat.sidRecArray.get(i).nSID;

			if (cur_sid==0){
				//add to end of list. 
				if (dsgdata0==null) dsgdata0=new double[maxMemoryAlloc];
				addData(dsgdata0, otBinaryDat.sidRecArray.get(i).data, n0);
				n0+=otBinaryDat.sidRecArray.get(i).data.length;
			}

			if (cur_sid==1){
				if (dsgdata1==null) dsgdata1=new double[maxMemoryAlloc];
				addData(dsgdata1, otBinaryDat.sidRecArray.get(i).data, n1);
				n1+=otBinaryDat.sidRecArray.get(i).data.length;
			}
			if (cur_sid==2){
				if (dsgdata2==null) dsgdata2=new double[maxMemoryAlloc];
				addData(dsgdata2, otBinaryDat.sidRecArray.get(i).data, n2);
				n2+=otBinaryDat.sidRecArray.get(i).data.length;
			}		
			if (cur_sid==3){
				if (dsgdata3==null) dsgdata3=new double[maxMemoryAlloc];
				addData(dsgdata3, otBinaryDat.sidRecArray.get(i).data, n3);
				n3+=otBinaryDat.sidRecArray.get(i).data.length;
			}

		}
		
		//now trim the array back top save memory and make life easy
		if (dsgdata0!=null) dsgdata0=Arrays.copyOf(dsgdata0, n0);
		if (dsgdata1!=null) dsgdata1=Arrays.copyOf(dsgdata1, n1);
		if (dsgdata2!=null) dsgdata2=Arrays.copyOf(dsgdata2, n2);
		if (dsgdata3!=null) dsgdata3=Arrays.copyOf(dsgdata3, n3);

		//holds temporary data on accelerometer, gyroscope and magnetometer in format ready to be converted to human readable form. 
		INER iner=new INER(); 
		//holds temporary pressure and temperature data
		PTMP ptmp=new PTMP(); 

		long[] imuTime;
		long[] times_IMU;
		double[][] accelerometer=null; // 3 axis accelerometer data
		double[][] magnetometer=null; //3 axis magnetometer
		double[][] gyroscope=null; //3 axis gyroscope
		
		long[] pTTime;
		double[] pressure;
		double[] temperature; 

		//Loop through all SIDs and get data. 
		for (int j=0; j<otBinaryDat.sidSpecArray.size() ; j++){

				
			if (String.valueOf(otBinaryDat.sidSpecArray.get(j).SID).equals("HYD1")){
				//nothing here yet
				double[] A0; 
				if(j==0) A0=dsgdata0;  
				if(j==1) A0=dsgdata1;
				if(j==2) A0=dsgdata2;
				if(j==3) A0=dsgdata3;
			}
						
			//get accelerometer, magnometer and gyroscope data.
			if (String.valueOf(otBinaryDat.sidSpecArray.get(j).SID).equals("INER")){
				
				System.out.println("Parsing IMU data");

				iner.nChan=(int) otBinaryDat.sidSpecArray.get(j).numChan;
				iner.sensorType=otBinaryDat.sidSpecArray.get(j).sensorType;

				if(j==0) iner.data=dsgdata0; iner.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==1) iner.data=dsgdata1; iner.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==2) iner.data=dsgdata2; iner.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==3) iner.data=dsgdata3; iner.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				
//				System.out.println("Print data: original ");
//				for (int i=0; i<iner.data.length; i++ ){
//					System.out.println(iner.data[i]);
//				}
				
				int N=0;

				//now we need to add calibration values to all current data. 
				int k=-1; 
				//accelerometer - 3 axis
				if ((iner.sensorType & 32) == 32){
					accelerometer=new double[iner.data.length/iner.nChan][3];
					System.out.println("Calibrating accelerometer data");
					for (k=0; k<3; k++){
						int n=k; 
						N=0; 
						while (n<iner.data.length){
							accelerometer[N][k]=iner.data[n]*accelCal;
							iner.data[n]=iner.data[n]*accelCal;
							n=n+iner.nChan;
							N++;
						}
					}
					k=3; 
				}
				
				int m=k; 
				//magnetometer - 3 axis
				if ((iner.sensorType & 16) == 16){
					System.out.println("Calibrating magnetometer data");
					magnetometer=new double[iner.data.length/iner.nChan][3];
					for (m=k; m<k+3; m++){
						int n=m; 
						N=0;
						while (n<iner.data.length){
							magnetometer[N][m-3]=iner.data[n]*magCal;
							iner.data[n]=iner.data[n]*magCal;
							n=n+iner.nChan; 
							N++;
						}
					}
					m=k+3;
				}
				
				//gyroscope - 3 axis
				if ((iner.sensorType & 8) == 8){
					System.out.println("Calibrating gyroscope data");
					gyroscope=new double[iner.data.length/iner.nChan][3];
					for (int r=m; r<m+3; r++){
						int n=r; 
						N=0;
						while (n<iner.data.length){
							gyroscope[N][r-6]=Math.toRadians(iner.data[n]*gyroCal);
							iner.data[n]=iner.data[n]*gyroCal;
							n=n+iner.nChan; 
							N++;
						}
					}
				}
				
				//create an array of times for data. Remember all these are sampled at same rate 
				//in open tag.
				times_IMU=new long[N]; 
				for (int i=0; i<N; i++){
					times_IMU[i]=(long) (i/(1000/(double) iner.SPus));
				}
				
				//now add all data to OTDATA class.
				otData.times=times_IMU; 
				otData.accelerometer=accelerometer; 
				otData.magnotometer=magnetometer; 
				otData.gyroscope=gyroscope; 

			}
		
											
//			//print out data
//			System.out.println("Print data: ");
//			for (int i=0; i<iner.data.length; i++ ){
//				System.out.println(iner.data[i]);
//			}
				
//			System.out.println("Acceleromter data");
//			for (int i=0; i<gyroscope.length; i++ ){
//				System.out.println(accelerometer[i][0]+ " "+accelerometer[i][1]+" "+accelerometer[i][2]);
//			}
			
//			System.out.println("Gyroscope data");
//			for (int i=0; i<gyroscope.length; i++ ){
//				System.out.println(gyroscope[i][0]+ " "+gyroscope[i][1]+" "+gyroscope[i][2]);
//			}

			//load up pressure and temperature data. 
			if (String.valueOf(otBinaryDat.sidSpecArray.get(j).SID).equals("PTMP")){
				ptmp.nChan=(int) otBinaryDat.sidSpecArray.get(j).numChan;
				if(j==0) ptmp.data=dsgdata0; ptmp.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==1) ptmp.data=dsgdata1; ptmp.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==2) ptmp.data=dsgdata2; ptmp.SPus=otBinaryDat.sidSpecArray.get(j).SPus;
				if(j==3) iner.data=dsgdata3; ptmp.SPus=otBinaryDat.sidSpecArray.get(j).SPus; //TODO- should that really be iner or bug in MALTAB code? 
				
//				for (int i=0; i<ptmp.data.length; i++){
//					System.out.println("PTMP val: "+ i + ":  "+((int)dsgdata1[i]) + " j "+j);
//				}
				
				//try and extract pressure and temperature data. Requires a calibration file. 
				OTData ptOTData;
				if (calFile!=null){
					PTMPCalData ptmpCalData= oPTMPCal(calFile);
					ptOTData =calibratePTMPData( ptmp, ptmpCalData);
					//add to main OTData class. 
					otData.timesPT=ptOTData.timesPT;
					otData.pressure=ptOTData.pressure;
					otData.temperature=ptOTData.temperature;
					
//					for (int i=0; i<otData.pressure.length; i++){
//						System.out.println("PTMP val: time: "+ otData.timesPT[i] + " pressure: "+otData.pressure[i] + " temperature "+otData.temperature[i]);
//					}

				}
				else{ 
					System.err.println("Calibration file could not be loaded. Temperature and pressure data has been ignored"); 
				}
		
			}
			
		}
		
	
		return otData;
	}

	
	/**
	 * Add data from one array to another (usually larger) array.
	 * @param array - array to add data to. 
	 * @param data - data to add to array
	 * @param start - index in array from which to add data
	 */
	private void addData(double[] array, double[] data, int start){
		for (int i=start; i<start+data.length; i++){
			array[i]=data[i-start]; 
		}
	}

	/**
	 * Combine two arrays. 
	 * @param a - array 1
	 * @param b - array 2
	 * @return new array
	 */
	public static double[][] concat(double[][] a, double[][] b){
        int length = a.length + b.length;
        double[][] result = new double[length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	/**
	 * Copy array from int to double. 
	 * @param source 
	 * @return new array of doubles.
	 */
	public static double[] copyFromIntArray(int[] source) {
	    double[] dest = new double[source.length];
	    for(int i=0; i<source.length; i++) {
	        dest[i] = source[i];
	    }
	    return dest;
	}

	/**
	 * Read an an unsigned 32 int with converting from little to big endian
	 * @param data_in - data uinput stream 
	 * @return long representing uint32.
	 * @throws IOException 
	 */
	public static long readUINT32(DataInputStream data_in) throws IOException{
		return swap(data_in.readInt()) & (~0L);
	}

	/**
	 * Read a signed short converting from little to big endian. 
	 * @param data_in - data input stream
	 * @return integer representing int16
	 * @throws IOException
	 */
	public static int readINT16(DataInputStream data_in) throws IOException{
		return swap(data_in.readShort());
	}
	
	/**
	 * Read an unsigned short converting from little to big endian. 
	 * @param data_in - data input stream
	 * @return integer representing int16
	 * @throws IOException
	 */
	public static int readUINT16(DataInputStream data_in) throws IOException{
		return swap(data_in.readShort()) & 0xFFFF;
	}


	/**
	 * Byte swap a single short value.
	 * @author <a href="mailto:jacob.dreyer@geosoft.no">Jacob Dreyer</a>
	 * @param value  Value to byte swap.
	 * @return Byte swapped representation.
	 */
	public static short swap (short value)
	{
		int b1 = value & 0xff;
		int b2 = (value >> 8) & 0xff;

		return (short) (b1 << 8 | b2 << 0);
	}


	/**
	 * Byte swap a single int value.
	 * @author <a href="mailto:jacob.dreyer@geosoft.no">Jacob Dreyer</a>
	 * @param value  Value to byte swap.
	 * @return Byte swapped representation.
	 */
	public static int swap (int value)
	{
		int b1 = (value >>  0) & 0xff;
		int b2 = (value >>  8) & 0xff;
		int b3 = (value >> 16) & 0xff;
		int b4 = (value >> 24) & 0xff;

		return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
	}


	/**
	 * Byte swap a single long value.
	 * @author <a href="mailto:jacob.dreyer@geosoft.no">Jacob Dreyer</a>
	 * @param value  Value to byte swap.
	 * @return Byte swapped representation.
	 */
	public static long swap (long value)
	{
		long b1 = (value >>  0) & 0xff;
		long b2 = (value >>  8) & 0xff;
		long b3 = (value >> 16) & 0xff;
		long b4 = (value >> 24) & 0xff;
		long b5 = (value >> 32) & 0xff;
		long b6 = (value >> 40) & 0xff;
		long b7 = (value >> 48) & 0xff;
		long b8 = (value >> 56) & 0xff;

		return b1 << 56 | b2 << 48 | b3 << 40 | b4 << 32 |
				b5 << 24 | b6 << 16 | b7 <<  8 | b8 <<  0;
	}


	/**
	 * Holds human readable open tag data.
	 * @author Jamie Macaulay
	 *
	 */
	public class OTData {
		
		/**
		 * The start time of the file in millis 
		 */
		public long startTime; 
		
		/**
		 * Milliseconds of measurements, measured from start time for IMU sensors
		 */
		public long[] times;
		
		
		/**
		 * Accelerometer data in g (units of gravitational field strength)
		 */
		public double[][] accelerometer; 

		/**
		 * Gyroscope data in RADIANS/s
		 */
		public double[][] gyroscope; 
		
		/**
		 * Magnetometer data in Flux (G)
		 */
		public double[][] magnotometer; 
		

		/**
		 * Milliseconds of measurments, measured from start time for pressure and temperature sensors 
		 */
		public long[] timesPT;
		
		/**
		 * Pressure data in millibar
		 */
		double[] pressure;
		
		/**
		 * Temperature data in celsius
		 */
		public double[] temperature;



	}  
	
	/**
	 * Holds calibration data for PTMP instruments. 
	 * @author Jamie Macaulay
	 *
	 */
	private class PTMPCalData  {
		
		int PSENS;
		int POFF;
		int TCSENS;
		int TCOFF;
		int TREF;
		int TEMPSENS;
	}
	
	/**
	 * Holds data when extracting acceleromter, magnometer and gyro data.
	 * @author Jamie Macaulay
	 *
	 */
	private class INER {

		int nChan; 

		long sensorType; 

		double[] data; 

		long SPus; 

	}
	
	/**
	 * Holds data when extracting pressure and temperature data
	 * @author Jamie Macaulay
	 *
	 */
	private class PTMP {

		int nChan; 

		long sensorType; 

		double[] data; 

		long SPus; 

	}

	/**
	 * Holds data loaded from binary file. 
	 * @author Jamie Macaulay
	 *
	 */
	private class OTBinaryData{

		DFHead dfHead;

		ArrayList<SIDSpec> sidSpecArray;

		ArrayList<SIDRec> sidRecArray;	

	}

	/**
	 * Holds specification data for a single sensor- e.g. accelerometer. 
	 * @author Jamie Macaulay 
	 *
	 */
	private class SIDSpec {

		public char[] SID;

		public long  nBytes; 

		public long numChan; 

		public long storeType; 

		public long sensorType;

		public long DForm; 

		public long SPus; 

		public long RECPTS; 

		public long RECINT; 

	};

	/**
	 * Holds data collected by OpenTag.
	 * @author Jamie Macaulay
	 *
	 */
	private class SIDRec {

		public int nSID;

		public long  chan; 

		public long nbytes; 

		//version >9999
		public long nbytes_2; 

		public double[] data; 

	};

	/**
	 * Holds header data from OpenTag DSG  file. 
	 * @author Jamie Macaulay 
	 *
	 */
	private class DFHead {

		/**Header**/
		public long version; 

		public long userID;

		public int sec;

		public int min;

		public int hour;

		public int day;

		public int mday;

		public int month;

		public int year; 

		public int timezone; 

		/**Header OpenTag2- Can be empty**/
		public float lat; 

		public float lon;

		public float depth; 

		public float DSGCal; 

		public float hydroCal; 

		public float lpFilt;

		/** Spec data for each sensor**/
		public ArrayList<SIDSpec> sidSpec=new ArrayList<SIDSpec>(); 

	}

//	public static void main(String[] args) {
////		ReadDSG readDSG=new ReadDSG(); 
////		File file =new File(filename);
////		File fileCal =new File(pTCalFilename);
////
////		readDSG.otLoadDat(file, fileCal);
//		
////		String number=Long.toString((long) 345); 
////		String number2=Long.toString((long) 3); 
////		Long l1 = (Long.parseUnsignedLong(number) << 16) | (Long.parseUnsignedLong(number2) << 8);
////		System.out.println("Long: "+l1);
//		
//			float hello=MadwickAlgorithm.invSqrt(0.15625f);
//			System.out.println(" Fast inv square root: "+hello);
//		
//	}

}