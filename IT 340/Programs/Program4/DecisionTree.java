import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Author: Kevin Dalle
 * Class: DecisionTree.java
 * Purpose: Representing the Decision Tree for the algorithm
 */

/**
 * @author dalle
 *
 */
public class DecisionTree {

	private Node root;

	private ArrayList<String[]> examples = new ArrayList<String[]>();
	private ArrayList<String> attributes = new ArrayList<String>();
	private ArrayList<String> classes = new ArrayList<String>();
	private HashMap<String, String[]> attributesValue = new HashMap<String, String[]>();
	private Double yes = 0.0;

	/*
	 * This is the constructor for the decision tree. It takes in both the meta
	 * file and the training file. This was because all the data from the
	 * training file is just stored inside an array to be used later when
	 * building the tree.
	 */
	public DecisionTree(File metaFile, File trainFile)
			throws FileNotFoundException {
		// Goes through both the meta file and train file and store the
		// appropriate data.
		Scanner mFile = new Scanner(metaFile);
		Scanner tFile = new Scanner(trainFile);

		String[] line;

		while (mFile.hasNextLine()) {
			line = mFile.nextLine().split(":");

			if (!line[0].equals("class")) {
				attributes.add(line[0]);
				attributesValue.put(line[0], line[1].split(","));
			} else {
				for (String s : line[1].split(",")) {
					classes.add(s);
				}
			}
		}

		mFile.close();

		while (tFile.hasNextLine()) {
			line = tFile.nextLine().split(",");
			examples.add(line);
		}

		tFile.close();
	}

	/*
	 * This method will build the tree based on the ID3 algorithm.
	 */
	public void BuildTree() {
		root = new Node("root", this.examples);
		DTree(examples, attributes, root);
	}

	/*
	 * This method is the ID3 algorithm.
	 */
	private void DTree(ArrayList<String[]> examples,
			ArrayList<String> attributes, Node parent) {
		boolean allOneClass = true;
		
		// Gets the class of the first example and then goes through the rest
		// to see if they are all the same
		String theClass = examples.get(0)[examples.get(0).length - 1];
		for (String[] e : examples) {
			if (!e[e.length - 1].equals(theClass)) {
				allOneClass = false;
			}
		}
		if (allOneClass) {
			Node leaf = new Node(theClass);
			parent.addChildren(leaf);
		} else if (attributes.size() == 0) {
			Node leaf = new Node(findBestClass());
			parent.addChildren(leaf);
		} else {
			HashMap<String, Double> entropies = calculateEntropy(examples,
					attributes);

			// Starts the entropy at an unbelievably high number so that there
			// is a starting point at which the first will always be better.
			double bestEntropy = 9999;
			String bestAttribute = attributes.get(0);

			for (String a : attributes) {
				double curEntropy = entropies.get(a);
				if (curEntropy < bestEntropy) {
					bestEntropy = curEntropy;
					bestAttribute = a;
				}
			}

			// Goes through every value and determines if it needs to recurse or create a node
			for (String v : attributesValue.get(bestAttribute)) {
				ArrayList<String[]> myExamples = new ArrayList<String[]>();
				int attLoc = this.attributes.indexOf(bestAttribute);

				for (String[] e : examples) {
					if (e[attLoc].equals(v)) {
						myExamples.add(e);
					}
				}

				if (myExamples.isEmpty()) {
					Node leaf = new Node(bestAttribute, v, true,
							findBestClass());
					parent.addChildren(leaf);
				} else {
					Node child = new Node(bestAttribute, v, myExamples);
					parent.addChildren(child);
					ArrayList<String> nAttributes = new ArrayList<String>();
					for (String a : attributes) {
						if (!a.equals(bestAttribute)) {
							nAttributes.add(a);
						}
					}
					DTree(myExamples, nAttributes, child);
				}
			}
		}
	}

