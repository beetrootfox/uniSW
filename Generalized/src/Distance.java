
/**
 * The Interface Distance.
 *
 * @param <A> the generic type
 */
public interface Distance<A> {
	
	/**
	 * Calculate distance between to positions.
	 *
	 * @param pos1 the position one
	 * @param pos2 the position two
	 * @return the double distance
	 */
	double calculate(A pos1, A pos2);
}
