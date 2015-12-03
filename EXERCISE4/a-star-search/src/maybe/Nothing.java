// WHAT (NOT) TO DO:
// -------------------
// REPLACE the "Exercise" lines with code (possibly empty code).
// LEAVE ALONE the "Exercise" lines for parts you are not solving.
//
// To get any marks for the whole exercise, your submission should
// compile using the "compile" script. Submissions that don't compile
// won't get any marks.

package maybe;

/**
 * Implementation of Nothing (using the "composite pattern").
 *
 * @param <A> the generic type
 */

public class Nothing<A> implements Maybe<A> {

	/**
	 * Instantiates a new nothing.
	 */
	public Nothing() {
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#isNothing()
	 */
	public boolean isNothing() {
		return true;
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#size()
	 */
	public int size() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Nothing";
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#has(java.lang.Object)
	 */
	public boolean has(A a) {
		return false;
	}

	// Higher-order functions:

	/* (non-Javadoc)
	 * @see maybe.Maybe#filter(maybe.Predicate)
	 */
	public Maybe<A> filter(Predicate<A> p) {
		return this;
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#map(maybe.Function)
	 */
	public <B> Maybe<B> map(Function<A, B> f) {
		return new Nothing<B>();
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#fold(maybe.Function, java.lang.Object)
	 */
	public <B> B fold(Function<A, B> f, B b) {
		return b;
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#all(maybe.Predicate)
	 */
	public boolean all(Predicate<A> p) {
		return true;
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#some(maybe.Predicate)
	 */
	public boolean some(Predicate<A> p) {
		return false;
	}

	/* (non-Javadoc)
	 * @see maybe.Maybe#forEach(maybe.Action)
	 */
	public void forEach(Action<A> f) {
	}

	// Unsafe operations:
	/* (non-Javadoc)
	 * @see maybe.Maybe#fromMaybe()
	 */
	public A fromMaybe() {
		throw new RuntimeException("Tried to get an element from nothing");
	}
}
