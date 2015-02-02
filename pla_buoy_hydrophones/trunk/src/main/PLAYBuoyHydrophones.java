package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import layout.MainPane;
 
public class PLAYBuoyHydrophones extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Hydrophone Movement Reconstruction");
        
        HydrophoneModelControl hydrophoneModelControl=new HydrophoneModelControl(); 
     
        StackPane root = new StackPane();
        root.getChildren().add(new MainPane(hydrophoneModelControl));
        primaryStage.setScene(new Scene(root, 500, 900));
        primaryStage.show();
    }
}