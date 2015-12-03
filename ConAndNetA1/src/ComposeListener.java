import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The listener interface for receiving compose events.
 * The class that is interested in processing a compose
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addComposeListener<code> method. When
 * the compose event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ComposeEvent
 */
public class ComposeListener implements ActionListener {

	/** The compositor */
	private Compositor compositor = null;
	
	/**
	 * Instantiates a new compose listener.
	 *
	 * @param compositor class that manages SMTP protocol for sending emails
	 */
	public ComposeListener(Compositor compositor){
		this.compositor = compositor;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("COMPOSE!!");		
		ComposeGUI compose = new ComposeGUI(compositor);
		compose.run();
	}

}
