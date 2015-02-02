package layout;

import dataUnits.Array;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * Pane 
 * @author Jamie Macaulay
 *
 */
public class ArrayPane extends TablePane<Array> {
	
	public ArrayPane(){
		super(); 

		TableColumn<Array,String>  arrayID = new TableColumn<Array,String>("Array ID");
		arrayID.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		TableColumn<Array,Object> arrayType = new TableColumn<Array, Object>("Array Type");
//		arrayType.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<ArrayType>(cellData.getValue().arrayTypeProperty().get()));


		TableColumn<Array,Integer> numHydrophones = new TableColumn<Array,Integer>("No. Hydrophone");
		numHydrophones.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Integer>(cellData.getValue().getHydrophones().size()));
	
		super.getTableView().getColumns().addAll(arrayID, arrayType, numHydrophones);
	}
	
	
	@Override
	Dialog<Array> createSettingsDialog(Array data) {
		if (data==null) return ArrayDialog.createDialog(new Array());
		else ArrayDialog.createDialog(data);
		return null;
	}
	

}
	
//	private class ButtonCell extends TableCell<Array, Boolean> {
//		
//		final Button cellButton;
//		
//		public ButtonCell(){
//			cellButton = new Button();
//			cellButton.setGraphic(new ImageView(settingsIcon));
//			cellButton.setOnAction((event)->{
//				
//			});
//		}
//		
//		//Display button if the row is not empty
//        @Override
//        protected void updateItem(Boolean t, boolean empty) {
//            super.updateItem(t, empty);
//            if(!empty){
//                setGraphic(cellButton);
//            }
//        }
//	}
	
