package layout.utils;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import main.HArrayModelControl;
import dataUnits.hArray.HArray;

/**
 * Combo box showing current arrays. 
 * @author Jamie Macaualy.
 *
 */
public class ParentArrayComboBox extends ComboBox<HArray>{
	
	
	public ParentArrayComboBox(){
		this.setItems(HArrayModelControl.getInstance().getArrays());
		//need to convert from object to name. 
		this.setConverter(new StringConverter<HArray>() {
	        @Override
	        public String toString(HArray user) {
	          if (user == null){
	            return null;
	          } else {
	            return user.nameProperty().getValue();
	          }
	        }
	
	      @Override
	      public HArray fromString(String userId) {
	          return null;
	      }
		});
		this.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(this, Priority.ALWAYS);
	}

}
