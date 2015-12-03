package Astar;
// Minimal class for a particular implementation of directed graphs.
// All we include is what is necessary to build a graph, in the class
// graph.

import java.util.*;

import rp.util.SimpleSet;


/**
 * The Class Node.
 *
 * @param <A> the generic type
 */
public class Node<A> {

	/** The contents. */
	private A contents;
	// Keep the implementation of sets open, by using the Set interface:
	/** The successors. */
	private SimpleSet<Node<A>> successors;

	// We can only build a node with an empty set of successors:
	/**
	 * Instantiates a new node.
	 *
	 * @param contents the contents
	 */
	@SuppressWarnings("deprecation")
	public Node(A contents) {
		this.contents = contents;
		// Choose any implementation of sets you please, but you need to
		// choose one.
		this.successors = new SimpleSet<Node<A>>();
	}

	// Hence we need this:
	/**
	 * Adds the successor.
	 *
	 * @param s the s
	 */
	public void addSuccessor(Node<A> s) {
		successors.add(s);
	}

	/**
	 * Contents equals.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean contentsEquals(A c) {
		return contents.equals(c);
	}

	// Get methods:
	/**
	 * Contents.
	 *
	 * @return the a
	 */
	public A contents() {
		return contents;
	}

	/**
	 * Successors.
	 *
	 * @return the simple set
	 */
	public SimpleSet<Node<A>> successors() {
		return successors;
	}
}
