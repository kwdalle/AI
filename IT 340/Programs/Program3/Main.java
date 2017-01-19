import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Kevin Dalle
 * kwdalle@ilstu.edu
 * IT340, Spring 2016
 * Project3: Main.java
 */

/**
 * @author kwdalle
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		boolean quitChoice = false;

		File metaFile;
		File trainingFile;

		String choice;

		Scanner keyboard = new Scanner(System.in);
		Scanner metaData = null;
		Scanner trainData = null;

		boolean trained = false;

		BayesSystem systemBayes = null;

		do {
			// Menu code for the users choices
			System.out.println("Options: ");
			System.out.println("Train");
			System.out.println("Classify Files");
			System.out.println("Read Data");
			System.out.println("Quit\n");

			System.out.println("What would you like to do?\n");
			choice = keyboard.nextLine().toLowerCase();

			if (choice.equals("train")) {

				boolean fileExists = false;

				// Loop to ensure that the files entered exists.
				do {
					System.out
							.println("\nPlease enter the meta file (with extentions).\n");

					metaFile = new File(keyboard.nextLine());

					System.out
							.println("\nPlease enter the training file (with extentions).\n");

					trainingFile = new File(keyboard.nextLine());
					try {
						metaData = new Scanner(metaFile);
						trainData = new Scanner(trainingFile);
						fileExists = true;
					} catch (FileNotFoundException e) {
						System.out
								.println("Files did not exist. Please try again.");
					}

				} while (fileExists == false);

				systemBayes = new BayesSystem(metaData, trainData);
				trained = true;
				metaData.close();
				trainData.close();

			} else if (choice.equals("classify files") && trained == true) {
				// Loops until you no longer wish to classify another file
				String answer = "yes";
				while (answer.equals("yes")) {
					try{
					System.out
							.println("\nPlease enter your input file name (with extentions)\n");
					File inFile = new File(keyboard.nextLine());
					System.out
							.println("\nPlease enter your output file name (with extentions)\n");
					File outFile = new File(keyboard.nextLine());

					Scanner iFile = new Scanner(inFile);
					PrintWriter oFile = new PrintWriter(outFile);
					
					systemBayes.classifyFile(iFile, oFile);
					}
					catch(FileNotFoundException e){
						System.out.println("\nFiles not found. Please try again.\n");
					}

					System.out.println("\nWould you like to classify another file?\n");
					answer = keyboard.nextLine();
				}
			} else if (choice.equals("read data") && trained == true) {
				try{
					System.out.println("\nPlease enter the Data input File (with extentions)\n");
					File inFile = new File(keyboard.nextLine());
					
					Scanner iFile = new Scanner(inFile);
					
					systemBayes.readData(iFile);
				}catch(FileNotFoundException e){
					System.out.println("\nFile not found, please try again.\n");
				}

			} else if (choice.equals("quit")) {
				quitChoice = true;
			} else {
				System.out
						.println("\nYou must train the system before you can classify a file or Read in Data.\n");
			}
		} while (quitChoice != true);

		keyboard.close();
	}

}
