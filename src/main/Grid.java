package main;

import java.util.ArrayList;

public class Grid {    
    private Node[][] area;
    private int dimensions;

    public Grid(int dimensions) {
    	this.dimensions = dimensions;
        area = new Node[dimensions][dimensions];
    }

    public void setNode(Node b) {
    	
    	double x = b.getX();
    	double y = b.getY();
    	
        area[(int) b.getX()][(int) b.getY()] = b;
    }


    public Node getNode(int x, int y) {
        return area[x][y];
    }
    
    // Returns nodes top, right, down, left if valid
    public ArrayList<Node> getAdjacentNodes(Node b) {
    	ArrayList<Node> adjNodes = new ArrayList<>();
    	
    	int posX = (int) b.getX();
    	int posY = (int) b.getY();
    	
    	if (posX - 1 >= 0)
    		adjNodes.add(getNode(posX - 1, posY));
    	if (posX + 1 < dimensions)
    		adjNodes.add(getNode(posX + 1, posY));
    	if (posY - 1 >= 0)
    		adjNodes.add(getNode(posX, posY - 1));
    	if (posY + 1 < dimensions)
    		adjNodes.add(getNode(posX, posY + 1));
    	
    	return adjNodes;
    }
    
    public boolean isInGrid(int x, int y) {
    	if (x < 0 || y < 0) 			return false;
    	if (x >= dimensions || y >= dimensions) return false;
    	return true;
    }
    
    public void resetCosts() {
    	for (int i = 0; i < area.length; i++) {
    		for (int j = 0; j < area[0].length; j++) {
    			Node node = getNode(i, j);
    			node.reset();
    		}
    	}
    }
}
