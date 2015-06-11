package layout.utils.chart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

/**
 * A quick implementation of a chart/plot. 
 * <p>
 * Standard FX were too slow for the volume data to be plotted. 
 * @author Jamie Macaulay
 *
 */
public class PlotPane extends BorderPane {
	
	/**
	 * Canvas for plotting data. 
	 */
	private Canvas plotCanvas;
	
	/**
	 * Canvas for drawing or making annotations. 
	 */
	private Canvas annotationCanvas;

	/*
	 *The y axis. 
	 */
	private PamAxisFX yAxis;

	/**
	 * The x axis. 
	 */
	private PamAxisFX xAxis;
	
	/**
	 * Pane which holds x axis 
	 */
	private AxisPane xAxisPane;

	/**
	 * Pane which holds y axis 
	 */
	private AxisPane yAxisPane;
	
	/**
	 * The graph data. Each represents DataSeries is a different seris on the line graph. 
	 */
	private ObservableList<DataSeries> data=FXCollections.observableArrayList();
	
	public PlotPane(){
		
		//axis
		//xAxis=new PamAxisFX(0, 50, 50, 100, 0, 20, PamAxisFX.BELOW_RIGHT, "Graph Units", PamAxisFX.LABEL_NEAR_CENTRE, "%4d");
		xAxis=new PamAxisFX(0, 50, 50, 50, 0, 20, PamAxisFX.ABOVE_LEFT, "Graph Units", PamAxisFX.LABEL_NEAR_CENTRE, "%4d");
		xAxis.setCrampLabels(true);
		yAxis=new PamAxisFX(0, 0, 50, 50, 50,0 , PamAxisFX.BELOW_RIGHT, "Graph Units", PamAxisFX.LABEL_NEAR_MAX, "%4d");
		yAxis.setCrampLabels(true);

		xAxisPane=new AxisPane(xAxis, Orientation.HORIZONTAL); 
		xAxisPane.setPrefHeight(75);

		yAxisPane=new AxisPane(yAxis,Orientation.VERTICAL); 
		yAxisPane.setPrefWidth(75);

		//because we're using a border layout the x axis pane needs a bit of space so it start at 0 on y axis instead of pixel 0
		HBox xAxisHolder=new HBox(); 
		Pane space=new Pane(); 
		space.minWidthProperty().bind(yAxisPane.widthProperty());
		HBox.setHgrow(xAxisPane, Priority.ALWAYS);
		xAxisHolder.getChildren().addAll(space, xAxisPane);
		
		//add axis to chart
		this.setTop(xAxisHolder);
		this.setRight(yAxisPane);
		
		//create canvas to plot data 
		plotCanvas=new Canvas(50,50); 
		annotationCanvas=new Canvas(50,50); 
	    Pane pane = new Pane();
	    pane.getChildren().add(plotCanvas);
	    pane.getChildren().add(annotationCanvas);
		
		this.setCenter(pane);
		
		//need to to resize the canvas inside the graph properly 
		this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
            	plotCanvas.setWidth(arg2.doubleValue()-yAxisPane.getWidth());
            	repaint();
            }
        });
		
		this.heightProperty().addListener(new ChangeListener<Number>() {
	            @Override
	            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
	            	plotCanvas.setHeight(arg2.doubleValue()-xAxisPane.getHeight());
	            	repaint();
	            }
	     });
		
		repaint(); 
	}
	
	/**
	 * Get the graph data. 
	 * @return the graph data 
	 */
	public ObservableList<DataSeries> getData(){
		return data;
	}
	
	/**
	 * Set the x axis limits. 
	 * @param xLim
	 */
	public void setXLim(double[] xLim){
		xAxis.setMinVal(xLim[0]);
		xAxis.setMaxVal(xLim[1]);
	}
	
	/**
	 * Set the x axis limits. 
	 * @param xLim - x axis limits two values, min and max. 
	 */
	public void setXLim(double xMin, double xMax){
		xAxis.setMinVal(xMin);
		xAxis.setMaxVal(xMax);
	}
	
	/**
	 * Set the y axis limits. 
	 * @param yLim - y axis limits two values, min and max. 
	 */
	public void setYLim(double[] yLim){
		yAxis.setMinVal(yLim[1]);
		yAxis.setMaxVal(yLim[0]);
	}
	
	/**
	 * Set the y axis limits. 
	 * @param yMin - the minimum value fo the y axis. 
	 * @param yMax - the maximum value of the y axis
	 */
	public void setYLim(double yMin, double yMax){
		yAxis.setMinVal(yMax);
		yAxis.setMaxVal(yMin);
	}
	

	public void repaint(){
		//System.out.println("repaint: "+plotCanvas.getWidth()+" "+plotCanvas.getHeight());
		
		plotCanvas.getGraphicsContext2D().clearRect(0, 0, plotCanvas.getWidth(), plotCanvas.getHeight());
		plotCanvas.getGraphicsContext2D().fill();

		xAxisPane.repaint();
		yAxisPane.repaint();
	
		//drawShapes(plotCanvas.getGraphicsContext2D());
		drawTestData(plotCanvas.getGraphicsContext2D());
	}
	
	
	
	private void drawShapes(GraphicsContext gc) {
		gc.setFill(Color.RED);
		for (int i=0; i<200; i++){
			int height=(int) (Math.random()*gc.getCanvas().getHeight());
			int width=(int) (Math.random()*gc.getCanvas().getWidth());
	        gc.fillOval(width, height, 10, 10);
	        gc.strokeOval(width, height, 10, 10);
		}
	 }
	
	private void drawTestData(GraphicsContext gc) {
		gc.setFill(Color.RED);
		for (int i=0; i<200; i++){
			double[] dataPoint={i,i};
			double posX=xAxis.getPosition(dataPoint[0]);
			double posY=yAxis.getPosition(dataPoint[1]);
	        gc.fillOval(posX, posY, 10, 10);
	        gc.strokeOval(posX,posY, 10, 10);
		}
	 }

}
