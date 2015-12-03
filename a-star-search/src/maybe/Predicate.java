// DO NOT MODIFY this file.
// Any modification will incurr into the mark zero for the whole exercise.

package maybe;


/**
 * The Interface Predicate.
 *
 * @param <A> the generic type
 */
public interface Predicate<A> {
    
    /**
     * Holds.
     *
     * @param a the a
     * @return true, if successful
     */
    boolean holds(A a);
}
