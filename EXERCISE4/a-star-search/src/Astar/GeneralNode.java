package Astar;

/**
 * The Class GeneralNode. Used to store additional information about a node
 * required in A* and generalised search algorithms
 *
 * @param <A>
 *            the generic type
 */
public class GeneralNode<A> {

	/** The node. */
	private Node<A> node;

	/** The cost to reach this node from the starting one. */
	private double costToReach;

	/** The distance to the goal node. */
	private double distToGoal;

	/** The time when this GeneralNode was created. */
	private long time;
	
	/** The parent. */
	private Node<A> parent;

	/**
	 * Instantiates a new general node.
	 *
	 * @param node
	 *            the node
	 */
	public GeneralNode(Node<A> node) {
		this.node = node;
		time = System.nanoTime();
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return costToReach;
	}

	/**
	 * Gets the creation time.
	 *
	 * @return the creation time
	 */
	public long getCreationTime() {
		return time;
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param node the new parent
	 */
	public void setParent(Node<A> node){
		parent = node;
	}
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Node<A> getParent(){
		return parent;
	}

	/**
	 * Gets the distance to goal.
	 *
	 * @return the distance to goal
	 */
	public double getDistToGoal() {
		return distToGoal;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost
	 *            the new cost
	 */
	public void setCost(double cost) {
		costToReach = cost;
	}

	/**
	 * Sets the distance to goal.
	 *
	 * @param distToGoal
	 *            the new distance to goal
	 */
	public void setDistToGoal(double distToGoal) {
		this.distToGoal = distToGoal;
	}

	/**
	 * Gets the node contents.
	 *
	 * @return the node contents
	 */
	public A getNodeContents() {
		return node.contents();
	}

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Node<A> getNode() {
		return node;
	}

	/**
	 * Gets the function A* operates on (sum of cost and heuristic distance).
	 *
	 * @return the f
	 */
	public double getF() {
		return costToReach + distToGoal;
	}
}
