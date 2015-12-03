package ilist;

/**
 * Implementation of the empty list
 * (using the "composite pattern").
 */

public class Nil<E> implements IList<E> {
    
  public Nil() { // Nothing to do in the constructor!
  }              // Could simply remove it.

  public boolean isEmpty() { 
    return true; 
  }

  public int size() {
    return 0;
  }

  public String toString() { 
    return "Nil"; 
  }

  public IList<E> append(IList<E> l) {
    return l;
  }

  public IList<E> append(E e) {
    return new Cons<E>(e , this);
  }

  public IList<E> reverse() {
    return this;
  }

  public boolean has(E e) {
    return false;
  }

  @Override
  public E getHead() {
	return null;
  }

  @Override
  public IList<E> getTail() {
	return null;
  }
}
