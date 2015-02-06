package layout;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import main.ArrayModelControl;
import dataUnits.Array;

/**
 * Combo box showing current arrays. 
 * @author Jamie Macaualy.
 *
 */
public class ParentArrayComboBox extends ComboBox<Array>{
	
	
	public ParentArrayComboBox(){
		this.setItems(ArrayModelControl.getInstance().getArrays());
		//need to convert from object to name. 
		this.setConverter(new StringConverter<Array>() {
	        @Override
	        public String toString(Array user) {
	          if (user == null){
	            return null;
	          } else {
	            return user.nameProperty().getValue();
	          }
	        }
	
	      @Override
	      public Array fromString(String userId) {
	          return null;
	      }
		});
		this.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(this, Priority.ALWAYS);
	}

}
