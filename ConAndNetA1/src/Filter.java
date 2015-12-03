/**
 * The Class Filter.
 */
public class Filter {
	
	/** The flag name. */
	private String flagName = "";
	
	/** The keyword -- the basis for search process that decides whether or not to flag an email */
	private String keyword = "";
	
	/**
	 * Instantiates a new filter.
	 *
	 * @param basis the basis
	 * @param flagName the flag name
	 */
	public Filter(String basis, String flagName){
		keyword = basis;
		flagName = flagName;
	}
	
	/**
	 * Instantiates a new filter. this constructor is used to check whether or not a filter with a particular name has already been used
	 *
	 * @param flagName the flag name
	 */
	public Filter(String flagName){
		flagName = flagName;
	}
	
	/**
	 * Gets the basis for the search
	 *
	 * @return the basis
	 */
	public String getBasis(){
		return keyword;
	}
	
	/**
	 * Gets the flag name.
	 *
	 * @return the flag name
	 */
	public String getFlag(){
		return flagName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object filter){
		if(filter instanceof Filter){
			return flagName.equals(((Filter) filter).getFlag());
		}else{
			return false;
		}
	}

}
