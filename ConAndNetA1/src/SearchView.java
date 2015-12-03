import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Message;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Class SearchView.
 */
public class SearchView extends JPanel{
	
	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The keyword. */
	private JTextField keyword = new JTextField();
	
	/** The find. */
	private JButton find = new JButton("Run search");
	
	/** The kl. */
	private JLabel kl = new JLabel("Input search criteria");
	
	/** The word panel. */
	private JPanel wordPanel = new JPanel();
	
	/** The button panel. */
	private JPanel buttonPanel = new JPanel();
	
	/** The r s. */
	private Message[] rS; 
	
	/**
	 * Instantiates a new search view.
	 *
	 * @param model the model
	 */
	public SearchView(IMAPMailProcessorModel model){
		super();
		this.model = model;
		keyword.setPreferredSize(new Dimension(300, 30));
		keyword.setText("-----keyword here-----");
		setLayout(new BorderLayout());
		find.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(keyword.getText());
				rS = model.searchMailBox(keyword.getText());
				DisplayResultGUI display = new DisplayResultGUI(model, rS);
				display.run();
			}
			
		});
		wordPanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout());
		wordPanel.add(kl);
		wordPanel.add(keyword);
		buttonPanel.add(find);
		add(wordPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
