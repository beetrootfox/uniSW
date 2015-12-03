import javax.swing.JFrame;
import javax.swing.JScrollPane;


/**
 * The Class SearchGUI.
 */
public class SearchGUI implements Runnable {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/**
	 * Instantiates a new search gui.
	 *
	 * @param model the model
	 */
	public SearchGUI(IMAPMailProcessorModel model){
		this.model = model;

	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		JFrame frame1 = new JFrame("Search");
		frame1.setSize(700, 800);
		SearchComponent comp = new SearchComponent(model);
	    JScrollPane pane = new JScrollPane(comp);
	    frame1.add(pane);
	    frame1.setVisible(true);
	}

}