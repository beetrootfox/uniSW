import javax.swing.JFrame;
import javax.swing.JScrollPane;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadGUI.
 */
public class ReadGUI implements Runnable {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The content. */
	private String content = "";
	
	/** The subject. */
	private String subject = "";
	
	/**
	 * Instantiates a new read gui.
	 *
	 * @param model the model
	 * @param content the content
	 * @param subject the subject
	 */
	public ReadGUI(IMAPMailProcessorModel model, String content, String subject){
		this.model = model;
		this.content = content;
		this.subject = subject;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		JFrame frame1 = new JFrame("Read");
		frame1.setSize(700, 800);
		ReadComponent comp = new ReadComponent(model, content, subject);
	    JScrollPane pane = new JScrollPane(comp);
	    frame1.add(pane);
	    frame1.setVisible(true);
	    
	}

}
