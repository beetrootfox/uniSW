import javax.swing.JPanel;

/**
 * The Class ComposeComponent.
 */
public class ComposeComponent extends JPanel{
	
	/**
	 * Instantiates a new compose component.
	 *
	 * @param compositor class that manages SMTP protocol for sending emails
	 */
	public ComposeComponent(Compositor compositor){
		super();
		ComposeView view = new ComposeView(compositor);
		add(view);
	}
}
