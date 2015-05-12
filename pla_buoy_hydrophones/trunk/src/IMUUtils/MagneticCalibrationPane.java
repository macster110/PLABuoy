package IMUUtils;

import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import layout.Array3DPane;

/**
 * Pane which allows users to visualise a magnetic calibration. 
 * @author jamie
 *
 */
public class MagneticCalibrationPane extends BorderPane {
	
	//keep track of mouse positions
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
	
	/**
	 * The camera transforms 
	 */
	private Rotate rotateY;
	private Rotate rotateX;
	private Translate translate;
	private Group root3D;
	private Group magneticMeasurments;
	private Group axisGroup;
	
	public MagneticCalibrationPane(MagneticCalibration magneticCalibration){
		
		
		this.setCenter(create3DMagneticMap());
	}
	
	
	private Pane createTopControls(){
		HBox topControls=new HBox(); 
		topControls.setSpacing(5);
		
		//button to perform extended calibration. 
		Button calcEllipse=new Button(); 
		calcEllipse.setOnAction((action)->{
			
		}); 
		
		return topControls; 
	}
	
	/**
	 * 3D pane which allow users to visualise calibration measurements. 
	 * @return 3D pane which shows magnetic measurements and ellipse calibration. 
	 */
	private Pane create3DMagneticMap(){
	    
		Pane pane3D=new Pane(); 
		
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(15000);
        camera.setNearClip(0.1);
        camera.setDepthTest(DepthTest.ENABLE);
        camera.getTransforms().addAll (
                rotateY=new Rotate(-45, Rotate.Y_AXIS),
                rotateX=new Rotate(-45, Rotate.X_AXIS),
                translate=new Translate(0, 0, -1000));
        
        //create main 3D group 
		root3D=new Group();
		magneticMeasurments=new Group(); 
		axisGroup=Array3DPane.buildAxes(100);
		axisGroup.getChildren().add(createAxisSphere());
		
		root3D.getChildren().add(magneticMeasurments);
		root3D.getChildren().add(axisGroup);
		

        //Use a SubScene to mix 3D and 2D stuff.        
		//note- make sure depth buffer in sub scene is enabled. 
        SubScene subScene = new SubScene(root3D, 500,500, true, SceneAntialiasing.BALANCED);
        subScene.widthProperty().bind(this.widthProperty());
        subScene.heightProperty().bind(this.heightProperty());
        subScene.setDepthTest(DepthTest.ENABLE);

        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera);

        //handle mouse events for sub scene
        handleMouse(subScene); 

        //create new group to add sub scene to 
        Group group = new Group();
        group.getChildren().add(subScene);
        
        //add group to window.
        pane3D.getChildren().add(group);
        pane3D.setDepthTest(DepthTest.ENABLE);
        
        return pane3D;
	}
	
	public Sphere createAxisSphere(){
		Sphere sphere=new Sphere(100);
		PhongMaterial material=new PhongMaterial(); 
		material.setSpecularColor(Color.TRANSPARENT);
		material.setDiffuseColor(Color.TRANSPARENT);
		sphere.setMaterial(material);
		//TODO SegmentedSphereMesh sphere1 = new SegmentedSphereMesh(100,0,26,1000);
	    sphere.setCullFace(CullFace.NONE);
		return sphere;
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
	
	

}
