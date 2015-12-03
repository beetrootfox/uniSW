package Astar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rp.util.Comparator;
import rp.util.SimpleSet;


/**
 * The Class PathFinder.
 * 
 * Module that can be used to find a path trough a graph between two given
 * nodes.
 *
 * @param <A>
 *            the generic type
 */
public class PathFinder<A> {

	/**
	 * Instantiates a new path finder.
	 */
	public PathFinder() {

	}

	/**
	 * Remember path. Recreates the path using map of visited nodes.
	 *
	 * @param map
	 *            the map
	 * @param path
	 *            the path
	 * @return the maybe. maybe
	 */
	public maybe.Maybe<ilist.IList<Node<A>>> rememberPath(
			ArrayList<GeneralNode<A>> map, ilist.IList<Node<A>> path) {
		if (findNode(map, path.getHead()).getParent().equals(path.getHead())) {
			return new maybe.Just<ilist.IList<Node<A>>>(path);
		} else {
			return rememberPath(map,
					new ilist.Cons<Node<A>>(findNode(map, path.getHead())
							.getParent(), path));
		}
	}
	
	/**
	 * Find node.
	 *
	 * @param map the map
	 * @param node the node
	 * @return the general node
	 */
	private GeneralNode<A> findNode(ArrayList<GeneralNode<A>> map, Node<A> node){
		GeneralNode<A> found = null;
		
		for(int i = 0; i < map.size(); i++){
			if(map.get(i).getNode().equals(node)){
				found = map.get(i);
			}
		}
		
		return found;
	}
	

	/**
	 * General search. Generalised algorithm that can work as Breadth
	 * First/Depth First/A*
	 *
	 * @param start
	 *            the start
	 * @param goal	
	private 
	 *            the goal
	 * @param costFun
	 *            the cost fun
	 * @param heuristicFun
	 *            the heuristic fun
	 * @param comp
	 *            the comp
	 * @return the maybe. maybe
	 */
	@SuppressWarnings("deprecation")
	public maybe.Maybe<ilist.IList<Node<A>>> generalSearch(Node<A> start,
			Node<A> goal, Distance<A> costFun, Distance<A> heuristicFun,
			Comparator<GeneralNode<A>> comp) {

		ArrayList<Node<A>> visited = new ArrayList<Node<A>>();
		ArrayList<GeneralNode<A>> journey = new ArrayList<GeneralNode<A>>();
		List<GeneralNode<A>> pender = new LinkedList<GeneralNode<A>>();
		GeneralNode<A> gStart = new GeneralNode<A>(start);
		gStart.setCost(0);
		gStart.setDistToGoal(heuristicFun.calculate(start.contents(),
				goal.contents()));
		pender.add(gStart);
		
		gStart.setParent(gStart.getNode());
		journey.add(gStart);
		while (!pender.isEmpty()) {
			GeneralNode<A> node = pender.get(0);
			pender.remove(0);
			double cost = node.getCost();
			Node<A> y = node.getNode();
			if (!visited.contains(y)) {
				if (y.equals(goal)) {
					return rememberPath(journey, new ilist.Cons<Node<A>>(y,
							new ilist.Nil<Node<A>>()));
				} else {
					visited.add(y);
					SimpleSet<Node<A>> sucrs = y.successors();
					for (Node<A> sucr : sucrs) {
						GeneralNode<A> genSucr = new GeneralNode<A>(sucr);
						genSucr.setCost(cost
								+ costFun.calculate(y.contents(),
										sucr.contents()));
						genSucr.setDistToGoal(heuristicFun.calculate(
								sucr.contents(), goal.contents()));
						if (findNode(journey, sucr) == null) {
							genSucr.setParent(y);
							journey.add(genSucr);
							pender.add(genSucr);
							rp.util.Collections.sort(pender, comp);
						} //else {
						//	if (journey.get(sucr.contents()).getCost() > genSucr
						//			.getCost()) {
						//		
						//		// journey.replace(sucr.contents(), node);
						//		journey.remove(sucr.contents());
						//		journey.put(sucr.contents(), node);
						//	}
						//}

					}
				}
			}
		}

		return new maybe.Nothing<ilist.IList<Node<A>>>();
	}
	
	/**
	 * Checks if is blocked.
	 *
	 * @param blocked the blocked
	 * @param node the node
	 * @return true, if is blocked
	 */
	private boolean isBlocked(ArrayList<A> blocked, Node<A> node){
		for(int i = 0; i < blocked.size(); i++){
			if(node.contents().equals(blocked.get(i))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * General search.
	 *
	 * @param start the start
	 * @param goal the goal
	 * @param costFun the cost fun
	 * @param heuristicFun the heuristic fun
	 * @param comp the comp
	 * @param blocked the blocked
	 * @return the maybe. maybe
	 */
	public maybe.Maybe<ilist.IList<Node<A>>> generalSearch(Node<A> start,
			Node<A> goal, Distance<A> costFun, Distance<A> heuristicFun,
			Comparator<GeneralNode<A>> comp, ArrayList<A> blocked) {

		ArrayList<Node<A>> visited = new ArrayList<Node<A>>();
		ArrayList<GeneralNode<A>> journey = new ArrayList<GeneralNode<A>>();
		List<GeneralNode<A>> pender = new LinkedList<GeneralNode<A>>();
		GeneralNode<A> gStart = new GeneralNode<A>(start);
		gStart.setCost(0);
		gStart.setDistToGoal(heuristicFun.calculate(start.contents(),
				goal.contents()));
		pender.add(gStart);
		
		gStart.setParent(gStart.getNode());
		journey.add(gStart);
		while (!pender.isEmpty()) {
			GeneralNode<A> node = pender.get(0);
			pender.remove(0);
			double cost = node.getCost();
			Node<A> y = node.getNode();
			if (!visited.contains(y)) {
				if (y.equals(goal)) {
					return rememberPath(journey, new ilist.Cons<Node<A>>(y,
							new ilist.Nil<Node<A>>()));
				} else {
					visited.add(y);
					SimpleSet<Node<A>> sucrs = y.successors();
					for (Node<A> sucr : sucrs) {
						if(!isBlocked(blocked, sucr)){
						GeneralNode<A> genSucr = new GeneralNode<A>(sucr);
						genSucr.setCost(cost
								+ costFun.calculate(y.contents(),
										sucr.contents()));
						genSucr.setDistToGoal(heuristicFun.calculate(
								sucr.contents(), goal.contents()));
						if (findNode(journey, sucr) == null) {
							genSucr.setParent(y);
							journey.add(genSucr);
							pender.add(genSucr);
							rp.util.Collections.sort(pender, comp);	
						} 
						}
				
						//else {
						//	if (journey.get(sucr.contents()).getCost() > genSucr
						//			.getCost()) {
						//		
						//		// journey.replace(sucr.contents(), node);
						//		journey.remove(sucr.contents());
						//		journey.put(sucr.contents(), node);
						//	}
						//}

					}
				}
			}
		}

		return new maybe.Nothing<ilist.IList<Node<A>>>();
	}

}
