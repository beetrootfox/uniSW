import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * The Class PathFinder.
 * 
 * Module that can be used to find a path trough a graph between two given nodes.
 *
 * @param <A> the generic type
 */
public class PathFinder<A> {
	
	/**
	 * Instantiates a new path finder.
	 */
	public PathFinder(){
		
	}
	
	  /**
	 * Reconstructs the path recursively.
	 *
	 * @param map the map holding nodes of path (their coordinates) as keys and their parents as values
	 * @param path the accumulator value initially holding the starting node
	 * @return Maybe list of nodes
	 */
	public maybe.Maybe<ilist.IList<Node<A>>> rememberPath(Map<A, Node<A>> map, ilist.IList<Node<A>> path){
		  if(map.get(path.getHead().contents()).equals(path.getHead())){
			  return new maybe.Just<ilist.IList<Node<A>>>(path);
		  }
		  else
		  return rememberPath(map, new ilist.Cons<Node<A>>(map.get(path.getHead().contents()), path));  	  
	  }

	 /**
 	 * Finds the path using Depth First search algorithm (successors are stored using stack).
 	 *
 	 * @param x the starting node
 	 * @param p the predicate determining when the algorithm finds the goal node
 	 * @return Maybe list of nodes
 	 */
 	public maybe.Maybe<ilist.IList<Node<A>>> depthFirstPath(Node<A> x, maybe.Predicate<A> p){
		  Stack<Node<A>> stack = new Stack<Node<A>>();
		  Map<A, Node<A>> visited = new LinkedHashMap<A, Node<A>>();
		  Map<A, Node<A>> journey = new LinkedHashMap<A, Node<A>>();
		  stack.push(x);
		  journey.put(x.contents(), x);
		  while(!stack.isEmpty()){
			  Node<A> y = stack.pop();
			  if(!visited.containsValue(y)){
				  if(p.holds(y.contents())){
					  return rememberPath(journey, new ilist.Cons<Node<A>>(y, new ilist.Nil<Node<A>>()));
				  }else{
					  visited.put(y.contents(), y);
					  Set<Node<A>> sucrs = y.successors();
					  for(Node<A> node : sucrs){
						  if(!journey.containsKey(node.contents())){
							  journey.put(node.contents(), y); 
							//only the new values are inserted into the map holding info
							//about the path to avoid looping
						  }
						  stack.push(node);
					  }
				  }
			  }
		  }
		  
		  return new maybe.Nothing<ilist.IList<Node<A>>>();
	  }
	  
	 /**
 	 * Finds the path using Breadth First search algorithm (successors are stored using queue).
 	 *
 	 * @param x the starting node
 	 * @param p the predicate determining when the algorithm finds the goal node
 	 * @return Maybe list of nodes
 	 */
 	public maybe.Maybe<ilist.IList<Node<A>>> breadthFirstPath(Node<A> x, maybe.Predicate<A> p){
		 Queue<Node<A>> queue = new LinkedList<Node<A>>();
		  Map<A, Node<A>> visited = new LinkedHashMap<A, Node<A>>();
		  Map<A, Node<A>> journey = new LinkedHashMap<A, Node<A>>();
		  queue.add(x);
		  journey.put(x.contents(), x);
		  while(!queue.isEmpty()){
			  Node<A> y = queue.poll();
			  if(!visited.containsValue(y)){
				  if(p.holds(y.contents())){
					  return rememberPath(journey, new ilist.Cons<Node<A>>(y, new ilist.Nil<Node<A>>()));
				  }else{
					  visited.put(y.contents(), y);
					  Set<Node<A>> sucrs = y.successors();
					  for(Node<A> node : sucrs){
						  if(!journey.containsKey(node.contents())){
							  journey.put(node.contents(), y);
							//only the new values are inserted into the map holding info
							//about the path to avoid looping
						  }
						  queue.add(node);
					  }
				  }
			  }
		  }
		  
		  return new maybe.Nothing<ilist.IList<Node<A>>>();
	  }

}
