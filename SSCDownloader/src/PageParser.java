import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * The Class PageParser.	Wrapper class for TagGrabber that launches two objects of said class in separate threads to retrieve files inside <img> and <a> tags
 */
public class PageParser {
	
	/** address of a web site to parse */
	private String webaddress;

	
	/**
	 * Instantiates a new page parser
	 *
	 * @param webaddress -- address of a web site to parse
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PageParser(String webaddress) throws IOException{
		this.webaddress = webaddress;
	}
	
	/**
	 * Gets the file links.
	 *	
	 * @param ext the ext
	 * @return the file links
	 */
	public ArrayList<String> getFileLinks(String[] ext){
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> aResults = new ArrayList<String>();
		ArrayList<String> imgResults = new ArrayList<String>();
		try{
			//Create a fixed Thread pool of 2 thread 
			ExecutorService pool = Executors.newFixedThreadPool(2);
			//one thread gets file links from within <a> tags
			TagGrabber aGrabber = new TagGrabber(webaddress, new String[] {"a", "href"}, ext);
			//another does the same for <img> tags
			TagGrabber imgGrabber = new TagGrabber(webaddress, new String[] {"img", "src"}, ext);
			
			Future<ArrayList<String>> aTagFuture = pool.submit(aGrabber);
			Future<ArrayList<String>> imgTagFuture = pool.submit(imgGrabber);
			
			aResults = aTagFuture.get();
			imgResults = imgTagFuture.get();
			//shutdown of the ExecutorService:
			pool.shutdown();
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(ExecutionException e){
			e.printStackTrace();
		}
		//merge links obtained from <a> and <img> tags
		links.addAll(aResults);
		links.addAll(imgResults);
		return links;
	}

}
