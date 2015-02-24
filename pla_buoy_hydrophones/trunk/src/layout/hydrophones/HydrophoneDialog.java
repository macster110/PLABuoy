package layout.hydrophones;

import layout.utils.ParentArrayComboBox;
import main.HArrayModelControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import dataUnits.Hydrophone;
import dataUnits.hArray.HArray;

public class HydrophoneDialog  extends Dialog<Hydrophone>{
	
	private static HydrophoneDialog singleInstance;
	
	Hydrophone hydrophone;

	private TextField xPos;

	private TextField yPos;

	private TextField zPos;

	private TextField xPosErr;

	private TextField yPosErr;

	private TextField zPosErr;

	private ComboBox<HArray> parentArrayComboBox; 

	private ComboBox<Integer> channelChoiceBox;
	
	public enum ErrorType {NO_ERROR, POS_FIELD, ERROR_FIELD, CHANNEL_ALREADY_USED}; 
	
	public HydrophoneDialog(){
		this.initOwner(HArrayModelControl.getInstance().getPrimaryStage());

		this.setTitle("Hydrophone Dialog");
		this.getDialogPane().setContent(createDialogPane());
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return hydrophone;
		    }
		    return null;
		});
		
		final Button btOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK); 
		btOk.addEventFilter(ActionEvent.ACTION, (event) -> { 
			ErrorType error=getParams();
			if (error==ErrorType.NO_ERROR) return; 
			else {
				//do not close
				event.consume();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");		
				
				switch (error){
				case CHANNEL_ALREADY_USED:
					alert.setContentText("Another hydrophone already uses this channel. Two hydrophone can not have the same channel");
					break;
				case ERROR_FIELD:
					alert.setContentText("Invalid data enetered in hydrophone errors field");
					break;
				case NO_ERROR:
					break;
				case POS_FIELD:
					alert.setContentText("Invalid data enetered in hydrophone position field");
					break;
				default:
					break;
				
				}
					
				alert.showAndWait();
			}
		}); 
		
	}
	
	public static Dialog<Hydrophone> createDialog(Hydrophone hydrophone){
		if (singleInstance==null) {
			singleInstance=new HydrophoneDialog();
		}
	
		singleInstance.setParams(hydrophone);
	
		return singleInstance; 
	}
	
	public ErrorType getParams(){
		
		if (HArrayModelControl.getInstance().checkHydrophoneChannels(channelChoiceBox.getValue())) return ErrorType.CHANNEL_ALREADY_USED;
	
		hydrophone.channelProperty().setValue(channelChoiceBox.getValue());
			
		hydrophone.parentArrayProperty().setValue(parentArrayComboBox.getValue());

		try{
			hydrophone.xPosProperty().setValue(Double.valueOf(xPos.getText()));
			hydrophone.yPosProperty().setValue(Double.valueOf(yPos.getText()));
			hydrophone.zPosProperty().setValue(Double.valueOf(zPos.getText()));
		}
		catch (Exception e){
			System.err.println("Invalid field in Hydrophone Dialog"); 
			return ErrorType.POS_FIELD; 
		}
		try{
			hydrophone.xErrProperty().setValue(Double.valueOf(xPosErr.getText()));
			hydrophone.yErrProperty().setValue(Double.valueOf(yPosErr.getText()));
			hydrophone.zErrProperty().setValue(Double.valueOf(zPosErr.getText()));
		}
		catch (Exception e){
			System.err.println("Invalid field in Hydrophone Dialog"); 
			return ErrorType.ERROR_FIELD; 
		}
		
		return ErrorType.NO_ERROR; 
	}
	
	public void setParams(Hydrophone hydrophone){
		
		this.hydrophone=hydrophone; 
		
		channelChoiceBox.setValue(hydrophone.channelProperty().getValue());
		
		parentArrayComboBox.setValue(hydrophone.parentArrayProperty().getValue());

	
		xPos.setText(Double.toString(hydrophone.xPosProperty().getValue()));
		yPos.setText(Double.toString(hydrophone.yPosProperty().getValue()));
		zPos.setText(Double.toString(hydrophone.zPosProperty().getValue()));
		
		xPosErr.setText(Double.toString(hydrophone.xErrProperty().getValue()));
		yPosErr.setText(Double.toString(hydrophone.yErrProperty().getValue()));
		zPosErr.setText(Double.toString(hydrophone.zErrProperty().getValue()));

	}
	

	/**
	 * Create the pane to allow users to change settings for the array. 
	 */
	private Pane createDialogPane(){
		
		double sectionPadding=10; 
		
		BorderPane mainPane=new BorderPane(); 
		
		mainPane.setPrefWidth(280);
		
		VBox vBox=new VBox(); 
		
		Label channelLabel=new Label("Channel"); 
		channelLabel.setPadding(new Insets(sectionPadding,0,0,0));
		channelChoiceBox=new ComboBox<Integer>();
		ObservableList<Integer> channelList= FXCollections.observableArrayList();
		//add 32 channel- the most PAMGUARD can currently handle. 
		for (int i=0; i<32; i++){
			channelList.add(i); 
		}
		
		channelChoiceBox.setItems(channelList);
		channelChoiceBox.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(channelChoiceBox, Priority.ALWAYS);
		channelChoiceBox.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer channel) {
              if (channel == null){
                return null;
              } else {
                return ("Channel "+channel);
              }
            }

          @Override
          public Integer fromString(String userId) {
              return null;
          }
		});
		
		
		Label parentArrayLabel=new Label("Parent Array");
		parentArrayLabel.setPadding(new Insets(sectionPadding,0,0,0));
		parentArrayComboBox = new ParentArrayComboBox();
		parentArrayComboBox.valueProperty().addListener((obs, t, t1)->{
			setHydrophoneDimEnabled(t1.getHydrophoneDimPos());
		}); 
		
		//attachment point 
		Label hydrophonePosLable=new Label("Hydrophone Position"); 
		hydrophonePosLable.setPadding(new Insets(sectionPadding,0,0,0));
		HBox hydrophonePos= new HBox(); 
		xPos=new TextField();
		xPos.setMaxWidth(50);
		yPos=new TextField();
		yPos.setMaxWidth(50);
		zPos=new TextField();
		zPos.setMaxWidth(50);
		hydrophonePos.setSpacing(5);
		hydrophonePos.getChildren().addAll(new Label("x (m)"), xPos, new Label("y (m)"), yPos, new Label("z (m)"), zPos);
		
		//attachment point 
		Label hydrophoneErrorLabel=new Label("Hydrophone Error"); 
		hydrophoneErrorLabel.setPadding(new Insets(sectionPadding,0,0,0));
		HBox hydrophoneErr= new HBox(); 
		xPosErr=new TextField();
		xPosErr.setMaxWidth(50);
		yPosErr=new TextField();
		yPosErr.setMaxWidth(50);
		zPosErr=new TextField();
		zPosErr.setMaxWidth(50);
		hydrophoneErr.setSpacing(5);
		hydrophoneErr.getChildren().addAll(new Label("x (m)"), xPosErr, new Label("y (m)"), yPosErr, new Label("z (m)"), zPosErr);
		
		
	
		vBox.getChildren().addAll(channelLabel, channelChoiceBox, parentArrayLabel, parentArrayComboBox, hydrophonePosLable, hydrophonePos, hydrophoneErrorLabel, hydrophoneErr); 
		
		mainPane.setCenter(vBox);
		
		return mainPane;
	}
	
	/**
	 * Set which hydrophone dimensions can be set
	 * @param dim - three element boolean for x,y,z. false to disable. 
	 */
	private void setHydrophoneDimEnabled(boolean[] dim){
		this.xPos.setDisable(!dim[0]);
		this.yPos.setDisable(!dim[1]);
		this.zPos.setDisable(!dim[2]);

	}
	 

}
