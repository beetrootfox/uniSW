
/**
 * The Enumerator Statuses.
 * enumerator class for all possible download statuses
 */
public enum Statuses {
	
	/** enum for status "in queue". */
	QUEUED ("In queue"),
	
	/** enum for status "downloading". */
	DOWNLOADING ("Downloading"),
	
	/** enum for status "download finished". */
	FINISHED ("Download completed");


    /** The extension. */
    private final String extension;       

    /**
     * Instantiates a new statuses.
     *
     * @param s the s
     */
    private Statuses(String s) {
        extension = s;
    }
    
    /**
     * Equals name.
     *
     * @param otherName the other name
     * @return true, if successful
     */
    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : extension.equals(otherName);
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
       return this.extension;
    }
}