	/*
	 * This method is purely meant to find the best class in the set of
	 * examples.
	 */
	private String findBestClass() {
		String bestClass = null;
		int bestCount = 0;
		for (String c : classes) {
			int count = 0;
			for (String[] e : examples) {
				if (e[e.length - 1].equals(c)) {
					count++;
				}
			}
			if (count > bestCount) {
				bestClass = c;
			}
		}

		return bestClass;
	}

	/*
	 * This method will calculate the entropy of the given attributes based on
	 * the examples the system was trained with. It will then return a map of
	 * entropies with the keys being the attributes and the values being the
	 * entropy of that attribute.
	 */
	private HashMap<String, Double> calculateEntropy(
			ArrayList<String[]> examples, ArrayList<String> attributes) {

		HashMap<String, Double> retVal = new HashMap<String, Double>();

		// These values are used to store the components of the entropy formula
		// throughout calculation.
		double numVal = 0;
		double outer = 0;
		double numClass = 0;
		double inner = 0;
		double entropy = 0;

		// This goes through every attribute and for those every value and calculates
		// the entropy for that attribute.
		for (String s : attributes) {
			for (String v : attributesValue.get(s)) {
				for (String[] e : examples) {
					if (e[this.attributes.indexOf(s)].equals(v)) {
						numVal++;
					}
				}
				if (numVal != 0) {
					outer = numVal / examples.size();
				}
				for (String c : classes) {
					for (String[] e : examples) {
						if (e[this.attributes.indexOf(s)].equals(v)
								&& e[e.length - 1].equals(c)) {
							numClass++;
						}
					}
					if (numVal != 0 && numClass != 0) {
						inner += -1.0
								* ((numClass / numVal) * ((Math.log(numClass
										/ numVal)) / Math.log(2)));
					}
					numClass = 0;
				}
				entropy += outer * inner;
				inner = 0;
				outer = 0;
				numVal = 0;
			}
			retVal.put(s, entropy);
			inner = 0;
			outer = 0;
			numVal = 0;
			entropy = 0;
		}
		return retVal;
	}

	/*
	 * This is the first method called to print the tree. It will lead to the
	 * below method in recursive calls.
	 */
	public void PrintTree() {
		System.out.println(root.getName());

		// Recursively calls the private version to print out every branch of
		// the root.
		for (int i = 0; i < root.getChildren().size(); i++) {
			int lvl = 1;

			printTree(root.getChildren().get(i), lvl);
		}
	}

	/*
	 * This method is going to print the rest of the tree from the root down.
	 */
	private void printTree(Node root, int level) {
		if (!root.isLeaf()) {
			for (int i = 0; i < level; i++) {
				System.out.print("\t");
			}

			System.out.println(root.getName());

			for (int i = 0; i < level; i++) {
				System.out.print("\t");
			}

			System.out.println(" =" + root.getValue());

			level++;

			int myLevel = level;
			for (int i = 0; i < root.getChildren().size(); i++) {
				printTree(root.getChildren().get(i), myLevel);
			}
		} else {
			if (!root.getName().equals("")) {
				for (int i = 0; i < level; i++) {
					System.out.print("\t");
				}

				System.out.println(root.getName());
			}
			if (!root.getValue().equals("")) {
				for (int i = 0; i < level; i++) {
					System.out.print("\t");
				}

				System.out.println(" =" + root.getValue());
			}

			if (!root.getName().equals("")) {
				for (int i = 0; i < level + 1; i++) {
					System.out.print("\t");
				}

				System.out.println(" =" + root.getClassification());
			} else {
				for (int i = 0; i < level; i++) {
					System.out.print("\t");
				}

				System.out.println(" =" + root.getClassification());
			}
		}

	}

	/*
	 * This method begins the classifying process and only deals with the root
	 * node.
	 */
	public void ClassifyFile(File inFile, File outFile)
			throws FileNotFoundException {
		Scanner iFile = new Scanner(inFile);
		PrintWriter oFile = new PrintWriter(outFile);
		String[] line;
		while (iFile.hasNextLine()) {
			line = iFile.nextLine().split(",");
			ArrayList<Node> children = root.getChildren();

			if (!children.get(0).isLeaf()) {
				recurseClassify(oFile, children, line);
			} else {
				writeToFile(oFile, children, line);
				System.out.println(1);
			}
		}

		iFile.close();
		oFile.close();
	}

