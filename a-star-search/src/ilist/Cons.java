package ilist;

/**
 * Implementation of a list that has a head and a tail
 * (using the "composite pattern").
 *
 * @param <E> the element type
 */

public class Cons<E> implements IList<E> {
    
  /** The head. */
  private final E head;
  
  /** The tail. */
  private final IList<E> tail; // Reference to another list 
                              // (not a list itself).

  /**
                               * Instantiates a new cons.
                               *
                               * @param head the head
                               * @param tail the tail
                               */
                              public Cons(E head,  IList<E> tail) {
    assert(tail != null);  // Tail should NOT be null. Use Nil instead.
    // See http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html

    this.head = head;      // The usual stuff now.
    this.tail = tail;
  }

  /* (non-Javadoc)
   * @see ilist.IList#isEmpty()
   */
  public boolean isEmpty() { 
   return false; 
  }

  /* (non-Javadoc)
   * @see ilist.IList#size()
   */
  public int size() {
    return 1 + tail.size(); // Is this a recursive call?
    // I prefer to call this "delegation" rather
    // than "recursion".
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return "Cons("  +  head + "," + tail.toString()  +  ")";
  }
    
  /* (non-Javadoc)
   * @see ilist.IList#append(ilist.IList)
   */
  public IList<E> append(IList<E> l) {
    return new Cons<E>(head, tail.append(l));
  }

  /* (non-Javadoc)
   * @see ilist.IList#append(java.lang.Object)
   */
  public IList<E> append(E e) {
    return append(new Cons<E>(e , new Nil<E>()));
  }

  /* (non-Javadoc)
   * @see ilist.IList#reverse()
   */
  public IList<E> reverse() {
    return tail.reverse().append(head);

    // // Equivalently:
    // IList <E> r = tail.reverse();
    // IList <E> s = r.append(head);
    // return s;
  }

  /* (non-Javadoc)
   * @see ilist.IList#has(java.lang.Object)
   */
  public boolean has(E e) {
    return (head.equals(e) || tail.has(e)); 
    // Short-circuit evaluation of "||" makes this efficient.
    // Search for "short-circuit evaluation" in the internet.
  }
  //Get methods:
  /* (non-Javadoc)
   * @see ilist.IList#getHead()
   */
  @Override
  public E getHead() {
	return head;
  }

  /* (non-Javadoc)
   * @see ilist.IList#getTail()
   */
  @Override
  public IList<E> getTail() {
	return tail;
  }
}
