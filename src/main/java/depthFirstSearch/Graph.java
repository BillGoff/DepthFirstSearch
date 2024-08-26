package depthFirstSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 * This class shows us how to create a Graph.  It then uses Depth First Search to search the Graph for the friends
 * of a specific individual.
 * @author bgoff
 *
 */
public class Graph 
{
	private Map<Vertex, List<Vertex>> adjVertices;

	public Graph() {
		this.adjVertices = new HashMap<Vertex, List<Vertex>>();
	}

	/**
	 * Add a Vertex if it doesn't already exist.
	 * @param label
	 */
	void addVertex(String label) 
	{
		adjVertices.putIfAbsent(new Vertex(label), new ArrayList<Vertex>());
	}

	/**
	 * Method is used to remove a Vertex.  
	 * @param label
	 */
	void removeVertex(String label) 
	{
		Vertex v = new Vertex(label);
		adjVertices.values().stream().forEach(e -> e.remove(v));
		adjVertices.remove(new Vertex(label));
	}

	/**
	 * This method is used to add the edge.  A Graph is nothing more than a tree.
	 * @param label1
	 * @param label2
	 */
	void addEdge(String label1, String label2) 
	{
		Vertex v1 = new Vertex(label1);
		Vertex v2 = new Vertex(label2);
		adjVertices.get(v1).add(v2);
		adjVertices.get(v2).add(v1);
	}

	/**
	 * Method used to remove an edge.
	 * @param label1
	 * @param label2
	 */
	void removeEdge(String label1, String label2) 
	{
		Vertex v1 = new Vertex(label1);
		Vertex v2 = new Vertex(label2);
		List<Vertex> eV1 = adjVertices.get(v1);
		List<Vertex> eV2 = adjVertices.get(v2);
		if (eV1 != null)
			eV1.remove(v2);
		if (eV2 != null)
			eV2.remove(v1);
	}

	/**
	 * This method is used to get the adjacent vertices. 
	 * @param label String that identifies the element we want to get the adjacent vertices for.
	 * @return List of adjacent vertices for the element identified by the String passed in.
	 */
	List<Vertex> getAdjVertices(String label) 
	{
		return adjVertices.get(new Vertex(label));
	}

	/**
	 * Helper method used to print the graph.
	 * @return String representation of the graph.
	 */
	public static String printGraph(Graph graph) 
	{
		StringBuffer sb = new StringBuffer();
		for (Vertex v : graph.adjVertices.keySet()) 
		{
			sb.append(v + " ");
			sb.append(graph.adjVertices.get(v) + "\n");
		}
		return sb.toString();
	}

	/**
	 * This method is used to get the list of Friends using the DFS approach to read through the Graph.
	 * @param graph Graph element to read through.
	 * @param root String value that is used to identify the element we want to get the values for.
	 * @return String the list of vertices associated with the root string passed in.  In this case we are calling them
	 * the friends.
	 */
	public static List<String> depthFirstTraversal(Graph graph, String root) 
	{
		List<String> friends = new ArrayList<String>();
		
		Set<String> visited = new LinkedHashSet<String>();
		Stack<String> stack = new Stack<String>();
		stack.push(root);
		while (!stack.isEmpty()) 
		{
			String vertex = stack.pop();
			if (!visited.contains(vertex)) 
			{
				visited.add(vertex);
				for (Vertex v : graph.getAdjVertices(vertex)) 
				{
					stack.push(v.label);
					if(root.compareTo(v.label) == 0)
						friends.add(vertex);
				}
			}
		}
		return friends;
	}

	/**
	 * Inner class used to hold the data.
	 * @author bgoff
	 *
	 */
	class Vertex 
	{
		String label;

		Vertex(String label) 
		{
			this.label = label;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return label;
		}

		private Graph getOuterType() {
			return Graph.this;
		}
	}

	/* Driver program to test above functions */
	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.addVertex("John");
		graph.addVertex("Bill");
		graph.addVertex("Tony");
		graph.addVertex("Kim");
		graph.addVertex("Maria");
		graph.addVertex("Dave");
		graph.addEdge("John", "Tony");
		graph.addEdge("Bill", "Dave");
		graph.addEdge("Kim", "Maria");
		graph.addEdge("Tony", "Dave");
		graph.addEdge("Dave", "Maria");
		graph.addEdge("Kim", "Bill");
		graph.addEdge("Dave", "John");
		
		
		System.out.println(printGraph(graph));
		
		System.out.println("Johns Friends: " + depthFirstTraversal(graph, "John"));
		System.out.println("Bills Friends: " + depthFirstTraversal(graph, "Bill"));
		System.out.println("Kims Friends: " + depthFirstTraversal(graph, "Kim"));
		System.out.println("Marias Friends: " + depthFirstTraversal(graph, "Maria"));
		System.out.println("Daves Friends: " + depthFirstTraversal(graph, "Dave"));

	}
}
