package layout;

import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import dataUnits.Hydrophone;

public class HydrophonePane extends TablePane<Hydrophone> {
	
	
	public HydrophonePane(MainView mainPane){
		super(mainPane.getArrayModelControl().getHydrophones());
		
		TableColumn<Hydrophone,Number>  hydrophoneID = new TableColumn<Hydrophone,Number>("Channel");
		hydrophoneID.setCellValueFactory(cellData -> cellData.getValue().channel);
	
		TableColumn<Hydrophone,String> hydrophoneArray = new TableColumn<Hydrophone,String>("Array");
		hydrophoneArray.setCellValueFactory(cellData -> cellData.getValue().getParentArray().nameProperty());

		TableColumn<Hydrophone,Number> xVal = new TableColumn<Hydrophone,Number>("x (m)");
		xVal.setCellValueFactory(cellData -> cellData.getValue().xPosProperty());

		TableColumn<Hydrophone,Number> yVal = new TableColumn<Hydrophone,Number>("y (m)");
		yVal.setCellValueFactory(cellData -> cellData.getValue().yPosProperty());

		TableColumn<Hydrophone,Number> zVal = new TableColumn<Hydrophone,Number>("z (m)");
		zVal.setCellValueFactory(cellData -> cellData.getValue().zPosProperty());

		TableColumn positionColumn=new TableColumn("Position"); 
		positionColumn.getColumns().addAll(xVal, yVal, zVal);
		
		
		TableColumn<Hydrophone,Number> xValError = new TableColumn<Hydrophone,Number>("x error (m)");
		xValError.setCellValueFactory(cellData -> cellData.getValue().xPosProperty());

		TableColumn<Hydrophone,Number> yValError = new TableColumn<Hydrophone,Number>("y error (m)");
		yValError.setCellValueFactory(cellData -> cellData.getValue().yPosProperty());

		TableColumn<Hydrophone,Number> zValError = new TableColumn<Hydrophone,Number>("z error (m)");
		zValError.setCellValueFactory(cellData -> cellData.getValue().zPosProperty());
		
		TableColumn errorColumn=new TableColumn("Errors"); 
		errorColumn.getColumns().addAll(xValError, yValError, zValError);
		
		super.getTableView().getColumns().addAll(hydrophoneID, hydrophoneArray,positionColumn, errorColumn);

	}
	

	@Override
	Dialog<Hydrophone> createSettingsDialog(Hydrophone data) {
		if (data==null) {
			Hydrophone newHydrophone=new Hydrophone(0, 0, 0); 
			return HydrophoneDialog.createDialog(newHydrophone);
		}
		else return HydrophoneDialog.createDialog(data);
	}

}
