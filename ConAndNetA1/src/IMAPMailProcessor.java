import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.BodyTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.mail.Multipart;

import com.sun.mail.imap.IMAPFolder;

/**
 * The Class IMAPMailProcessor.
 */
public class IMAPMailProcessor {
   
    /** The folder imap folder */
    private IMAPFolder folder = null;
    
    /** The store object */
    private Store store = null;
    
    /** The username. */
    private String username = "";
    
    /** The password. */
    private String password = "";
    
    /** The session. */
    private Session session = null;
    
    /** The array of messages retrieved from the folder */
    private Message messages[];
    
    /** list of filters set by the user */
    private ArrayList<Filter> filters = new ArrayList<Filter>();
    
    /** list of flags set by the user */
    private HashSet<String> userFlags = new HashSet<String>();
    
    /** The message count -- use to keep track of updates in the inbox */
    private int messageCount = 0;
    
   
    /**
     * Instantiates a new IMAP mail processor.
     *
     * @param username 
     * @throws MessagingException the messaging exception
     */
    public IMAPMailProcessor(String username) throws MessagingException{
    			long st1 = System.currentTimeMillis();
                this.username = username;           

                // Step 1.1: Set all Properties
                Properties props = System.getProperties();
                props.setProperty("mail.store.protocol", "imaps");
               
                // get the password
                JPasswordField pwd = new JPasswordField(10); 
                int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION); 
                if(action < 0) {
                    JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
                    System.exit(0);
                }
                else
                    this.password = new String(pwd.getPassword()); 
               
                // Set Property with username and password for authentication 
                props.setProperty("mail.user", this.username);
                props.setProperty("mail.password", this.password);

                //Step 1.2: Establish a mail session (java.mail.Session)
                this.session = Session.getDefaultInstance(props);
                    // Step 2: Get the Store object from the mail session
                    // A store needs to connect to the IMAP server 
                    store = session.getStore("imaps");
                    store.connect("imap.googlemail.com", this.username, password);
                    // Step 3: Choose a folder, in this case, we chose inbox
                    folder = (IMAPFolder) store.getFolder("inbox");

                    // Step 4: Open the folder
                    if(!folder.isOpen())
                        folder.open(Folder.READ_WRITE);
               
                    // Step 5: Get messages from the folder
                    // Get total number of message
                    System.out.println("No of Messages : " + folder.getMessageCount());
                    // Get total number of unread message
                    System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
                    
