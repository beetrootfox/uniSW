package ilist;

/**
 * Implementation of the empty list
 * (using the "composite pattern").
 *
 * @param <E> the element type
 */

public class Nil<E> implements IList<E> {
    
  /**
   * Instantiates a new nil.
   */
  public Nil() { // Nothing to do in the constructor!
  }              // Could simply remove it.

  /* (non-Javadoc)
   * @see ilist.IList#isEmpty()
   */
  public boolean isEmpty() { 
    return true; 
  }

  /* (non-Javadoc)
   * @see ilist.IList#size()
   */
  public int size() {
    return 0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() { 
    return "Nil"; 
  }

  /* (non-Javadoc)
   * @see ilist.IList#append(ilist.IList)
   */
  public IList<E> append(IList<E> l) {
    return l;
  }

  /* (non-Javadoc)
   * @see ilist.IList#append(java.lang.Object)
   */
  public IList<E> append(E e) {
    return new Cons<E>(e , this);
  }

  /* (non-Javadoc)
   * @see ilist.IList#reverse()
   */
  public IList<E> reverse() {
    return this;
  }

  /* (non-Javadoc)
   * @see ilist.IList#has(java.lang.Object)
   */
  public boolean has(E e) {
    return false;
  }

  /* (non-Javadoc)
   * @see ilist.IList#getHead()
   */
  @Override
  public E getHead() {
	return null;
  }

  /* (non-Javadoc)
   * @see ilist.IList#getTail()
   */
  @Override
  public IList<E> getTail() {
	return null;
  }
}
