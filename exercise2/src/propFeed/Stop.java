package propFeed;

import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.subsumption.Behavior;

/**
 * The Class Stop.
 */
public class Stop implements Behavior{

	
	/** The sensor. */
	private final OpticalDistanceSensor sensor;
	
	/** The boolean which tells the arbitrator that the behavior should be active . */
	private boolean control;
	
	/** The boolean which tells us when the behavior is suppressed. */
	private boolean suppressed;
	
	/**
	 * Instantiates a new stop.
	 *
	 * @param sensor the sensor
	 * @param pilot the pilot
	 */
	public Stop(OpticalDistanceSensor sensor){
		this.sensor = sensor;
		control = false;
		suppressed = false;
	}
	
	/**
	 * Distance to speed.
	 *
	 * @param distance the distance
	 */
	public void distToSpeed(double distance) {
		if(distance > 22 || distance < 18){
			control = false;
			suppressed = true;
		}
	}

	@Override
	public boolean takeControl() {
		if(sensor.getDistance() > 220 && sensor.getDistance() < 180){
			control = true;
		}
		return control;	
	}

	@Override
	public void action() {
		
		while (!suppressed) {
			Thread.yield();
		}
		suppressed = false;
	}

	@Override
	public void suppress() {
		
	}
}
