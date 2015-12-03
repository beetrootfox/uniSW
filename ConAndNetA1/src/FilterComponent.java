import javax.swing.JPanel;

/**
 * The Class FilterComponent.
 */
public class FilterComponent extends JPanel{
	
	/**
	 * Instantiates a new filter component.
	 *
	 * @param model the model
	 */
	public FilterComponent(IMAPMailProcessorModel model){
		super();
		FilterView view = new FilterView(model);
		add(view);
	}
}
