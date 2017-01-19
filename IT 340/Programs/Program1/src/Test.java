package edu.ilstu.it340.program1.kwdalle;

public class Test {

	public static void main(String[] args) {
		EightPuzzle test = new EightPuzzle(8,6,7,2,5,4,3,0,1);
		
		AStarSearch test1 = new AStarSearch(new Node(((EightPuzzle)test),"Manhattan".toLowerCase()));
		test1.search();
	}

}
