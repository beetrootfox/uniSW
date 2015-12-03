package localisation;


import lejos.geom.Point;
import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;

// TODO: Auto-generated Javadoc
/**
 * Example structure for an action model that should move the probabilities 1
 * cell in the requested direction. In the case where the move would take the
 * robot into an obstacle or off the map, this model assumes the robot stayed in
 * one place. This is the same as the model presented in Robot Programming
 * lecture on action models.
 * 
 * Note that this class doesn't actually do this, instead it shows you a
 * <b>possible</b> structure for your action model.
 * 
 * @author Nick Hawes
 * 
 */
public class PerfectActionModel implements ActionModel {
	
	/** The highest prob. */
	private float highestProb = 0;
	
	/** The possible position. */
	private Point possiblePosition;

	/* (non-Javadoc)
	 * @see rp.robotics.localisation.ActionModel#updateAfterMove(rp.robotics.localisation.GridPositionDistribution, rp.robotics.mapping.Heading)
	 */
	@Override
	public GridPositionDistribution updateAfterMove(
			GridPositionDistribution _from, Heading _heading) {

		// Create the new distribution that will result from applying the action
		// model
		GridPositionDistribution to = new GridPositionDistribution(_from);
		
		// Move the probability in the correct direction for the action
		return movePlusX(_from, to, _heading);
	}
	
	/**
	 * Gets the highest prob.
	 *
	 * @return the highest prob
	 */
	public Point getHighestProb(){
		if(highestProb == 1){
			return possiblePosition;
		}else{
			return null;
		}
	}

	/**
	 * Move probabilities from _from one cell in the plus x direction into _to.
	 *
	 * @param _from the _from
	 * @param _to the _to
	 * @param _heading the _heading
	 * @return the grid position distribution
	 */
	private GridPositionDistribution movePlusX(GridPositionDistribution _from,
			GridPositionDistribution _to, Heading _heading) {

		// iterate through points updating as appropriate
		for (int y = 0; y < _to.getGridHeight(); y++) {

			for (int x = 0; x < _to.getGridWidth(); x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write the value from _from (x-1, y) to
					// the _to (x, y) value

					// The below code does not translate the value, just copies
					// it to the same position

				
					// position after move
					int toX = x;
					int toY = y;
					// position before move
					int fromX = 0;
					int fromY = 0;
					
					if (_heading == Heading.PLUS_X) {
						fromX = x - 1;
						fromY = y;
					} else if (_heading == Heading.PLUS_Y) {
						// you could implement a movePlusY etc. or you could find a way do
						// do all moves in a single method. Hint: all changes are just + or
						// - 1 to an x or y value.
						fromX = x;
						fromY = y - 1;
					} else if (_heading == Heading.MINUS_X) {
						fromX = x + 1;
						fromY = y;
					} else if (_heading == Heading.MINUS_Y) {
						fromX = x;
						fromY = y + 1;
					}
										
						float fromProb;
					
						if(fromX > _from.getGridWidth() - 1 || fromY > _from.getGridHeight() - 1
								|| fromX < 0 || fromY < 0){
							fromProb = 0;
						}else{
							if(!_to.isValidTransition(fromX, fromY, toX, toY)){
								fromProb = 0;
							}else{
								fromProb = _from.getProbability(fromX, fromY);
							}
						}
							
						if(fromProb > highestProb){
							highestProb = fromProb;
							possiblePosition = new Point(toX, toY);
							if(highestProb > 0.09){
								System.out.println(toX + " " + toY);
							}
						}
						// set probability for position after move
						_to.setProbability(toX, toY, fromProb);
				}
			}
		}
		_to.normalise();
		return _to;
	}
	
}
