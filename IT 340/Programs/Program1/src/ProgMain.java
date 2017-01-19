/**
 * 
 */
package edu.ilstu.it340.program1.kwdalle;

/**
 * @author kwdalle
 *
 */
public class ProgMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Accepts input from the command line, then creates the basic FWGC and
		// the requested 8Puzzle.
		ProblemBase test;
		if (args[2].toLowerCase().equals("fwgc")) {
			test = new FWGC();
		} else {
			test = new EightPuzzle(Integer.parseInt(args[2]),
					Integer.parseInt(args[3]), Integer.parseInt(args[4]),
					Integer.parseInt(args[5]), Integer.parseInt(args[6]),
					Integer.parseInt(args[7]), Integer.parseInt(args[8]),
					Integer.parseInt(args[9]), Integer.parseInt(args[10]));
		}
		// Determines what search to use, or if null is given uses all of them.
		if (args[0].toLowerCase().equals("bfs")) {
			BFS search = new BFS(new Node(test));
			System.out.println("BFS");
			search.search();
			// Sees if you want the number of nodes expanded to be printed.
			if (args[1].toLowerCase().equals("yes")) {
				System.out.println();
				System.out.println("Nodes Expanded: " + search.nodesExpanded());
			}
		} else if (args[0].toLowerCase().equals("dfs")) {
			DFS search2 = new DFS(new Node(test));
			System.out.println("DFS");
			search2.search();
			if (args[1].toLowerCase().equals("yes")) {
				System.out.println();
				System.out
						.println("Nodes Expanded: " + search2.nodesExpanded());
			}
		} else if (args[0].toLowerCase().equals("astar") && test instanceof EightPuzzle) {
			AStarSearch search3 = new AStarSearch(new Node(test, args[11].toLowerCase()));
			
			System.out.println("A*");
			search3.search();
			if (args[1].toLowerCase().equals("yes")) {
				System.out.println();
				System.out
						.println("Nodes Expanded: " + search3.nodesExpanded());
			}
		} else {
			BFS search = new BFS(new Node(test));
			DFS search2 = new DFS(new Node(test));
			System.out.println("BFS");
			search.search();
			System.out.println();
			System.out.println("DFS");
			search2.search();
			if (args[1].toLowerCase().equals("yes")) {
				System.out.println();
				System.out.println("BFS Nodes Expanded: "
						+ search.nodesExpanded());
				System.out.println("DFS Nodes Expanded: "
						+ search2.nodesExpanded());
			}
			// If null is chosen then it makes sure it is an 8 puzzle since FWGC is not relavent for this
			// search.
			if (test instanceof EightPuzzle) {
				AStarSearch search3;
				System.out.println("A*");
				search3 = new AStarSearch(new Node(test,
						args[11].toLowerCase()));
				search3.search();
				if (args[1].toLowerCase().equals("yes")) {
					System.out.println("A* Nodes Expanded: "
							+ search3.nodesExpanded());
				}
			}
		}
	}
}
