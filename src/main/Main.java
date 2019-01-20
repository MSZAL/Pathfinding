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
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("FXMLPathFinding.fxml"));
    	Scene scene = new Scene(root, 840, 560);
    	
        primaryStage.setTitle("Pathfinding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

        public static void main (String[]args){
            launch(args);
        }
}
