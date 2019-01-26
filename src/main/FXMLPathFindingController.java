package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FXMLPathFindingController {
    @FXML private GridPane gridPane;
    @FXML private TextField dimensionsField;
    @FXML private ToggleGroup toggleGroup;
    @FXML private CheckBox allowDiagonal;
    
    private Grid grid;
    private int dimensions;
    
    private double currentMouseX = -1;
    private double currentMouseY = -1;
    
    private Node start = null;
    private Node end = null;
    
    private int getRow(double x) {
    	return (int) (x / ((gridPane.getWidth() / dimensions)));
    }
    
    private int getColumn(double y) {
    	return (int) (y / ((gridPane.getHeight() / dimensions)));
    }
    
    private boolean isValidMousePosition(double x, double y) {
    	
    	if (x < 0 || x >= gridPane.getWidth() || y < 0 || y >= gridPane.getHeight())
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
		
		findPath();
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
    	
    	findPath();
    	
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
    	
    	findPath();
    }
    
    private void reset() {
    	for (int i = 0; i < dimensions; i++) {
    		for (int j = 0; j < dimensions; j++) {
    			Node node = grid.getNode(i, j);
    			if (!node.isWall() && !node.equals(start) && !node.equals(end))
    				node.setFill(Color.WHITE);
    		}
    	}
    }
    
    private void findPath() {
    	if (toggleGroup.getSelectedToggle() == null) return;
    	if (start == null || end == null) {
    		grid.resetCosts(false);
    		reset();
    		return;
    	}
    	
    	String algorithm = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    	boolean diagonal = allowDiagonal.isSelected();
    	
    	grid.resetCosts(false);
    	reset();
    	
    	if (algorithm.equals("AStar")) {
    		Pathfinding.aStar(start, end, diagonal, grid);
    	}
    	else if (algorithm.equals("DFS")) {
    		Pathfinding.dfs(start, end, diagonal, grid);
    	}
    	else if (algorithm.equals("Dijkstra")) {
    		Pathfinding.dijkstra(start, end, diagonal, grid);
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
    	
    	start = null;
    	end = null;
    	
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
    	
    	// When user has checked or unchecked, should run current algorithm with option
    	ChangeListener<Boolean> checkBoxListener = new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				findPath();
				gridPane.requestFocus();
			}
    	};
    	
    	// When user changes their algorithm option it should run the newly selected algorithm
    	ChangeListener<Toggle> toggleListener = new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				findPath();
				gridPane.requestFocus();
			}
    		
    	};
    	
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
              
              // Tracking mouse movement for later use in the keyHandler to set start/end
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
				}
				
				// Assign end node
				else if (key == KeyCode.E) {
					setEnd(currentMouseX, currentMouseY);
				}
			}
    		
    	};
    	
    	toggleGroup.selectedToggleProperty().addListener(toggleListener);
    	allowDiagonal.selectedProperty().addListener(checkBoxListener);
    	
    	gridPane.setOnMouseClicked(mouseHandler);
    	gridPane.setOnMouseDragged(mouseHandler);
    	gridPane.setOnMouseMoved(mouseHandler);
    	gridPane.setOnKeyPressed(keyHandler);
    	
    	gridPane.requestFocus(); // Required or key events will not register
    }
}
