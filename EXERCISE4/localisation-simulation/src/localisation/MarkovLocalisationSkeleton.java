package localisation;


import javax.swing.JFrame;

import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.PerfectSensorModel;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.simulation.SimulatedRobot;
import rp.robotics.visualisation.GridMapViewer;
import rp.robotics.visualisation.GridPositionDistributionVisualisation;
import rp.robotics.visualisation.KillMeNow;
import lejos.geom.Point;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Pose;
import lejos.util.Delay;


// TODO: Auto-generated Javadoc
/**
 * The Class MarkovLocalisationSkeleton.
 */
public class MarkovLocalisationSkeleton {

	// Maps
	/** The m_line map. */
	private final LineMap m_lineMap;
	
	/** The m_grid map. */
	private final IGridMap m_gridMap;

	// Probability distribution over the position of a robot on the given
	// grid map
	/** The m_distribution. */
	private GridPositionDistribution m_distribution;

	// Robot to drive around
	/** The m_robot. */
	private final SimulatedRobot m_robot;

	// Visualisation
	/** The m_map vis. */
	private GridPositionDistributionVisualisation m_mapVis;
	
	/** The m_translation amount. */
	private final float m_translationAmount;

	/**
	 * Instantiates a new markov localisation skeleton.
	 *
	 * @param _robot the _robot
	 * @param _lineMap the _line map
	 * @param _gridMap the _grid map
	 * @param _translationAmount the _translation amount
	 */
	public MarkovLocalisationSkeleton(SimulatedRobot _robot, LineMap _lineMap,
			IGridMap _gridMap, float _translationAmount) {


		m_robot = _robot;
		m_lineMap = _lineMap;
		m_gridMap = _gridMap;
		m_distribution = new GridPositionDistribution(m_gridMap);
		m_translationAmount = _translationAmount;
	}
	
	/**
	 * Gets the prob at.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the prob at
	 */
	public float getProbAt(int x, int y){
		return m_distribution.getProbability(x, y);
	}

	/**
	 * Optionally run the visualisation of the robot and localisation process.
	 * This is not necessary to run the localisation and could be removed once
	 * on the real robot.
	 */
	public void visualise() {
		JFrame frame = new JFrame("Map Viewer");
		frame.addWindowListener(new KillMeNow());

		// visualise the distribution on top of a line map
		m_mapVis = new GridPositionDistributionVisualisation(m_distribution,
				m_lineMap, 2);

		// Visualise the robot
		m_mapVis.addRobot(m_robot);

		frame.add(m_mapVis);
		frame.pack();
		frame.setSize(1050, 600);
		frame.setVisible(true);
	}

	/**
	 * *
	 * Move the robot and update the distribution with the action model.
	 *
	 * @param distance the distance
	 * @param _heading the _heading
	 * @param _actionModel the _action model
	 * @param _sensorModel the _sensor model
	 */
	private void move(float distance, Heading _heading,
			ActionModel _actionModel, SensorModel _sensorModel) {
		// move robot
		m_robot.translate(m_translationAmount);

		// now update estimate of position using the action model
		m_distribution = _actionModel.updateAfterMove(m_distribution, _heading);

		// if visualising, update the shown distribution
		if (m_mapVis != null) {
			m_mapVis.setDistribution(m_distribution);
		}

		// A short delay so we can see what's going on
		Delay.msDelay(500);

		//m_distribution = _sensorModel.updateAfterSensing(m_distribution,
		//		_heading, m_robot.getRangeValues());

		// if visualising, update the shown distribution
		if (m_mapVis != null) {
			m_mapVis.setDistribution(m_distribution);
		}

		// A short delay so we can see what's going on
		Delay.msDelay(500);
	}

	/**
	 * Run.
	 */
	public void run() {

		ActionModel actionModel = new PerfectActionModel();
		SensorModel sensorModel = new PerfectSensorModel();

		int horizontal = 5;
		int vertical = 1;

		// Assuming all the moves go in this direction. This will not work once
		// the robot turns...
		Heading movementHeading;
		int moves;

		while (true) {

			movementHeading = Heading.PLUS_X;
			moves = horizontal;
			for (int i = 0; i < moves; i++) {

				// For the simulated robotto I am simply moving it a preset
				// distance
				// to make it look as it if it successfully moved to the next
				// grid
				// position. For your code you should not use translation on the
				// continuous map, but instead use the something that abstracts
				// the
				// grid movement.

				move(m_translationAmount, movementHeading, actionModel,
						sensorModel);
			}

			m_robot.rotate(90);
			movementHeading = Heading.PLUS_Y;
			moves = vertical;
			for (int i = 0; i < moves; i++) {
				move(m_translationAmount, movementHeading, actionModel,
						sensorModel);
			}

			m_robot.rotate(90);
			movementHeading = Heading.MINUS_X;
			moves = horizontal;
			for (int i = 0; i < moves; i++) {
				move(m_translationAmount, movementHeading, actionModel,
						sensorModel);
			}

			m_robot.rotate(90);
			movementHeading = Heading.MINUS_Y;
			moves = vertical;
			for (int i = 0; i < moves; i++) {
				move(m_translationAmount, movementHeading, actionModel,
						sensorModel);
			}

			m_robot.rotate(90);

		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

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

		// This does the same as above but adds noise to the range readings
		// SimulatedRobot robot = SimulatedRobot.createSingleSensorRobot(
		// startPose, lineMap);

		MarkovLocalisationSkeleton ml = new MarkovLocalisationSkeleton(robot,
				lineMap, gridMap, junctionSeparation);
		ml.visualise();
		ml.run();

	}
}
