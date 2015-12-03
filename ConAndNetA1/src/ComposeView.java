import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Message;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class ComposeView.
 */
public class ComposeView extends JPanel{
	
	/** compositor class that manages SMTP protocol for sending emails (instance) */
	private Compositor compositor = null;
	
	/** The subject of an email */
	private JTextField subject = new JTextField();
	
	/** The body of an email */
	private JTextArea body = new JTextArea();
	
	/** The to -- recepient of an email */
	private JTextField to = new JTextField();
	
	/** The cc -- recepients of the copies of an email */
	private JTextField cc = new JTextField();
	
	/** The path -- paths for attachments separated by comas */
	private JTextField path = new JTextField();
	
	/** The send -- button to initialize sending a message */
	private JButton send = new JButton("Send message");
	
	/** The word panel. */
	private JPanel wordPanel = new JPanel();
	
	/** The button panel. */
	private JPanel buttonPanel = new JPanel();
	
	
	/**
	 * Instantiates a new composeview.
	 *
	 * @param compositor 
	 */
	public ComposeView(Compositor compositor){
		super();
		this.compositor = compositor;
		to.setPreferredSize(new Dimension(300, 30));
		to.setText("--------recepient here---------");
		cc.setPreferredSize(new Dimension(500, 30));
		cc.setText("---------CC here, separate with coma----------");
		path.setPreferredSize(new Dimension(600, 30));
		path.setText("----------paths here, separate with coma-----------");
		subject.setPreferredSize(new Dimension(300, 30));
		subject.setText("-----subject here-----");
		body.setPreferredSize(new Dimension(500, 300));
		body.setText("------body here here------");
		
		setLayout(new BorderLayout());
		
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				compositor.SendMessage(path.getText(), subject.getText(), body.getText(), to.getText(), cc.getText());
			}
			
		});
		wordPanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout());
		wordPanel.add(to);
		wordPanel.add(cc);
		wordPanel.add(subject);
		wordPanel.add(body);
		buttonPanel.add(path);
		buttonPanel.add(send);
		add(wordPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
