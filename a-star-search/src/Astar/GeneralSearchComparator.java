package Astar;
import rp.util.Comparator;

/**
 * The Class GeneralSearchComparator. Provides a choice between three
 * specialised comparators need to test the generalised search
 * 
 */
public class GeneralSearchComparator implements
		Comparator<GeneralNode<Coordinate>> {

	/** The stack. Whether or not stack will be used for search */
	boolean stack;

	/** The A star. Whether or not the executed search will be A* */
	boolean AStar;

	// Two constructors make testing easier. In order to see the search result
	// for A* use constructor with no arguments
	// in order to see the result for Depth First search use constructor with
	// argument "true"
	// "false" will produce a Breath First result

	/**
	 * Instantiates a new general search comparator.
	 *
	 * @param stack
	 *            the stack
	 */
	public GeneralSearchComparator(boolean stack) {
		this.stack = stack;
		AStar = false;
	}

	/**
	 * Instantiates a new general search comparator.
	 */
	public GeneralSearchComparator() {
		stack = false;
		AStar = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(GeneralNode<Coordinate> o1, GeneralNode<Coordinate> o2) {
		if (AStar) {
			if (o1.getF() < o2.getF()) {
				return -1;
			} else {
				if (o1.getF() > o2.getF()) {
					return 1;
				} else {
					return 0;
				}
			}
		} else {
			if (stack) {
				if (o1.getCreationTime() < o2.getCreationTime()) {
					return 1;
				} else {
					if (o1.getCreationTime() > o2.getCreationTime()) {
						return -1;
					} else {
						return 0;
					}
				}
			} else {
				if (o1.getCreationTime() < o2.getCreationTime()) {
					return -1;
				} else {
					if (o1.getCreationTime() > o2.getCreationTime()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}

}
