import javax.swing.JPanel;

/**
 * The Class ReadComponent.
 */
public class ReadComponent extends JPanel{
	
	/**
	 * Instantiates a new read component.
	 *
	 * @param model the model
	 * @param content the content
	 * @param subject the subject
	 */
	public ReadComponent(IMAPMailProcessorModel model, String content, String subject){
		super();
		ReadView view = new ReadView(model, content, subject);
		add(view);
	}
}
