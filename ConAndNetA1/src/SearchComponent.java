import javax.swing.JPanel;

/**
 * The Class SearchComponent.
 */
public class SearchComponent extends JPanel{
	
	/**
	 * Instantiates a new search component.
	 *
	 * @param model the model
	 */
	public SearchComponent(IMAPMailProcessorModel model){
		super();
		SearchView view = new SearchView(model);
		add(view);
	}
}
