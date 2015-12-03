

import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The Class PickDirection.
 * Executes a given list of moves.
 * If a translation to the next position is impossible will stop the robot and 
 * display the according message (module coping with obstacles will be expanded for Exercise 4 
 * of robotics in order to deal with dynamic environment). 
 */
public class PickDirection implements Behavior {
	
	/** The pilot. */
	private final DifferentialPilot pilot;
	
	/** The dist sen. */
	private final OpticalDistanceSensor distSen;
	
	/** The rotatex. */
	private final int ROTATEX = 1;
	
	/** The rotatey. */
	private final int ROTATEY = 2;
	
	/** The list of moves. */
	private int[] move;
	
	/** The iterator. */
	private int iterator;
	
	/** The suppressed. */
	private static boolean suppressed;
	

	/**
	 * Instantiates a new pick direction.
	 *
	 * @param pilot the pilot
	 * @param move the move
	 */
	public PickDirection(DifferentialPilot pilot, int[] move){
		suppressed = false;
		this.pilot = pilot;
		iterator = 0;
		this.move = move;
		distSen = new OpticalDistanceSensor(SensorPort.S4);
		
	}
	
    /**
     * Rotate in direction of x axes.
     */
    public void rotateX(){
    	pilot.rotate(90);
    }
    
    /**
     * Rotate in direction of y axes.
     */
    public void rotateY(){
    	pilot.rotate(-90);
    }
    
    /**
     * Translate forward.
     *
     * @return true, if successful
     */
    public boolean translateForward(){
    	if(distSen.getDistance() < 200){
    		return true;
    	}else{
    		FollowLine.claimControl();
    		return true;
    	}
    }
    
    /**
     * Sets the suppressed.
     *
     * @param v the new suppressed
     */
    public static void setSuppressed(boolean v){
    	suppressed = v;
    }
    
    /**
     * Execute move.
     *
     * @param m the m
     */
    public void executeMove(int m){
		switch(m){
		case ROTATEX:
			rotateX();
		case ROTATEY:
			rotateY();
		default:
			setSuppressed(translateForward());
		}
    }
	
	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#takeControl()
	 */
	@Override
	public boolean takeControl() {
		return true;
	}

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#action()
	 */
	@Override
	public void action() {
		try{
			while(!suppressed){
				executeMove(move[iterator]);
				iterator++;
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Execution finished");
			setSuppressed(true);
		}
			
	}

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#suppress()
	 */
	@Override
	public void suppress() {
		
	}

}
