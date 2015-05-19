package IMUUtils.magneticCalibration;

import java.util.Arrays;
import java.util.Comparator;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import layout.Array3DPane;

/**
 * Pane which allows users to visualise a magnetic calibration. 
 * @author Jamie Macaulay
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
	
	/**
	 * Group which holds raw magnetic measurments
	 */
	private Group magneticMeasurments;
	
	/**
	 * Group which holds measurements after calibration values have been added. 
	 */
	private Group calMagMeasurments; 

	
	/**
	 * Group containing axis and a reference 
	 */
	private Group axisGroup;
	
	/**
	 * Sphere around axis to help reference data.
	 */
	private Sphere sphereAxisSphere;
	
	/**
	 * Only show every nth point on the graph- keeps things fast. e..g 4 only show every fourth m3d magnetic point.
	 */
	private int pointFilter=2;
	
	/**
	 * Button to calculate calibration ellipse
	 */
	private Button calcEllipse;
	
	/**
	 * Button to show compensated values
	 */
	private Button calcCompValues;
	
	/**
	 * Calibration values 
	 */
	private Label calibrationValues; 
	
	/**
	 * Class for calculating magnetic calibrations. 
	 */
	private final MagneticCalibration magCal=new MagneticCalibration();
	
	/**
	 * The percentile to keep. 
	 */
	private double percentileKeep=1;
	
	/**
	 * Scale factor for all 3D stuff. 
	 */
	private double scaleFactor=100; 
	
	/**
	 * Current raw magnetometer data.
	 */
	private double[][] magnetomterData;
	
	
	/**
	 * Create the magnetic calibration pane. 
	 * @param magneticCalibration - magnetic calibration class to calibrate the pane. 
	 */
	public MagneticCalibrationPane() {
		this.setTop(createTopControls());
		this.setCenter(create3DMagneticMap());
	}
	
	/**
	 * Create top tool bar controls. 
	 * @return top controls.
	 */
	private Pane createTopControls(){
		HBox topControls=new HBox(); 
		topControls.setPadding(new Insets(5,5,5,5));
		topControls.setSpacing(5);
		topControls.setAlignment(Pos.CENTER_LEFT);

		//button to perform extended calibration. 
		calcEllipse=new Button("Calculate Ellipse"); 
		calcEllipse.setOnAction((action)->{

		}); 

		//button to calculate 
		calcCompValues=new Button("Calibrate Values"); 
		calcCompValues.setOnAction((action)->{
			magCal.calcCalValues(magnetomterData); 
			magCal.calibrateValues(magnetomterData); 
		}); 

		calibrationValues=new Label("Calibration values: "); 
		calibrationValues.setAlignment(Pos.CENTER);

		topControls.getChildren().addAll(calcEllipse, calcCompValues, calibrationValues); 

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
		calMagMeasurments=new Group(); 
//		axisGroup=Array3DPane.buildAxes(100, Color.RED, Color.SALMON, Color.BLUE, Color.CYAN, Color.LIMEGREEN, Color.LIME, Color.WHITE);
		axisGroup=Array3DPane.buildAxes(100, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
		axisGroup.getChildren().add(sphereAxisSphere=createAxisSphere());
		
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
	
	/**
	 * Create a mesh sphere for reference. 
	 * @return mesh sphere for reference. 
	 */
	public Sphere createAxisSphere(){
		Sphere sphere=new Sphere(35);
		PhongMaterial material=new PhongMaterial(); 
		material.setSpecularColor(Color.WHITE);
		material.setDiffuseColor(Color.WHITE);
		sphere.setMaterial(material);
	    sphere.setDrawMode(DrawMode.LINE);
		return sphere;
	}
	
	/**
	 * Set the percentile of results to remove. e.g. 0.95 would remove the top 5% of results. This can help filter 
	 * out spurious reults. 
	 * @return remove. percentile of results to keep. Value is between 0 and 1. 
	 */
	public void setPercentileKeep(double pKeep){
		this.percentileKeep=pKeep;
	}
	
	/**
	 * Filter out a percentile of top (greatest in magnitude) magnotometer results.
	 * @param percentile - percentile to keep. 
	 * @param magnetomterData - Nx3 array of magnetometer data.
	 * @return the filtered array; 
	 */
	public double[][] getPercentileMagArray(double[][] magnetomterData, double percentile){
		if (percentile>=1 || percentile<=0) return magnetomterData;
		
		double[] magMagnitude=new double[magnetomterData.length]; 
		for (int i=0; i<magnetomterData.length; i++){
			//work out magnitude (no need to sqrt as this takes time and we're only using this to sort and get index)
			magMagnitude[i]=Math.pow(magnetomterData[i][0],2)+Math.pow(magnetomterData[i][1],2)+Math.pow(magnetomterData[i][2],2);
		}
		
		//work out indexes. 
		ArrayIndexComparator comparator = new ArrayIndexComparator(magMagnitude);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);
		
		//now sort magnetomter results and remove percentiles 
		int percentileNum=(int) (percentile*magnetomterData.length);
		
		double[][] magDataSorted=new double[percentileNum][3]; 
		for (int i=0; i<magnetomterData.length; i++){		
			if (indexes[i]<percentileNum-1) magDataSorted[i]=magnetomterData[indexes[i]]; 
		}
		
		return magDataSorted;
	}
	
	/**
	 * Get the index of a sorted array
	 * @author Jamie Macaulay (from http://stackoverflow.com/questions/4859261/get-the-indices-of-an-array-after-sorting)
	 *
	 */
	public class ArrayIndexComparator implements Comparator<Integer>
	{
	    private final double[] array;

	    public ArrayIndexComparator(double[] array)
	    {
	        this.array = array;
	    }

	    public Integer[] createIndexArray()
	    {
	        Integer[] indexes = new Integer[array.length];
	        for (int i = 0; i < array.length; i++)
	        {
	            indexes[i] = i; // Autoboxing
	        }
	        return indexes;
	    }

	    @Override
	    public int compare(Integer index1, Integer index2)
	    {
	         // Autounbox from Integer to int to use as array indexes
	        return (int) (array[index1]-array[index2]);
	    }
	}
	
	/**
	 * Set the raw magnetomer data. 
	 * @param magnetomterDataIn Nx3 array of raw magnotometer measurments. 
	 */
	public void setMagnetomterData(double[][] magnetomterDataIn){
		this.magnetomterData=magnetomterDataIn; 
		addMagnetomterData(magnetomterDataIn, true)
	}
	

	
	/**
	 * Add calibrated magnetometer data to graoh 
	 * @param calMagData - calibrated magnetometer data 
	 * @param remove - remove any previous data 
	 */
	private void addCalibratedMagData(double[][] calMagData, boolean remove){
		
		if (remove) calMagMeasurments.getChildren().removeAll(calMagMeasurments.getChildren());
		
		Sphere sphere;
		PhongMaterial material=new PhongMaterial(); 
		material.setDiffuseColor(Color.RED);
		for (int i=0; i<calMagData.length; i=i+pointFilter){
			sphere=new Sphere(1); 
			
			sphere.setTranslateX(calMagData[i][0]*scaleFactor);
			sphere.setTranslateY(calMagData[i][1]*scaleFactor);
			sphere.setTranslateZ(calMagData[i][2]*scaleFactor);

		
			sphere.setMaterial(material);
			
			calMagMeasurments.getChildren().add(sphere);
		}

	}

	
	/**
	 * Add raw magnetometer data to the graph. 
	 * @param magnetomterData - magnetomer data - three dimensions.
	 * @param remove - true to remove all previous data. False keeps data. 
	 */
	private  void addMagnetomterData(double[][] magnetomterDataIn, boolean remove){
		
		double[][] magnetomterData= getPercentileMagArray(magnetomterDataIn, this.percentileKeep);
		
		if (remove) magneticMeasurments.getChildren().removeAll(magneticMeasurments.getChildren());
		
		//find max value 
		double max=Double.MIN_VALUE;
		for (int i=0; i<magnetomterData.length; i++){
			if (max<Math.abs(magnetomterData[i][0])){
				max=Math.abs(magnetomterData[i][0]);
			}
		}
		
		//Add Data
		//JavaFX 8 way
		Sphere sphere;
		for (int i=0; i<magnetomterData.length; i=i+pointFilter){
			sphere=new Sphere(1); 
			
			sphere.setTranslateX(magnetomterData[i][0]*scaleFactor);
			sphere.setTranslateY(magnetomterData[i][1]*scaleFactor);
			sphere.setTranslateZ(magnetomterData[i][2]*scaleFactor);

			PhongMaterial material=new PhongMaterial(); 
			material.setDiffuseColor(new Color(0, 1-Math.abs(magnetomterData[i][0]/max),  Math.abs(magnetomterData[i][0]/max), 1));
			sphere.setMaterial(material);
			
			magneticMeasurments.getChildren().add(sphere);
		}
		
		//FXYZ way 
//		ArrayList<Double> pointX=new ArrayList<Double>();
//		ArrayList<Double> pointY=new ArrayList<Double>();
//		ArrayList<Double> pointZ=new ArrayList<Double>();
//		ArrayList<Color> colours=new ArrayList<Color>();
//		for (int i=0; i<magnetomterData.length; i=i+pointFilter){
//			pointX.add(magnetomterData[i][0]*100);
//			pointY.add(magnetomterData[i][1]*100);
//			pointZ.add(magnetomterData[i][2]*100);
//			colours.add(new Color(0, 1-Math.abs(magnetomterData[i][0]/max),  Math.abs(magnetomterData[i][0]/max), 1));
//		}
//		
//		ScatterPlot scatterPlot=new ScatterPlot(100,1,true); 
//		
//		scatterPlot.setXYZData(pointX, pointY, pointZ,colours);
//		magneticMeasurments.getChildren().add(scatterPlot);
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

	 /**
	  * Get the current magnetic calibration result. 
	  * @return the current magnetic calibration result. 
	  */
	public MagneticCalibration getMagneticCalibration() {
		return this.magCal;
	}
	
	

}
