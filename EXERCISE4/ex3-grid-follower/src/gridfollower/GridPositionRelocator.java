package gridfollower;

import java.util.ArrayList;

import rp.robotics.mapping.Heading;

// TODO: Auto-generated Javadoc
/**
 * The Class GridPositionRelocator.
 */
public class GridPositionRelocator {
	
	/** The relocation plan. */
	private ArrayList<Move> relocationPlan;
	
	/** The after heading. */
	private Heading afterHeading;
	
	/**
	 * Instantiates a new grid position relocator.
	 *
	 * @param relocationPlan the relocation plan
	 * @param afterHeading the after heading
	 */
	public GridPositionRelocator(ArrayList<Move> relocationPlan, Heading afterHeading){
		this.relocationPlan = relocationPlan;
		this.afterHeading = afterHeading;
	}
	
	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public ArrayList<Move> getPlan(){
		return relocationPlan;
	}
	
	/**
	 * Gets the after heading.
	 *
	 * @return the after heading
	 */
	public Heading getAfterHeading(){
		return afterHeading;
	}
}
