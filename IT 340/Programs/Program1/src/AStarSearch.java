/**
 * 
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.*;
import java.util.Stack;

/**
 * @author kwdalle
 *
 */
public class AStarSearch {
	private ArrayList<Node> frontier = new ArrayList<Node>(); 
	private int nodesExpanded = 0;
	private int cost = 0;
	private String hueristic;

	private Node currentNode;

	public AStarSearch(Node node) {
		currentNode = node;
		this.hueristic = node.getHueristic();
	}

	public void search() {
		Stack<Node> path = new Stack<Node>();
		// Searches through the generated Successors till one at the beginning
		// till one of the nodes in the array list is the goal state, and thus will have the best heuristic (i.e 0) and will be chosen.
		while (!currentNode.goalStateCheck()) {
			for (ProblemBase temp : currentNode.generateSuccessors()) {
				Node temp1 = new Node(temp, currentNode, hueristic);
				if (!this.reapeats(temp1))
				{
						frontier.add(temp1);
				}
			}
			nodesExpanded++;
			int bestIndex = 0;
			// chooses the best index based on whether it is better or equal to the current based on the heuristic.
			// I chose to let it take states that were even since its possible that a move will only generate states
			// that have an equal heuristic or worse. In testing this actually caused a dramatic reduction in the 
			// number of nodes expanded by Manhattan A*.
			for (int k = 0; k < frontier.size(); k++)
			{
				if(frontier.get(k).getPriority() <= frontier.get(bestIndex).getPriority())
				{
					bestIndex = k;
				}
			}
			currentNode = frontier.remove(bestIndex);
		}
		// If the current node is a goal state then it will back track its
		// parents till it reaches one who does not have a parent (the root)
		if (currentNode.goalStateCheck()) {
			while (currentNode.getParent() != null) {
				path.push(currentNode);
				cost += currentNode.operatorCost();
				currentNode = currentNode.getParent();
			}
		}
		// Prints the root and then prints the rest of the path and its cost to
		// get there.
		currentNode.printState();

		while (!path.empty()) {
			System.out.println();
			path.pop().printState();
		}
		System.out.println("Total Cost: " + cost);
	}

	public int nodesExpanded() {
		// Used for debugging if you want to know how many nodes were expanded.
		return nodesExpanded;
	}
	
	public boolean reapeats(Node n) {
		boolean retVal = false;
		Node checkNode = n.getParent();
		// Look for repeats while were not at the root and we have not found one
		// already.
		while (checkNode != null && !retVal) {
			if (n.getStateObject()
					.equals(checkNode.getStateObject())) {
				retVal = true;
			}
			checkNode = checkNode.getParent();
		}
		return retVal;
	}
}