	/*
	 * This method is the recursive part of the classifying portion of the
	 * learning.
	 */
	private void classifyFile(PrintWriter oFile, Node curNode, String[] line) {
		ArrayList<Node> children = curNode.getChildren();

		if (children.size() == 1) {
			writeToFile(oFile, children, line);
		} else if (children.size() == 0 && !curNode.getName().equals("")) {
			// This case deals with the odd case where the classification is
			// stored within the node and not a child
			// AKA as the case where you split and their are no instance with
			// that value within the examples set
			for (String s : line) {
				if (!classes.contains(s)) {
					oFile.write(s + ",");
				}
			}
			oFile.println(curNode.getClassification());
		} else {
			recurseClassify(oFile, children, line);
		}
	}

	/*
	 * Since this code was repeated in both versions of the classify method it
	 * was made into a method itself.
	 */
	private void writeToFile(PrintWriter oFile, ArrayList<Node> children,
			String[] line) {
		for (String s : line) {
			if (!classes.contains(s)) {
				oFile.write(s + ",");
			}
		}
		oFile.println(children.get(0).getClassification());
	}

	/*
	 * Since this code was repeated in both versions of the classify method it
	 * was made into a method itself.
	 */
	private void recurseClassify(PrintWriter oFile, ArrayList<Node> children,
			String[] line) {
		for (Node n : children) {
			String attribute = n.getName();
			int attLoc = this.attributes.indexOf(attribute);
			String val = n.getValue();
			if (line[attLoc].equals(val)) {
				classifyFile(oFile, n, line);
			}
		}
	}

	/*
	 * Though this code is very similar to the above classification code it does
	 * not change anything so I felt it was necessary to repeat it since it
	 * performs a different function. This method is meant to begin the analysis
	 * of the data with in a file.
	 */
	public void AnalyzeData(File inFile) throws FileNotFoundException {
		Scanner iFile = new Scanner(inFile);
		String[] line;
		double total = 0;
		yes = 0.0;

		while (iFile.hasNextLine()) {
			line = iFile.nextLine().split(",");
			ArrayList<Node> children = root.getChildren();

			if (!children.get(0).isLeaf()) {
				recursiveAnalyze(children, line);
			} else {
				String c = children.get(0).getClassification();
				if (c.equals(line[line.length - 1])) {
					yes++;
				}
			}

			total++;
		}

		System.out.println("\nAccuracy: " + (yes / total) * 100 + "\n\n");

		iFile.close();
	}

	/*
	 * This is the recursive part of the analysis.
	 */
	private void analyzeData(Node curNode, String[] line) {
		ArrayList<Node> children = curNode.getChildren();

		if (children.size() == 1) {
			String c = children.get(0).getClassification();
			if (c.equals(line[line.length - 1])) {
				yes++;
			}
		} else if (children.size() == 0 && !curNode.getName().equals("")) {
			// This case deals with the odd case where the classification is
			// stored within the node and not a child
			// AKA as the case where you split and their are no instance with
			// that value within the examples set
			String c = curNode.getClassification();
			if (c.equals(line[line.length - 1])) {
				yes++;
			}
		} else {
			recursiveAnalyze(children, line);
		}
	}

	/*
	 * This was the code that both of the analyze functions shared, thus it was
	 * made into its own function.
	 */
	private void recursiveAnalyze(ArrayList<Node> children, String[] line) {
		for (Node n : children) {
			String attribute = n.getName();
			int attLoc = attributes.indexOf(attribute);
			String val = n.getValue();
			if (line[attLoc].equals(val)) {
				analyzeData(n, line);
			}
		}
	}
}
