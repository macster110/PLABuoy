package layout.hydrophones;

import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import layout.ArrayModelView;
import layout.ControlPane;
import layout.utils.TablePane;
import main.HArrayModelControl;
import dataUnits.Hydrophone;

public class HydrophoneTablePane extends TablePane<Hydrophone> implements ControlPane{
	
	/**
	 * Reference to the main view. 
	 */
	private ArrayModelView mainPane;


	public HydrophoneTablePane(ArrayModelView mainPane){
		super(mainPane.getArrayModelControl().getHydrophones());
		
		this.mainPane=mainPane;
		
		TableColumn<Hydrophone,Number>  hydrophoneID = new TableColumn<Hydrophone,Number>("Channel");
		hydrophoneID.setCellValueFactory(cellData -> cellData.getValue().channel);
	
		TableColumn<Hydrophone,String> hydrophoneArray = new TableColumn<Hydrophone,String>("Array");
		hydrophoneArray.setCellValueFactory(cellData -> cellData.getValue().parentArrayProperty().getValue().nameProperty());

		TableColumn<Hydrophone,Number> xVal = new TableColumn<Hydrophone,Number>("x (m)");
		xVal.setCellValueFactory(cellData -> cellData.getValue().xPosProperty());

		TableColumn<Hydrophone,Number> yVal = new TableColumn<Hydrophone,Number>("y (m)");
		yVal.setCellValueFactory(cellData -> cellData.getValue().yPosProperty());

		TableColumn<Hydrophone,Number> zVal = new TableColumn<Hydrophone,Number>("z (m)");
		zVal.setCellValueFactory(cellData -> cellData.getValue().zPosProperty());

		TableColumn positionColumn=new TableColumn("Position"); 
		positionColumn.getColumns().addAll(xVal, yVal, zVal);
		
		
		TableColumn<Hydrophone,Number> xValError = new TableColumn<Hydrophone,Number>("x error (m)");
		xValError.setCellValueFactory(cellData -> cellData.getValue().xErrProperty());

		TableColumn<Hydrophone,Number> yValError = new TableColumn<Hydrophone,Number>("y error (m)");
		yValError.setCellValueFactory(cellData -> cellData.getValue().yErrProperty());

		TableColumn<Hydrophone,Number> zValError = new TableColumn<Hydrophone,Number>("z error (m)");
		zValError.setCellValueFactory(cellData -> cellData.getValue().zErrProperty());
		
		TableColumn errorColumn=new TableColumn("Errors"); 
		errorColumn.getColumns().addAll(xValError, yValError, zValError);
		
		super.getTableView().getColumns().addAll(hydrophoneID, hydrophoneArray,positionColumn, errorColumn);

	}
	

	@Override
	public Dialog<Hydrophone> createSettingsDialog(Hydrophone data) {
		if (data==null) {
			Hydrophone newHydrophone=new Hydrophone(0, 0, 0); 
			newHydrophone.setParentArray(HArrayModelControl.getInstance().getReferenceArray());
			return HydrophoneDialog.createDialog(newHydrophone);
		}
		else return HydrophoneDialog.createDialog(data);
	}


	@Override
	public void notifyChange(ChangeType type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dialogClosed(Hydrophone data) {
		//Update hydrophones;  
		System.out.println("Update hydrophones"); 
		mainPane.getArrayModelControl().updateArrayHydrophones();
		
	}

}
