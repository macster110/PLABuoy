package layout.arrays;

import layout.ParentArrayComboBox;
import main.ArrayModelControl;
import dataUnits.Array;
import main.ArrayManager.ArrayType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class ArrayDialog extends Dialog<Array>{
	
	private static ArrayDialog singleInstance;
	private TextField yPos;
	private TextField xPos;
	private TextField zPos;
	private ComboBox<Array> attachmentComboBox;
	private ComboBox<ArrayType> arrayType;
	private TextField nameField;
	
	/**
	 * The array for which the dialog is changing settings. 
	 */
	private Array array;
	private ComboBox<Orientation> orientationComboBox; 
	
	//create the dialog
	public ArrayDialog(){
		
		this.setTitle("Array Dialog");
		this.getDialogPane().setContent(createDialogPane());
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return array;
		    }
		    return null;
		});
		
		final Button btOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK); 
		btOk.addEventFilter(ActionEvent.ACTION, (event) -> { 
			if (getParams());
			else {
				//do not close
				event.consume();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");			
				alert.setContentText("Invalid data enetered in Array Dialog field");
				alert.showAndWait();
			}
		}); 
	
	}
	
	public static Dialog<Array> createDialog(Array array){
		if (singleInstance==null) {
			singleInstance=new ArrayDialog();
		}
	
		singleInstance.setParams(array);
	
		return singleInstance; 
	}
	
	public Boolean getParams(){
		array.nameProperty().setValue(nameField.getText());
		array.arrayTypeProperty().setValue(arrayType.getValue());
		try {
		array.xPosProperty().setValue(Double.valueOf(xPos.getText()));
		array.yPosProperty().setValue(Double.valueOf(yPos.getText()));
		array.zPosProperty().setValue(Double.valueOf(zPos.getText()));
		}
		catch (Exception e){
			System.err.println("Invalid field in Array Dialog"); 
			return false;
		}
		return true; 
	}
	
	public void setParams(Array array){
		this.array=array; 
		nameField.setText(array.nameProperty().getValue());
		arrayType.setValue(array.arrayTypeProperty().getValue());
		
		//attachmentComboBox.setItems(ArrayModelControl.getInstance().getArrays());
		attachmentComboBox.setValue(array.parentArrayProperty().getValue());
		
		orientationComboBox.setValue(array.orientationProperty().getValue());
		
		xPos.setText(Double.toString(array.xPosProperty().get()));
		yPos.setText(Double.toString(array.yPosProperty().get()));
		zPos.setText(Double.toString(array.zPosProperty().get()));
	}
	
	/**
	 * Create the pane to allow users to change settings for the array. 
	 */
	private Pane createDialogPane( ){
		
		double sectionPadding=15; 
				
		BorderPane mainPane=new BorderPane(); 
		
		VBox mainControls=new VBox(); 
		mainControls.setSpacing(2);
		mainControls.setPrefWidth(200);
		
		Label nameLabel=new Label("Array Name"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		nameField=new TextField();

		Label arrayOrientationLabel=new Label("Orientation"); 
		arrayOrientationLabel.setPadding(new Insets(sectionPadding,0,0,0));
		orientationComboBox = new ComboBox<Orientation>();
		orientationComboBox.getItems().addAll(Orientation.values());
		orientationComboBox.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(orientationComboBox, Priority.ALWAYS);
		
		Label arrayTypeLabel=new Label("Array Type"); 
		arrayTypeLabel.setPadding(new Insets(sectionPadding,0,0,0));
		arrayType = new ComboBox<ArrayType>();
		arrayType.setItems(FXCollections.observableArrayList(ArrayType.values()));
		arrayType.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(arrayType, Priority.ALWAYS);
		arrayType.valueProperty().addListener((obs, t, t1)->{
			switch(t1){
			case RIGID_ARRAY:
				orientationComboBox.setDisable(true);
			break;
			case LINEAR_FILEXIBLE_ARRAY:
				orientationComboBox.setDisable(false);
				break;
			default:
				break;
			}
		}); 
		

		Label parentArrayLabel=new Label("Parent Array");
		parentArrayLabel.setPadding(new Insets(sectionPadding,0,0,0));
		attachmentComboBox = new ParentArrayComboBox();
	
		//attachment point 
		Label attachmentLabel=new Label("Attachment Point"); 
		attachmentLabel.setPadding(new Insets(sectionPadding,0,0,0));
		HBox arrayPos= new HBox(); 
		xPos=new TextField();
		yPos=new TextField();
		zPos=new TextField();
		arrayPos.setSpacing(10);
		arrayPos.getChildren().addAll(new Label("x (m)"), xPos, new Label("y (m)"), yPos, new Label("z (m)"), zPos);
		
		mainControls.getChildren().addAll(nameLabel, nameField, arrayTypeLabel, arrayType, 
				parentArrayLabel, attachmentComboBox, arrayOrientationLabel, orientationComboBox, attachmentLabel, arrayPos); 
		
		mainPane.setCenter(mainControls);
				
		return mainPane;

	}
	
	
	

	


}
