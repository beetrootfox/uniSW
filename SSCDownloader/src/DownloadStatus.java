
/**
 * The Class DownloadStatus
 * 	holds relevant information about downloading threads
 */
public class DownloadStatus {
	
	/** The status. */
	private Statuses status;
	
	/** The thread name. */
	private String threadName = null;
	
	/** The file name. */
	private String fileName;
	
	/**
	 * Instantiates a new download status.
	 *
	 * @param status the status
	 * @param threadName the thread name
	 * @param fileName the file name
	 */
	public DownloadStatus(Statuses status, String threadName, String fileName){
		this.status = status;
		this.threadName = threadName;
		this.fileName = fileName;
	}
	
	/**
	 * Instantiates a new download status.
	 *
	 * @param status the status
	 * @param fileName the file name
	 */
	public DownloadStatus(Statuses status, String fileName){
		this.status = status;
		this.fileName = fileName;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus(){
		return status.toString();
	}
	
	/**
	 * Gets the thread name.
	 *
	 * @return the thread name
	 */
	public String getThreadName(){
		return	threadName;
	}
	
	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Statuses status){
		this.status = status;
	}
	
	/**
	 * Sets the thread name.
	 *
	 * @param name the new thread name
	 */
	public void setThreadName(String name){
		threadName = name;
	}
}
