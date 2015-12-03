
import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

/**
 * The Class BallLover.
 */
public class BallLover extends RobotProgrammingDemo {
	
    /** The robot. */
    private final WheeledRobotSystem robot;
    
    /** The pilot. */
    private final DifferentialPilot pilot;
    
    
	/**
	 * Instantiates a new ball lover.
	 */
	public BallLover(){
	        robot = new WheeledRobotSystem(RobotConfigs.CASTOR_BOT);
	        pilot = robot.getPilot();
	        pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/3);
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		NXTCam cam = new NXTCam(SensorPort.S1);
		cam.setTrackingMode(NXTCam.COLOR);
		cam.sortBy(NXTCam.COLOR);
		cam.enableTracking(true);
		
		//What this effectively does is dividing 'picture' captured by the NXTCam into
		//three sections. If the tracked object is in the middle section, robot will move forward,
		//if it is in one of the side sections, robot will turn accordingly. 
		while(m_run){
			if(cam.getRectangle(0).width > 7){
				while(cam.getRectangle(0).x > 60 && cam.getRectangle(0).x < 120 ){
					pilot.forward();
					Delay.msDelay(100);
				}
				while(cam.getRectangle(0).x < 60){
					pilot.rotate(15);
					Delay.msDelay(100);
				
				}
				while(cam.getRectangle(0).x > 110){
					pilot.rotate(-15);
					Delay.msDelay(100);
					
				}
			}else{
				pilot.stop();
			}
		}
	}
	
	/**
	 * The main method.
	 */
	public static void main(String[] args){
		Button.waitForAnyPress();
		RobotProgrammingDemo edwardo = new BallLover();
		edwardo.run();
		
	}

}
