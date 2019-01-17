package main;

import javax.annotation.PreDestroy;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Node extends Rectangle {

    public double globalCost;
    public double localCost;

	public Node predecessor;
	
    private boolean isWall;

    public Node() {
    	super();
    }
    
    public Node(int x, int y, double width, double height) {
        super(x,y, width, height);
        isWall = false;
        globalCost = Double.MAX_VALUE;
        localCost = Double.MAX_VALUE;
        predecessor = null;
    }

    public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}
	
	public void reset(boolean keepWall) {
		isWall = keepWall;
	    globalCost = Double.MAX_VALUE;
	    localCost = Double.MAX_VALUE;
	    predecessor = null;
	}
}
