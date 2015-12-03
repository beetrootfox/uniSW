import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

/**
 * The Class FileDownloader.
 *  runnable class to download a single file with a direct link
 */
public class FileDownloader extends Observable implements Runnable{
	
	/** direct link to a file */
	private String urlstr;
	
	/** The path to the destination folder */
	private String folderPath;
	
	/** status of the download */
	private DownloadStatus status;	
	
	/** name of the file being downloaded */
	private String fileName;
	
	/**
	 * Instantiates a new file downloader.
	 *
	 * @param urlstr the direct link
	 * @param folderPath the folder path
	 */
	public FileDownloader(String urlstr, String folderPath){
		super();
		this.urlstr = urlstr;
		this.folderPath = folderPath;
		this.fileName = urlstr.substring( urlstr.lastIndexOf('/')+1, urlstr.length() );
		this.status = new DownloadStatus(Statuses.QUEUED, fileName);
	}
	
	/**
	 * Update status.
	 *
	 * @param status the status of downloading process
	 */
	private void updateStatus(Statuses status){
		this.status.setStatus(status);
		setChanged();
		notifyObservers(this.status);
		
	}
	
	/**
	 * Gets the status of the download
	 *
	 * @return the status
	 */
	public DownloadStatus getStatus(){
		return status;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
		//Thread.sleep(1000);
		//Communicate to the wrapper class that the download has started
		this.status.setThreadName(Thread.currentThread().getName());
		updateStatus(Statuses.DOWNLOADING);
	
		//Thread.sleep(1000);
		
		//download the file to the specified folder in the system
		URL url = new URL(urlstr);
		//System.out.println(urlstr + "-- THIS IS WHAT WE READ!");
		InputStream in = url.openStream();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + fileName));
		for (int b; (b = in.read()) != -1;) {
			out.write(b);
		}
		out.close();
		in.close();
		//Thread.sleep(1000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} //catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		
		//communicate to the wrapper class that the download has finished
		updateStatus(Statuses.FINISHED);
		
	}

}
