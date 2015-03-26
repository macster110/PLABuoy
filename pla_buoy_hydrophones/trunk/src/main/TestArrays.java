package main;

import layout.ControlPane.ChangeType;
import dataUnits.Hydrophone;
import dataUnits.hArray.FlexibleVerticalArray;
import dataUnits.hArray.RigidHArray;
import dataUnits.movementSensors.OpenTagSensor;

/**
 * Some test arrays. 
 * @author Jamie Macaulay; 
 *
 */
public class TestArrays  {
	
	///TEST ARRAYS///

	public static void createTestArray1(ArrayModelControl arrayModelControl){
		Hydrophone hydrophone1=new Hydrophone(0, 0, 0); 
		hydrophone1.channelProperty().set(0);
		hydrophone1.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone2=new Hydrophone(0, 0, -20); 
		hydrophone2.channelProperty().set(1);
		hydrophone2.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone3=new Hydrophone(0, 10, -20); 
		hydrophone3.channelProperty().set(2);
		hydrophone3.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone4=new Hydrophone(0, -10, -20); 
		hydrophone4.channelProperty().set(3);
		hydrophone4.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone5=new Hydrophone(0, 20, 0); 
		hydrophone5.channelProperty().set(4);
		hydrophone5.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone6=new Hydrophone(0, -10, 0); 
		hydrophone6.channelProperty().set(5);
		hydrophone6.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone7=new Hydrophone(-10, 0, 0); 
		hydrophone7.channelProperty().set(6);
		hydrophone7.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		Hydrophone hydrophone8=new Hydrophone(10, 0, 0); 
		hydrophone8.channelProperty().set(7);
		hydrophone8.parentArrayProperty().set(arrayModelControl.getReferenceArray());

		arrayModelControl.getHydrophones().addAll(hydrophone1, hydrophone2, hydrophone3, hydrophone4,
				hydrophone5, hydrophone6, hydrophone7, hydrophone8); 

		OpenTagSensor openTag=new OpenTagSensor(); 
		openTag.parentArrayProperty().set(arrayModelControl.getReferenceArray());
		openTag.yRefPositionProperty().setValue(10);
		openTag.sensorNameProperty.setValue("Open Tag Test1");
		arrayModelControl.getSensorManager().getSensorList().add(openTag);

		arrayModelControl.notifyModelChanged(ChangeType.HYDROPHONE_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.SENSOR_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.ARRAY_CHANGED);

		arrayModelControl.updateArrayHydrophones();
		arrayModelControl.updateArraySensors();
	}

	public static void createTestArray2(ArrayModelControl arrayModelControl){

		RigidHArray subArray1=new RigidHArray(); 
		subArray1.nameProperty().setValue("Sub Array1");
		subArray1.parentHArrayProperty().setValue(arrayModelControl.getReferenceArray());
		double[] subArrayPos={0.,0.,-20.};
		subArray1.setParentAttachPoint(subArrayPos);
		arrayModelControl.getHArrayManager().getHArrayList().add(subArray1);

		Hydrophone hydrophone1=new Hydrophone(0, 0, -20); 
		hydrophone1.channelProperty().set(10);
		hydrophone1.parentArrayProperty().set(subArray1);

		arrayModelControl.getHydrophones().addAll(hydrophone1); 

		OpenTagSensor openTag=new OpenTagSensor(); 
		openTag.parentArrayProperty().set(subArray1);
		openTag.sensorNameProperty.setValue("Open Tag Test2");
		arrayModelControl.getSensorManager().getSensorList().add(openTag);


		arrayModelControl.notifyModelChanged(ChangeType.HYDROPHONE_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.SENSOR_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.ARRAY_CHANGED);

		arrayModelControl.updateArrayHydrophones();
		arrayModelControl.updateArraySensors();
	}

