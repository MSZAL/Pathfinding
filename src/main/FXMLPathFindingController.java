package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class FXMLPathFindingController {
    @FXML private GridPane gridPane;
    @FXML private TextField dimensionsField;
    
    private Grid grid;
        
    @FXML protected void handleGenerateButtonAction(ActionEvent event) {
    	
    	int dimensions = 0;
    	
    	try {
    		dimensions = Integer.parseInt(dimensionsField.getText());
    	}
    	catch (NumberFormatException nfe) {
    		System.out.println("Error: Have not provided an appropriate dimensions");
    	}
    	
    	if (dimensions <= 0) return;
    	
    	gridPane.getChildren().clear();
    	
    	grid = new Grid(dimensions);
    	
    	for (int row = 0; row < dimensions; row++) {
    		for (int col = 0; col < dimensions; col++) {
    			Node node = new Node();
    			node.setStroke(Color.DARKGRAY);
    			node.setFill(Color.WHITE);
    			node.setStrokeWidth(.5);
    			node.setX(row);
    			node.setY(col);
    			node.setWidth((gridPane.getWidth() / dimensions) - 1);
    			node.setHeight((gridPane.getHeight() / dimensions) - 1);
    			grid.setNode(node);
    			gridPane.add(node, col, row);
    		}
    	}
    	
    	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				//System.out.println("Mouse Event Detected");
			}
    	};
    	
    	gridPane.setOnMouseClicked(mouseHandler);
    }
}
