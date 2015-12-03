import java.util.ArrayList;
import java.util.Observable;

import javax.mail.Message;


/**
 * The Class IMAPMailProcessorModel. Wrapper class for IMAPMailProcessor to ensure data encapsulation and make using processor clearer
 */
public class IMAPMailProcessorModel extends Observable{
	
	/** The imaproc. */
	private IMAPMailProcessor imaproc = null;
	
	/**
	 * Instantiates a new IMAP mail processor model.
	 *
	 * @param imaproc the imaproc
	 */
	public IMAPMailProcessorModel(IMAPMailProcessor imaproc){
		super();
		this.imaproc = imaproc;
	}
	
	/**
	 * Update folder. This method is called after any changes were applied to the message set
	 */
	public void updateFolder(){
		
		imaproc.updateFolder();
		setChanged();
		notifyObservers();
		System.out.println("UPDATED");

	}
	
	/**
	 * Gets the mail subjects.
	 *
	 * @return the mail subjects
	 */
	public String[] getMailSubjects(){
		return imaproc.getMailSubjects();
	}
	
	/**
	 * Gets the message flags.
	 *
	 * @param n the n
	 * @return the message flags
	 */
	public ArrayList<String> getMessageFlags(int n){
		return imaproc.getMessageFlags(n);
	}
	
	/**
	 * Gets the message flags.
	 *
	 * @param m the m
	 * @return the message flags
	 */
	public ArrayList<String> getMessageFlags(Message m){
		return imaproc.getMessageFlags(m);
	}
	
	/**
	 * Gets the message content.
	 *
	 * @param n the n
	 * @return the message content
	 */
	public String getMessageContent(int n){
		return imaproc.getMessageContent(n);
	}
	
	/**
	 * Gets the message content.
	 *
	 * @param m the m
	 * @return the message content
	 */
	public String getMessageContent(Message m){
		return imaproc.getMessageContent(m);
	}
	
	/**
	 * Gets the message subject.
	 *
	 * @param m the m
	 * @return the message subject
	 */
	public String getMessageSubject(Message m){
		return imaproc.getMessageSubject(m);
	}
	
	/**
	 * Search mail box.
	 *
	 * @param keyword the keyword
	 * @return the message[]
	 */
	public Message[] searchMailBox(String keyword){
		return imaproc.searchMailBox(keyword);
	}
	
	/**
	 * Close store.
	 */
	public void closeStore(){
		imaproc.closeStore();
	}
	
	/**
	 * Close folder.
	 *
	 * @param bool the bool
	 */
	public void closeFolder(boolean bool){
		imaproc.closeFolder(bool);
	}
	
	/**
	 * New filter.
	 *
	 * @param basis the basis
	 * @param flagName the flag name
	 * @return the int
	 */
	public int newFilter(String basis, String flagName){
		return imaproc.newFilter(basis, flagName);
	}
	
	/**
	 * Renew flags.
	 */
	public void renewFlags(){
		imaproc.renewFlags();
	}
	
	/**
	 * Removes the user flag.
	 *
	 * @param flagName the flag name
	 */
	public void removeUserFlag(String flagName){
		imaproc.removeUserFlag(flagName);
	}
	
	/**
	 * Check for new messages.
	 *
	 * @return the int
	 */
	public int checkForNewMessages(){
		return imaproc.checkForNewMessages();
	}
	
	/**
	 * Gets the message subject.
	 *
	 * @param n the n
	 * @return the message subject
	 */
	public String getMessageSubject(int n){
		return imaproc.getMessageSubject(n);
	}
	

}
