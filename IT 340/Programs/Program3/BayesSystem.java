import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author kwdalle
 *
 */
public class BayesSystem {

	private int lines;
	private double[] classProbs;

	private ArrayList<ArrayList<ArrayList<Double>>> count = new ArrayList<ArrayList<ArrayList<Double>>>();

	private ArrayList<String> att = new ArrayList<String>();
	private ArrayList<String> classes = new ArrayList<String>();
	// This hash map is used purely to find the location of values later.
	private HashMap<String, String[]> valueLoc = new HashMap<String, String[]>();

	public BayesSystem(Scanner metaFile, Scanner trainFile) {
		String[] line;

		// These arrays are purely meant for storing things that will no longer
		// be needed once initial probabilities are developed
		ArrayList<String> vals = new ArrayList<String>();
		double[] classCount;

		// For every line in the meta file store things to create the array
		// later.
		while (metaFile.hasNextLine()) {
			line = metaFile.nextLine().split(":");
			att.add(line[0]); // Stores the attribute to be used later
			vals.add(line[1]); // Stores the values to be able to get the class
								// next.

			valueLoc.put(line[0], line[1].split(",")); // All Attributes are
														// mapped to their
														// values for future
														// reference
		}

		// Here is where the classes are determined and stored
		line = vals.get(vals.size() - 1).split(",");
		for (int i = 0; i < line.length; i++) {
			classes.add(line[i]);
		}

		classProbs = new double[classes.size()];
		classCount = new double[classes.size()];

		for (int i = 0; i < classes.size(); i++) {
			classProbs[i] = 0.0;
			classCount[i] = 1.0;
		}

		// 3 D array is created to store counts and later probabilities in.
		for (int i = 0; i < line.length; i++) {
			count.add(new ArrayList<ArrayList<Double>>());
			for (int k = 0; k < att.size() - 1; k++) {
				count.get(i).add(new ArrayList<Double>());
				String[] line2 = vals.get(k).split(",");
				for (int c = 0; c < line2.length; c++) {
					// As instructed all counts are to have a one added to them,
					// so instead they start with an initial value of 1.
					count.get(i).get(k).add(1.0);
				}
			}
		}

		// Extracts data from the train file to gather our counts and store them
		// in the 3D array.
		while (trainFile.hasNextLine()) {
			line = trainFile.nextLine().split(",");
			int classLoc = classes.indexOf(line[line.length - 1]);
			classCount[classLoc]++;
			for (int i = 0; i < line.length - 1; i++) {
				String attribute = att.get(i);
				String[] values = valueLoc.get(attribute);
				int valLoc = 0;
				for (int k = 0; k < values.length; k++) {
					if (values[k].equals(line[i])) {
						valLoc = k;
					}
				}
				Double oldVal = count.get(classLoc).get(i).get(valLoc);
				Double newVal = oldVal + 1;
				count.get(classLoc).get(i).set(valLoc, newVal);
			}
			// Keeps track of the number of lines for calculating probabilities
			// (this is also the number of instances).
			this.lines++;
		}

		// Calculates the probabilities of the classes themselves.
		for (int i = 0; i < count.size(); i++) {
			double count = classCount[i];
			double divisor = lines + classCount.length;
			double prob = count / divisor;
			this.classProbs[i] = prob;
		}

		// Goes and calculates the probabilities of every value that the 3D
		// array represents and then stores said probability within that array
		for (int i = 0; i < count.size(); i++) {
			for (int k = 0; k < count.get(i).size(); k++) {
				for (int l = 0; l < count.get(i).get(k).size(); l++) {
					Double numVal = count.get(i).get(k).get(l);
					Double numSpecificClass = classCount[i];
					Double numValues = (double) count.get(i).get(k).size();

					Double denominator = numSpecificClass + numValues;

					Double prob = numVal / denominator;

					count.get(i).get(k).set(l, prob);
				}
			}
		}
	}

	public void classifyFile(Scanner inFile, PrintWriter outFile) {
		// Sets the used variables to their starting values
		double bestProb = 0.0;
		Integer bestClass = null;
		while (inFile.hasNextLine()) {
			// Splits the line based on the standard delimiter for the files
			String origLine = inFile.nextLine();
			String[] sepLine = origLine.split(",");

			// Both prob and bestProb are reset here for each line
			double prob = 0.0;
			bestProb = 0.0;

			for (int j = 0; j < classes.size(); j++) {
				// Prob is reset for every attribute in each line.
				prob = 0.0;
				for (int i = 0; i < sepLine.length; i++) {
					if (!classes.contains(sepLine[i])) {
						String Attribute = att.get(i);
						String[] vals = this.valueLoc.get(Attribute);
						int valLoc = 0;

						// Finds the location of the value we are looking for in
						// the 3D array.
						for (int k = 0; k < vals.length; k++) {
							if (sepLine[i].equals(vals[k])) {
								valLoc = k;
							}
						}
						// Depending on whether it is the first value in the
						// line depends on if we simply add the prob or multiply
						// them
						if (i == 0) {
							prob = count.get(j).get(i).get(valLoc);
						} else {
							prob *= count.get(j).get(i).get(valLoc);
						}
					}
				}

				// The resulting probabilities are then multiplied by the
				// probability of the classes and then compared to the current
				// best probability.
				prob *= classProbs[j];
				if (prob > bestProb) {
					bestProb = prob;
					bestClass = j;
				}
			}
			// Writes the new line to the specified file. Then writes the best
			// class to the end of the file.
			for (int i = 0; i < sepLine.length; i++) {
				if (!classes.contains(sepLine[i])) {
					outFile.write(sepLine[i] + ",");
				}
			}
			outFile.println(classes.get(bestClass));
		}

		// Closes the file so that it is ensured that the data is written.
		outFile.close();
	}

	public void readData(Scanner inFile) {
		// Sets the used variables to their starting values
		double bestProb = 0.0;
		Integer bestClass = null;
		
		double yes = 0.0;
		double total = 0.0;
		while (inFile.hasNextLine()) {
			// Splits the line based on the standard delimiter for the files
			String origLine = inFile.nextLine();
			String[] sepLine = origLine.split(",");

			// Both prob and bestProb are reset here for each line
			double prob = 0.0;
			bestProb = 0.0;

			for (int j = 0; j < classes.size(); j++) {
				// Prob is reset for every attribute in each line.
				prob = 0.0;
				for (int i = 0; i < sepLine.length; i++) {
					if (!classes.contains(sepLine[i])) {
						String Attribute = att.get(i);
						String[] vals = this.valueLoc.get(Attribute);
						int valLoc = 0;

						// Finds the location of the value we are looking for in
						// the 3D array.
						for (int k = 0; k < vals.length; k++) {
							if (sepLine[i].equals(vals[k])) {
								valLoc = k;
							}
						}
						// Depending on whether it is the first value in the
						// line depends on if we simply add the prob or multiply
						// them
						if (i == 0) {
							prob = count.get(j).get(i).get(valLoc);
						} else {
							prob *= count.get(j).get(i).get(valLoc);
						}
					}
				}

				// The resulting probabilities are then multiplied by the
				// probability of the classes and then compared to the current
				// best probability.
				prob *= classProbs[j];
				if (prob > bestProb) {
					bestProb = prob;
					bestClass = j;
				}
			}
			
			if(classes.get(bestClass).equals(sepLine[sepLine.length-1]))
			{
				yes++;
			}
			
			total++;
		}
		
		System.out.println("\nThe Accuracy of this File is: " + yes/total*100 + "%\n");
	}
}
