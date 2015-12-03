package localistationTest;


import lejos.geom.Point;
import lejos.robotics.navigation.Pose;

import org.testng.Assert;
import org.testng.annotations.Test;

import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.simulation.SimulatedRobot;
import rp.robotics.visualisation.GridMapViewer;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalisationTest.
 */
public class LocalisationTest {
	
		/**
		 * Test.
		 */
		@Test
		public static void test(){
			// Work on this map
			RPLineMap lineMap = MapUtils.create2015Map1();

			// Grid map configuration

			// Grid junction numbers
			int xJunctions = 14;
			int yJunctions = 8;

			float junctionSeparation = 30;

			int xInset = 15;
			int yInset = 15;

			IGridMap gridMap = GridMapViewer.createGridMap(lineMap, xJunctions,
					yJunctions, xInset, yInset, junctionSeparation);

			// the starting position of the robot for the simulation. This is not
			// known in the action model or position distribution
			int startGridX = 2;
			int startGridY = 1;


			// this converts the grid position into the underlying continuous
			// coordinate frame
			Point startPoint = gridMap.getCoordinatesOfGridPosition(startGridX,
					startGridY);

			// starting heading
			float startTheta = Heading.toDegrees(Heading.PLUS_X);

			Pose startPose = new Pose(startPoint.x, startPoint.y, startTheta);

			// This creates a simulated robot with single, forward pointing distance
			// sensor with similar properties to the Lego ultrasonic sensor but
			// without the noise
			SimulatedRobot robot = SimulatedRobot.createSingleNoiseFreeSensorRobot(
					startPose, lineMap);

			localisation.MarkovLocalisationSkeleton ml = new localisation.MarkovLocalisationSkeleton(robot,
					lineMap, gridMap, junctionSeparation);
			//measure the probability of robot being were it actually is before the localising sequence of moves is executed
			float initProb = ml.getProbAt(startGridX, startGridY);
			ml.run();
			//measure the probability after the localisation
			//robot returns to the starting position after the sequence of moves is executed, so the coordinates are the same
			float endProb = ml.getProbAt(startGridX, startGridY);
			Assert.assertTrue(endProb > initProb, "Probability of robot being in its actual position did not increase!");
			System.out.println("final probability: " + endProb + "; starting probability: " + initProb);
			
		}
}
