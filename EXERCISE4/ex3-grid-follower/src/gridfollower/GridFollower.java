package gridfollower;

import ilist.IList;

import java.util.ArrayList;

import Astar.Coordinate;
import Astar.Distance;
import Astar.Graph;
import Astar.Node;
import Astar.PathFinder;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import maybe.Maybe;
import rp.config.RobotConfigs;
import rp.robotics.mapping.Heading;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.SimpleSet;


// TODO: Auto-generated Javadoc
/**
 * The Class FollowGrid.
 */
public class GridFollower extends RobotProgrammingDemo {

	/** The robot. */
	private final WheeledRobotSystem robot;
	
	/** The heading. */
	private Heading heading;
	
	/** The graph. */
	private Graph<Coordinate> graph;
	
	/** The path. */
	private ilist.IList<Node<Coordinate>> path;
	
	/** The pilot. */
	private final DifferentialPilot pilot;
	
	/** The light sensor on the right side. */
	private final LightSensor ls2;
	
	/** The light sensor on the left side. */
	private final LightSensor ls3;
	
	/** The ds1. */
	private final OpticalDistanceSensor ds1;
	
	/** An array of moves required to follow a path. */
	private ArrayList<GridPositionRelocator> move;
	
	/** The driver. */
	private double driver;
	
	/** The blocked. */
	private ArrayList<Coordinate> blocked;
	
	/** The x. */
	private int x = 0;
	
	/** The y. */
	private int y = 0;
	
