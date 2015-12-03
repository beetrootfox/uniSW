package ilist;

/**
 * Interface for immutable lists using the "composite pattern".
 *
 * @param <E> the element type
 */

public interface IList<E> {
  
  /**
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  public boolean isEmpty();
  
  /**
   * Size.
   *
   * @return the int
   */
  public int     size();
  
  /**
   * Reverse.
   *
   * @return the i list
   */
  public IList<E> reverse();
  
  /**
   * Append.
   *
   * @param l the l
   * @return the i list
   */
  public IList<E> append(IList<E> l);
  
  /**
   * Append.
   *
   * @param e the e
   * @return the i list
   */
  public IList<E> append(E e);
  
  /**
   * Checks for.
   *
   * @param e the e
   * @return true, if successful
   */
  public boolean has(E e);  
  
  /**
   * Gets the head.
   *
   * @return the head
   */
  public E getHead();
  
  /**
   * Gets the tail.
   *
   * @return the tail
   */
  public IList<E> getTail();
  
}

/*

 We have two implementations of the IList interface:

    (1) The class Nil.
    (2) The class Cons.

 There is only one object of type NIL, the empty list.

 An object of type Cons<E> has a head, of type E, and a tail, which is
 a (reference to an) IList<E>.

 A list [1,2,3] is written (rather verbosely) as

 new Cons<Integer>(1,new Cons<Integer>(2,new Cons<Integer>(3,new Nil<Integer>())));

 For the sake of brevity, the toString method produces the string
 "Cons(1,Cons(2,Cons(3,Nil)))" for this list (in ocaml-like syntax).

 This is an instance of the "composite pattern", because we have one
 interface for lists, but two class implementations, one for each case
 Nil and Cons. Any list is built by combining Nil and Cons as above.

 We shall see more examples soon, including trees and arithmetic expressions. 

 Look at SampleUsage.java and run it to see what it outputs.

 */
