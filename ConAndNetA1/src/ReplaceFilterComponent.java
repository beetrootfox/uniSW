import javax.swing.JPanel;

/**
 * The Class ReplaceFilterComponent.
 */
public class ReplaceFilterComponent extends JPanel{
	
	/**
	 * Instantiates a new replace filter component.
	 *
	 * @param model the model
	 * @param basis the basis
	 * @param flagName the flag name
	 */
	public ReplaceFilterComponent(IMAPMailProcessorModel model, String basis, String flagName){
		super();
		ReplaceFilterView view = new ReplaceFilterView(model, basis, flagName);
		//model.addObserver(view);
		add(view);
	}
}
