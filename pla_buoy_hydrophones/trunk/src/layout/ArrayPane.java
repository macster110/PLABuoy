package layout;

import javafx.event.EventHandler;
import dataUnits.Array;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * Pane 
 * @author Jamie Macaulay
 *
 */
public class ArrayPane extends BorderPane {
	
    private TableView<Array> table = new TableView<Array>();
    
	public 	Image settingsIcon=new Image(getClass().getResourceAsStream("/resources/SettingsButtonMediumGrey.png"));
    
    /**
	 * Holds all array components.
	 */
	ObservableList<Array> data = FXCollections.observableArrayList(new Array("Array 1", 1),
			new Array("Array 2", 1),
			new Array("Array 2", 2),
			new Array("Array 3", 1)
	);
		
    
	public ArrayPane(){
		
	    TableColumn<Array,String>  arrayID = new TableColumn<Array,String>("Array ID");
	    arrayID.setCellValueFactory(new Callback<CellDataFeatures<Array, String>, ObservableValue<String>>() {
	        public ObservableValue<String> call(CellDataFeatures<Array, String> p) {
	            // p.getValue() returns the Person instance for a particular TableView row
	            return p.getValue().getNameProperty();
	        }
	     });
	    
        TableColumn<Array,String> arrayType = new TableColumn<Array,String>("Array Type");
        arrayType.setCellValueFactory(new Callback<CellDataFeatures<Array, String>, ObservableValue<String>>() {
	        public ObservableValue<String> call(CellDataFeatures<Array, String> p) {
	            // p.getValue() returns the Person instance for a particular TableView row
	            return p.getValue().getNameProperty();
	        }
	     });
        
        TableColumn<Array,Integer> numHydrophones = new TableColumn<Array,Integer>("No. Hydrophone");
        numHydrophones.setCellValueFactory(new Callback<CellDataFeatures<Array, Integer>, ObservableValue<Integer>>() {
	        public ObservableValue<Integer> call(CellDataFeatures<Array, Integer> p) {
	            // p.getValue() returns the Person instance for a particular TableView row
	            return new ReadOnlyObjectWrapper<Integer>(p.getValue().getHydrophones().size());
	        }

	     });
        
        table.getColumns().addAll(arrayID, arrayType, numHydrophones);
        
        //Insert Button
        TableColumn<Array,Boolean> col_action = new TableColumn<Array,Boolean>("Settings");
        col_action.setSortable(false);
         
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Array, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(
							CellDataFeatures<Array, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
                	
                });
        
        col_action.setCellFactory(
                new Callback<TableColumn<Array, Boolean>, TableCell<Array, Boolean>>() {

					@Override
					public TableCell<Array, Boolean> call(
							TableColumn<Array, Boolean> arg0) {
						 return new ButtonCell();
					}

        });
        table.getColumns().add(col_action);
        table.setItems(data);
        
        this.setCenter(table);
        
        
        //bottom button pane
        
        
        
	}
	
	private class ButtonCell extends TableCell<Array, Boolean> {
		
		final Button cellButton;
		
		public ButtonCell(){
			cellButton = new Button();
			cellButton.setGraphic(new ImageView(settingsIcon));
			cellButton.setOnAction((event)->{
				
			});
		}
		
		//Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
	}
	
	
	

}
