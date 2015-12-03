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
 * Implementation of Just
 * (using the "composite pattern").
 *
 * @param <A> the generic type
 */

public class Just<A> implements Maybe<A> {
  
  /** The something. */
  private final A something; 
  
  /**
   * Instantiates a new just.
   *
   * @param something the something
   */
  public Just(A something) { 
    this.something = something;
  }              

  /* (non-Javadoc)
   * @see maybe.Maybe#isNothing()
   */
  public boolean isNothing() { 
    return false;
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#size()
   */
  public int size() {
    return 1;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() { 
    return "Just(" + something + ")";
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#has(java.lang.Object)
   */
  public boolean has(A a) {
    return something.equals(a);
  }

  // Higher-order functions:

  /* (non-Javadoc)
   * @see maybe.Maybe#filter(maybe.Predicate)
   */
  public Maybe<A> filter(Predicate<A> p) {
    if(p.holds(something)){
    	return new Just<A>(something);
    }else{
    	return new Nothing<A>();
    }
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#map(maybe.Function)
   */
  public <B> Maybe<B> map(Function<A,B> f) {
    return new Just<B>(f.apply(something));
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#fold(maybe.Function, java.lang.Object)
   */
  public <B> B fold(Function<A,B> f, B b) {
    return f.apply(something);
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#all(maybe.Predicate)
   */
  public boolean all(Predicate<A> p) {
    return p.holds(something);
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#some(maybe.Predicate)
   */
  public boolean some(Predicate<A> p) {
    return p.holds(something);
  }

  /* (non-Javadoc)
   * @see maybe.Maybe#forEach(maybe.Action)
   */
  public void forEach(Action<A> f) {
    f.apply(something);
  }

  // Unsafe operation:
  /* (non-Javadoc)
   * @see maybe.Maybe#fromMaybe()
   */
  public A fromMaybe() {
    return something;
  }
}
