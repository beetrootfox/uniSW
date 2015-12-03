package Astar;

/**
 * The Class Coordinate.
 */
public class Coordinate {
	
	/** The y. */
	public int x, y;

	/**
	 * Instantiates a new coordinate.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		Coordinate c = (Coordinate) o;
		return this.x == c.x && this.y == c.y;
	}

	// The following is similar to the proposal by Jack Hair in the
	// module facebook group. We need to make sure that
	//
	// (*) Equal objects have equal hash codes.
	//
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return x + y; // Any function of x and y with property (*) will do.

		// "return 0;" works, but having all data to have the same hash
		// code defeats the idea of hash tables and make them inefficient.
	}
}
