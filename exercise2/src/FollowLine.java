

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

/**
 * The Class FollowLine. 
 * Follows a dark line on the ground using two light sensors and trying to keep the
 * line in between. Current implementation features proportional feedback control and
 * uses pilot.steer(v) to drive and ButtonListeners to read sensor inputs. 
 */
public class FollowLine extends RobotProgrammingDemo {

    /** The robot. */
    private final WheeledRobotSystem robot;
    
    /** The pilot. */
    private final DifferentialPilot pilot;
    
    /** The driver. */
    private double driver;
    
    /** The speed. */
    private double speed;
    
    /** The error. */
    private double error;
    
    private final LightSensor ls2;
    private final LightSensor ls3;
   

    /**
     * Instantiates a new follow line.
     */
    public FollowLine(){
        robot = new WheeledRobotSystem(RobotConfigs.CASTOR_BOT);
        pilot = robot.getPilot();
        driver = 0;
        ls2 = new LightSensor(SensorPort.S2);
        ls3 = new LightSensor(SensorPort.S3);
        ls2.setFloodlight(true);   //flood light makes event handlers more reliable in darker environments.
        ls3.setFloodlight(true);
        this.speed = 1;
        this.error = 1;
    }
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    	System.out.println("Press LEFT for a circular track and RIGHT for more complicated one. ");
    	
    	Button.LEFT.addButtonListener(new ButtonListener(){

			@Override
			public void buttonPressed(Button b) {
				speed = 1;
				error = 0.6;
				
			}

			@Override
			public void buttonReleased(Button b) {
				// TODO Auto-generated method stub
				
			}
		
    	});
    	
    	Button.RIGHT.addButtonListener(new ButtonListener(){

			@Override
			public void buttonPressed(Button b) {
				speed = 2.3;
				error = 1.6;
				
			}

			@Override
			public void buttonReleased(Button b) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    	Button.waitForAnyPress();
    	
        pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/speed);
    	
    	SensorPort.S2.addSensorPortListener(new SensorPortListener() {
			
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver =  driver - (aNewValue - aOldValue)*error;
				
			}
		});
    	
    	SensorPort.S3.addSensorPortListener(new SensorPortListener() {
    	
			@Override
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

				driver = driver + (aNewValue - aOldValue)*error;
		
				
			}
		});
    
        while(m_run){
        	
        	while(true){
        		pilot.steer(driver);
        		Thread.yield();
        	}
                       
        }
  
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	Button.waitForAnyPress();
        FollowLine madness = new FollowLine();
        madness.run();
    }

}