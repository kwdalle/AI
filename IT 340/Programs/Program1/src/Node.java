/**
 * This is a wrapper class for ProblemBase objects.
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.ArrayList;

/**
 * @author kwdalle
 *
 */
public class Node {
	private ProblemBase state;
	private Node parent;
	private int cost;
	private String hueristic;
	private int priority;

	public Node(ProblemBase item, Node parent, String heuristic) {
		// This constructor is used for A* to get the heuristic as an attribute
		// of the state.
		state = item; // Keeps the state within the node.
		this.parent = parent; // Points to the parent.
		cost = this.parent.getCost() + 1;
		this.hueristic = heuristic;
		if (heuristic.toLowerCase().equals("manhattan")) {
			this.priority = ((EightPuzzle) state).manhattanDistance() + cost;
		} else {
			this.priority = ((EightPuzzle) state).compareState() + cost;
		}
	}

	public Node(ProblemBase item, Node parent) {
		state = item; // Keeps the state within the node.
		this.parent = parent; // Points to the parent.
		cost = this.parent.getCost() + 1;
	}

	public Node(ProblemBase item, String hueristic) {
		// This is another constructor for A*, this one is used for the very
		// first node, hence why the parent is null.
		state = item;
		parent = null;
		cost = 0;
		this.hueristic = hueristic;
		if (hueristic.toLowerCase().equals("manhattan")) {
			this.priority = ((EightPuzzle) state).manhattanDistance() + cost;
		} else {
			this.priority = ((EightPuzzle) state).compareState() + cost;
		}
	}

	public Node(ProblemBase item) {
		state = item;
		parent = null;
		cost = 0;
	}

	// Wrapper functions for things needed for search.
	public boolean goalStateCheck() {
		return state.goalStateCheck();
	}

	public ArrayList<ProblemBase> generateSuccessors() {
		return state.generateSuccessors();
	}

	public void printState() {
		state.printState();
	}

	public Node getParent() {
		return parent;
	}

	public int[] getState() {
		return state.getCurrentState();
	}

	public int operatorCost() {
		return state.operatorCost();
	}

	public ProblemBase getStateObject() {
		return state;
	}

	public int getCost() {
		return cost;
	}

	public String getHueristic() {
		return hueristic;
	}

	public int getPriority() {
		return priority;
	}

}
