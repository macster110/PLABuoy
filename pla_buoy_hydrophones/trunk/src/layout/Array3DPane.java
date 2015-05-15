package layout;

import java.util.ArrayList;
import org.fxyz.geometry.Point3D;
import dataUnits.movementSensors.MovementSensor;
import arrayModelling.ArrayPos;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Create a 3D visualisation of the array.
 * <p>
 * Co-rodinate systems:
 * <p><p>
 * Because the pla_buoy_hydrophones is a program designed to integaret with PAMGUARD we use the PAMGUARD co-rdinate system. This is;
 * <p>
 * x point right
 * <p>
 * y points north or into the screen
 * <p>
 * z is height and points up
 * <p><p>
 * This is different from the JavAFX 3D system in which
 * <p>
 * x points right
 * <p>
 * y points down
 * <p>
 * z points into the screen
 * <p>
 * Thus the source code for this class is a little bit more complex. By convention the co-ordinate system is only changed for display purposes and remains 
 * in PAMGUARD format throughout the rest of code. 
 * @author Jamie Macaulay
 *
 */
public class Array3DPane extends BorderPane implements  ControlPane{
	
	private double scaleFactor=20; 
	private double axisSize=10*scaleFactor; 

	//keep track of mouse positions
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
   
    /**
     * This is the group which rotates 
     */
	Group root3D;
	
	/**
	 * Group which holds array shapes. 
	 */
	Group arrayGroup; 
	
	/**
	 * Group which holds axis and other non changing bits. 
	 */
	Group axisGroup; 

	/**
	 * The camera transforms 
	 */
	private Rotate rotateY;
	private Rotate rotateX;
	private Translate translate;
	
	/**
	 * Reference to to the mainm view. 
	 */
	private ArrayModelView mainView; 

	public Array3DPane(ArrayModelView mainView){
		this.mainView=mainView;
		    
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(15000);
        camera.setNearClip(0.1);
        camera.setDepthTest(DepthTest.ENABLE);
        camera.getTransforms().addAll (
                rotateY=new Rotate(-45, Rotate.Y_AXIS),
                rotateX=new Rotate(-45, Rotate.X_AXIS),
                translate=new Translate(0, 200, -2000));
        
        //create main 3D group 
		root3D=new Group();
		arrayGroup=buildAxes(axisSize); //create axis group
		axisGroup=new Group();
		
		root3D.getChildren().add(arrayGroup);
		root3D.getChildren().add(axisGroup);
		

        //Use a SubScene to mix 3D and 2D stuff.        
		//note- make sure depth buffer in sub scene is enabled. 
        SubScene subScene = new SubScene(root3D, 500,500, true, SceneAntialiasing.BALANCED);
        subScene.widthProperty().bind(this.widthProperty());
        subScene.heightProperty().bind(this.heightProperty());
        subScene.setDepthTest(DepthTest.ENABLE);

        subScene.setFill(Color.WHITE);
        subScene.setCamera(camera);

        //handle mouse events for sub scene
        handleMouse(subScene); 

        //create new group to add sub scene to 
        Group group = new Group();
        group.getChildren().add(subScene);
        
        //add group to window.
        this.getChildren().add(group);
        this.setDepthTest(DepthTest.ENABLE);
        
	}
	
