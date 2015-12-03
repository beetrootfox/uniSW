package Astar;
import java.util.ArrayList;

import gridMap.r3GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;


/**
 * The Class PathTest.
 */
public class PathTest {

	/**
	 * Instantiates a new path test.
	 */
	public PathTest() {

	}

	/**
	 * Prints the path.
	 *
	 * @param path
	 *            the path
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
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {

		int[][] nick = { { 0, 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 1, 1, 0, 2 },
				{ 0, 2, 0, 3, 0, 1 }, { 0, 3, 0, 2, 0, 4 },
				{ 0, 4, 0, 3, 0, 5 }, { 0, 5, 0, 6, 1, 5, 0, 4 },
				{ 0, 6, 1, 6, 0, 5 }, { 1, 0, 0, 0, 1, 1, 2, 0 },
				{ 1, 1, 1, 2, 2, 1, 1, 0, 0, 1 }, { 1, 2, 2, 2, 1, 1, 1, 3 },
				{ 1, 3, 1, 2, 1, 4, 2, 3 }, { 1, 4, 2, 4, 1, 5, 1, 3 },
				{ 1, 5, 1, 4, 2, 5, 1, 6, 0, 5 }, { 1, 6, 0, 6, 1, 5, 2, 6 },
				{ 2, 0, 3, 0, 2, 1, 1, 0 }, { 2, 1, 2, 2, 1, 1, 2, 0, 3, 1 },
				{ 2, 2, 1, 2, 2, 1, 2, 3, 3, 2 },
				{ 2, 3, 2, 2, 2, 4, 3, 3, 1, 3 },
				{ 2, 4, 1, 4, 2, 5, 2, 3, 3, 4 },
				{ 2, 5, 2, 4, 1, 5, 2, 6, 3, 5 }, { 2, 6, 3, 6, 2, 5, 1, 6 },
				{ 3, 0, 2, 0, 3, 1 }, { 3, 1, 3, 0, 4, 1, 2, 1, 3, 2 },
				{ 3, 2, 2, 2, 4, 2, 3, 1 }, { 3, 3, 2, 3, 3, 4 },
				{ 3, 4, 2, 4, 3, 3 }, { 3, 5, 3, 6, 2, 5, 4, 5 },
				{ 3, 6, 2, 6, 3, 5 }, { 4, 0 }, { 4, 1, 4, 2, 5, 1, 3, 1 },
				{ 4, 2, 4, 1, 5, 2, 3, 2 }, { 4, 3 }, { 4, 4 },
				{ 4, 5, 5, 5, 3, 5 }, { 4, 6 }, { 5, 0 },
				{ 5, 1, 4, 1, 5, 2, 6, 1 }, { 5, 2, 4, 2, 5, 1, 6, 2 },
				{ 5, 3 }, { 5, 4 }, { 5, 5, 4, 5, 6, 5 }, { 5, 6 },
				{ 6, 0, 7, 0, 6, 1 }, { 6, 1, 6, 0, 5, 1, 6, 2, 7, 1 },
				{ 6, 2, 5, 2, 6, 1, 7, 2 }, { 6, 3, 7, 3, 6, 4 },
				{ 6, 4, 6, 3, 7, 4 }, { 6, 5, 5, 5, 6, 6, 7, 5 },
				{ 6, 6, 7, 6, 6, 5 }, { 7, 0, 6, 0, 7, 1, 8, 0 },
				{ 7, 1, 8, 1, 7, 0, 6, 1, 7, 2 },
				{ 7, 2, 7, 3, 8, 2, 6, 2, 7, 1 },
				{ 7, 3, 6, 3, 7, 2, 7, 4, 8, 3 },
				{ 7, 4, 7, 3, 8, 4, 6, 4, 7, 5 },
				{ 7, 5, 8, 5, 7, 6, 7, 4, 6, 5 }, { 7, 6, 6, 6, 7, 5, 8, 6 },
				{ 8, 0, 8, 1, 7, 0, 9, 0 }, { 8, 1, 8, 2, 9, 1, 7, 1, 8, 0 },
				{ 8, 2, 8, 1, 7, 2, 8, 3 }, { 8, 3, 8, 2, 7, 3, 8, 4 },
				{ 8, 4, 8, 5, 8, 3, 7, 4 }, { 8, 5, 9, 5, 8, 4, 7, 5, 8, 6 },
				{ 8, 6, 8, 5, 7, 6, 9, 6 }, { 9, 0, 9, 1, 8, 0 },
				{ 9, 1, 8, 1, 9, 2, 9, 0 }, { 9, 2, 9, 1, 9, 3 },
				{ 9, 3, 9, 2, 9, 4 }, { 9, 4, 9, 5, 9, 3 },
				{ 9, 5, 8, 5, 9, 4, 9, 6 }, { 9, 6, 9, 5, 8, 6 } };

		Graph<Coordinate> nicksGraph = new Graph<Coordinate>();

		for (int i = 0; i < nick.length; i++) {
			// What we are going to do relies on the two following facts
			// about nick:
			assert (nick[i].length >= 2); // (1)
			assert (nick[i].length % 2 == 0); // (2)

			int x = nick[i][0]; // Can't get array out of bounds
			int y = nick[i][1]; // because of assertion (1).
			Coordinate c = new Coordinate(x, y);

			// Find or create node:
			Node<Coordinate> node = nicksGraph.nodeWith(c);

			for (int j = 2; j < nick[i].length; j = j + 2) {
				int sx = nick[i][j];
				int sy = nick[i][j + 1];
				Coordinate sc = new Coordinate(sx, sy);
				// Find or create successor node, and then add it
				Node<Coordinate> s = nicksGraph.nodeWith(sc);
				node.addSuccessor(s);
			}
		}

		PathTest test = new PathTest();

		RPLineMap map = MapUtils.create2015Map1();

		r3GridMap grid = new r3GridMap(14, 8, 15, 15, 30, map);

		MapToGraph conv = new MapToGraph(grid);

		PathFinder<Coordinate> finder = new PathFinder<Coordinate>();

		//GeneralSearchComparator comp = new GeneralSearchComparator();

		// GeneralSearchComparator comp = new GeneralSearchComparator(true);
		// //uncomment the previous comment to use DepthFirst
		 GeneralSearchComparator comp = new GeneralSearchComparator(false);
		// //uncomment the prevoius comment to use BreadthFirst
		 ArrayList<Coordinate> blocked = new ArrayList<Coordinate>();
	//	 blocked.add(new Coordinate(1, 3));
		// blocked.add(new Coordinate(2, 1));
		 	
		ilist.IList<Node<Coordinate>> path = finder.generalSearch(
				conv.getGraph().nodeWith(new Coordinate(0, 0)),
				conv.getGraph().nodeWith(new Coordinate(6, 1)),
				new Distance<Coordinate>() {

					@Override
					public double calculate(Coordinate pos1, Coordinate pos2) {

						//return Math.abs(pos1.x - pos2.x)
						//		+ Math.abs(pos1.y - pos2.y);
						 return 0; //uncomment the previous comment if you are
						// not using A*
					}

				}, new Distance<Coordinate>() {

					@Override
					public double calculate(Coordinate pos1, Coordinate pos2) {

					//	return Math.abs(pos1.x - pos2.x)
						//		+ Math.abs(pos1.y - pos2.y);
						 return 0; //uncomment the previous comment if you are
						// not using A*
					}

				}, comp, blocked).fromMaybe();

		test.printPath(path);

	}

}
