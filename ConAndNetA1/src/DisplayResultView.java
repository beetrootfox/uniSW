import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.mail.Message;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;


/**
 * The Class DisplayResultView.
 */
public class DisplayResultView extends JPanel implements Observer{
	
	/** The model. */
	private IMAPMailProcessorModel model = null;
	
	/** The result set of the search */
	private Message[] rS;
	
	/** The mails. */
	private JButton[] mails = null;
	
	/** The table. */
	private JTable table = null;
	
	/** The column names. */
	private String[] columnNames = {"Subject", "Flags"};;
	
	/** The data. */
	private Object[][] data = null;
	
	/** The th -- my email box that i used for testing currently holds ~6000 messages. TH is used to display only the limited amount of those messages*/
	private int th = 0;

	/**
	 * Instantiates a new display result view.
	 *
	 * @param model the model
	 * @param rS the result set of the search
	 */
	public DisplayResultView(IMAPMailProcessorModel model, Message[] rS){
		super();
		this.model = model;
		this.rS = rS;
		if(rS.length < 20) {th = rS.length;} else {th = 20;}
		JPanel buttons = new JPanel();
		JPanel tablePanel = new JPanel();
		setLayout(new BorderLayout());
		GridLayout b = new GridLayout(th, 1); 
		buttons.setLayout(b);
		
		mails = new JButton[th];
		data = new Object[th][2];
	
		for(int i = 0; i < th; i++){
			//mails[i] = new JButton(subjects[i]);
			//labels[i] = new JLabel("FLAG");
			//mails[i].setHorizontalAlignment(SwingConstants.LEFT);
			//add(mails[i]);
			//add(labels[i]);
			
			data[i][0] = model.getMessageSubject(rS[i]);
			data[i][1] = model.getMessageFlags(rS[i]).toString();
			mails[i] = new JButton("Read " + i );
			mails[i].setSize(90, 30);
			mails[i].addActionListener(new ReadListener(model, rS[i]));
			//mails[i].setBounds(mails[0].getBounds().x, mails[0].getBounds().y + mails[0].getHeight() + 5, 90, 30);
			buttons.add(mails[i]);
		}
		
		table = new JTable(data, columnNames);
		table.getColumnModel().getColumn(0).setMinWidth(600);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.setRowHeight(30);
		tablePanel.add(table);
		add(tablePanel, BorderLayout.WEST);
		add(buttons, BorderLayout.EAST);
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println(rS.length);
		for(int i = 0; i < th; i++){
			//mails[i].setText(subjects[i]);
			table.setValueAt(model.getMessageSubject(rS[i]), i, 0);
			table.setValueAt(model.getMessageFlags(rS[i]).toString(), i, 1);
		}
		System.out.println("REPAIN!WEASDJ!!");
		repaint();
		
	}
	

}