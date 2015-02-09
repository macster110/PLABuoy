package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import layout.MainView;
 
public class PLAYBuoyHydrophones extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Hydrophone Movement Reconstruction");
        
        //create the primary control class; 
        ArrayModelControl.create(); 
        MainView mainView=new MainView(ArrayModelControl.getInstance(), primaryStage);
        ArrayModelControl.getInstance().setMainView(mainView);
        
        StackPane root = new StackPane();
        root.getChildren().add(mainView);
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }
}