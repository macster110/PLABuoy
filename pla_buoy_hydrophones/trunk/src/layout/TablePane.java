package layout;

import dataUnits.Array;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Pane which holds data wihin a table  and has add, settings and delete buttons. 
 * @author Jamie Macaulay
 *
 * @param <T> - type of data yunit
 */
abstract class TablePane<T> extends BorderPane {

	/**
	 * The table to hold data of type T. 
	 */
    private TableView<T> table;
    
    /**
   	 * Holds all data units.
   	 */
   	ObservableList<T> data = FXCollections.observableArrayList();
    
	public TablePane(){
		table = new TableView<T>();
        this.setCenter(createArrayPane());
	}
	
	/**
	 * Create the pane with table and add, settings and delete buttons. 
	 * @return pane with table and control buttons. 
	 */
	protected Pane createArrayPane(){

		BorderPane arrayPane=new BorderPane();

		//enable double clicking on table row. 
		table.setRowFactory( tv -> {
		    TableRow<T> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            T rowData = row.getItem();
		            editArray(rowData);
		        }
		    });
		    return row ;
		});
		
		table.getItems().setAll(data);


	    //create pane holding add, edit and remove controls
        TableButtonPane buttonPane=new TableButtonPane(Orientation.VERTICAL); 
        buttonPane.getAddButton().setOnAction((event)->{
        	createNewArray();
        });
        
        buttonPane.getSettingsButton().setOnAction((event)->{
        	editArray(table.getSelectionModel().getSelectedItem());
        });
        
        buttonPane.getDeleteButton().setOnAction((event)->{
        	deleteArray(table.getSelectionModel().getSelectedItem());
        });
        
        arrayPane.setCenter(table);
        arrayPane.setRight(buttonPane);
        
		return arrayPane;
	}
	
	private void createNewArray(){
		Dialog<T> arrayDialog=createSettingsDialog(null); 
		arrayDialog.showAndWait();
	}
	
	private void editArray(T data){
		Dialog<T> arrayDialog=createSettingsDialog(data); 
		arrayDialog.showAndWait();
	}
	
	private void deleteArray(T data){
		table.getItems().remove(data);
	}

	/**
	 * Create a dialog to change data settings for each row in table. 
	 * @param data - data to edit. null to  create a new instance of data
	 * @return dialog to change data settigns. 
	 */
	abstract Dialog<T> createSettingsDialog(T data);

	public TableView<T> getTableView() {
		return table;
	}
		

}
