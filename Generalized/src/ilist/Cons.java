package ilist;

/**
 * Implementation of a list that has a head and a tail
 * (using the "composite pattern").
 */

public class Cons<E> implements IList<E> {
    
  private final E head;
  private final IList<E> tail; // Reference to another list 
                              // (not a list itself).

  public Cons(E head,  IList<E> tail) {
    assert(tail != null);  // Tail should NOT be null. Use Nil instead.
    // See http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html

    this.head = head;      // The usual stuff now.
    this.tail = tail;
  }

  public boolean isEmpty() { 
   return false; 
  }

  public int size() {
    return 1 + tail.size(); // Is this a recursive call?
    // I prefer to call this "delegation" rather
    // than "recursion".
  }

  public String toString() {
    return "Cons("  +  head + "," + tail.toString()  +  ")";
  }
    
  public IList<E> append(IList<E> l) {
    return new Cons<E>(head, tail.append(l));
  }

  public IList<E> append(E e) {
    return append(new Cons<E>(e , new Nil<E>()));
  }

  public IList<E> reverse() {
    return tail.reverse().append(head);

    // // Equivalently:
    // IList <E> r = tail.reverse();
    // IList <E> s = r.append(head);
    // return s;
  }

  public boolean has(E e) {
    return (head.equals(e) || tail.has(e)); 
    // Short-circuit evaluation of "||" makes this efficient.
    // Search for "short-circuit evaluation" in the internet.
  }
  //Get methods:
  @Override
  public E getHead() {
	return head;
  }

  @Override
  public IList<E> getTail() {
	return tail;
  }
}
