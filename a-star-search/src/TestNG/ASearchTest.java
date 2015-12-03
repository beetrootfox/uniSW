package TestNG;

import gridMap.r3GridMap;
import ilist.IList;

import java.util.Random;

import maybe.Maybe;

import org.testng.Assert;
import org.testng.annotations.Test;

import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import Astar.Coordinate;
import Astar.Distance;
import Astar.MapToGraph;
import Astar.Node;
import Astar.PathFinder;

/**
 * The Class ASearchTest.
 */
public class ASearchTest {

	
    /**
     * Test.
     */
    @Test(invocationCount = 1000)
    public static void test() {
            RPLineMap lineMap = MapUtils.create2015Map1();
            // grid width
            int xJunctions = 12;
            // grid height
            int yJunctions = 8;
            // distance between two points
            float junctionSeparation = 30;
            // the x distance from the edge
            int xInset = 15; 
            // the y distance from the edge
            int yInset = 15;
            
            r3GridMap gridmap = new r3GridMap(xJunctions, yJunctions, xInset, yInset, junctionSeparation, lineMap);
            MapToGraph graph = new MapToGraph(gridmap);
            //grid.convert();
            
            Random generator = new Random();
            int xStart, yStart, xGoal, yGoal;

            xStart = generator.nextInt(xJunctions - 1);
            yStart = generator.nextInt(yJunctions - 1);
            xGoal = generator.nextInt(xJunctions - 1);
            yGoal = generator.nextInt(yJunctions - 1);
            
            while(xGoal == xStart && yGoal == yStart){
                xGoal = generator.nextInt(xJunctions - 1);
                yGoal = generator.nextInt(yJunctions - 1);
            }
            
            
            Node<Coordinate> start = graph.getGraph().nodeWith(new Coordinate(xStart, yStart));
            Node<Coordinate> goal = graph.getGraph().nodeWith(new Coordinate(xGoal, yGoal));
            
            //initialize comparator that enables priority queue functionality
            Astar.GeneralSearchComparator comp = new Astar.GeneralSearchComparator();
            
            PathFinder<Coordinate> finder = new PathFinder<Coordinate>();
            
            Maybe<IList<Node<Coordinate>>> path = finder.generalSearch(
            				start, goal,
            				new Distance<Coordinate>() {

            					@Override
            					public double calculate(Coordinate pos1, Coordinate pos2) {

            						return Math.abs(pos1.x - pos2.x)
            								+ Math.abs(pos1.y - pos2.y);
            					}

            				}, new Distance<Coordinate>() {

            					@Override
            					public double calculate(Coordinate pos1, Coordinate pos2) {

            						return Math.abs(pos1.x - pos2.x)
            								+ Math.abs(pos1.y - pos2.y);
            					}

            				}, comp);
            
            if(gridmap.isObstructed(xStart, yStart) || gridmap.isObstructed(xGoal, yGoal)) {
                   Assert.assertTrue(path.isNothing(), "Found non-existing path" + xStart + ", " + yStart + "; " + xGoal + ", " + yGoal);
            }else{
            	Assert.assertFalse(path.isNothing(), "No path found");   
            	Assert.assertEquals(path.fromMaybe().getHead(), start, "Starting nodes does not match");
            	Assert.assertEquals(path.fromMaybe().reverse().getHead(), goal, "Goal nodes does not match");
            }

    }
}
