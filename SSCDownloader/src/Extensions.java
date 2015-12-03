
/**
 * The Enum Extensions.
 * enumerator class to make it easier for developers to work with some common extensions
 *
 */
public enum Extensions {
	
	/** The jpeg extension */
	JPEG ("jpe?g"),
    
    /** The png extension */
    PNG ("png"),
    
    /** The gif extension */
    GIF ("gif"),
    
    /** The zip extension */
    ZIP ("zip"),
    
    /** The rar extension */
    RAR ("rar"),
    
    /** The txt extension */
    TXT ("txt");


    /** The extension. */
    private final String extension;       

    /**
     * Instantiates a new extensions.
     *
     * @param s the s
     */
    private Extensions(String s) {
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


