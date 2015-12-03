
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.mail.Multipart;

import com.sun.mail.imap.IMAPFolder;

public class IMAPClient {
	public static void main(String[] args) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		IMAPFolder folder = null;
		Store store = null;


		String username = "ilchenko.daniil280@gmail.com";
		String password = "";	        

		// Step 1.1: Set all Properties
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		
		JPasswordField pwd = new JPasswordField(10);  
		int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION);  
		if(action < 0) {
			JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected"); 
			System.exit(0); 
		}
		else 
			password = new String(pwd.getPassword());  
		
		// Set Property with username and password for authentication  
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);

		//Step 1.2: Establish a mail session (java.mail.Session)
		Session session = Session.getDefaultInstance(props);

		try 
		{
			// Step 2: Get the Store object from the mail session
			// A store needs to connect to the IMAP server  
			store = session.getStore("imaps");
			store.connect("imap.googlemail.com",username, password);
		// Step 3: Choose a folder, in this case, we chose your inbox
			folder = (IMAPFolder) store.getFolder("inbox"); 

			// Step 4: Open the folder
			if(!folder.isOpen())
				folder.open(Folder.READ_WRITE);
			
			// Step 5: Get messages from the folder
			// Get total number of message
			System.out.println("No of Messages : " + folder.getMessageCount());
			// Get total number of unread message
			System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());

			Message messages[] = folder.getMessages();

			// Get all messages
			for(int i = 0; i < 20; i++) {
				Flags mes_flag = messages[i].getFlags();
				// Get subject of each message 
				System.out.println("The " + i + "th message is: " + messages[i].getSubject());
				System.out.println(messages[i].getContentType());
				if(messages[i].getContentType().contains("TEXT/PLAIN")) {
					System.out.println(messages[i].getContent());
				}
				else 
				{
					
					// How to get parts from multiple body parts of MIME message
					if(messages[i].getContent() instanceof Multipart){
						Multipart multipart = (Multipart) messages[i].getContent();
						

						for (int x = 0; x < multipart.getCount(); x++) {
							System.out.println("-----------" + x + "----------------");
							BodyPart bodyPart = multipart.getBodyPart(x);
							// If the part is a plan text message, then print it out.
							
							if(bodyPart.getContentType().contains("TEXT/PLAIN")) 
							{
								System.out.println(bodyPart.getContentType());
								System.out.println(bodyPart.getContent().toString());
							}else{
								System.out.println(bodyPart.getDescription() + " " + bodyPart.getContentType());
							}

						}
					}
				}


				System.out.println("Has this message been read?  " + mes_flag.contains(Flag.SEEN));
			}	




		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (folder != null && folder.isOpen()) { folder.close(true); }
			if (store != null) { store.close(); }
		}

	}
}
