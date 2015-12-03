package Astar;
import gridMap.r3GridMap;


/**
 * The Class MapToGraph.
 */
public class MapToGraph {
	
	/** The map. */
	private r3GridMap map;
	
	/** The graph. */
	private Graph<Coordinate> graph;

	/**
	 * Instantiates a new map to graph.
	 *
	 * @param map the map
	 */
	public MapToGraph(r3GridMap map) {
		this.map = map;
		this.graph = new Graph<Coordinate>();
		convertMG();
	}

	/**
	 * Gets the graph.
	 *
	 * @return the graph
	 */
	public Graph<Coordinate> getGraph() {
		return graph;
	}

	/**
	 * Convert mg.
	 */
	private void convertMG() {
		int xSize = map.getXSize();
		int ySize = map.getYSize();
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				Coordinate c = new Coordinate(i, j);
				if (!map.isObstructed(c.x, c.y)
						&& map.isValidGridPosition(c.x, c.y)) {
					Node<Coordinate> node = this.graph.nodeWith(c);
					for (int k = -1; k < 2; k = k + 2) {
						Coordinate s1 = new Coordinate(i + k, j);
						if (map.isValidTransition(c.x, c.y, s1.x, s1.y)) {
							Node<Coordinate> sn1 = graph.nodeWith(s1);
							node.addSuccessor(sn1);
						}
						Coordinate s2 = new Coordinate(i, j + k);
						if (map.isValidTransition(c.x, c.y, s2.x, s2.y)) {
							Node<Coordinate> sn2 = graph.nodeWith(s2);
							node.addSuccessor(sn2);
						}

					}

				}
			}
		}
	}
}
