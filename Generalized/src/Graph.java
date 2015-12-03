import java.util.*;



// We represent a graph as table of pairs (contents, node with that contents).
// This assumes that each node has a unique contents.
// This is a minimal class so that a graph can be created.

public class Graph<A> {

  // Keep the implementation of maps open, by using the Map interface:
  private Map<A,Node<A>> nodes;


  // Constructs the empty graph:
  public Graph() {
    // Choose any implementation of sets you please, but you need to
    // choose one. 
    nodes = new LinkedHashMap<A,Node<A>>(); 
  }

  // Find or create node with a given contents c:
  public Node<A> nodeWith(A c) { 
    Node<A> node; // Deliberately uninitialized.
    if (nodes.containsKey(c)) {
      node = nodes.get(c);
    } else {
      node = new Node<A>(c);
      nodes.put(c,node);
    }
    return node;
  }
  
  // Get method:
  public Map<A,Node<A>> nodes() {
    return nodes;
  }

}

