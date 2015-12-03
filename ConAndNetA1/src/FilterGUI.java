import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The Class FilterGUI.
 */
public class FilterGUI implements Runnable {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/**
	 * Instantiates a new filter gui.
	 *
	 * @param model the model
	 */
	public FilterGUI(IMAPMailProcessorModel model){
		this.model = model;

	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		JFrame frame1 = new JFrame("Filter");
		frame1.setSize(700, 800);
		FilterComponent comp = new	FilterComponent(model);
	    JScrollPane pane = new JScrollPane(comp);
	    frame1.add(pane);
	    frame1.setVisible(true);
	}

}