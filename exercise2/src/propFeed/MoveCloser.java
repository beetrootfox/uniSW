package propFeed;

import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The Class MoveCloser.
 */
public class MoveCloser implements Behavior{

	/** The pilot. */
	private final DifferentialPilot pilot;
	
	/** The sensor. */
	private final OpticalDistanceSensor sensor;
	
	/** The boolean which tells the arbitrator that the behavior should be active . */
	private boolean control;
	
	/** The distance. */
	private int distance;
	
	/** The boolean which tells us when the behavior is suppressed. */
	private boolean suppressed;
	
	/**
	 * Instantiates a new move closer.
	 *
	 * @param sensor the sensor
	 * @param pilot the pilot
	 */
	public MoveCloser(OpticalDistanceSensor sensor, DifferentialPilot pilot){
		this.sensor = sensor;
		this.pilot = pilot;
		control = false;
		suppressed = false;
	}
	
	/**
	 * Distance to speed.
	 *
	 * @param distance the distance
	 * @param range the range
	 * @return the speed
	 */
	public int distToSpeed(double distance, int range) {
		if(distance <= 22){
			control = false;
			suppressed = true;
		}
		double speed = (distance) * pilot.getMaxTravelSpeed() / range;
		return (int) speed;
	}

	@Override
	public boolean takeControl() {
		if(sensor.getDistance() > 220){
			control = true;
		}
		return control;	
	}

	@Override
	public void action() {
		distance = sensor.getDistance();
		pilot.setTravelSpeed(distToSpeed(distance*0.1, 80));
		pilot.forward();
		while (!suppressed) {
			distance = sensor.getDistance();
			pilot.setTravelSpeed(distToSpeed(distance*0.1, 80));
			Thread.yield();
		}
		pilot.stop();
		suppressed = false;
	}

	@Override
	public void suppress() {
		
	}

}
