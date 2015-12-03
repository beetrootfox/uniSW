import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The listener interface for receiving search events.
 * The class that is interested in processing a search
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSearchListener<code> method. When
 * the search event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SearchEvent
 */
public class SearchListener implements ActionListener {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/**
	 * Instantiates a new search listener.
	 *
	 * @param model the model
	 */
	public SearchListener(IMAPMailProcessorModel model){
		this.model = model;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("SEARCH!");		
		SearchGUI search = new SearchGUI(model);
		search.run();
	}

}
