import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Class FilterView.
 */
public class FilterView extends JPanel{
	
	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The keyword. */
	private JTextField keyword = new JTextField();
	
	/** The flag name. */
	private JTextField flagName = new JTextField();
	
	/** The find. */
	private JButton find = new JButton("Add filter");
	
	/** The kl. */
	private JLabel kl = new JLabel("Input search criteria and a name for a new flag");
	
	/** The word panel. */
	private JPanel wordPanel = new JPanel();
	
	/** The button panel. */
	private JPanel buttonPanel = new JPanel();
	
	/** The create. */
	private int create = 0; 
	
	/**
	 * Instantiates a new filter view.
	 *
	 * @param model the model
	 */
	public FilterView(IMAPMailProcessorModel model){
		super();
		this.model = model;
		keyword.setPreferredSize(new Dimension(300, 30));
		keyword.setText("-----keyword here-----");
		flagName.setPreferredSize(new Dimension(300, 30));
		flagName.setText("------flag name here------");
		setLayout(new BorderLayout());
		find.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(keyword.getText());
				create = model.newFilter(keyword.getText(), flagName.getText());
				if(create == 1){
					model.updateFolder();
				}else{
					//if the flag suggested by the user has already been used, give user a choice to replace that flag
					ReplaceFilterGUI replace = new ReplaceFilterGUI(model, keyword.getText(), flagName.getText()); 
					replace.run();
				}
			}
			
		});
		wordPanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout());
		wordPanel.add(kl);
		wordPanel.add(keyword);
		wordPanel.add(flagName);
		buttonPanel.add(find);
		add(wordPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
