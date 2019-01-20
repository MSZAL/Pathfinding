package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.annotation.PreDestroy;

import javafx.scene.paint.Color;

public class Pathfinding {
	
	
	public static boolean dfs(Node start, Node end, final Grid grid) {
		
		Stack<Node> stack = new Stack<>();
		
		HashMap<Node, Boolean> visited = new HashMap<>();
		
		stack.add(start);
		visited.put(start, true);
		
		while (!stack.isEmpty()) {
			Node element = stack.pop();
			
			if (!element.equals(start) && !element.equals(end))
				// element.setFill(Color.BLUE);
			
			if (element.equals(end))
				return true;
			
			List<Node> neighbors = grid.getAdjacentNodes(element);
			
			for (Node n : neighbors) {
				if (visited.get(n) == null && !n.isWall()) {
					stack.add(n);
					visited.put(n,true);
					if (!n.equals(start) && !n.equals(end))
						n.setFill(Color.AQUAMARINE);
				}
			}
		}
		
		return false;
	}
	
	private static Node getLowestDistanceNodeDijkstra(Set<Node> unvisited) {
		Node lowestDistanceNode = null;
		double lowestDistance = Double.MAX_VALUE;
		
		for (Node node : unvisited) {
			if (node.localCost < lowestDistance) {
				lowestDistance 	   = node.localCost;
				lowestDistanceNode = node;
			}	
		}
		return lowestDistanceNode;
	}
	
	public static boolean dijkstra(Node start, Node end, final Grid grid) {
		
		Set<Node> unvisited = new HashSet<>();
		Set<Node> visited 	= new HashSet<>();
		
		start.localCost = 0;
		
		unvisited.add(start);
		
		while (!unvisited.isEmpty()) {
			Node current = getLowestDistanceNodeDijkstra(unvisited);
			unvisited.remove(current);
			
			List<Node> neighbors = grid.getAdjacentNodes(current);
			
			for (Node node : neighbors) {
				if (!node.isWall() && !visited.contains(node)) {
					
					double newDistance = current.localCost + 1;
					if (newDistance < node.localCost) {
						node.localCost = newDistance;
						node.predecessor = current;
					}
					unvisited.add(node);
				}
			}
			visited.add(current);
			
			// Highlight all nodes visited
			if (!current.equals(start) && !current.equals(end))
				// current.setFill(Color.YELLOW);
			
			if (visited.contains(end)) break;
		}
		
		Node node = end;
		
		// Highlight path
		while (node.predecessor != null) {
			Node predecessor = node.predecessor;
			if (!predecessor.equals(start))
				predecessor.setFill(Color.AQUAMARINE);
			node = predecessor;
		}
		
		return false;
	}
	
	private static Node getLowestGlobalCostNode(Set<Node> set) {
		
		Node lowestNode = null;
		
		for (Node x : set) {
			
			if (lowestNode == null)
				lowestNode = x;
			else {
				if (x.globalCost < lowestNode.globalCost)
					lowestNode = x;
			}
			
		}
		
		return lowestNode;
	}
	
	// Calculated using Manhattan distance
	private static double calculateManhattanDistance(Node current, Node end) {
		return  Math.abs(current.getX() - end.getX()) +
				Math.abs(current.getY() - end.getY());
	}
	
	// Calculate using Pythagorean theorem
	private static double pythagoreanDistance(Node current, Node end) {
		
		return Math.sqrt(Math.pow(end.getX() - current.getX(), 2)
				+ Math.pow(end.getY() - current.getY(), 2));
	}
	
	public static boolean aStar(Node start, Node end, final Grid grid) {
		
		Set<Node> unvisited = new HashSet<>();
		Set<Node> visited = new HashSet<>();
		
		start.localCost = 0;
		start.globalCost = calculateManhattanDistance(start, end);
		//start.globalCost = pythagoreanDistance(start, end);
		
		unvisited.add(start);
		
		while (!unvisited.isEmpty()) {
			
			// Next node from unvisited has the lowest global cost
			Node current = getLowestGlobalCostNode(unvisited);
			unvisited.remove(current);
			
			List<Node> neighbors = grid.getAdjacentNodes(current);
			
			for (Node x : neighbors) {
				if (!x.isWall()) {
					
					double newLocal = current.localCost + 1;
					
					if (newLocal < x.localCost) {
						x.localCost = newLocal;
						x.globalCost = x.localCost + calculateManhattanDistance(x, end);
//						x.globalCost = x.localCost + pythagoreanDistance(x, end);
						x.predecessor = current;
					}
					
					if (!unvisited.contains(x) && !visited.contains(x)  ) {
						unvisited.add(x);
					}
				}
			}
			visited.add(current);
			
			if (!current.equals(start) && !current.equals(end))
				// current.setFill(Main.VISITED);
			
			if (visited.contains(end)) break;
		}
		
		Node predecessor = end.predecessor;
		while (predecessor != null) {
			if (predecessor.equals(start)) return true;
			predecessor.setFill(Main.PATH);
			predecessor = predecessor.predecessor;
		}
		
		return false;
	}
}