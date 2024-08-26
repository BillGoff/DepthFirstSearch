package depthFirstSearch;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.stream.Collectors;



/**
 * This class shows how to do a Depth First Search (DFS) on a Binary Tree.
 * @author bgoff
 *
 */
public class BinaryTree 
{	
	private static final boolean ASC = true;
    private static final boolean DESC = false;
    
	/**
	 * This method is used to create the Nodes.  It parses the String passed in into a unique list of characters.  It 
	 * then creates a root node and adds each character to the root node.
	 * @param data String to parse into nodes.
	 * @return Node the root node.
	 */
	public static Node createNodes(String data)
	{
		Node root = null;
		HashSet<String> hs = new HashSet<String>();
    	
		for(int i = 0; i < data.length(); i++)
			hs.add(String.valueOf(data.charAt(i)));

		for(String s: hs)
			root = insert(root, s);

		return root;
	}
	
	/**
	 * method used to make a duration human readable.
	 * @param duration Duration to make human readable.
	 * @return String Duration in a human readable form.
	 */
	public static String formatDuration(Duration duration) 
	{
		return duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
	}
	
	/**
	 * (U) This method is used to insert the data into the tree.
	 * @param root The root node.
	 * @param data String data to add to the tree.
	 * @return Node the new root node.
	 */
	public static Node insert(Node root, String data) 
	{
		if(root == null)
			return new Node(data);
		else 
		{
			Node cur;
            
			if(data.compareTo(root.value) <= 0)
			{
				cur = insert(root.left, data);
				root.left = cur;
			} 
			else 
			{
				cur = insert(root.right, data);
				root.right = cur;
			}
			return root;
		}
	}
	
	/**
	 * Sort the HashMap by the Duration.  So we can see which one is the fastest/slowest.
	 * @param unsortMap Map to sort.
	 * @param order Boolean that tells us which order to sort by.
	 * @return Map the sorted map.
	 */
	private static Map<String, Duration> sortByValue(Map<String, Duration> unsortMap, final boolean order)
	{
        List<Entry<String, Duration>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
	
	/**
	 * Recursively traverse the Tree in order.
	 * @param node Node we are currently on.
	 */
	public static void traverseInOrder(Node node) 
	{
		if (node != null) 
		{
			traverseInOrder(node.left);
			visit(node);
			traverseInOrder(node.right);
		}
	}
	
	/**
	 * This method shows how to traverse the tree in order, without using recursion.
	 * @param root Node, the root node.
	 */
	public static void traverseInOrderWithoutRecursion(Node root) 
	{
		Stack<Node> stack = new Stack<Node>();
		Node current = root;

		while (current != null || !stack.isEmpty()) 
		{
			while (current != null) 
			{
				stack.push(current);
				current = current.left;
			}

			Node top = stack.pop();
			visit(top);
			current = top.right;
		}
	}
	
	/**
	 * Recursively traverse the Tree post order.
	 * @param node Node we are currently on.
	 */
	public static void traversePostOrder(Node node) 
	{
		if (node != null) 
		{
			traversePostOrder(node.left);
			traversePostOrder(node.right);
			visit(node);
		}
	}
		
	/**
	 * Recursively traverse the Tree in order.
	 * @param node Node we are currently on.
	 */
	public static void traversePreOrder(Node node)
	{
		if(node != null)
		{
			visit(node);
			traversePreOrder(node.left);
			traversePreOrder(node.right);
		}
	}
	
	/**
	 * This method shows how to traverse the tree Post Order, without using recursion.
	 * @param root Node, the root node.
	 */
	public static void traversePostOrderWithoutRecursion(Node root) 
	{
		Stack<Node> stack = new Stack<Node>();
		Node prev = root;
		Node current = root;
		stack.push(root);

		while (!stack.isEmpty()) 
		{
			current = stack.peek();
			boolean hasChild = (current.left != null || current.right != null);
			boolean isPrevLastChild = (prev == current.right || 
					(prev == current.left && current.right == null));

			if (!hasChild || isPrevLastChild) 
			{
				current = stack.pop();
				visit(current);
				prev = current;
			} 
			else 
			{
				if (current.right != null)
					stack.push(current.right);

				if (current.left != null) 
					stack.push(current.left);
			}
		}
	}
	
	/**
	 * This method shows how to traverse the tree, without using recursion.
	 * @param root Node, the root node.
	 */
	public static void traversePreOrderWithoutRecursion(Node root) 
	{
		Stack<Node> stack = new Stack<Node>();
		
		Node current = root;
		stack.push(current);
		
		while(!stack.isEmpty()) 
		{
			current = stack.pop();
			visit(current);
	        
			if(current.right != null) 
				stack.push(current.right);
			if(current.left != null)
				stack.push(current.left);
		}
	}
	
	/**
	 * Method used to print out node's value we are currently on.
	 * @param node the Node who's value we will print out.
	 */
	public static void visit(Node node)
	{
		System.out.println(node.value);
	}
	
	/* Driver program to test above functions */
	public static void main(String[] args)
	{
		Node rootNode = createNodes("qwertyuioplkjhgfdsazxcvbnmasdl;fkjas;dlf");
		
		Map<String, Duration> times = new HashMap<String, Duration>();

		Instant start = Instant.now();         
		traversePreOrder(rootNode);
		Instant stop = Instant.now();
		System.out.println("");
		times.put("TraversePreOrder", Duration.between(start, stop));
		
//		System.out.println("TraversePreOrder:  " + formatDuration(Duration.between(start, stop)));
		
		start = Instant.now();         
		traverseInOrder(rootNode);
		stop = Instant.now();
		System.out.println("");
//		System.out.println("TraverseInOrder:  " + formatDuration(Duration.between(start, stop)));
		
		times.put("TraverseInOrder", Duration.between(start, stop));

		
		start = Instant.now();         
		traversePostOrder(rootNode);
		stop = Instant.now();
		System.out.println("");
//		System.out.println("TraversePostOrder:  " + formatDuration(Duration.between(start, stop)));
		
		times.put("TraversePostOrder", Duration.between(start, stop));

		
		start = Instant.now();         
		traversePreOrderWithoutRecursion(rootNode);
		stop = Instant.now();
		System.out.println("");
//		System.out.println("TraversePreOrderWithoutRecursion:  " + formatDuration(Duration.between(start, stop)));
		
		times.put("TraversePreOrderWithoutRecursion", Duration.between(start, stop));

		start = Instant.now();         
		traverseInOrderWithoutRecursion(rootNode);
		stop = Instant.now();
		System.out.println("");
//		System.out.println("TraverseInOrderWithoutRecursion:  " + formatDuration(Duration.between(start, stop)));

		times.put("TraverseInOrderWithoutRecursion", Duration.between(start, stop));

		
		start = Instant.now();         
		traversePostOrderWithoutRecursion(rootNode);
		stop = Instant.now();
		System.out.println("");
//		System.out.println("TraversePostOrderWithoutRecursion:  " + formatDuration(Duration.between(start, stop)));
		
		times.put("TraversePostOrderWithoutRecursion", Duration.between(start, stop));
		
		times = sortByValue(times, ASC);
		
		times.forEach((key, value) -> System.out.println("Method : " + key + " Value : " + formatDuration(value)));
	}
}
