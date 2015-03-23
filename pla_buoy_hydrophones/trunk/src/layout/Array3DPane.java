package layout;

import java.util.ArrayList;

import org.fxyz.geometry.Point3D;

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
	 * The camera to 
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
                rotateY=new Rotate(0, Rotate.Y_AXIS),
                rotateX=new Rotate(0, Rotate.X_AXIS),
                translate=new Translate(0, 0, -200));
        
        //create main 3D group 
		root3D=new Group();
		arrayGroup=new Group(); 
		axisGroup=new Group();
		
		root3D.getChildren().add(arrayGroup);
		root3D.getChildren().add(axisGroup);
		
		//build axis. 
		buildAxes();

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
	 * Create axis
	 */
	private void buildAxes() {
        double length = 2d*axisSize;
        double width = axisSize/100d;
        double radius = 2d*axisSize/100d;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        
        Text xText=new Text("x"); 
        xText.setStyle("-fx-font: 10px Tahoma; -fx-text-fill: green;");
        Text yText=new Text("z"); 
        yText.setStyle("-fx-font: 10px Tahoma; -fx-text-fill: green;");
        Text zText=new Text("y"); 
        zText.setStyle("-fx-font: 10px Tahoma; -fx-text-fill: green;");

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
				break;
			case HYDROPHONE_CHANGED:
				System.out.println("SensorSimPane: HYDROPHONE_CHANGED");
				break;
			case SENSOR_CHANGED:
				System.out.println("SensorSimPane: SENSOR_CHANGED");
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
		
			//draw hydrophones
			Sphere sphere;
			for (int i=0; i<arrayPos.getHydrophonePos().size(); i++){
				 sphere=new Sphere(1*scaleFactor);
				 sphere.setTranslateX(arrayPos.getHydrophonePos().get(i)[0]*scaleFactor);
				 sphere.setTranslateY(-arrayPos.getHydrophonePos().get(i)[2]*scaleFactor);
				 sphere.setTranslateZ(arrayPos.getHydrophonePos().get(i)[1]*scaleFactor);
				 arrayGroup.getChildren().add(sphere);
			
			}
			
			//draw streamer
		    PolyLine3D polyLine3D;
		    ArrayList<Point3D> streamerPoints;
		    
			for (int i=0; i<arrayPos.getStreamerPositions().size(); i++){
				if (arrayPos.getStreamerPositions().get(i)==null) return; 
				streamerPoints=new ArrayList<Point3D>(); 
				 for (int j=0; j<arrayPos.getStreamerPositions().get(i).size(); j++){
					 //need to convert to fxyz 3D point - stupid but no work around. 
					 Point3D newPoint=new Point3D((float) (arrayPos.getStreamerPositions().get(i).get(j).getX()*scaleFactor),
							(float) (-arrayPos.getStreamerPositions().get(i).get(j).getZ()*scaleFactor), (float) (arrayPos.getStreamerPositions().get(i).get(j).getY()*scaleFactor));
					 streamerPoints.add(newPoint);
				 }
				 polyLine3D=new PolyLine3D(streamerPoints, 9, Color.BLUE); 
				 arrayGroup.getChildren().add(polyLine3D);
			 }

	}
	
	
	

	/**
	 * Create test shapes for the 3D scene
	 */
	private void addTestShapes(){
		final PhongMaterial redMaterial = new PhongMaterial();
	       redMaterial.setSpecularColor(Color.ORANGE);
	       redMaterial.setDiffuseColor(Color.RED);		
	       
	    ArrayList<Point3D> randPoints=new ArrayList<Point3D>(); 
		for (int i=0; i<50; i++){
			Box mySphere = new Box(100,100, 100);
			mySphere.setTranslateX(Math.random()*500);
			mySphere.setTranslateY(Math.random()*500);
			mySphere.setTranslateZ(Math.random()*200);
			mySphere.setMaterial(redMaterial);
			mySphere.setDepthTest(DepthTest.ENABLE);
			arrayGroup.getChildren().add(mySphere);
	        randPoints.add(new Point3D((float) Math.random()*500,(float) Math.random()*500, (float) Math.random()*200));
		}
		
//	    AmbientLight light = new AmbientLight(Color.WHITE);
//        light.getScope().add(root3D);
//        root3D.getChildren().add(light);
		
       PolyLine3D polyLine3D=new PolyLine3D(randPoints, 1, Color.BLUE); 
       arrayGroup.getChildren().add(polyLine3D);

	}


}
