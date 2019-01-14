package main;

import java.util.ArrayList;

public class Grid {

    public static final int N = 25;
    public static Grid grid;
    private Node[][] area;

    public static Grid getInstance() {
        if (grid == null) {
            grid = new Grid();
            return grid;
        }
        return grid;
    }

    private Grid() {
        area = new Node[N][N];
    }

    public void setNode(Node b) {
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
    		adjNodes.add(grid.getNode(posX - 1, posY));
    	if (posX + 1 < Grid.N)
    		adjNodes.add(grid.getNode(posX + 1, posY));
    	if (posY - 1 >= 0)
    		adjNodes.add(grid.getNode(posX, posY - 1));
    	if (posY + 1 < Grid.N)
    		adjNodes.add(grid.getNode(posX, posY + 1));
    	
    	return adjNodes;
    }
    
    public boolean isInGrid(int x, int y) {
    	if (x < 0 || y < 0) 			return false;
    	if (x >= Grid.N || y >= Grid.N) return false;
    	return true;
    }

}
