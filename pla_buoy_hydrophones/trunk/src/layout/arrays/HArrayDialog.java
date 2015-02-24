package layout.arrays;

import layout.utils.ParentArrayComboBox;
import main.HArrayManager;
import main.HArrayModelControl;
import dataUnits.hArray.HArray;
import main.HArrayManager.ArrayType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;

public class HArrayDialog extends Dialog<HArray>{
	
	private static HArrayDialog singleInstance;
	private TextField yPos;
	private TextField xPos;
	private TextField zPos;
	private ComboBox<HArray> parentArrayComboBox;
	private ComboBox<ArrayType> arrayType;
	private TextField nameField;
	
	/**
	 * Reference to the array manager
	 */
	private HArrayManager arrayManager;
	
	/**
	 * The array for which the dialog is changing settings. 
	 */
	private HArray array;
	
	/**
	 * Custom pane in which specific controls for different array types are palced. 
	 */
	private BorderPane customSensorPane; 
	
	//create the dialog
	public HArrayDialog(){
		this.initOwner(HArrayModelControl.getInstance().getPrimaryStage());
		arrayManager=HArrayModelControl.getInstance().getHArrayManager();

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
	
	public static Dialog<HArray> createDialog(HArray array){
		if (singleInstance==null) {
			singleInstance=new HArrayDialog();
		}
	
		singleInstance.setParams(array);
	
		return singleInstance; 
	}
	
	public Boolean getParams(){
		array.nameProperty().setValue(nameField.getText());
		array.hArrayTypeProperty().setValue(arrayType.getValue());
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
	
	public void setParams(HArray array){
		this.array=array; 
		nameField.setText(array.nameProperty().getValue());
		arrayType.setValue(array.hArrayTypeProperty().getValue());
		
		//attachmentComboBox.setItems(ArrayModelControl.getInstance().getArrays());
		parentArrayComboBox.setValue(array.parentHArrayProperty().getValue());
				
		xPos.setText(Double.toString(array.xPosProperty().get()));
		yPos.setText(Double.toString(array.yPosProperty().get()));
		zPos.setText(Double.toString(array.zPosProperty().get()));
		
		createArrayPane(array);

	}
	
	/**
	 * Create the pane to allow users to change settings for the array. 
	 */
	private Pane createDialogPane( ){
		
		double sectionPadding=15; 
				
		BorderPane mainPane=new BorderPane(); 
		customSensorPane=new BorderPane(); 
		
		VBox mainControls=new VBox(); 
		mainControls.setSpacing(2);
		mainControls.setPrefWidth(270);
		
		Label nameLabel=new Label("Array Name"); 
		nameLabel.setPadding(new Insets(5,0,0,0));
		nameField=new TextField();
		
		//parent array. 
		Label parentArrayLabel=new Label("Parent Array");
		parentArrayLabel.setPadding(new Insets(sectionPadding,0,0,0));
		parentArrayComboBox = new ParentArrayComboBox();
		parentArrayComboBox.valueProperty().addListener((obs, t, t1)->{
			if (t1!=null) setArrayDimEnabled(t1.getHydrophoneDimPos());
		}); 
		
		//attachment point 
		Label attachmentLabel=new Label("Attachment Point"); 
		attachmentLabel.setPadding(new Insets(sectionPadding,0,0,0));
		HBox arrayPos= new HBox(); 
		arrayPos.setAlignment(Pos.CENTER);
		xPos=new TextField();
		xPos.setMaxWidth(50);
		yPos=new TextField();
		yPos.setMaxWidth(50);
		zPos=new TextField();
		zPos.setMaxWidth(50);
		arrayPos.setSpacing(5);
		arrayPos.getChildren().addAll(new Label("x (m)"), xPos, new Label("y (m)"), yPos, new Label("z (m)"), zPos);
		
		Label arrayTypeLabel=new Label("Array Type"); 
		arrayTypeLabel.setPadding(new Insets(sectionPadding,0,0,0));
		arrayType = new ComboBox<ArrayType>();
		arrayType.setItems(FXCollections.observableArrayList(ArrayType.values()));
		arrayType.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(arrayType, Priority.ALWAYS);
		arrayType.valueProperty().addListener((obs, t, t1)->{
			if (array.hArrayTypeProperty().get()!=t1){
				this.array=arrayManager.createNewArray(obs.getValue());
				//have create new array. 
				setParams(array); 
			}
		}); 
		

		mainControls.getChildren().addAll(nameLabel, nameField, parentArrayLabel, parentArrayComboBox, attachmentLabel, arrayPos, arrayTypeLabel, arrayType); 
		
		mainPane.setTop(mainControls);
		mainPane.setCenter(customSensorPane);

				
		return mainPane;

	}
	
	
	/**
	 * Create the specific pane in the dialog for the selected sensor. 
	 */
	public void createArrayPane(HArray array){
		customSensorPane.setCenter(null); 
		System.out.println("Set new array pane...");
		customSensorPane.setCenter(array.getArrayPane());
		array.getArrayPane().setParams(array);


		//TODO- this is a bit cimbersome and maybe fixed in new version of JavaFX
		//need to get stage and resize because new controls will have been added. 
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.sizeToScene();

		System.out.println("Array pane set...");

		
	}
	
	/**
	 * Set which hydrophone dimensions can be set
	 * @param dim - three element boolean for x,y,z. false to disable. 
	 */
	private void setArrayDimEnabled(boolean[] dim){
		this.xPos.setDisable(!dim[0]);
		this.yPos.setDisable(!dim[1]);
		this.zPos.setDisable(!dim[2]);

	}
	
	
	

	


}
