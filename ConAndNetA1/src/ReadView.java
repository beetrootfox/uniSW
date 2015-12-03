import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * The Class ReadView.
 */
public class ReadView extends JPanel{

	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The content. */
	private String content = "";
	
	/** The subject. */
	private String subject = "";
	
	/** The body. */
	private JTextArea body = null;
	
	/** The subj. */
	private JTextField subj = null;
	
	/** The ls. */
	private JLabel ls = null;
	
	/** The lb. */
	private JLabel lb = null;
	
	/** The subject field description panel. */
	private JPanel sLogoPanel = new JPanel();
	
	/** The subject field panel. */
	private JPanel sPanel = new JPanel();
	
	/** The body field description panel. */
	private JPanel bLogoPanel = new JPanel();
	
	/** The body field panel. */
	private JPanel bPanel = new JPanel();
	
	/**
	 * Instantiates a new read view.
	 *
	 * @param model the model
	 * @param content the content
	 * @param subject the subject
	 */
	public ReadView(IMAPMailProcessorModel model, String content, String subject){
		super();
		this.model = model;
		this.content = content;
		this.subject = subject;
		BoxLayout b = new BoxLayout(this, 1);
		setLayout(b);
		subj = new JTextField();
		body = new JTextArea();
		body.setText(content);
		subj.setText(subject);
		ls = new JLabel("Subject:");
		lb = new JLabel("Body:");
		sLogoPanel.add(ls);
		sPanel.add(subj);
		bLogoPanel.add(lb);
		bPanel.add(body);
		add(sLogoPanel);
		add(sPanel);
		add(bLogoPanel);
		add(bPanel);
		model.updateFolder();
	}
}
