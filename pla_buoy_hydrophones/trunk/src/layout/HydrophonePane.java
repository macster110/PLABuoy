package layout;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import dataUnits.Hydrophone;

public class HydrophonePane extends TablePane<Hydrophone> {
	
	
	public HydrophonePane(){
		super();
		
		TableColumn<Hydrophone,Integer>  hydrophoneID = new TableColumn<Hydrophone,Integer>("Hydrophone Channel");
		hydrophoneID.setCellValueFactory(new PropertyValueFactory<Hydrophone, Integer>("channel"));
	
		TableColumn<Hydrophone,String> hydrophoneArray = new TableColumn<Hydrophone,String>("Array");
		hydrophoneArray.setCellValueFactory(cellData -> cellData.getValue().getParentArray().nameProperty());

		TableColumn<Hydrophone,Double> xVal = new TableColumn<Hydrophone,Double>("x");
		xVal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Double>(cellData.getValue().getPos()[0]));

		
		TableColumn<Hydrophone,Double> yVal = new TableColumn<Hydrophone,Double>("y");
		yVal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Double>(cellData.getValue().getPos()[1]));

		
		TableColumn<Hydrophone,Double> zVal = new TableColumn<Hydrophone,Double>("z");
		zVal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Double>(cellData.getValue().getPos()[2]));
		
		super.getTableView().getColumns().addAll(hydrophoneID, hydrophoneArray, xVal, yVal, zVal);

	}
	

	@Override
	Dialog<Hydrophone> createSettingsDialog(Hydrophone data) {
	
		
		return null;
	}

}
