package layout;

import org.controlsfx.glyphfont.Glyph;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Pane which controls to batch process data. 
 * @author Jamie Macaulay
 *
 */
public class ImportArrayPane extends HBox implements ControlPane {
	
	private ComboBox<String> filePath;
	
	/**
	 *Create a process pane which contains basic controls such as loading a config and batch procesing. 
	 */
	public ImportArrayPane(){
		
		
//		Label importLabel=new Label("Import Array");
//		importLabel.setAlignment(Pos.CENTER);
		

		//HBox for importing data
		filePath=new ComboBox<String>(); 
		filePath.setEditable(true);
		//filePath.setMaxWidth(Double.MAX_VALUE);
		//HBox.setHgrow(filePath, Priority.ALWAYS);

		Button browseButton =new Button("", Glyph.create("FontAwesome|FILE"));
		browseButton.setOnAction((action)->{
			//browse for settings file. 
		});
		browseButton.prefHeightProperty().bind(filePath.heightProperty());
		
		HBox importHBox=new HBox(); 
		importHBox.setAlignment(Pos.CENTER_LEFT);
		//importHBox.setStyle("-fx-background-color: red;");
		importHBox.setSpacing(5);
		importHBox.getChildren().addAll(filePath, browseButton); 
		
		//save data button
		Button saveButton =new Button("", Glyph.create("FontAwesome|SAVE"));
		saveButton.setOnAction((action)->{
			//browse for settings file. 
		});
		saveButton.prefHeightProperty().bind(filePath.heightProperty());

	
		this.setSpacing(10);
		this.getChildren().addAll(importHBox, saveButton);  
		this.setPadding(new Insets(5));

	}

	@Override
	public void notifyChange(ChangeType type) {
		// TODO Auto-generated method stub
		
	}

}
