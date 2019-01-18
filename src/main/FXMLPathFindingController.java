package main;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;

public class FXMLPathFindingController {
    @FXML private GridPane gridPane;
    @FXML private TextField dimensionsField;
    @FXML private ToggleGroup toggleGroup;
    
    private Grid grid;
    private int dimensions;
    
    private double currentMouseX = -1;
    private double currentMouseY = -1;
    
    private Node start = null;
    private Node end = null;
    
    private int getRow(double x) {
    	return (int) (x / (gridPane.getWidth() / dimensions));
    }
    
    private int getColumn(double y) {
    	return (int) (y / (gridPane.getHeight() / dimensions));
    }
    
    private boolean isValidMousePosition(double x, double y) {
    	
    	if (x < 0 || x > gridPane.getWidth() || y < 0 || y > gridPane.getHeight())
    		return false;
    	return true;
    }
    
	private void setObstacle(double x, double y, boolean setWall) {
		
		if (!isValidMousePosition(x, y)) return;
		
		Node node = grid.getNode(getColumn(y), getRow(x));
		
		if (setWall) {
			node.setFill(Color.BLACK);
		}
		else {
			node.setFill(Color.WHITE);
		}
		
		node.setWall(setWall);
	}
	
    private void setStart(double x, double y) {
    	if (!isValidMousePosition(x, y)) return;
    	
    	Node node = grid.getNode(getColumn(y), getRow(x));
    	
    	if (node.equals(end)) {
    		end = null;
    	}
    	
    	if (start != null) {
    		start.setFill(Color.WHITE);
    	}
    	start = node;
    	start.setFill(Color.GREEN);
    	start.setWall(false);
    	
    }
    
    private void setEnd(double x, double y) {
    	if (!isValidMousePosition(x, y)) return;
    	
    	Node node = grid.getNode(getColumn(y), getRow(x));
    	
    	if (node.equals(start)) {
    		start = null;
    	}
    	
    	if (end != null) {
    		end.setFill(Color.WHITE);
    	}
    	end = node;
    	end.setFill(Color.RED);
    	end.setWall(false);
    }
    
    private void findPath() {
    	if (toggleGroup.getSelectedToggle() == null) return;
    	if (start == null || end == null) return;
    	
    	String algorithm = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    	
    	if (algorithm.equals("AStar")) {
    		Pathfinding.aStar(start, end, grid);
    	}
    	else if (algorithm.equals("DFS")) {
    		Pathfinding.dfs(start, end, grid);
    	}
    	else if (algorithm.equals("Dijkstra")) {
    		Pathfinding.dijkstra(start, end, grid);
    	}
    }
        
    @FXML protected void handleGenerateButtonAction(ActionEvent event) {
    	
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
				if (event.getButton() == MouseButton.PRIMARY) {
                  switch (event.getEventType().getName()) {
                      case "MOUSE_CLICKED":
                      case "MOUSE_DRAGGED":
                          setObstacle(event.getX(), event.getY(), true);
                          findPath();
                          break;
                  }
              }

              if (event.getButton() == MouseButton.SECONDARY) {
                  switch (event.getEventType().getName()) {
                      case "MOUSE_CLICKED":
                      case "MOUSE_DRAGGED":
                          setObstacle(event.getX(), event.getY(), false);
                          findPath();
                          break;
                  }
              }
              
              if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            	  currentMouseX = event.getX();
            	  currentMouseY = event.getY();
              }
			}
    	};
    	
    	EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode key = event.getCode();
				
				// Assign start node
				if (key == KeyCode.S) {
					setStart(currentMouseX, currentMouseY);
					findPath();
				}
				
				// Assign end node
				else if (key == KeyCode.E) {
					setEnd(currentMouseX, currentMouseY);
					findPath();
				}
			}
    		
    	};
    	
    	gridPane.setOnMouseClicked(mouseHandler);
    	gridPane.setOnMouseDragged(mouseHandler);
    	gridPane.setOnMouseMoved(mouseHandler);
    	gridPane.setOnKeyPressed(keyHandler);
    	
    	gridPane.requestFocus(); // Required or key events will not register
    }
}
