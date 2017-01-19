import java.util.ArrayList;

/**
 * Author: Kevin Dalle
 * Class: Node.java
 * Purpose: Nodes will be what is stored within the Decision Tree.
 */

/**
 * @author dalle
 *
 */
public class Node {
	private String name = "";
	private String value = "";
	private String classification = "";
	
	private ArrayList<String[]> examples;
	
	private ArrayList<Node> children = new ArrayList<Node>();
	
	private boolean isLeaf;
	
	public Node(String name, ArrayList<String[]> examples)
	{
		this.name = name;
		this.examples = examples;
	}
	
	public Node(String name, String value, ArrayList<String[]> examples)
	{
		this.name = name;
		this.value = value;
		this.examples = examples;
	}
	
	public Node(String classification)
	{
		this.classification = classification;
		this.isLeaf = true;
	}
	public Node(String name, String value,boolean isLeaf, String classification)
	{
		this.name = name;
		this.value = value;
		this.isLeaf = true;
		this.classification = classification;
	}
	
	public String getClassification()
	{
		return this.classification;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void addChildren(Node child) {
		this.children.add(child);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public ArrayList<String[]> getExamples() {
		return examples;
	}

	public boolean isLeaf() {
		return isLeaf;
	}
}
