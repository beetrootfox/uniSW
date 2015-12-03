package Test;

import gridMap.r3GridMap;
import gridfollower.GridFollower;
import ilist.IList;
import lejos.nxt.Button;
import maybe.Maybe;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.systems.RobotProgrammingDemo;
import Astar.Coordinate;
import Astar.Distance;
import Astar.MapToGraph;
import Astar.Node;
import Astar.PathFinder;


// TODO: Auto-generated Javadoc
/**
 * The Class PartOneTest.
 */
public class PartOneTest {

	/** The map. */
	private RPLineMap map;
	
	/** The grid. */
	private r3GridMap grid;
	
	/** The graph. */
	private MapToGraph graph;
	
	/** The finder. */
	private PathFinder<Coordinate> finder;
	
	/** The start coord. */
	private Coordinate startCoord;
	
	/** The goal coord. */
	private Coordinate goalCoord;
	
	/**
	 * Instantiates a new part one test.
	 *
	 * @param map the map
	 * @param start the start
	 * @param goal the goal
	 */
	public PartOneTest(RPLineMap map, Coordinate start, Coordinate goal){
		this.map = map;
		this.grid = new r3GridMap(14, 8, 15, 15, 30, this.map);
		this.graph = new MapToGraph(grid);
		this.finder = new PathFinder<Coordinate>();
		this.startCoord = start;
		this.goalCoord = goal;
	}
	
	/**
	 * Run test.
	 */
	public void runTest(){
		Node<Coordinate> start = graph.getGraph().nodeWith(startCoord);
        Node<Coordinate> goal = graph.getGraph().nodeWith(goalCoord);
        
        //initialize comparator that enables priority queue functionality
        Astar.GeneralSearchComparator comp = new Astar.GeneralSearchComparator(false);
        
        
        Maybe<IList<Node<Coordinate>>> path = finder.generalSearch(
        				start, goal,
        				new Distance<Coordinate>() {

        					@Override
        					public double calculate(Coordinate pos1, Coordinate pos2) {

        						return 0;
        					}

        				}, new Distance<Coordinate>() {

        					@Override
        					public double calculate(Coordinate pos1, Coordinate pos2) {

        						return 0;
        					}

        				}, comp);
        
        if(!path.isNothing()){
        	RobotProgrammingDemo demo = new GridFollower(path.fromMaybe(), graph.getGraph());
        	demo.run();
        }
	}
	
	/**
	 * Prints the path.
	 *
	 * @param path the path
	 */
	public void printPath(ilist.IList<Node<Coordinate>> path) {
		if (!path.isEmpty()) {
			System.out.print("(" + path.getHead().contents().x + ", "
					+ path.getHead().contents().y + "), ");
			printPath(path.getTail());
		} else {
			System.out.println();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		RPLineMap lineMap = MapUtils.create2015Map1();
		PartOneTest test = new PartOneTest(lineMap, new Coordinate(0, 0), new Coordinate(1, 6));
		Button.waitForAnyPress();
		test.runTest();
		
	}

}