	/**
	 * Create a 3D axis with default colours set. 
	 * @param- size of the axis
	 */
	public Group buildAxes(double axisSize) {
		 return buildAxes( axisSize,Color.DARKRED, Color.RED,
				 Color.DARKGREEN, Color.GREEN,
				 Color.DARKBLUE, Color.BLUE,
				 Color.BLACK); 
	}
		
	
	/**
	 * Create a 3D axis. 
	 * @param- size of the axis
	 */
	public static Group buildAxes(double axisSize, Color xAxisDiffuse, Color xAxisSpectacular,
			Color yAxisDiffuse, Color yAxisSpectacular,
			Color zAxisDiffuse, Color zAxisSpectacular,
			Color textColour) {
		Group axisGroup=new Group(); 
        double length = 2d*axisSize;
        double width = axisSize/100d;
        double radius = 2d*axisSize/100d;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(xAxisDiffuse);
        redMaterial.setSpecularColor(xAxisSpectacular);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(yAxisDiffuse);
        greenMaterial.setSpecularColor( yAxisSpectacular);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(zAxisDiffuse);
        blueMaterial.setSpecularColor(zAxisSpectacular);
        
        Text xText=new Text("x"); 
        xText.setStyle("-fx-font: 20px Tahoma;");
        xText.setFill(textColour);
        Text yText=new Text("z"); 
        yText.setStyle("-fx-font: 20px Tahoma; ");
        yText.setFill(textColour);
        Text zText=new Text("y"); 
        zText.setStyle("-fx-font: 20px Tahoma; ");
        zText.setFill(textColour);

        xText.setTranslateX(axisSize+5);
        yText.setTranslateY(-(axisSize+5));
        zText.setTranslateZ(axisSize+5);

        Sphere xSphere = new Sphere(radius);
        Sphere ySphere = new Sphere(radius);
        Sphere zSphere = new Sphere(radius);
        xSphere.setMaterial(redMaterial);
        ySphere.setMaterial(greenMaterial);
        zSphere.setMaterial(blueMaterial);
         
        xSphere.setTranslateX(axisSize);
        ySphere.setTranslateY(-axisSize);
        zSphere.setTranslateZ(axisSize);
         
        Box xAxis = new Box(length, width, width);
        Box yAxis = new Box(width, length, width);
        Box zAxis = new Box(width, width, length);
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
         
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.getChildren().addAll(xText, yText, zText);
        axisGroup.getChildren().addAll(xSphere, ySphere, zSphere);
        return axisGroup;
    }
	
	
	 private void handleMouse(SubScene scene) {
	    	
	        scene.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override public void handle(MouseEvent me) {
	                mousePosX = me.getSceneX();
	                mousePosY = me.getSceneY();
	                mouseOldX = me.getSceneX();
	                mouseOldY = me.getSceneY();
	            }
	        });
	        
	        scene.setOnScroll(new EventHandler<ScrollEvent>() {
	            @Override public void handle(ScrollEvent event) {
	            	System.out.println("Scroll Event: "+event.getDeltaX() + " "+event.getDeltaY()); 
                	translate.setZ(translate.getZ()+  event.getDeltaY() *0.001*translate.getZ());   // + 
	            }
	        });
	        
	        
	        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
	            public void handle(MouseEvent me) {
	                mouseOldX = mousePosX;
	                mouseOldY = mousePosY;
	                mousePosX = me.getSceneX();
	                mousePosY = me.getSceneY();
	                mouseDeltaX = (mousePosX - mouseOldX);
	                mouseDeltaY = (mousePosY - mouseOldY);

	                double modifier = 1.0;
	                double modifierFactor = 0.1;

	                if (me.isControlDown()) {
	                    modifier = 0.1;
	                }
	                if (me.isShiftDown()) {
	                    modifier = 10.0;
	                }
	                if (me.isPrimaryButtonDown()) {
	                	rotateY.setAngle(rotateY.getAngle() + mouseDeltaX * modifierFactor * modifier * 2.0);  // +
	                	rotateX.setAngle(rotateX.getAngle() - mouseDeltaY * modifierFactor * modifier * 2.0);  // -
	                }
	                if (me.isSecondaryButtonDown()) {
	                	translate.setX(translate.getX() -mouseDeltaX * modifierFactor * modifier * 5);
	                	translate.setY(translate.getY() - mouseDeltaY * modifierFactor * modifier * 5);   // +
	                }
	              
	               
	            }
	        });
	  }
	
	@Override
	public void notifyChange(ChangeType type) {
	
			switch (type){
			case ARRAY_CHANGED:
				System.out.println("SensorSimPane: ARRAY_CHANGED");
				drawArrays(this.mainView.getArrayModelControl().getArrayModelManager().getArrayPos()); 
				break;
			case HYDROPHONE_CHANGED:
				System.out.println("SensorSimPane: HYDROPHONE_CHANGED");
				drawArrays(this.mainView.getArrayModelControl().getArrayModelManager().getArrayPos()); 
				break;
			case SENSOR_CHANGED:
				System.out.println("SensorSimPane: SENSOR_CHANGED");
				drawArrays(this.mainView.getArrayModelControl().getArrayModelManager().getArrayPos()); 
				break;
			case NEW_ARRAY_POS_CALCULATED:
				drawArrays(this.mainView.getArrayModelControl().getArrayModelManager().getArrayPos()); 
				break;
				
			default:
				break;
			}
	}
	
	/**
	 * Draw the entire array 
	 * @param pos - hydrophone and streamer positions in the same co-ordinate frame as the reference frame. 
	 */
	public void drawArrays(ArrayList<ArrayList<ArrayPos>> pos){
		
		arrayGroup.getChildren().removeAll(arrayGroup.getChildren()); 

		if (pos==null){
			System.err.println("Array3DPane: Hydrophone positions are null");
			return; 
		}
				
		for (int i=0; i< pos.size(); i++){
			for (int j=0; j<pos.get(i).size(); j++){
				drawArray(pos.get(i).get(j)); 
			}
		}
		
		System.out.println("Draw 3D hydrophone array");
	}
	
	/**
	 * Draw an array.
	 * @param arrayPos - hydrophone and streamer positions in the same co-ordinate frame as the reference frame. 
	 */
	private void drawArray(ArrayPos arrayPos){
		
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.RED.brighter());
        
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.LIMEGREEN);
        greenMaterial.setSpecularColor(Color.LIMEGREEN.brighter());
		
			//draw hydrophones
			Sphere sphere;
			for (int i=0; i<arrayPos.getTransformHydrophonePos().size(); i++){
				 sphere=new Sphere(0.5*scaleFactor);
				 sphere.setTranslateX(arrayPos.getTransformHydrophonePos().get(i)[0]*scaleFactor);
				 sphere.setTranslateY(-arrayPos.getTransformHydrophonePos().get(i)[2]*scaleFactor);
				 sphere.setTranslateZ(arrayPos.getTransformHydrophonePos().get(i)[1]*scaleFactor);
				 sphere.setMaterial(redMaterial);
				 arrayGroup.getChildren().add(sphere);
			
			}
			
			//draw sensors
			Box box;
			for (int i=0; i<arrayPos.getTransformSensorPos().size(); i++){
				
				box=new Box(0.5*scaleFactor, 0.5*scaleFactor, 1*scaleFactor);
				box.setTranslateX(arrayPos.getTransformSensorPos().get(i)[0]*scaleFactor);
				box.setTranslateY(-arrayPos.getTransformSensorPos().get(i)[2]*scaleFactor);
				box.setTranslateZ(arrayPos.getTransformSensorPos().get(i)[1]*scaleFactor);
				
				//if possible try and transform angle of the box. (this is a bit weird)
				MovementSensor movementSensor=arrayPos.getHArray().getMovementSensors().get(i);
				
				//remember to compensate for reference orientation. 
				Double[] sensorData=new Double[3];
				sensorData[0]=movementSensor.getOrientationData(arrayPos.getTime())[0];//-movementSensor.getReferenceOrientation()[0]; 
				sensorData[1]=movementSensor.getOrientationData(arrayPos.getTime())[1];//-movementSensor.getReferenceOrientation()[1]; 
				sensorData[2]=movementSensor.getOrientationData(arrayPos.getTime())[2];//-movementSensor.getReferenceOrientation()[2]; 
				
				//System.out.println("Box heading: "+Math.toDegrees(sensorData[0])+ " pitch "+Math.toDegrees(sensorData[1])+ " roll "+  Math.toDegrees(sensorData[2])); 
				Rotate rotHeading=	new Rotate(movementSensor.getHasSensors()[0] ? Math.toDegrees(sensorData[0]): 0, 0, 0, 0, Rotate.Y_AXIS);
				//pitch- we rotate about y axis
				Rotate rotPitch=	new Rotate(movementSensor.getHasSensors()[1] ? Math.toDegrees(sensorData[1]): 0, 0, 0, 0, Rotate.X_AXIS);
				//roll - we rotate about x axis
				Rotate rotRoll=		new Rotate(movementSensor.getHasSensors()[2] ? -Math.toDegrees(sensorData[2]):0,0, 0, 0, Rotate.Z_AXIS);
				
				//transform box
				box.getTransforms().addAll( rotRoll, rotHeading, rotPitch);
			
				box.setMaterial(greenMaterial);
			    arrayGroup.getChildren().add(box);
			}
			
			//draw streamer
		    PolyLine3D polyLine3D;
		    ArrayList<Point3D> streamerPoints;
		    
			for (int i=0; i<arrayPos.getTransformStreamerPositions().size(); i++){
				if (arrayPos.getTransformStreamerPositions().get(i)==null) return; 
				streamerPoints=new ArrayList<Point3D>(); 
				 for (int j=0; j<arrayPos.getTransformStreamerPositions().get(i).size(); j++){
					 
					 //TODO- use cylinder for line
//					 Cylinder cylinder=createConnection(arrayPos.getTransformStreamerPositions().get(i).get(j).multiply(scaleFactor),  arrayPos.getTransformStreamerPositions().get(i).get(j+1).multiply(scaleFactor),0.2*scaleFactor); 
//					 arrayGroup.getChildren().add(cylinder);

					 //need to convert to fxyz 3D point - stupid but no work around. 
					 Point3D newPoint=new Point3D((float) (arrayPos.getTransformStreamerPositions().get(i).get(j).getX()*scaleFactor),
							(float) (-arrayPos.getTransformStreamerPositions().get(i).get(j).getZ()*scaleFactor), (float) (arrayPos.getTransformStreamerPositions().get(i).get(j).getY()*scaleFactor));
					 streamerPoints.add(newPoint);
				 }
				 polyLine3D=new PolyLine3D(streamerPoints, 4, Color.BLUE); 
				 arrayGroup.getChildren().add(polyLine3D);
			 }

	}
	
	
	

