import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadGUI.
 */
public class DownloadGUI extends JFrame{
	
	/** The links. */
	private ArrayList<String> links;
	
	/** The folder path. */
	private String folderPath;
	
	/** The process. */
	private DownloadManager process;
	
	/** The log. */
	private JTextArea log;
	
	/**
	 * The Class DownloadManager.
	 */
	class DownloadManager extends SwingWorker<Integer, DownloadStatus> implements Observer {
		
		/**
		 * Instantiates a new download manager.
		 */
		public DownloadManager(/*ArrayList<String> links, String folderPath*/){
			//links = links;
			//folderPath = folderPath;
		}
		
		/*public void downloadAll(){
			//Step 1: Create a fixed Thread pool of 2 thread 
			ExecutorService pool = Executors.newFixedThreadPool(2);
			FileDownloader runnableobj = null;
			for (int counter=0; counter<links.size(); counter++) {
				//Create objects of Runnable		
				runnableobj = new FileDownloader(links.get(counter), folderPath);
				runnableobj.addObserver(this);
				// Step 2: to execute Runnable/Callable objects, which creates a new thread and launches it immediately 
				pool.submit(runnableobj);
				System.out.println(runnableobj.getStatus().getStatus());
			}
			// Step 3: shutdown of the ExecutorService:
			pool.shutdown();
		}*/
		
		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		@Override
		public void update(Observable arg0, Object arg1) {
			System.out.println(((DownloadStatus) arg1).getStatus());
			publish((DownloadStatus) arg1);
		}
		
		/*public static void main(String[] args){
			try {
				PageParser pp = new PageParser("http://melvynmathews.co.uk/test/");
				DownloadManager dm = new DownloadManager(pp.getFileLinks(new String[] {Extensions.JPEG.toString(), Extensions.PNG.toString()}), "/home/students/dxi459/");
				dm.downloadAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		/* (non-Javadoc)
		 * @see javax.swing.SwingWorker#doInBackground()
		 */
		@Override
		protected Integer doInBackground() throws Exception {
			//Step 1: Create a fixed Thread pool of 2 thread 
			ExecutorService pool = Executors.newFixedThreadPool(2);
			FileDownloader runnableobj = null;
			for (int counter=0; counter<links.size(); counter++) {
				//Create objects of Runnable		
				runnableobj = new FileDownloader(links.get(counter), folderPath);
				runnableobj.addObserver(this);
				// Step 2: to execute Runnable/Callable objects, which creates a new thread and launches it immediately 
				pool.submit(runnableobj);
			}
			// Step 3: shutdown of the ExecutorService:
			pool.shutdown();
			return links.size();
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.SwingWorker#process(java.util.List)
		 */
		protected void process(List<DownloadStatus> dss){
			DownloadStatus st = dss.get(dss.size() - 1);
			log.append(st.getThreadName() + " is (was) working on " + st.getFileName() + " Status: " + st.getStatus() + "\n");
			
		}
		
	}
	
	/**
	 * Instantiates a new download gui.
	 */
	public DownloadGUI(){
		
		Container cp = this.getContentPane();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JPanel fields = new	JPanel(); 
		JPanel buttons = new JPanel();
		fields.setLayout(new BoxLayout(fields, 1));
		buttons.setLayout(new GridLayout(2, 1));
		fields.setPreferredSize(new Dimension(340, 60));
		
		
	
		
		JTextField addressField = new JTextField("http://melvynmathews.co.uk/test/");
		JTextField pathField = new JTextField("/home/students/dxi459/");
		JTextField extField = new JTextField("list of file extensions");
		JButton start = new JButton("Start Downloading");
		JButton cancel = new JButton("Cancell all downloads");
		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			//	JOptionPane.showMessageDialog(null, addressField.getText() +
			//			pathField.getText() + extField.getText());
				
				try {
					PageParser pp = new PageParser(addressField.getText());
					links = pp.getFileLinks(extField.getText().split(","));
					folderPath = pathField.getText();
					process = new DownloadManager();
					process.execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				process.cancel(true);
			}
			
		});
		
		fields.add(addressField);
		fields.add(pathField);
		fields.add(extField);
		buttons.add(start);
		buttons.add(cancel);

	
		log = new JTextArea();
		
		cp.add(fields);
		cp.add(buttons);
		cp.add(log);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Resonpsive email subject JList");
		setSize(900, 640);
		setVisible(true);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				DownloadGUI gg = new DownloadGUI();
			}
		});
	}
	
}
