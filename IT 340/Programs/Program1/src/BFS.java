/**
 * 
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

/**
 * @author kwdalle
 *
 */
public class BFS {
	private Queue<Node> frontier = new LinkedList<Node>();
	private int nodesExpanded = 0;
	private int cost = 0;

	private Node currentNode;

	public BFS(Node node) {
		currentNode = node;
	}

	public void search() {
		Stack<Node> path = new Stack<Node>();
		// Searches through the generated Successors till one at the beginning
		// of the queue is the goal state.
		while (!currentNode.goalStateCheck()) {
			for (ProblemBase temp : currentNode.generateSuccessors()) {
				Node temp1 = new Node(temp, currentNode);
				if (!this.reapeats(temp1))
				{
						frontier.add(temp1);
				}
			}
			// After generating the successors we add to the nodes expanded.
			nodesExpanded++;
			currentNode = frontier.poll();
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
		Node checkNode = n;
		// Look for repeats while were not at the root and we have not found one
		// already.
		while (n.getParent() != null && !retVal) {
			if (n.getParent().getStateObject()
					.equals(checkNode.getStateObject())) {
				retVal = true;
			}
			n = n.getParent();
		}
		return retVal;
	}
}