	public static void createTestVertArray(ArrayModelControl arrayModelControl){

		FlexibleVerticalArray subArray1=new FlexibleVerticalArray(); 
		subArray1.nameProperty().setValue("Vertical Array 1");
		subArray1.parentHArrayProperty().setValue(arrayModelControl.getReferenceArray());
		double[] subArrayPos={0.,0.,0.};
		subArray1.setParentAttachPoint(subArrayPos);
		arrayModelControl.getHArrayManager().getHArrayList().add(subArray1);


		Hydrophone hydrophone1=new Hydrophone(0, 0, 0); 
		hydrophone1.channelProperty().set(10);
		hydrophone1.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone2=new Hydrophone(0, 0, -5); 
		hydrophone2.channelProperty().set(11);
		hydrophone2.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone3=new Hydrophone(0, 10, -10); 
		hydrophone3.channelProperty().set(12);
		hydrophone3.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone4=new Hydrophone(0, -10, -15); 
		hydrophone4.channelProperty().set(13);
		hydrophone4.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone5=new Hydrophone(0, 20, -25); 
		hydrophone5.channelProperty().set(14);
		hydrophone5.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone6=new Hydrophone(0, -10, -30); 
		hydrophone6.channelProperty().set(15);
		hydrophone6.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone7=new Hydrophone(-10, 0, -40); 
		hydrophone7.channelProperty().set(16);
		hydrophone7.parentArrayProperty().set(subArray1);

		Hydrophone hydrophone8=new Hydrophone(10, 0, -45); 
		hydrophone8.channelProperty().set(17);
		hydrophone8.parentArrayProperty().set(subArray1);

		arrayModelControl.getHydrophones().addAll(hydrophone1, hydrophone2, hydrophone3, hydrophone4,
				hydrophone5, hydrophone6, hydrophone7, hydrophone8); 

		OpenTagSensor openTag=new OpenTagSensor(); 
		openTag.parentArrayProperty().set(subArray1);
		openTag.sensorNameProperty.setValue("Open Tag Test1");
		openTag.zRefPositionProperty().setValue(-10);
		openTag.pitchRefProperty().setValue(Math.toRadians(-90)); 
		arrayModelControl.getSensorManager().getSensorList().add(openTag);

		OpenTagSensor openTag2=new OpenTagSensor(); 
		openTag2.parentArrayProperty().set(subArray1);
		openTag2.sensorNameProperty.setValue("Open Tag Test2");
		openTag2.zRefPositionProperty().setValue(-20);
		openTag2.pitchRefProperty().setValue(Math.toRadians(-90)); 
		arrayModelControl.getSensorManager().getSensorList().add(openTag2);

		OpenTagSensor openTag3=new OpenTagSensor(); 
		openTag3.parentArrayProperty().set(subArray1);
		openTag3.sensorNameProperty.setValue("Open Tag Test2");
		openTag3.zRefPositionProperty().setValue(-35);
		openTag3.pitchRefProperty().setValue(Math.toRadians(-90)); 
		arrayModelControl.getSensorManager().getSensorList().add(openTag3);


		arrayModelControl.notifyModelChanged(ChangeType.HYDROPHONE_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.SENSOR_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.ARRAY_CHANGED);

		arrayModelControl.updateArrayHydrophones();
		arrayModelControl.updateArraySensors();
	}
	
	
	public static void createNERCKE2014(ArrayModelControl arrayModelControl){

		
		//create flexible vertical component
		FlexibleVerticalArray verticalArray=new FlexibleVerticalArray(); 
		verticalArray.nameProperty().setValue("Vertical Array");
		verticalArray.parentHArrayProperty().setValue(arrayModelControl.getReferenceArray());
		double[] subArrayPos={0.,0.,0.};
		verticalArray.setParentAttachPoint(subArrayPos);
		arrayModelControl.getHArrayManager().getHArrayList().add(verticalArray);

		Hydrophone hydrophone0=new Hydrophone(0, 0, 0); 
		hydrophone0.channelProperty().set(10);
		hydrophone0.parentArrayProperty().set(verticalArray);

		Hydrophone hydrophone1=new Hydrophone(0, 0, -5); 
		hydrophone1.channelProperty().set(11);
		hydrophone1.parentArrayProperty().set(verticalArray);

		Hydrophone hydrophone2=new Hydrophone(0, 10, -15); 
		hydrophone2.channelProperty().set(12);
		hydrophone2.parentArrayProperty().set(verticalArray);

		Hydrophone hydrophone3=new Hydrophone(0, -10, -30); 
		hydrophone3.channelProperty().set(13);
		hydrophone3.parentArrayProperty().set(verticalArray);

		arrayModelControl.getHydrophones().addAll(hydrophone0, hydrophone1, hydrophone2, hydrophone3);

		OpenTagSensor openTag=new OpenTagSensor(); 
		openTag.parentArrayProperty().set(verticalArray);
		openTag.sensorNameProperty.setValue("op1");
		openTag.zRefPositionProperty().setValue(-10);
		openTag.pitchRefProperty().setValue(Math.toRadians(-90)); 
		arrayModelControl.getSensorManager().getSensorList().add(openTag);

//		OpenTagSensor openTag2=new OpenTagSensor(); 
//		openTag2.parentArrayProperty().set(verticalArray);
//		openTag2.sensorNameProperty.setValue("op2");
//		openTag2.zRefPositionProperty().setValue(-19);
//		openTag2.pitchRefProperty().setValue(Math.toRadians(-90)); 
//		arrayModelControl.getSensorManager().getSensorList().add(openTag2);

		OpenTagSensor openTag3=new OpenTagSensor(); 
		openTag3.parentArrayProperty().set(verticalArray);
		openTag3.sensorNameProperty.setValue("op3");
		openTag3.zRefPositionProperty().setValue(-35);
		openTag3.pitchRefProperty().setValue(Math.toRadians(-90)); 
		arrayModelControl.getSensorManager().getSensorList().add(openTag3);
		
		//create cluster array
		RigidHArray cluster=new RigidHArray(); 
		cluster.nameProperty().setValue("Vertical Array");
		cluster.parentHArrayProperty().setValue(verticalArray);
		double[] positon={0.,0.,-20};
		cluster.setParentAttachPoint(positon);
		arrayModelControl.getHArrayManager().getHArrayList().add(cluster);
		
		Hydrophone hydrophone4=new Hydrophone(0, 1, 0); 
		hydrophone4.channelProperty().set(14);
		hydrophone4.parentArrayProperty().set(cluster);

		Hydrophone hydrophone5=new Hydrophone(0, -1, 0); 
		hydrophone5.channelProperty().set(15);
		hydrophone5.parentArrayProperty().set(cluster);
		
		Hydrophone hydrophone6=new Hydrophone(1, 0, -2); 
		hydrophone6.channelProperty().set(15);
		hydrophone6.parentArrayProperty().set(cluster);

		Hydrophone hydrophone7=new Hydrophone(-1, 0, -2); 
		hydrophone7.channelProperty().set(16);
		hydrophone7.parentArrayProperty().set(cluster);
		
		OpenTagSensor openTag4=new OpenTagSensor(); 
		openTag4.parentArrayProperty().set(cluster);
		openTag4.sensorNameProperty.setValue("op4");
		arrayModelControl.getSensorManager().getSensorList().add(openTag4);
		
		arrayModelControl.getHydrophones().addAll(hydrophone4, hydrophone5, hydrophone6, hydrophone7);


		arrayModelControl.notifyModelChanged(ChangeType.HYDROPHONE_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.SENSOR_CHANGED);
		arrayModelControl.notifyModelChanged(ChangeType.ARRAY_CHANGED);

		arrayModelControl.updateArrayHydrophones();
		arrayModelControl.updateArraySensors();
	}
	
	

}
