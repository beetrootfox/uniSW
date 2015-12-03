package localisation;


import gridMap.r3GridMap;
import gridfollower.Move;

import java.util.Random;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.PerfectSensorModel;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.visualisation.GridPositionDistributionVisualisation;
import rp.systems.WheeledRobotSystem;


// TODO: Auto-generated Javadoc
/**
 * The Class Localiser.
 */
public class Localiser {

	// Maps

	/** The m_grid map. */
	private final IGridMap m_gridMap;

	// Probability distribution over the position of a robot on the given
	// grid map
	/** The m_distribution. */
	private GridPositionDistribution m_distribution;





	
	/** The driver. */
	private double driver;
	
	/** The ls2. */
	private LightSensor ls2;
	
	/** The ls3. */
	private LightSensor ls3;
	
	/** The ds1. */
	private OpticalDistanceSensor ds1;
	
	/** The pilot. */
	private DifferentialPilot pilot;
	
	/** The location. */
	private Point location = null;

	/**
	 * Instantiates a new localiser.
	 *
	 * @param _gridMap the _grid map
	 */
	public Localiser(IGridMap _gridMap) {

		m_gridMap = _gridMap;
		m_distribution = new GridPositionDistribution(m_gridMap);

		
		this.driver = 0;
		this.ls2 = new LightSensor(SensorPort.S2);
		this.ls3 = new LightSensor(SensorPort.S3);
		this.ds1 = new OpticalDistanceSensor(SensorPort.S1);
		this.pilot = new WheeledRobotSystem(RobotConfigs.CASTOR_BOT).getPilot();
		this.pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/3);
		
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
	 * *
	 * Move the robot and update the distribution with the action model.
	 *
	 * @param heading the heading
	 * @param move the move
	 * @return the heading
	 */

	
	private Heading updateHeading(Heading heading, Move move){
		switch(heading){
		case PLUS_X:
			switch(move){
			case TURN_X:
				return Heading.MINUS_Y;
			case TURN_Y:
				return Heading.PLUS_Y;
			}
		case PLUS_Y:
			switch(move){
			case TURN_X:
				return Heading.PLUS_X;
			case TURN_Y:
				return Heading.MINUS_X;
			}
		case MINUS_X:
			switch(move){
			case TURN_X:
				return Heading.PLUS_Y;
			case TURN_Y:
				return Heading.MINUS_Y;
			}
		case MINUS_Y:
			switch(move){
			case TURN_X:
				return Heading.MINUS_X;
			case TURN_Y:
				return Heading.PLUS_X;
			}
		}
		return null;
	}

	/**
	 * Run.
	 */
	public void run() {

		PerfectActionModel actionModel = new PerfectActionModel();
		
        SensorPort.S2.addSensorPortListener(new SensorPortListener() {
			
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver =  driver - (aNewValue - aOldValue)*0.6;
				
			}
		});
    	
    	SensorPort.S3.addSensorPortListener(new SensorPortListener() {
    	
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver = driver + (aNewValue - aOldValue)*0.6;
		
				
			}
		});

		// Assuming all the moves go in this direction. This will not work once
		// the robot turns...
		Heading movementHeading = Heading.PLUS_X;
		Random gen = new Random();
		
		System.out.println("Place on bright surface");
		Button.waitForAnyPress();
		ls2.calibrateHigh();
		ls3.calibrateHigh();
		
		System.out.println("Place on dark surface");
		Button.waitForAnyPress();
		ls2.calibrateLow();
		ls3.calibrateLow();
		
		Button.waitForAnyPress();

		main:
		while(true){
			for(int i = 0; i < 6; i++){
			if(!(ds1.getDistance() < 190)){
			boolean go = true;
				while(go){
					pilot.steer(driver);
					if(ls2.getLightValue() < 45 && ls3.getLightValue() < 45){
						go = false;
						pilot.stop();
						pilot.travel(52);
						m_distribution = actionModel.updateAfterMove(m_distribution, movementHeading);
						System.out.println(movementHeading);
						if(actionModel.getHighestProb() != null){
							location = actionModel.getHighestProb();
							break main;
						}
					}
					Thread.yield();
				}
			}else{
				if(gen.nextBoolean()){
					pilot.rotate(-90);
					movementHeading = updateHeading(movementHeading, Move.TURN_X);
				}else{
					pilot.rotate(90);
					movementHeading = updateHeading(movementHeading, Move.TURN_Y);
				}
			}
			}
			
		} 
		
		/*
		main:
			while(true){
				pilot.rotate(-90);
				movementHeading = updateHeading(movementHeading, Move.TURN_X);
				if(!(ds1.getDistance() < 170)){
				boolean go = true;
					while(go){
						pilot.steer(driver);
						if(ls2.getLightValue() < 40 && ls3.getLightValue() < 40){
							go = false;
							pilot.stop();
							pilot.travel(52);
							m_distribution = actionModel.updateAfterMove(m_distribution, movementHeading);
							System.out.println(movementHeading);
							if(actionModel.getHighestProb() != null){
								location = actionModel.getHighestProb();
								break main;
							}
						}
						Thread.yield();
					}
				}else{
					while(ds1.getDistance() < 190){
						pilot.rotate(90);
						movementHeading = updateHeading(movementHeading, Move.TURN_Y);
						pilot.stop();
					}
					boolean go = true;
					while(go){
						pilot.steer(driver);
						if(ls2.getLightValue() < 60 && ls3.getLightValue() < 60){
							go = false;
							pilot.stop();
							pilot.travel(52);
							m_distribution = actionModel.updateAfterMove(m_distribution, movementHeading);
							System.out.println(movementHeading);
							if(actionModel.getHighestProb() != null){
								location = actionModel.getHighestProb();
								break main;
							}
						}
						Thread.yield();
					}
				}
			}
		*/
		System.out.println(location.x + ", " + location.y);
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

		IGridMap gridMap = new r3GridMap(xJunctions,
				yJunctions, xInset, yInset, junctionSeparation, lineMap);

		// the starting position of the robot for the simulation. This is not
		// known in the action model or position distribution


		// this converts the grid position into the underlying continuous
		// coordinate frame


		// starting heading


		// This creates a simulated robot with single, forward pointing distance
		// sensor with similar properties to the Lego ultrasonic sensor but
		// without the noise


		// This does the same as above but adds noise to the range readings
		// SimulatedRobot robot = SimulatedRobot.createSingleSensorRobot(
		// startPose, lineMap);

		Localiser ml = new Localiser(gridMap);
		ml.run();
		System.out.println("press");
		Button.waitForAnyPress();
		System.out.println("location: " + ml.location.toString());
		Button.waitForAnyPress();

	}
}
