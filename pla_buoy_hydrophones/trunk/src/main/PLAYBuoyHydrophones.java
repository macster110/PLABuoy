package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import layout.ArrayModelView;
 
public class PLAYBuoyHydrophones extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Hydrophone Movement Reconstruction");
        
        //create the primary control class; 
        HArrayModelControl.create(); 
        ArrayModelView mainView=new ArrayModelView(HArrayModelControl.getInstance(), primaryStage);
        HArrayModelControl.getInstance().setMainView(mainView);
        
        StackPane root = new StackPane();
        root.getChildren().add(mainView);
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }
}