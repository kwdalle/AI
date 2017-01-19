/**
 * This class implements the EightPuzzle problem.
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author kwdalle
 *
 */
public class EightPuzzle implements ProblemBase {

	private static final int[] GOAL_STATE = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
	private static final int[][] MOVES = { { 1, 3}, { 0, 2, 4 }, { 1, 5 },
			{ 0, 4, 6 }, { 1, 3, 5, 7}, { 2, 4, 8 }, { 3, 7 }, { 4, 6, 8 },
			{ 5, 7 } };
	private final static int[][] CORRECT_POSITIONS= {{1,1},{1,2},{1,3},{2,1},{2,2},{2,3},{3,1},{3,2},{3,3}};
	private int[] currentState;

	public EightPuzzle() {
		// Creates a generic state if none is given.
		currentState = new int[] { 8, 7, 6, 5, 4, 3, 2, 1, 0 };
	}

	public EightPuzzle(int[] startingState) {
		// Here it creates a state from an array given
		currentState = new int[] { startingState[0], startingState[1],
				startingState[2], startingState[3], startingState[4],
				startingState[5], startingState[6], startingState[7],
				startingState[8] };
	}

	public EightPuzzle(int one, int two, int three, int four, int five,
			int six, int seven, int eight, int nine) {
		// Lastly it creates a state from pure ints.
		currentState = new int[] { one, two, three, four, five, six, seven,
				eight, nine };
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
	public ArrayList<ProblemBase> generateSuccessors() {
		ArrayList<ProblemBase> successorStates = new ArrayList<ProblemBase>();
		// Two for loops, one to find the blank and one to generate states.
		int pos = 0;
		for (int i = 0; i < currentState.length; i++) {
			if (currentState[i] == 0) {
				pos = i;
			}
		}

		for (int i = 0; i < MOVES[pos].length;) {
			int temp = currentState[MOVES[pos][i]];
			currentState[MOVES[pos][i]] = 0;
			currentState[pos] = temp;
			successorStates.add(new EightPuzzle(currentState));
			temp = currentState[pos];
			currentState[pos] = 0;
			currentState[MOVES[pos][i]] = temp;
			i++;
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
		// Simply prints out the states in the correct format.
		System.out.println(currentState[0] + " " + currentState[1] + " "
				+ currentState[2]);
		System.out.println(currentState[3] + " " + currentState[4] + " "
				+ currentState[5]);
		System.out.println(currentState[6] + " " + currentState[7] + " "
				+ currentState[8]);
	}

	@Override
	public int compareState() {
		// compares two different states to find how different they are.
		int diff = 0;
		for (int i = 0; i < this.currentState.length; i++) {
			if (this.currentState[i] != GOAL_STATE[i] && this.currentState[i] !=0) {
				diff++;
			}
		}
		return diff;
	}

	public boolean equals(ProblemBase state2) {
		// The equals method returns that the two objects are equal if their states are equal.
		if (this.currentState[0] == state2.getCurrentState()[0]
				&& this.currentState[1] == state2.getCurrentState()[1]
				&& this.currentState[2] == state2.getCurrentState()[2]
				&& this.currentState[3] == state2.getCurrentState()[3]
				&& this.currentState[4] == state2.getCurrentState()[4]
				&& this.currentState[5] == state2.getCurrentState()[5]
				&& this.currentState[6] == state2.getCurrentState()[6]
				&& this.currentState[7] == state2.getCurrentState()[7]
				&& this.currentState[8] == state2.getCurrentState()[8]) {
			return true;
		} else
			return false;

	}

	public int[] getCurrentState() {
		// Getter so that the state can be compared with another in the equals function.
		return currentState;
	}

	public int manhattanDistance() {
		// Calculates Manhattan distance by looking up the tiles position in an array, which holds the row and column number if it was in a table,
		// those numbers are then used to calculate the distance.
		int distance = 0;
		int correctPos = 0;
		for(int i = 0; i < this.currentState.length; i++)
		{
			if(currentState[i] != 0)
			{
				correctPos = currentState[i] -1;
				int currentPosRow = CORRECT_POSITIONS[i][0];
				int currentPosCol = CORRECT_POSITIONS[i][1];
			
				int correctPosRow = CORRECT_POSITIONS[correctPos][0];
				int correctPosCol = CORRECT_POSITIONS[correctPos][1];
			
				distance += (Math.abs(currentPosRow-correctPosRow) + Math.abs(currentPosCol-correctPosCol));
			}
		}
		return distance;
	}

}
