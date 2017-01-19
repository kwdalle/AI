import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Assignment: Program 4 - ID3 Decision Tree Algorithm
 * Author: Kevin Dalle
 * Class: IT 340
 * Instructor: Dr. Califf
 */

/**
 * @author dalle
 *
 */
public class Main {

	/**
	 * @param args
	 * 
	 *            This class is the interface for the rest of the program. There
	 *            is a menu for the user to able to choose options from. If
	 *            during any of those options incorrect data is entered than the
	 *            user will be returned to the main menu. The choices will be
	 *            Train, Classify File, Print Tree, Analyze Data, and Quit. All
	 *            of these choices will be chosen by entering a number between 1
	 *            and 5.
	 */
	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);

		boolean quit = false;
		boolean trained = false;

		DecisionTree tree = null;

		String choice = null;

		do {
			// Simple menu for the options you can choose
			System.out.println("Options:");
			System.out.println("(1) - Train");
			System.out.println("(2) - Classify File");
			System.out.println("(3) - Print Tree");
			System.out.println("(4) - Analyze Data");
			System.out.println("(5) - Quit");

			System.out.print("\n\nEnter Choice - [1-5]: ");

			choice = keyboard.nextLine();

			System.out.println("\n");
			
			// Simple if statement to select the correct options based on your choice
			if (choice.equals("1")) {
				trained = true;

				File metaFile;
				File trainFile;

				System.out.print("Meta File: ");

				metaFile = new File(keyboard.nextLine());

				System.out.print("\nTraining File: ");

				trainFile = new File(keyboard.nextLine());
				
				System.out.println("\n");

				// All exceptions are caught here in the main prgram.
				try {
					tree = new DecisionTree(metaFile, trainFile);
					tree.BuildTree();
				} catch (FileNotFoundException e) {
					System.out.println("\n File names were not valid. \n");
				}
			} else if (choice.equals("2") && trained == true) {
				System.out.print("Input File: ");

				File iFile = new File(keyboard.nextLine());

				System.out.print("\nOutput File: ");

				File oFile = new File(keyboard.nextLine());

				System.out.println("\n");
				try {
					tree.ClassifyFile(iFile, oFile);
				} catch (FileNotFoundException e) {
					System.out.println("\nFile name was not valid.\n");
				}

			} else if (choice.equals("3") && trained == true) {
				tree.PrintTree();
				
				System.out.println("\n");

			} else if (choice.equals("4") && trained == true) {

				System.out.print("Input File: ");
				
				File iFile = new File(keyboard.nextLine());
				
				System.out.println();
				try {
					tree.AnalyzeData(iFile);
				} catch (FileNotFoundException e) {
					System.out.println("File name was not valid.");
				}

			} else if (choice.equals("5")) {
				quit = true;
				System.out.println("Terminating");
			} else {
				System.out
						.println("\nYour choice was invalid. Please try again.\n\n");
			}
		} while (!quit);

		keyboard.close();
	}

}
