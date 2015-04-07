package pla_buoy_interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class PLAInterfaceView extends BorderPane {
	
	TextField ipTextField; 
	
	public PLAInterfaceView(){
		
		
		
		  Button btn = new Button();
	        btn.setText("Say 'Hello World'");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Hello World!");
	            }
	        });
	        
	        this.setCenter(btn);
	}
	
	


}
