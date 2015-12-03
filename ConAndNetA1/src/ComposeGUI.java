import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The Class ComposeGUI.
 */
public class ComposeGUI implements Runnable {

	/** The compositor */
	private Compositor compositor = null;
	
	/**
	 * Instantiates a new compose GUI
	 *
	 * @param compositor class that manages SMTP protocol for sending emails
	 */
	public ComposeGUI(Compositor compositor){
		this.compositor = compositor;

	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		JFrame frame1 = new JFrame("Compose");
		frame1.setSize(700, 800);
		ComposeComponent comp = new	ComposeComponent(compositor);
	    JScrollPane pane = new JScrollPane(comp);
	    frame1.add(pane);
	    frame1.setVisible(true);
	}

}