package IMUUtils.gyroscopeCalibration;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import IMUUtils.openTag.ReadDSG;
import IMUUtils.openTag.ReadDSG.OTData;

public class GyroscopeCalibrationTest extends Application {


	//Some test files from and OpenTag
	//private static String filename="F:/Wales_2015/pla_buoy/20150422/Open_Tags/op7/14.DSG"; 
	private static String filename="C:/Users/jamie/Google Drive/SMRU_research/Open_Tag/Open Tag Madgwick Test/Heading Test/42.DSG";
	private static 	String pTCalFilename="C:/Users/jamie/Desktop/Open_Tag_Heading_Test/op3/PRESSTMP.CAL";

	@Override
	public void start(Stage primaryStage) throws Exception {

		//open OT file 
		ReadDSG readDSG=new ReadDSG(); 
		OTData otData=readDSG.otLoadDat(new File(filename), new File(pTCalFilename));

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Month");
		//creating the chart
		final LineChart<Number,Number> lineChart = 
				new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setTitle("Gyrsocope");
		//defining a series
		XYChart.Series<Number, Number> seriesX = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> seriesY = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> seriesZ = new XYChart.Series<Number, Number>();

		seriesX.setName("GyroX");
		seriesY.setName("GyroY");
		seriesZ.setName("GyroZ");

		for (int i=0; i<otData.gyroscope.length; i=i+30 ){
			seriesX.getData().add(new XYChart.Data<Number, Number>(i, otData.gyroscope[i][0]));
			seriesY.getData().add(new XYChart.Data<Number, Number>(i, otData.gyroscope[i][1]));
			seriesZ.getData().add(new XYChart.Data<Number, Number>(i, otData.gyroscope[i][2]));

		}
		lineChart.getData().addAll(seriesX,seriesY,seriesZ);
		lineChart.setCreateSymbols(false);


		System.out.println("Show line chart: ");
		primaryStage.setScene(new Scene(lineChart, 1000,800));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}


