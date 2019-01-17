package main;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static final Color START_COLOR = Color.GREEN;
    public static final Color END_COLOR = Color.RED;
    public static final Color OBSTACLE = Color.BLACK;
    public static final Color EMPTY = Color.WHITE;
    public static final Color VISITED = Color.YELLOW;
    public static final Color PATH = Color.AQUAMARINE;
    

//    public double boxDimension = 35;
//    public double maxDimension; // = boxDimension * Grid.N;
//
//    private double strokeWidth;

//    private Grid grid = Grid.getInstance();
//    private Node start;
//    private Node end;
//
//    private boolean isValidPoint(double x, double y) {
//        if (x < 0 || y < 0 || x >= maxDimension || y >= maxDimension) { return false; }
//        return true;
//    }
//
//    private int getRow(double x) {
//        return (int) (x/(boxDimension));
//    }
//
//    private int getCol(double y) {
//        return (int) (y/(boxDimension));
//    }
//
//    private void setStart() {
//        start = grid.getNode(0,0);
//        start.setFill(START_COLOR);
//    }
//
//    private void setEnd() {
//        end = grid.getNode(Grid.N - 1, Grid.N - 1);
//        end.setFill(END_COLOR);
//    }
//
//    private void setObstacle(double x, double y, boolean set) {
//        if (!isValidPoint(x,y)) return;
//        int posX = getRow(x);
//        int posY = getCol(y);
//
//        Node node = grid.getNode(posX,posY);
//
//        if (node.equals(start) || node.equals(end)) return;
//
//        if (set) {
//            node.setFill(OBSTACLE);
//            node.setWall(true);
//        }
//        else {
//            node.setFill(EMPTY);
//            node.setWall(false);
//        }
//    }
//    
//    private void resetNodes() {
//    	for (int row = 0; row < Grid.N; row++) {
//    		for (int col = 0; col < Grid.N; col++) {
//    			Node node = grid.getNode(row, col);
//    			if (!node.equals(start) && !node.equals(end)) {
//    				
//    				if (node.isWall()) {
//    					node.reset(true);
//    				}
//    				else {
//    					node.reset(false);
//    					node.setFill(EMPTY);
//    				}
//    			}
//    		}
//    	}
//    	
//    	start.reset(false);
//    	end.reset(false);
//    	
//    	start.setFill(START_COLOR);
//    	end.setFill(END_COLOR);
//    }

    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("FXMLPathFinding.fxml"));
    	Scene scene = new Scene(root, 840, 600);
    	
        primaryStage.setTitle("Pathfinding");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        GridPane root = new GridPane();
//
//        for (int i = 0; i < Grid.N; i++) {
//            for (int j = 0; j < Grid.N; j++) {
//                Node b = new Node(j,i, boxDimension); // j & i are swapped because of setConstraints definition
//                grid.setNode(b);
//                b.setFill(EMPTY);
//                b.setStroke(Color.BLACK);
//                strokeWidth = b.getStrokeWidth();
//                root.setHgap(0);
//                root.setVgap(0);
//                GridPane.setConstraints(b, j, i);
//                root.getChildren().add(b);
//            }
//        }
//        boxDimension += strokeWidth;
//        maxDimension = boxDimension * Grid.N;
//
//        setStart();
//        setEnd();
//
//        Scene scene = new Scene(root, maxDimension, maxDimension, Color.WHITE);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("A* Pathing");
//        primaryStage.show();
//
//        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.getButton() == MouseButton.PRIMARY) {
//                    switch (event.getEventType().getName()) {
//                        case "MOUSE_CLICKED":
//                        case "MOUSE_DRAGGED":
//                            // System.out.println("DRAGGING");
//                            setObstacle(event.getX(), event.getY(), true);
//                            break;
//                    }
//                }
//
//                if (event.getButton() == MouseButton.SECONDARY) {
//                    switch (event.getEventType().getName()) {
//                        case "MOUSE_CLICKED":
//                        case "MOUSE_DRAGGED":
//                            setObstacle(event.getX(), event.getY(), false);
//                            break;
//                    }
//                }
//            }
//        };
//        
//        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
//        	
//			
//			@Override
//			public void handle(KeyEvent event) {
//				KeyCode key = event.getCode();
//				
//				if (key == KeyCode.D) {
//					Pathfinding.dijkstra(start, end, grid);
//				}
//				else if (key == KeyCode.A) {
//					Pathfinding.aStar(start, end, grid);
//				}
//				else if (key == KeyCode.C) {
//					resetNodes();
//				}
//			}
//		};
//
//        scene.setOnMouseDragged(mouseHandler);
//        scene.setOnMouseClicked(mouseHandler);
//        scene.setOnMouseReleased(mouseHandler);
//        scene.setOnKeyPressed(keyHandler);
    }

        public static void main (String[]args){
            launch(args);
        }
}
