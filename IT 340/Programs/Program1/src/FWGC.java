/**
 * This class implements the FWGC problem.
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author kwdalle
 *
 */
public class FWGC implements ProblemBase {

	private static final int[] GOAL_STATE = { 1, 1, 1, 1 }; // This is the goal
															// state we are
															// looking to
															// achieve
	private int[] currentState; // This will hold the current state

	public FWGC() {
		currentState = new int[] { 0, 0, 0, 0 };
	}

	public FWGC(int[] startingState) { // If given an array when creating it
										// copies it into the current state.
		currentState = new int[] { startingState[0], startingState[1],
				startingState[2], startingState[3] };
	}

	public FWGC(int f, int w, int g, int c) { // If given individual positions
												// it will add them to the state
		currentState = new int[] { f, w, g, c };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ilstu.it340.program1.kwdalle.ProblemBase#goalStateCheck()
	 */
	@Override
	public boolean goalStateCheck() {
		if (Arrays.equals(currentState, GOAL_STATE)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ilstu.it340.program1.kwdalle.ProblemBase#generateSuccessors()
	 */
	@Override
	public ArrayList<ProblemBase> generateSuccessors() { // Generates all
															// possible states
															// based on the
															// position of the
															// farmer, than
															// discards the
															// invalid ones.
		ArrayList<int[]> successors = new ArrayList<int[]>();
		if (currentState[0] == 0) {
			if (currentState[1] == 0) {
				successors.add(this.move(1, this.currentState, 1));
			}
			if (currentState[2] == 0) {
				successors.add(this.move(2, this.currentState, 1));
			}
			if (currentState[3] == 0) {
				successors.add(this.move(3, this.currentState, 1));
			}
		}
		if (currentState[0] == 1) {
			if (currentState[1] == 1) {
				successors.add(this.move(1, this.currentState, 0));
			}
			if (currentState[2] == 1) {
				successors.add(this.move(2, this.currentState, 0));
			}
			if (currentState[3] == 1) {
				successors.add(this.move(3, this.currentState, 0));
			}
			successors.add(this.move(0, this.currentState, 0));
		}
		for (int i = 0; i < successors.size(); i++) { // Compares the temp
														// states to all
														// possible invalid
														// cases.
			if (Arrays.equals(successors.get(i), new int[] { 1, 1, 0, 0 })
					|| (Arrays.equals(successors.get(i),
							new int[] { 1, 0, 0, 0 }))
					|| (Arrays.equals(successors.get(i),
							new int[] { 1, 0, 0, 1 }))
					|| (Arrays.equals(successors.get(i),
							new int[] { 0, 1, 1, 0 }))
					|| (Arrays.equals(successors.get(i),
							new int[] { 0, 0, 1, 1 }))
					|| (Arrays.equals(successors.get(i),
							new int[] { 0, 1, 1, 1 }))
					|| (Arrays.equals(successors.get(i),
							new int[] { 0, 0, 0, 0 }))) {
				successors.remove(i);
				i = 0;
			}
			if (Arrays.equals(successors.get(i), currentState)) { // Throws away
																	// any
																	// action
				// that would lead to
				// the current state.
				successors.remove(successors.indexOf(successors.get(i)));
				i = 0;
			}
		}
		// Stores in an ArrayList of objects so that it is general.
		ArrayList<ProblemBase> successorStates = new ArrayList<ProblemBase>();
		for (int i = 0; i < successors.size(); i++) {
			successorStates.add(new FWGC(successors.get(i)));
		}
		return successorStates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ilstu.it340.program1.kwdalle.ProblemBase#operatorCost()
	 */
	@Override
	public int operatorCost() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ilstu.it340.program1.kwdalle.ProblemBase#printState()
	 */
	@Override
	public void printState() {
		System.out.println("Farmer position " + currentState[0]);
		System.out.println("Wolf position " + currentState[1]);
		System.out.println("Goat position " + currentState[2]);
		System.out.println("Cabbage position " + currentState[3]);
	}

	private int[] move(int thing, int[] temp, int newPos) {
		int[] temp1 = Arrays.copyOf(temp, temp.length); // Creates a copy of the
														// currentState so its
														// not changed.
		temp1[0] = newPos;
		temp1[thing] = newPos;
		return temp1;
	}

	@Override
	public int compareState() {
		int diff = 0;
		for (int i = 0; i < this.currentState.length; i++) {
			if (this.currentState[i] != GOAL_STATE[i]) {
				diff++;
			}
		}
		return diff;
	}

	public int getPos(int thing) {
		return this.currentState[thing];
	}

	public boolean equals(ProblemBase state2) {
		if (this.currentState[0] == state2.getCurrentState()[0]
				&& this.currentState[1] == state2.getCurrentState()[1]
				&& this.currentState[2] == state2.getCurrentState()[2]
				&& this.currentState[3] == state2.getCurrentState()[3]) {
			return true;
		} else
			return false;
	}

	public int[] getCurrentState() {
		return currentState;
	}

}
