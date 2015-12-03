import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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
	 * Remember path. Recreates the path using map of visited nodes.
	 *
	 * @param map the map
	 * @param path the path
	 * @return the maybe. maybe
	 */
	public maybe.Maybe<ilist.IList<Node<A>>> rememberPath(Map<A, GeneralNode<A>> map, ilist.IList<Node<A>> path){
		  if(map.get(path.getHead().contents()).getNode().equals(path.getHead())){
			  return new maybe.Just<ilist.IList<Node<A>>>(path);
		  }
		  else
		  return rememberPath(map, new ilist.Cons<Node<A>>(map.get(path.getHead().contents()).getNode(), path));  	  
	  }


 	/**
	  * General search. Generalised algorithm that can work as Breadth First/Depth First/A*
	  *
	  * @param start the start
	  * @param goal the goal
	  * @param costFun the cost fun
	  * @param heuristicFun the heuristic fun
	  * @param comp the comp
	  * @return the maybe. maybe
	  */
	 public maybe.Maybe<ilist.IList<Node<A>>> generalSearch(Node<A> start, Node<A> goal, Distance<A> costFun, 
															Distance<A> heuristicFun, Comparator<GeneralNode<A>> comp ){
 		
		  Map<A, Node<A>> visited = new LinkedHashMap<A, Node<A>>();
		  Map<A, GeneralNode<A>> journey = new LinkedHashMap<A, GeneralNode<A>>();
		  PriorityQueue<GeneralNode<A>> pender = new PriorityQueue<GeneralNode<A>>(comp);
		  GeneralNode<A> gStart = new GeneralNode<A>(start);
		  gStart.setCost(0);
		  gStart.setDistToGoal(heuristicFun.calculate(start.contents(), goal.contents()));
		  pender.add(gStart);
		  
		  journey.put(gStart.getNodeContents(), gStart);
		  int count = 0;
		  while(!pender.isEmpty()){
			  count++;
			  GeneralNode<A> node = pender.poll();	
			  double cost = node.getCost();
			  Node<A> y = node.getNode();
			  if(!visited.containsKey(y.contents())){
				  if(y.equals(goal)){
					  System.out.println("!" + count + "!");
					  return rememberPath(journey, new ilist.Cons<Node<A>>(y, new ilist.Nil<Node<A>>()));
				  }else{
					  visited.put(y.contents(), y);
					  Set<Node<A>> sucrs = y.successors();
					  for(Node<A> sucr : sucrs){
						  GeneralNode<A> genSucr = new GeneralNode<A>(sucr);
						  genSucr.setCost(cost + costFun.calculate(y.contents(), sucr.contents()));
						  genSucr.setDistToGoal(heuristicFun.calculate(sucr.contents(), goal.contents()));
						  if(!journey.containsKey(sucr.contents())){
							  journey.put(sucr.contents(), node);
							  pender.add(genSucr);
						  }else{
							  if(journey.get(sucr.contents()).getCost() > genSucr.getCost()){
								  journey.replace(sucr.contents(), node);
							  }
						  }
						  
					  	} 
					 }
				  }
		  }
		  System.out.println("!" + count + "!");
		  return new maybe.Nothing<ilist.IList<Node<A>>>();
	  }
}
