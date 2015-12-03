import javax.mail.Message;
import javax.swing.JPanel;


/**
 * The Class DisplayResultComponent
 */
public class DisplayResultComponent extends JPanel{
	
	/**
	 * Instantiates a new display result component
	 *
	 * @param model 
	 * @param rS the result set (array really) of messages the search produced
	 */
	public DisplayResultComponent(IMAPMailProcessorModel model, Message[] rS){
		super();
		DisplayResultView view = new DisplayResultView(model, rS);
		model.addObserver(view);
		add(view);
	}
}
