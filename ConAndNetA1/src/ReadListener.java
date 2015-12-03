import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Message;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving read events.
 * The class that is interested in processing a read
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReadListener<code> method. When
 * the read event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReadEvent
 */
public class ReadListener implements ActionListener {
	
	/** The model. */
	private IMAPMailProcessorModel model;
	
	/** The message. */
	private int message = 0;
	
	/** The r s. */
	private Message rS = null;
	
	/** The content. */
	String content = "";
	
	/** The subject. */
	String subject = "";
	
	/**
	 * Instantiates a new read listener.
	 *
	 * @param model the model
	 * @param message the message
	 */
	public ReadListener(IMAPMailProcessorModel model, int message){
		this.model = model;
		this.message = message;
		content = model.getMessageContent(message);
		subject = model.getMessageSubject(message);
	}
	
	/**
	 * Instantiates a new read listener.
	 *
	 * @param model the model
	 * @param rS the r s
	 */
	public ReadListener(IMAPMailProcessorModel model, Message rS){
		this.model = model;
		this.rS = rS;
		content = model.getMessageContent(rS);
		subject = model.getMessageSubject(rS);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//INITIALIZE GUI
		/*
			ReadGUI read = new ReadGUI(model, content, subject);
			read.run();
		*/
		if(rS == null){
			ReadGUI read = new ReadGUI(model, model.getMessageContent(message), model.getMessageSubject(message));
			read.run();
		}else{
			ReadGUI read = new ReadGUI(model, model.getMessageContent(rS), model.getMessageSubject(rS));
			read.run();
		}

	}

}
