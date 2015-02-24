package layout.arrays;

import layout.ArrayModelView;
import layout.ControlPane;
import layout.utils.TablePane;
import main.HArrayModelControl;
import dataUnits.hArray.HArray;
import dataUnits.hArray.RigidHArray;
import main.HArrayManager.ArrayType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

/**
 * Pane to add, remove and edit arrays. 
 * @author Jamie Macaulay
 *
 */
public class ArrayTablePane extends TablePane<HArray> implements ControlPane {
	
	public ArrayTablePane(ArrayModelView mainPane){
		super(mainPane.getArrayModelControl().getArrays()); 

		TableColumn<HArray,String>  arrayID = new TableColumn<HArray,String>("Array Name");
		arrayID.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		TableColumn<HArray,ArrayType> arrayType = new TableColumn<HArray, ArrayType>("Array Type");
		arrayType.setCellValueFactory(cellData -> cellData.getValue().hArrayTypeProperty());

		TableColumn<HArray,String> attachmentArray = new TableColumn<HArray, String>("Parent Array");
		attachmentArray.setCellValueFactory(cellData -> {
			if (cellData.getValue().parentHArrayProperty().getValue()==null) return  new ReadOnlyObjectWrapper<String>("none");
			else return cellData.getValue().parentHArrayProperty().get().nameProperty();
		});

		TableColumn<HArray,Number> numHydrophones = new TableColumn<HArray,Number>("No. Hydrophones");
		numHydrophones.setCellValueFactory(cellData -> Bindings.size(cellData.getValue().getHydrophones()));
		

		TableColumn<HArray,Number> numSensors = new TableColumn<HArray,Number>("No. Sensors");
		numSensors.setCellValueFactory(cellData -> Bindings.size(cellData.getValue().getMovementSensors()));

		TableColumn parentPoint = new TableColumn("Attachment Point");

		TableColumn<HArray,Number> xPos = new TableColumn<HArray,Number>("x (m)");
		xPos.setCellValueFactory(cellData -> cellData.getValue().xPosProperty());

		TableColumn<HArray,Number> yPos = new TableColumn<HArray,Number>("y (m)");
		yPos.setCellValueFactory(cellData -> cellData.getValue().yPosProperty());

		TableColumn<HArray,Number> zPos = new TableColumn<HArray,Number>("z (m)");
		zPos.setCellValueFactory(cellData -> cellData.getValue().zPosProperty());
        
		//override default row behaviour in order to prevent the first row from being selected.  
		getTableView().setRowFactory( tv -> {
			TableRow<HArray> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (row.isEmpty()) return; 

				if (row.getIndex()==0) {
					event.consume();
				}

				getButtonPane().getSettingsButton().setDisable(row.getIndex()==0);
				getButtonPane().getDeleteButton().setDisable(row.getIndex()==0);

				if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
					HArray rowData = row.getItem();
					editData(rowData);
				}
			});
			return row ;
		});

		//disable buttons so reference array can't be deleted;
		getButtonPane().getSettingsButton().setDisable(true);
		getButtonPane().getDeleteButton().setDisable(true);

		parentPoint.getColumns().addAll(xPos, yPos, zPos);

		getTableView().getColumns().addAll(arrayID, arrayType, attachmentArray, parentPoint, numHydrophones,numSensors);

	}
	
	
	@Override
	public Dialog<HArray> createSettingsDialog(HArray data) {
		if (data==null) {
			HArray newArray=new RigidHArray(); 
			newArray.nameProperty().setValue("New Array");
			//add reference array as parent. 
			newArray.parentHArrayProperty().setValue(HArrayModelControl.getInstance().getArrays().get(0));
			return HArrayDialog.createDialog(newArray);
		}
		else return HArrayDialog.createDialog(data);
	}


	@Override
	public void notifyChange(ChangeType type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dialogClosed(HArray data) {
		// TODO Auto-generated method stub
		
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
	
