package layout.utils.chart;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class AxisPane extends Pane {
	
	/**
	 * The canvas is where all the drawing of axis etc takes place.. 
	 */
	protected Canvas canvas;
			
	/**
	 * Orientation of axis.
	 */
	private Orientation orientation=Orientation.VERTICAL;

	/**
	 * Background colour for axis
	 */
	private Color axisColour=Color.TRANSPARENT;

	/**
	 * left inset property. 
	 */
	private DoubleProperty leftPaddingProperty=new AxisInsetProperty(0); 
	

	/**
	 * left inset property. 
	 */
	private DoubleProperty rightPaddingProperty=new AxisInsetProperty(0); 

	/**
	 *The axis. This handles stores info on axis mon and max. 
	 */
	private PamAxisFX axis;
	
	public AxisPane(){
		axis=new PamAxisFX(0, 0, 50, 100, 0, 10, PamAxisFX.ABOVE_LEFT, "Graph Units", PamAxisFX.LABEL_NEAR_CENTRE, "%4d");
		axis.setCrampLabels(true);
		createChart(axis);
	}
	
	public AxisPane(PamAxisFX axis, Orientation orientation){
		this.axis=axis;
		this.orientation=orientation; 
		createChart(axis);
	}
	
	private void createChart(PamAxisFX axis){
		//create the canvas
        canvas = new Canvas(50, 50);
		//add the canvas to the panel. 
		this.getChildren().add(canvas);
		//add listeners to allow canvas to resize and repaint with the graph changing size; 
		addResizeListeners();
		repaint();
	}
	
	
	/**
	 * Set whether the panel is for a horizontal or vertical axis. 
	 * @param orientation- horizontal or vertical. 
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		repaint();
	}
	
	/**
	 * Add a listeners to the canvas to check for resize and repaint. 
	 */
	public void addResizeListeners(){
		this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
            	canvas.setWidth(arg0.getValue().doubleValue());
            	repaint();
            }
        });

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
            	canvas.setHeight(arg0.getValue().doubleValue());
            	repaint();
            }
        });
	}

	/**
	 * Repaint the axis.
	 */
	public void repaint(){
		
		//canvas.getGraphicsContext2D().setStroke(strokeColor);
		canvas.getGraphicsContext2D().setFill(this.axisColour);
		canvas.getGraphicsContext2D().clearRect(0, 0,  canvas.getWidth(), canvas.getHeight());
		canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		if (orientation==Orientation.HORIZONTAL) paintHorizontal(canvas);
		else  paintVertical(canvas);
	}

	/**
	 * Draw the axis if vertical.
	 * @param canvas
	 */
	public void paintHorizontal(Canvas canvas){
		if (axis.getTickPosition()==PamAxisFX.BELOW_RIGHT) axis.drawAxis(canvas.getGraphicsContext2D(), leftPaddingProperty.getValue(), 0, canvas.getWidth()-rightPaddingProperty.getValue(), 0);
		if (axis.getTickPosition()==PamAxisFX.ABOVE_LEFT) axis.drawAxis(canvas.getGraphicsContext2D(),leftPaddingProperty.getValue(), canvas.getHeight(), canvas.getWidth()-rightPaddingProperty.getValue(), canvas.getHeight());

	}
	
	/**
	 * Draw the axis if horizontal
	 * @param canvas
	 */
	public void paintVertical(Canvas canvas){
		if (axis.getTickPosition()==PamAxisFX.ABOVE_LEFT) axis.drawAxis(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight(), canvas.getWidth(), 0);
		if (axis.getTickPosition()==PamAxisFX.BELOW_RIGHT)
			axis.drawAxis(canvas.getGraphicsContext2D(), 0, canvas.getHeight(), 0, 0);
	}
	
	public DoubleProperty getWidthProperty(){
		return canvas.widthProperty();
	}
	
	public DoubleProperty getHeightProperty(){
		return canvas.heightProperty();
	}

	/**
	 * Get the background colour for the axis. 
	 * @return the background colour fo the axis. 
	 */
	public Color getAxisColour() {
		return axisColour;
	}

	/**
	 * Set the background colour of the axis
	 * @param axisColour- the colour to set the background of the axis to. 
	 */
	public void setAxisColour(Color axisColour) {
		this.axisColour = axisColour;
	}

	/**
	 * Get the stroke colour for the axis
	 * @return the stroke colour for the axis. 
	 */
	public Color getStrokeColor() {
		return axis.getStrokeColor(); 
	}

	/**
	 * Set the stroke colour for the axis. 
	 * @param strokeColor- the stroke colour for the axis. 
	 */
	public void setStrokeColor(Color strokeColor) {
		this.axis.setStrokeColor(strokeColor); 
	}
	
	/**
	 * Simple property class which repaints axis when changed. 
	 * @author Jamie Macaulay
	 *
	 */
	class AxisInsetProperty extends SimpleDoubleProperty{
		
		AxisInsetProperty(){
			super();
			super.addListener((obsval,oldVal, newVal)->{
				repaint();
			});
		}
		
		AxisInsetProperty(double val){
			super(val); 
			super.addListener((obsval,oldVal, newVal)->{
				repaint();
			});
		}
		
	}
}