//	/**
//	 * Create test shapes for the 3D scene
//	 */
//	private void addTestShapes(){
//		final PhongMaterial redMaterial = new PhongMaterial();
//	       redMaterial.setSpecularColor(Color.ORANGE);
//	       redMaterial.setDiffuseColor(Color.RED);		
//	       
//	    ArrayList<Point3D> randPoints=new ArrayList<Point3D>(); 
//		for (int i=0; i<50; i++){
//			Box mySphere = new Box(100,100, 100);
//			mySphere.setTranslateX(Math.random()*500);
//			mySphere.setTranslateY(Math.random()*500);
//			mySphere.setTranslateZ(Math.random()*200);
//			mySphere.setMaterial(redMaterial);
//			mySphere.setDepthTest(DepthTest.ENABLE);
//			arrayGroup.getChildren().add(mySphere);
//	        randPoints.add(new Point3D((float) Math.random()*500,(float) Math.random()*500, (float) Math.random()*200));
//		}
//		
////	    AmbientLight light = new AmbientLight(Color.WHITE);
////        light.getScope().add(root3D);
////        root3D.getChildren().add(light);
//		
//       PolyLine3D polyLine3D=new PolyLine3D(randPoints, 1, Color.BLUE); 
//       arrayGroup.getChildren().add(polyLine3D);
//
//	}
	
