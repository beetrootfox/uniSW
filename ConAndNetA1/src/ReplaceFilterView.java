import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class ReplaceFilterView.
 */
public class ReplaceFilterView extends JPanel{
	
	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The basis. */
	private String basis = "";
	
	/** The flag name. */
	private String flagName = "";
	
	/** The replace. */
	private JButton replace = new JButton("Replace");
	
	/** The question. */
	private JLabel question = new JLabel("Such filter already exists, do you wish to replace this filter?");
	
	/** The kl. */
	private JLabel kl = new JLabel("Input search criteria and a name for a new flag");
	
	/** The word panel. */
	private JPanel wordPanel = new JPanel();
	
	/** The button panel. */
	private JPanel buttonPanel = new JPanel();
	
	/** The create. */
	private int create = 0; 
	
	/**
	 * Instantiates a new replace filter view.
	 *
	 * @param model the model
	 * @param basis the basis
	 * @param flagName the flag name
	 */
	public ReplaceFilterView(IMAPMailProcessorModel model, String basis, String flagName){
		super();
		this.model = model;
		this.basis = basis;
		this.flagName = flagName;


		setLayout(new BorderLayout());
		replace.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				model.removeUserFlag(flagName);
				model.newFilter(basis, flagName);
				model.updateFolder();
			}
			
		});

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(question);
		buttonPanel.add(replace);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
