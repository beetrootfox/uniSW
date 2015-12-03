import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The listener interface for receiving filter events.
 * The class that is interested in processing a filter
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addFilterListener<code> method. When
 * the filter event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FilterEvent
 */
public class FilterListener implements ActionListener {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/**
	 * Instantiates a new filter listener.
	 *
	 * @param model the model
	 */
	public FilterListener(IMAPMailProcessorModel model){
		this.model = model;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("FILTER!");		
		FilterGUI filter = new FilterGUI(model);
		filter.run();
	}

}
