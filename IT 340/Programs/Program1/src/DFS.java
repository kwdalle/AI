/**
 * 
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.Stack;

/**
 * @author kwdalle
 *
 */
public class DFS {
	private Stack<Node> frontier = new Stack<Node>();

	private Node currentNode;
	private int nodesExpanded = 0;
	private int cost = 0;

	public DFS(Node node) {
		currentNode = node;
	}

	public void search() {
		Stack<Node> path = new Stack<Node>();
		// Similar to the BFS except it uses a stack instead of a queue.
		while (!currentNode.goalStateCheck()) {
			for (ProblemBase temp : currentNode.generateSuccessors()) {
				Node temp1 = new Node(temp, currentNode);
				if (!this.reapeats(temp1)) {
					frontier.push(temp1);
				}
			}
			nodesExpanded++;
			currentNode = frontier.pop();
		}
		if (currentNode.goalStateCheck()) {
			while (currentNode.getParent() != null) {
				path.push(currentNode);
				cost += currentNode.operatorCost();
				currentNode = currentNode.getParent();
			}
		}
		currentNode.printState();

		while (!path.empty()) {
			System.out.println();
			path.pop().printState();
		}
		System.out.println("Total Cost: " + cost);
	}

	public int nodesExpanded() {
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
