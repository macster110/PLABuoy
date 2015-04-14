package pla_buoy_interface;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PLAInterfaceMain extends Application {

	 public static void main(String[] args) {
	        launch(args);
	    }
	    
	    @Override
	    public void start(Stage primaryStage) {
	    
	        primaryStage.setTitle("PLA Buoy Interface");
	        
	        PLAControl plaControl=new PLAControl(); 
	        PLAInterfaceView plaInterfaceView=new PLAInterfaceView(plaControl); 
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(plaInterfaceView);
	        primaryStage.setScene(new Scene(root, 300, 250));
	        primaryStage.show();
			FlatterFX.style(FlatterInputType.TOUCH);

	    }
}