	/**
	 * Instantiates a new follow grid.
	 *
	 * @param path the path
	 * @param graph the graph
	 */
	public GridFollower(ilist.IList<Node<Coordinate>> path, Graph<Coordinate> graph){
		this.blocked = new ArrayList<Coordinate>();
		this.driver = 0;
		this.graph = graph;
		this.path = path;
		this.heading = Heading.PLUS_X;
		this.move = new ArrayList<GridPositionRelocator>();
		this.robot = new WheeledRobotSystem(RobotConfigs.CASTOR_BOT);
		ls2 = new LightSensor(SensorPort.S2);
		ls3 = new LightSensor(SensorPort.S3);
		ds1 = new OpticalDistanceSensor(SensorPort.S1);
		this.pilot = robot.getPilot();
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/3);
		this.x += path.getHead().contents().x;
		this.y += path.getHead().contents().y;
		nodesToMoves(this.path);
	
	}
	
	/**
	 * Prints the path.
	 *
	 * @param path the path
	 */
	public void printPath(ilist.IList<Node<Coordinate>> path) {
		if (!path.isEmpty()) {
			System.out.print("(" + path.getHead().contents().x + ", "
					+ path.getHead().contents().y + "), ");
			printPath(path.getTail());
		} else {
			System.out.println();
		}
	}
	
	
	/**
	 * Change path.
	 *
	 * @param c the c
	 */
	private void changePath(Coordinate c){
		
		blocked.add(reflectTranslation(x, y));
		Node<Coordinate> current = graph.nodeWith(c);
		Node<Coordinate> goal = graph.nodeWith(path.reverse().getHead().contents());
		PathFinder<Coordinate> finder = new PathFinder<Coordinate>();
        Astar.GeneralSearchComparator comp = new Astar.GeneralSearchComparator(false);
	    Maybe<IList<Node<Coordinate>>> newPath = finder.generalSearch(
  				current, goal,
  				new Distance<Coordinate>() {

  					@Override
  					public double calculate(Coordinate pos1, Coordinate pos2) {

  						return 0;
  					}

  				}, new Distance<Coordinate>() {

  					@Override
  					public double calculate(Coordinate pos1, Coordinate pos2) {

  						return 0;
  					}

  				}, comp, blocked);
	    if(!newPath.isNothing()){
	    	this.path = newPath.fromMaybe();
	    	this.move = new ArrayList<GridPositionRelocator>();
	    	nodesToMoves(newPath.fromMaybe());
	    }else{
	    	System.out.println("No possible path");
	    	System.exit(1);
	    }
	}
	
	/**
	 * Nodes to moves.
	 *
	 * @param _path the _path
	 */
	private void nodesToMoves (ilist.IList<Node<Coordinate>> _path){
		if(!_path.isEmpty() && !_path.getTail().isEmpty()){
			//System.out.print("recursion call! ");
			ArrayList<Move> steps = null;
			Heading afterHeading = heading;
			Node<Coordinate> from = _path.getHead();
			Node<Coordinate> to = _path.getTail().getHead();
			System.out.print(from.contents().x + ", " + from.contents().y + " / " + to.contents().x
					+ " ," + to.contents().y);
			if(to.contents().x - from.contents().x > 0){
				afterHeading = Heading.PLUS_X;
				steps = changeHeadingTo(afterHeading);
				heading = afterHeading;
			}else{
				if(to.contents().x - from.contents().x < 0){
					afterHeading = Heading.MINUS_X;
					steps = changeHeadingTo(afterHeading);
					heading = afterHeading;
				}else{
					if(to.contents().y - from.contents().y > 0){
						afterHeading = Heading.PLUS_Y;
						steps = changeHeadingTo(afterHeading);
						heading = afterHeading;
					}else{
						if(to.contents().y - from.contents().y < 0){
							afterHeading = Heading.MINUS_Y;
							steps = changeHeadingTo(afterHeading);
							heading = afterHeading;
						}
						
					}
				}
			}
			
			System.out.print(steps.toString());

			move.add(new GridPositionRelocator(steps, afterHeading));
			nodesToMoves(_path.getTail());
		}
	}
	
	/**
	 * Change heading to.
	 *
	 * @param newHeading the new heading
	 * @return the array list
	 */
	private ArrayList<Move> changeHeadingTo(Heading newHeading) {
		ArrayList<Move> steps = new ArrayList<Move>();
		switch(newHeading){
		case PLUS_X:
			switch(heading){
			case PLUS_X:
				steps.add(Move.TRANSLATE);
				return steps;
			case PLUS_Y:
				steps.add(Move.TURN_X);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_X:
				steps.add(Move.TURN_Y);
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_Y:
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			default:
				System.out.println("Incorrect Heading");
			}
		break;
		case PLUS_Y:
			switch(heading){
			case PLUS_X:
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			case PLUS_Y:
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_X:
				steps.add(Move.TURN_X);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_Y:
				steps.add(Move.TURN_Y);
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			default:
				System.out.println("Incorrect Heading");
			}
		break;
		case MINUS_X:
			switch(heading){
			case PLUS_X:
				steps.add(Move.TURN_Y);
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			case PLUS_Y:
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_X:
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_Y:
				steps.add(Move.TURN_X);
				steps.add(Move.TRANSLATE);
				return steps;
			default:
				System.out.println("Inco2rrect Heading");
			}
		case MINUS_Y:
			switch(heading){
			case PLUS_X:
				steps.add(Move.TURN_X);
				steps.add(Move.TRANSLATE);
				return steps;
			case PLUS_Y:
				steps.add(Move.TURN_X);
				steps.add(Move.TURN_X);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_X:
				steps.add(Move.TURN_Y);
				steps.add(Move.TRANSLATE);
				return steps;
			case MINUS_Y:
				steps.add(Move.TRANSLATE);
				return steps;
			default: 
				System.out.println("Incorrect Heading");
			}
		break;
		default:
			System.out.println("Incorrect Req_Heading");
		}
		return steps;
	}
	
	/**
	 * Reflect translation.
	 */
	private void reflectTranslation(){
		switch(heading){
		case PLUS_X:
			this.x++;
			break;
		case PLUS_Y:
			this.y++;
			break;
		case MINUS_Y:
			this.y--;
			break;
		case MINUS_X:
			this.x--;
			break;
		}
	}
	
	/**
	 * Reflect translation.
	 *
	 * @param _x the _x
	 * @param _y the _y
	 * @return the coordinate
	 */
	private Coordinate reflectTranslation(int _x, int _y){
		int xC = _x;
		int yC = _y;
		switch(heading){
		case PLUS_X:
			xC++;
			break;
		case PLUS_Y:
			yC++;
			break;
		case MINUS_Y:
			yC--;
			break;
		case MINUS_X:
			xC--;
			break;
		}
		return new Coordinate(xC, yC);
	}

	/**
	 * Follow.
	 *
	 * @param rel the rel
	 */
	private void follow(ArrayList<GridPositionRelocator> rel){
		main:
		for(int i = 0; i < move.size(); i++){
			GridPositionRelocator relocator = move.get(i);
			ArrayList<Move> steps = relocator.getPlan();
			for(int j = 0; j < steps.size(); j++){
				Move m = steps.get(j);
				System.out.println(m);
				
				if(m.equals(Move.TURN_X)){
					pilot.rotate(-90);
					System.out.println(-90);
				}else{
					if(m.equals(Move.TURN_Y)){
						pilot.rotate(90);
						System.out.println(90);
					}else{
						System.out.println("go");
						if(ds1.getDistance() < 170){
							i = Integer.MAX_VALUE;
							heading = relocator.getAfterHeading();
							Coordinate c = new Coordinate(x, y);
							changePath(c);
							follow(move);
							break main;
						}else{
							boolean go = true;
							while(go){
								pilot.steer(driver);
								if(ls2.getLightValue() < 60 && ls3.getLightValue() < 60){
									pilot.stop();
									pilot.travel(46);
									heading = relocator.getAfterHeading();
									reflectTranslation();
									go = false;
								}
								Thread.yield();
							}
						}
					}
				}
			}	
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {		
		
		
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
  
		
		//This code is to make calibration process fast and clear. 
		System.out.println("Place on bright surface");
		Button.waitForAnyPress();
		ls2.calibrateHigh();
		ls3.calibrateHigh();
		
		System.out.println("Place on dark surface");
		Button.waitForAnyPress();
		ls2.calibrateLow();
		ls3.calibrateLow();
		
		Button.waitForAnyPress();
	
		

		
		/*
		for(int i = 0; i < move.size(); i++){
			GridPositionRelocator reloc = move.get(i);
			ArrayList<Move> steps = reloc.getPlan();
			for(int j = 0; j < steps.size(); j++){
				Move m = steps.get(j);
				System.out.print(m);
				Delay.msDelay(2000);
			}
		}
		*/

		follow(move);
		
		Sound.beepSequence();

		
		if(new Coordinate(x, y).equals(path.reverse().getHead().contents())){
			System.out.println("SUCCESS!");
		}else{
			System.out.println("FAILURE!");
		}

	}
	
}


