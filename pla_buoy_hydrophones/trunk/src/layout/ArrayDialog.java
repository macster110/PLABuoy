package layout;

import dataUnits.Array;
import dataUnits.Array.ArrayType;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ArrayDialog extends Dialog<Array>{
	
	private static ArrayDialog singleInstance; 
	
	//create the dialog
	public ArrayDialog(){
		
		this.setTitle("Array Dialog");
		this.getDialogPane().setContent(createDialogPane());
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return getParams();
		    }
		    return null;
		});
	}
	
	public static Dialog<Array> createDialog(Array array){
		if (singleInstance==null) {
			singleInstance=new ArrayDialog();
		}
	
		singleInstance.setParams(array);
		
	

		
		return singleInstance; 
	}
	
	public static Array getParams(){
		
		return new Array(); 
	}
	
	public void setParams(Array array){
		
	}
	
	/**
	 * Create the pane to allow users to change settings for the array. 
	 */
	private Pane createDialogPane( ){
				
		BorderPane mainPane=new BorderPane(); 
		
		ComboBox<ArrayType> cbxStatus = new ComboBox<>();
		cbxStatus.setItems(FXCollections.observableArrayList(ArrayType.values()));
		
		mainPane.setCenter(cbxStatus);
				
		return mainPane;

	}
	
	
	

	


}
