/**
 * This Interface will be used as the base for any problem that we have, those individual problems will be implemented as classes that implement
 * this interface.
 */
package edu.ilstu.it340.program1.kwdalle;

import java.util.ArrayList;

/**
 * @author kwdalle
 *
 */
public interface ProblemBase {

	public boolean goalStateCheck(); // Will compare the current state to the
										// goalState.

	public ArrayList<ProblemBase> generateSuccessors(); // Genereates all
														// successors as
														// ProblemBase's to keep
														// generality.

	public int operatorCost(); // Simply gives the cost for the operator.

	public void printState(); // Just prints the state to the screen.

	public int compareState();

	public boolean equals(ProblemBase state2);

	public int[] getCurrentState();
}
