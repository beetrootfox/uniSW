// DO NOT MODIFY this file.
// Any modification will incurr into the mark zero for the whole exercise.

package maybe;

/**
 * Interface for the Maybe type using the "composite pattern".
 * We include high-order methods.
 * We will use A,B,C for type variables.
 *
 * @param <A> the generic type
 */

public interface Maybe<A> {
  
  /**
   * Checks if is nothing.
   *
   * @return true, if is nothing
   */
  public boolean isNothing();
  
  /**
   * Size.
   *
   * @return the int
   */
  public int     size();         
  
  /**
   * Checks for.
   *
   * @param a the a
   * @return true, if successful
   */
  public boolean has(A a);            
  // Higher-order methods:
  /**
   * Filter.
   *
   * @param p the p
   * @return the maybe
   */
  public Maybe<A> filter(Predicate<A> p);     
  
  /**
   * Map.
   *
   * @param <B> the generic type
   * @param f the f
   * @return the maybe
   */
  public <B> Maybe<B> map(Function<A,B> f);   
  
  /**
   * Fold.
   *
   * @param <B> the generic type
   * @param f the f
   * @param b the b
   * @return the b
   */
  public <B> B fold(Function<A,B> f, B b); 
  
  /**
   * All.
   *
   * @param p the p
   * @return true, if successful
   */
  public boolean all(Predicate<A> p);         
  
  /**
   * Some.
   *
   * @param p the p
   * @return true, if successful
   */
  public boolean some(Predicate<A> p);       
  
  /**
   * For each.
   *
   * @param a the a
   */
  public void forEach(Action<A> a);          
// Unsafe operation, which should not be used (or even offered in this interface).
  /**
 * From maybe.
 *
 * @return the a
 */
public A fromMaybe();  
  // A method cases is not needed, because in this case it is the same as fold.
} 

/*

 We have two implementations of the Maybe interface:

    (1) The class Nothing.
    (2) The class Just.

 There is only one object of type Nothing.

 Look at SampleUsage.java.

 */
