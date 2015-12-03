import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.mail.MessagingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;


/**
 * The Class InboxView.
 */
public class InboxView extends JPanel implements Observer{
	
	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The subjects. */
	private String[] subjects = null;
	
	/** The mails. */
	private JButton[] mails = null;
	
	/** The table. */
	private JTable table = null;
	
	/** The column names. */
	private String[] columnNames = {"Subject", "Flags"};;
	
	/** The data. */
	private Object[][] data = null;
	
	/** The search. */
	private JButton search = new JButton("Search inbox");
	
	/** The add filter. */
	private JButton addFilter = new JButton("Add custom flag");
	
	/** The compose. */
	private JButton compose = new JButton("Compose");
	
	/** The sf. */
	private JPanel sf = new JPanel();
	
	/** The compositor. */
	private Compositor compositor = null;

	/**
	 * Instantiates a new inbox view.
	 *
	 * @param model the model
	 * @param compositor the compositor
	 */
	public InboxView(IMAPMailProcessorModel model, Compositor compositor){
		super();
		this.model = model;
		this.compositor = compositor;
		JPanel buttons = new JPanel();
		JPanel tablePanel = new JPanel();
		subjects = model.getMailSubjects();
		setLayout(new BorderLayout());
		GridLayout b = new GridLayout(subjects.length, 1); 
		buttons.setLayout(b);
		
		mails = new JButton[subjects.length];
		data = new Object[subjects.length][2];
		for(int i = 0; i < subjects.length; i++){
			//mails[i] = new JButton(subjects[i]);
			//labels[i] = new JLabel("FLAG");
			//mails[i].setHorizontalAlignment(SwingConstants.LEFT);
			//add(mails[i]);
			//add(labels[i]);
			
			data[i][0] = subjects[i];
			data[i][1] = model.getMessageFlags(i).toString();
			mails[i] = new JButton("Read " + i );
			mails[i].setSize(90, 30);
			mails[i].addActionListener(new ReadListener(model, i));
			//mails[i].setBounds(mails[0].getBounds().x, mails[0].getBounds().y + mails[0].getHeight() + 5, 90, 30);
			buttons.add(mails[i]);
		}
		
		table = new JTable(data, columnNames);
		table.getColumnModel().getColumn(0).setMinWidth(600);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.setRowHeight(30);
		search.addActionListener(new SearchListener(model));
		addFilter.addActionListener(new FilterListener(model));
		compose.addActionListener(new ComposeListener(compositor));
		tablePanel.add(table);
		sf.add(search);
		sf.add(addFilter);
		sf.add(compose);
		sf.setLayout(new GridLayout());
		add(tablePanel, BorderLayout.WEST);
		add(buttons, BorderLayout.EAST);
		add(sf, BorderLayout.PAGE_END);
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		subjects = model.getMailSubjects();
		System.out.println(subjects.length);
		for(int i = 0; i < subjects.length; i++){
			//mails[i].setText(subjects[i]);
			table.setValueAt(subjects[i], i, 0);
			table.setValueAt(model.getMessageFlags(i).toString(), i, 1);
		}
		System.out.println("REPAIN!WEASDJ!!");
		repaint();
		
	}
	

}
