import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The Class InboxGUI. Displays messages currently in the inbox
 */
public class InboxGUI {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		IMAPMailProcessor proc = null;
		try {
			proc = new IMAPMailProcessor("ilchenko.daniil280@gmail.com");
			IMAPMailProcessorModel model = new IMAPMailProcessorModel(proc);
			Compositor compositor = new Compositor("ilchenko.daniil280@gmail.com");
			InboxComponent comp = new InboxComponent(model, compositor);
			JFrame frame = new JFrame("Inbox");
			frame.setSize(700, 800);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JScrollPane pane = new JScrollPane(comp);
		    frame.add(pane);
		    frame.setVisible(true);
		    
		    // Check mail once in "freq" MILLIseconds  
		    //do this so that if we receive new messages during program's operation they get into out system
            int freq = Integer.parseInt("3000");  
  
            for (;;) {  
                 System.out.println("Theread will sleep for 3 seconds");  
                 try {
                	 Thread.sleep(freq);
                 } catch (InterruptedException e) {
                	 e.printStackTrace();
                 } // sleep for freq milliseconds  
                 System.out.println("Thread awake after 3 seconds");  
                 if(Math.abs(model.checkForNewMessages()) > 0) {System.out.println(model.checkForNewMessages()); model.updateFolder();}  
                 System.out.println();  
           }
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			proc.closeStore();
			proc.closeFolder(true);
		}
	}
}
