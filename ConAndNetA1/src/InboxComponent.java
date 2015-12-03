import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The Class InboxComponent.
 */
public class InboxComponent extends JPanel{
	
	/**
	 * Instantiates a new inbox component.
	 *
	 * @param model the model
	 * @param compositor the compositor
	 */
	public InboxComponent(IMAPMailProcessorModel model, Compositor compositor){
		super();
		InboxView view = new InboxView(model, compositor);
		model.addObserver(view);
		add(view);
	}
}
