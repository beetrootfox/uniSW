package propFeed;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

/**
 * The Class WallLover.
 */
public class WallLover extends RobotProgrammingDemo {

	/** The sensor. */
	private final OpticalDistanceSensor sensor;
	
	/** The robot. */
	private final WheeledRobotSystem robot;
	
	/** The pilot. */
	private final DifferentialPilot pilot;

	/**
	 * Instantiates a new wall lover.
	 */
	public WallLover() {
		this.robot = new WheeledRobotSystem(
				RobotConfigs.CASTOR_BOT);
		sensor = new OpticalDistanceSensor(SensorPort.S4);
		this.pilot = robot.getPilot();
	}
	
	/**
	 * Run.
	 */
	@Override
	public void run() {
		Arbitrator arby = new Arbitrator(new Behavior[] {new Stop(sensor), new MoveCloser(sensor, pilot), 
															new FallBack(sensor, pilot) });
		arby.start();
	}

	/**
	 * The main method.
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		RobotProgrammingDemo demo = new WallLover();
		demo.run();
	}
}
