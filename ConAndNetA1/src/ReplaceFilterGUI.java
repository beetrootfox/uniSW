import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The Class ReplaceFilterGUI.
 */
public class ReplaceFilterGUI implements Runnable {

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The basis. */
	private String basis = "";
	
	/** The flag name. */
	private String flagName = "";
	
	/**
	 * Instantiates a new replace filter gui.
	 *
	 * @param model the model
	 * @param basis the basis
	 * @param flagName the flag name
	 */
	public ReplaceFilterGUI(IMAPMailProcessorModel model, String basis, String flagName){
		this.model = model;
		this.basis = basis;
		this.flagName = flagName;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		JFrame frame1 = new JFrame("Replace Filter?");
		frame1.setSize(700, 800);
		ReplaceFilterComponent comp = new ReplaceFilterComponent(model, basis, flagName);
	    JScrollPane pane = new JScrollPane(comp);
	    frame1.add(pane);
	    frame1.setVisible(true);
	}

}