//	/**
//	 * Draw cylinder between two points
//	 * Adapted from Rahel Lüthy. http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/ Accessed. 26/03/2015
//	 * @param origin - point 1
//	 * @param target - point 2
//	 * @return cylinder which stretches between two points. 
//	 */
//	public Cylinder createConnection(Point3D origin, Point3D target, double width) {
//	    Point3D yAxis = new Point3D(0, 0, 1);
//	    Point3D diff = target.subtract(origin);
//	    double height = diff.magnitude();
//
//	    Point3D mid = target.midpoint(origin);
//	    Translate moveToMidpoint = new Translate(mid.getX(), -mid.getZ(), mid.getY());
//
//	    Point3D axisOfRotation = diff.crossProduct(yAxis);
//	    
//	    //System.out.println("diff.normalize().dotProduct(yAxis) "+diff.normalize().dotProduct(yAxis)+" axisOfRotation "+axisOfRotation);
//	    
//	    int factor=1; 
//	    if (axisOfRotation.getY()>0) factor=-1; 
//	    
//	    double angle = Math.acos(diff.normalize().dotProduct(yAxis));
//		Rotate rotateAroundCenter=new Rotate(factor*Math.toDegrees(angle), axisOfRotation.getX(), axisOfRotation.getY(), axisOfRotation.getZ(), Rotate.Z_AXIS);
//		
//	    axisOfRotation = diff.crossProduct(new Point3D(0, 1, 0));
//	    angle = Math.acos(diff.normalize().dotProduct(new Point3D(0, 1, 0)));
//		Rotate rotateAroundHeading=new Rotate(Math.toDegrees(angle)+90, axisOfRotation.getX(), axisOfRotation.getY(), axisOfRotation.getZ(), Rotate.Y_AXIS);
//		
//	    //Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
//
//	    Cylinder line = new Cylinder(width, height);
//
//	    line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter, rotateAroundHeading);
//
//	    return line;
//	}


}
