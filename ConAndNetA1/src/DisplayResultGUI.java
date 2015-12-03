import javax.mail.Message;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


/**
 * The Class DisplayResultGUI.
 */
public class DisplayResultGUI implements Runnable {

	/** The model */
	private IMAPMailProcessorModel model = null;
	
	/** The result set of the search */
	private Message[] rS;
	
	/**
	 * Instantiates a new GUI to display the search result.
	 *
	 * @param model the model
	 * @param rS the result set of the search
	 */
	public DisplayResultGUI(IMAPMailProcessorModel model, Message[] rS){
		this.model = model;
		this.rS = rS;

	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("PRINT SEARCH RESULTS MAAAAAAN YEAH!!!");
		JFrame frame1 = new JFrame("Search Results");
		frame1.setSize(700, 800);
		DisplayResultComponent comp = new DisplayResultComponent(model, rS);
		JScrollPane pane = new JScrollPane(comp);
		frame1.add(pane);
	    frame1.setVisible(true);
	}

}