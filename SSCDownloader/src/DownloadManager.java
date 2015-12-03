import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * The Class DownloadManager
 * creates a thread pool and dispatches individual file links to be downloaded, each in a separate thread
 */
public class DownloadManager implements Observer {
	
	/** arraylist of direct file links */
	private ArrayList<String> links;
	
	/** The path to the destination folder */
	private String folderPath;
	
	/** The number of threads to download with*/
	private int threads = 3;
	
	/**
	 * Instantiates a new download manager.
	 *
	 * @param links the links
	 * @param folderPath the path to destination folder
	 */
	public DownloadManager(ArrayList<String> links, String folderPath, int threads){
		this.links = links;
		this.folderPath = folderPath;
		this.threads = threads;
	}
	
	/**
	 * create an instance of FileDownloader for each link in the list and execute it
	 */
	public void downloadAll(){
		//Step 1: Create a fixed Thread pool 
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		FileDownloader runnableobj = null;
		System.out.println("Job queue: " + links);
		for (int counter=0; counter<links.size(); counter++) {
			//Create objects of Runnable		
			runnableobj = new FileDownloader(links.get(counter), folderPath);
			runnableobj.addObserver(this);
			// Step 2: to execute Runnable/Callable objects, which creates a new thread and launches it immediately 
			pool.submit(runnableobj);
		}
		// Step 3: shutdown of the ExecutorService:
		pool.shutdown();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	// Observable -- observer relationship between threads and DownloadManager is formed in order to monitor the status of each individual download
	@Override
	public void update(Observable arg0, Object arg1) {
		DownloadStatus st = ((DownloadStatus) arg1);
		System.out.println(st.getThreadName() +
				" is working on " + st.getFileName() + " Status: " + st.getStatus());
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) throws UnknownHostException{
		String s = "";
		ArrayList<String> ext = new ArrayList<String>();
		int threads = 3;
		//below a bunch of while loops to implement a command line interface
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			while(true){
				System.out.println("Enter link to a website to download from.");
				s = br.readLine();
	            if(!s.toUpperCase().equals("")){
	            		break;
	            }
	        }
			PageParser pp = new PageParser(s);
			while(true){
				System.out.println("Enter file type (i.e. jpg, png) to download (F to proceed with downloading).");
				s = br.readLine();
	            if(s.toUpperCase().equals("F")){
	            		break;
	            }
	            ext.add(s);
	        }
			String folderPath = "";
			while(true){
				System.out.println("Enter a path to a directory for downloads to be saved to.");
				s = br.readLine();
	            if(!s.toUpperCase().equals("")){
	            	folderPath = s;
	            	break;
	            }
	        }
			while(true){
				System.out.println("Enter number of threads to download with.");
				s = br.readLine();
	            if(!s.toUpperCase().equals("")){
	            	threads = Integer.parseInt(s);
	            	break;
	            }
	        }
			DownloadManager dm = new DownloadManager(pp.getFileLinks(ext.toArray(new String[] {})), folderPath, threads);
			dm.downloadAll();
		} catch (UnknownHostException e) {
			System.out.println("You entered an invalid webaddress!");
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			System.out.println("IllegalArgumentException was thrown make sure to follow the instructions.");
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
