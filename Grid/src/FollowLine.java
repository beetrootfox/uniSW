

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;


/**
 * The Class FollowLine.
 */
public class FollowLine implements Behavior{

    /** The pilot. */
    private final DifferentialPilot pilot;
    
    /** The driver. */
    private double driver;
    
    /** The supressed. */
    private static boolean supressed;
    
    /** The ls2. */
    private LightSensor ls2;
    
    /** The ls3. */
    private LightSensor ls3;
    
    private static boolean control;

   

    /**
     * Instantiates a new follow line.
     *
     * @param pilot the pilot
     * @param ls2 the ls2
     * @param ls3 the ls3
     */
    public FollowLine(DifferentialPilot pilot, LightSensor ls2, LightSensor ls3){
    	this.ls2 = ls2;
    	this.ls3 = ls3;
        this.pilot = pilot;
        driver = 0;
        supressed = false;
        control = false;
        pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/2.3);
        
        SensorPort.S2.addSensorPortListener(new SensorPortListener() {
			
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver =  driver - (aNewValue - aOldValue)*1.2;
				
			}
		});
    	
    	SensorPort.S3.addSensorPortListener(new SensorPortListener() {
    	
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver = driver + (aNewValue - aOldValue)*1.2;
		
				
			}
		});
    }
    
	
    /**
     * Unsup.
     */
    public static void claimControl(){
    	control = true;
    }

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#takeControl()
	 */
	@Override
	public boolean takeControl() {
		return control;
	}

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#action()
	 */
	@Override
	public void action() {
		
	        	while(!supressed){
	        		pilot.steer(driver);
	        		
	        		if(ls2.getLightValue() < 60 && ls3.getLightValue() < 60){
	        			pilot.stop();
	        			supressed = true;
	        			PickDirection.setSuppressed(false);
	        		}
	        		
	        		Thread.yield();
	        	}
	        	pilot.travel(16);	
	        	control = false;
	}

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#suppress()
	 */
	@Override
	public void suppress() {

	}


}