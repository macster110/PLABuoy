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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import layout.ControlPane.ChangeType;

public class Array3DPane extends BorderPane implements  ControlPane{
	
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
		addTestShapes();

        
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
				this.mainView.getArrayModelControl().getArrayModelManager().getArrayPos(); 
				break;
				
			default:
				break;
			}
	}
	
	
	public void drawArray(ArrayPos pos){
		
		
		System.out.println("Draw 3D hydrophone array");
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
	        root3D.getChildren().add(mySphere);
	        randPoints.add(new Point3D((float) Math.random()*500,(float) Math.random()*500, (float) Math.random()*200));
		}
		
//	    AmbientLight light = new AmbientLight(Color.WHITE);
//        light.getScope().add(root3D);
//        root3D.getChildren().add(light);
		
       PolyLine3D polyLine3D=new PolyLine3D(randPoints, 1, Color.BLUE); 
       root3D.getChildren().add(polyLine3D);

	}


}