                    //get messages from the folder
                    this.messages = folder.getMessages();
                    this.messageCount = messages.length;
                    reverseMessages();    //reverse messages so that we can work with the most recent ones
                    System.out.println("Constructor took " + (System.currentTimeMillis() - st1));

    }
    
    //GET METHODS
    /**
     * Gets the folder.
     *
     * @return the folder
     */
    public Folder getFolder(){
    	return folder;
    }
   
    /**
     * Gets the mail subjects.
     *
     * @return mail subjects
     */
    public String[] getMailSubjects(){
    	//Set subjects.length = messages.length (i have a sizable inbox with ~ 6000 emails so it takes a while to work through all of them hence the treshold)
    	//long st2 = System.currentTimeMillis();
        String subjects[] = new String[20]; 
    	try{
            for(int i = 0; i < subjects.length; i++){
                String subject;
                //get subject for every message
				subject = messages[i].getSubject();
				
				//get already present custom flags for every message (to handle futher addition of flags more effectively)
                String[] flags = messages[i].getFlags().getUserFlags();
                for(int j = 0; j < flags.length; j++){
                	userFlags.add(flags[j]);
                }
                if(subject != null ){
                    subjects[i] = subject;
                }else{
                    subjects[i] = "(no subject)";
                }
            }
            //System.out.println("getMailSubjects finished at" + (System.currentTimeMillis() - st2));

    	}catch(MessagingException e){
        	e.printStackTrace();
        }
        return subjects;
    }
    
    /**
     * Gets user flags.
     *
     * @return user flags
     */
    public HashSet<String> getUserFlags(){
    	return userFlags;
    }
    
    /**
     * Gets the message content.
     *
     * @param n the id of the message in the folder
     * @return the message content
     */
    public String getMessageContent(int n){
    	String content = "";
    	try{
        if(messages[n].getContentType().contains("TEXT/PLAIN")){
            content = content + messages[n].getContent().toString();
        }else{
        	// get parts from multiple body parts of MIME message
			if(messages[n].getContent() instanceof Multipart){
				Multipart multipart = (Multipart) messages[n].getContent();
				for (int x = 0; x < multipart.getCount(); x++) {
					BodyPart bodyPart = multipart.getBodyPart(x);
					// If the part is a plan text message, then print it out.
					
					if(bodyPart.getContentType().contains("TEXT/PLAIN")) 
					{
						content = content + bodyPart.getContent().toString();
					}else{
						bodyPart.getContent();
					}

				}
			}        	
        }
        if(content.equals("")){
        	return "cannot read this message: no content of TEXT/PLAIN type";
        }
    	}catch(MessagingException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
        return content;
    }
    
    //in general get methods that take instance of Message as an argument are used to work with search results
    /**
     * Gets the message content. 
     *
     * @param m the m
     * @return the message content
     */
    public String getMessageContent(Message m){
    	String content = "";
    	try{
        if(m.getContentType().contains("TEXT/PLAIN")){
            content = content + m.getContent().toString();
        }else{
        	// get parts from multiple body parts of MIME message
			if(m.getContent() instanceof Multipart){
				Multipart multipart = (Multipart)m.getContent();
				for (int x = 0; x < multipart.getCount(); x++) {
					BodyPart bodyPart = multipart.getBodyPart(x);
					// If the part is a plan text message, then print it out.
					
					if(bodyPart.getContentType().contains("TEXT/PLAIN")) 
					{
						content = content + bodyPart.getContent().toString();
					}else{
						bodyPart.getContent();
					}

				}
			}        	
        }
        if(content.equals("")){
        	return "cannot read this message: no content of TEXT/PLAIN type";
        }
    	}catch(MessagingException e){
    		e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
        return content;
    }
    
    /**
     * Gets the message flags.
     *
     * @param n the n
     * @return the message flags
     */
    public ArrayList<String> getMessageFlags(int n){
        ArrayList<String> flags = new ArrayList<String>();
        Flags f;
		try {
			f = messages[n].getFlags();
		//retrieve all system flags
        if(f.contains(Flag.SEEN)) {flags.add("SEEN");}
        if(f.contains(Flag.ANSWERED)) {flags.add("ANSWERED");}
        if(f.contains(Flag.DELETED)) {flags.add("DELETED");}
        if(f.contains(Flag.DRAFT)) {flags.add("DRAFT");}
        if(f.contains(Flag.FLAGGED)) {flags.add("FLAGGED");}
        if(f.contains(Flag.RECENT)) {flags.add("RECENT");}
        if(f.contains(Flag.USER)) {flags.add("USER");}
        
        //retrieve all user flags
        String[] users = messages[n].getFlags().getUserFlags();
        for(int i = 0; i < users.length; i++){

            	flags.add(users[i]);
        	
        }
		} catch (MessagingException e) {
			e.printStackTrace();
		}
       
        return flags;
    }
    
    /**
     * Gets the message flags.
     *
     * @param m the m
     * @return the message flags
     */
    public ArrayList<String> getMessageFlags(Message m){
        ArrayList<String> flags = new ArrayList<String>();
        Flags f;
		try {
			f = m.getFlags();
		//retrieve all system flags
        if(f.contains(Flag.SEEN)) {flags.add("SEEN");}
        if(f.contains(Flag.ANSWERED)) {flags.add("ANSWERED");}
        if(f.contains(Flag.DELETED)) {flags.add("DELETED");}
        if(f.contains(Flag.DRAFT)) {flags.add("DRAFT");}
        if(f.contains(Flag.FLAGGED)) {flags.add("FLAGGED");}
        if(f.contains(Flag.RECENT)) {flags.add("RECENT");}
        if(f.contains(Flag.USER)) {flags.add("USER");}
        //retrieve all user flags
        String[] users = m.getFlags().getUserFlags();
        for(int i = 0; i < users.length; i++){

            	flags.add(users[i]);
        	
        }
		} catch (MessagingException e) {
			e.printStackTrace();
		}
       
        return flags;
    }
    
    /**
     * Gets subject of a single message.
     *
     * @param m Message
     * @return the message subject
     */
    public String getMessageSubject(Message m){
    	String sbj = "";
    	try {
			sbj = m.getSubject();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	return sbj;
    }
    
    /**
     * Gets the subject of a single message
     *
     * @param n the n
     * @return the message subject
     */
    public String getMessageSubject(int n){
    	String sbj = "";
    	try {
			sbj = messages[n].getSubject();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	return sbj;
    }
    
    // OTHER METHODS
   
    /**
     * Search mail box (body and all headers).
     *
     * @param s the keyword to search for
     * @return the message[]
     */
    public Message[] searchMailBox(String s){
    	//long st3 = System.currentTimeMillis();
    	ArrayList<SearchTerm> terms = new ArrayList<SearchTerm>(); 
    	terms.add(new BodyTerm(s));
    	if(messages.length > 0){
    		try{
    			Enumeration headers = messages[0].getAllHeaders();
                while(headers.hasMoreElements()){
                	Header h = (Header) headers.nextElement();
					terms.add(new HeaderTerm(h.getName(), s));
                }
              //  System.out.println("Search finished at" + (System.currentTimeMillis() - st3));      
                //serach body of the message and all of its headers for a particular string S
                return folder.search(new OrTerm(terms.toArray(new SearchTerm[0])));
    		}catch(MessagingException e){
    			e.printStackTrace();
    		}
    	}
		return new Message[0];
    }
    
    /**
     * Search only subject and body of an email.
     *
     * @param s the keyword to search for
     * @return the message[]
     * @throws MessagingException the messaging exception
     */
    public Message[] searchSAndB(String s) throws MessagingException{
        HashSet<Integer> mss = new HashSet<Integer>();
       
        for(int i = 0; i < 20; i++){
            if(messages[i].match(new BodyTerm(s))){
                mss.add(i);
            }else{
            	if(messages[i].match(new SubjectTerm(s))){
            		mss.add(i);
				}
            }
        }
        return folder.search(new OrTerm(new SubjectTerm(s), new BodyTerm(s)));
    }
   
    /**
     * Reverse messages. so that we first see the most recent emails
     */
    private void reverseMessages(){
        Message temp = null;
        for(int i = 0; i < messages.length/2; i++){
            temp = messages[i];
            messages[i] = messages[messages.length -1 -i];
            messages[messages.length -1 -i] = temp;
        }
    }
    

    /**
     * Sets the flag to seen.
     *
     * @param n the new flag to seen
     * @throws MessagingException the messaging exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setFlagToSEEN(int n) throws MessagingException, IOException{
            messages[n].setFlag(Flag.SEEN, true);
    }
   
    /**
     * Close store.
     */
    public void closeStore(){
        if(store != null) {
        	try {
        		store.close();
        	} catch (MessagingException e) {
        		e.printStackTrace();
			}
        }
    }
   
    /**
     * Close folder.
     *
     * @param bool the bool
     */
    public void closeFolder(boolean bool){
        if(folder != null && folder.isOpen()) {try {
			folder.close(bool);
		} catch (MessagingException e) {
			e.printStackTrace();
		}}
    }
    
    /**
     * New filter.
     *
     * @param basis the basis
     * @param flagName the flag name
     * @return the int
     */
    public int newFilter(String basis, String flagName){
    	try{
    	Filter filter = new Filter(basis, flagName);
    	if(!userFlags.contains(flagName)){
    		filters.add(filter);
    		userFlags.add(flagName);
    		setCustomFlag(basis, flagName);
    		return 1;
    	}
    	}catch(MessagingException e){
    		e.printStackTrace();
    	}
    		return 0;

    }
    
    /**
     * Renew flags.
     */
    public void renewFlags(){
    	try{
    	for(int i = 0; i < filters.size(); i++){
    		setCustomFlag(filters.get(i).getBasis(), filters.get(i).getFlag());
    	}
    	}catch(MessagingException e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Removes the user flag.
     *
     * @param flagName the flag name
     */
    public void removeUserFlag(String flagName){
    	try{
    	folder.setFlags(messages, new Flags(flagName), false);
    	userFlags.remove(flagName);
    	filters.remove(new Filter(flagName));
    	}catch(MessagingException e){
    		e.printStackTrace();
    	}
    }
    /**
     * Sets the custom flag.
     *
     * @param basis the keyword to search for (positive matches will be flagged)
     * @param flagName the flag name
     * @return the number of search results [for debugging purposes]
     * @throws MessagingException the messaging exception
     */
    private int setCustomFlag(String basis, String flagName) throws MessagingException{	
    	Message[] toFlag = searchMailBox(basis);
    	//folder.setFlags(searchMailBox(basis), new Flags(flagName), true);
    	for(int i = 0; i < toFlag.length; i++){
    		toFlag[i].setFlags(new Flags(flagName), true);
    	}
    	return toFlag.length;
    }
    
    /**
     * Check for new messages.
     *
     * @return the number of new messages
     */
    public int checkForNewMessages(){
    	try {
    		System.out.println("MESSAGE COUNT " + folder.getMessageCount());
    		System.out.println("CONSTANT " + messageCount);
    		int difference = folder.getMessageCount() - messageCount;
    		System.out.println(difference);
    		return difference;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    /**
     * Update folder.
     */
    public void updateFolder(){
        try {
        	 folder = (IMAPFolder) store.getFolder("inbox");

             // Step 4: Open the folder
             if(!folder.isOpen())
                 folder.open(Folder.READ_WRITE);
        
			messages = folder.getMessages();
			reverseMessages();
			
			System.out.println(messages.length);
			messageCount = messages.length;
			renewFlags();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    /**
     * The main method. Used for testing most of the functionality without the gui.
     *
     * @param args the arguments
     * @throws MessagingException the messaging exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws MessagingException, IOException {
    	/*
    	
    	long startTime = System.currentTimeMillis();

        IMAPMailProcessor proc = new IMAPMailProcessor("ilchenko.daniil280@gmail.com");
        proc.reverseMessages();
        String s[] = proc.getMailSubjects();
       //System.out.println(proc.newFilter("account", "SPAM"));
        //System.out.println(proc.getUserFlags().contains("BORING"));
       proc.removeUserFlag("MARTIN");
       // System.out.println(proc.getUserFlags().contains("BORING"));
        String[] userFlags = proc.getUserFlags().toArray(new String[0]);
        System.out.println("start");
        for(int i = 0; i < userFlags.length; i++){
        	System.out.println(userFlags[i]);
        }
        for(int i = 0; i < s.length; i++){
            System.out.println("------ SUBJECT: " + s[i]);
           // proc.setFlagToSEEN(i);
           System.out.println("-------FLAGS BEFORE:" + proc.getMessageFlags(i));
           System.out.println("-----------------------------MESSAGE STARTS------------------------------");
           System.out.println(proc.getMessageContent(i));
           System.out.println("-----------------------------MESSAGE FINISHES------------------------------");
           System.out.println("-------FLAGS AFTER:" + proc.getMessageFlags(i));
        }
        ArrayList<String> flags = proc.getMessageFlags(12);
        for(int i = 0; i < flags.size(); i++){
            System.out.println(flags.get(i));
        }
        Message[] searchRs = proc.searchMailBox("account");
       // Object[] nums = searchRs.toArray();
        System.out.println(searchRs.length);
       proc.closeFolder(true);
       proc.closeStore();
       System.out.println("finish at " + (System.currentTimeMillis() - startTime));
	*/
    }
}