




import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

/**
 * The Class FollowGrid.
 */
public class FollowGrid extends RobotProgrammingDemo {

	/** The robot. */
	private final WheeledRobotSystem robot;
	
	/** The pilot. */
	private final DifferentialPilot pilot;
	
	/** The ls2. */
	private final LightSensor ls2;
	
	/** The ls3. */
	private final LightSensor ls3;
	
	/** The move. */
	private final int[] move;
	
	/**
	 * Instantiates a new follow grid.
	 *
	 * @param move the move
	 */
	public FollowGrid(int[] move){
		this.robot = new WheeledRobotSystem(
				RobotConfigs.CASTOR_BOT);
		this.move = move;
		ls2 = new LightSensor(SensorPort.S2);
		ls3 = new LightSensor(SensorPort.S3);
		//ls2.setFloodlight(false);
		//ls3.setFloodlight(false);
		this.pilot = robot.getPilot();
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/1.5);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {		
	

		
		System.out.println("Place on bright surface");
		Button.waitForAnyPress();
		ls2.calibrateHigh();
		ls3.calibrateHigh();
		
		System.out.println("Place on dark surface");
		Button.waitForAnyPress();
		ls2.calibrateLow();
		ls3.calibrateLow();
		
		Button.waitForAnyPress();
		

		
		Arbitrator arby = new Arbitrator(new Behavior[] {new PickDirection(pilot, move), 
															new FollowLine(pilot, ls2, ls3)}, false);
		
		arby.start();

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		Button.waitForAnyPress();
		RobotProgrammingDemo demo = new FollowGrid(new int[] {3, 2, 3, 1, 3});
		demo.run();
	}

}

