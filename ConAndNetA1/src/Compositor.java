import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Compositor. Used to send emails using SMTP protocol
 */
public class Compositor {

	/**
	 * The username
	 *
	 */
	private String username = "";
	
	/** The password. */
	private String password = "";
	
	/** The smtphost. */
	private String smtphost = "";
	
	/** The subject. */
	private String subject = "";
	
	/** The body. */
	private String body = "";
	
	/** The to -- recepient of an email */
	private String to = "";
	
	/** The paths. */
	private String[] paths;
	
	/** The session. */
	private Session session = null;

	/**
	 * Instantiates a new compositor.
	 *
	 * @param username the username
	 */
	public Compositor(String username){
		this.username = username;

		smtphost = "smtp.gmail.com";
		// Step 1: Set all Properties
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");
		
		 JPasswordField pwd = new JPasswordField(10); 
         int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION); 
         if(action < 0) {
             JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
             System.exit(0);
         }
         else
             this.password = new String(pwd.getPassword()); 

		// Set Property with username and password for authentication  
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);

		//Step 2: Establish a mail session (java.mail.Session)
		session = Session.getDefaultInstance(props);
	}
	
	/**
	 * Send message.
	 *
	 * @param path the paths to the attachments supplied as a single string separated by comas
	 * @param subject 
	 * @param body 
	 * @param to -- recepient
	 * @param cc -- copies
	 */
	public void SendMessage(String path, String subject, String body, String to, String cc){
		try {

			MimeMessage message = new MimeMessage(session);
			//set email address from which the message will be sent
			message.setFrom(new InternetAddress("ilchenko.daniil280@gmail.com"));
			//set recieving email address
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			//if there are any copies, include them
			if(cc.length() > 0){
				String[] ccs = cc.split(",");
				for(int i = 0; i < ccs.length; i++){
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccs[i]));
				}
			}
			//sets subject of a message
			message.setSubject(subject);
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(body);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			//if there are any attachments, split the strings by comas and retrieve the individual paths
			if(path.length() > 0){
				String[] paths = path.split(",");
				for(int i = 0; i < paths.length; i++){
					bodyPart = new MimeBodyPart();
					String filename = paths[i];
					DataSource source = new FileDataSource(filename);
					bodyPart.setDataHandler(new DataHandler(source));
					bodyPart.setFileName(filename);
					//add ready attachment to the multipart
					multipart.addBodyPart(bodyPart);
				}
			}
			
			message.setContent(multipart);
			
			message.saveChanges();
			
			// Step 4: Send the message by javax.mail.Transport .			
			Transport tr = session.getTransport("smtp");	// Get Transport object from session		
			tr.connect(smtphost, username, password); // We need to connect
			tr.sendMessage(message, message.getAllRecipients()); // Send message

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}	
	
}
