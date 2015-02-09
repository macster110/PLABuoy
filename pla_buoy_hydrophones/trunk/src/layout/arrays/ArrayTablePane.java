package layout.arrays;

import layout.MainView;
import layout.TablePane;
import main.ArrayModelControl;
import dataUnits.Array;
import main.ArrayManager.ArrayType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

/**
 * Pane to add, remove and edit arrays. 
 * @author Jamie Macaulay
 *
 */
public class ArrayTablePane extends TablePane<Array> {
	
	public ArrayTablePane(MainView mainPane){
		super(mainPane.getArrayModelControl().getArrays()); 

		TableColumn<Array,String>  arrayID = new TableColumn<Array,String>("Array Name");
		arrayID.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		TableColumn<Array,ArrayType> arrayType = new TableColumn<Array, ArrayType>("Array Type");
		arrayType.setCellValueFactory(cellData -> cellData.getValue().arrayTypeProperty());

		TableColumn<Array,String> attachmentArray = new TableColumn<Array, String>("Parent Array");
		attachmentArray.setCellValueFactory(cellData -> {
			if (cellData.getValue().parentArrayProperty().getValue()==null) return  new ReadOnlyObjectWrapper<String>("none");
			else return cellData.getValue().parentArrayProperty().get().nameProperty();
		});

		TableColumn<Array,Integer> numHydrophones = new TableColumn<Array,Integer>("No. Hydrophones");
		numHydrophones.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Integer>(cellData.getValue().getHydrophones().size()));

		TableColumn parentPoint = new TableColumn("Attachment Point");

		TableColumn<Array,Number> xPos = new TableColumn<Array,Number>("x (m)");
		xPos.setCellValueFactory(cellData -> cellData.getValue().xPosProperty());

		TableColumn<Array,Number> yPos = new TableColumn<Array,Number>("y (m)");
		yPos.setCellValueFactory(cellData -> cellData.getValue().yPosProperty());

		TableColumn<Array,Number> zPos = new TableColumn<Array,Number>("z (m)");
		zPos.setCellValueFactory(cellData -> cellData.getValue().zPosProperty());
        
		//override default row behaviour in order to prevent the first row from being selected.  
		getTableView().setRowFactory( tv -> {
			TableRow<Array> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (row.isEmpty()) return; 

				if (row.getIndex()==0) {
					event.consume();
				}

				getButtonPane().getSettingsButton().setDisable(row.getIndex()==0);
				getButtonPane().getDeleteButton().setDisable(row.getIndex()==0);

				if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
					Array rowData = row.getItem();
					editData(rowData);
				}
			});
			return row ;
		});

		//disable buttons so reference array can't be deleted;
		getButtonPane().getSettingsButton().setDisable(true);
		getButtonPane().getDeleteButton().setDisable(true);

		parentPoint.getColumns().addAll(xPos, yPos, zPos);

		getTableView().getColumns().addAll(arrayID, arrayType, attachmentArray, parentPoint, numHydrophones);

	}
	
	
	@Override
	public Dialog<Array> createSettingsDialog(Array data) {
		if (data==null) {
			Array newArray=new Array(); 
			newArray.nameProperty().setValue("New Array");
			//add reference array as parent. 
			newArray.parentArrayProperty().setValue(ArrayModelControl.getInstance().getArrays().get(0));
			return ArrayDialog.createDialog(newArray);
		}
		else return ArrayDialog.createDialog(data);
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
	
