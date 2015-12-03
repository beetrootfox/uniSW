import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The Class TagGrabber.
 * Extracts direct links from tags ussing regular expressions 
 */
public class TagGrabber implements Callable<ArrayList<String>>{
	
	/** The webaddress -- address of a website to parse */
	private String webaddress;
	
	/** array of two html elements: a tag (i.e. a) and a property of that tag we are interested in (i.e. href) */
	private String[] tags;
	
	/** html document retrieved from the website */
	private Document doc;
	
	/** The extensions to look for */
	private String[] extensions;
	
	
	/**
	 * Instantiates a new tag grabber.
	 *
	 * @param webaddress -- address of a website to parse
	 * @param tags -- array of two html elements: a tag (i.e. a) and a property of that tag we are interested in (i.e. href
	 * @param extensions the extensions to look for
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public TagGrabber(String webaddress, String[] tags, String[] extensions) throws IOException{

		this.webaddress = webaddress;
		this.tags = tags;
		this.extensions = extensions;
		this.doc = Jsoup.connect(webaddress).get();

	}
		
		/**
		 * Builds the regular expression used to parse the html document 
		 *
		 * @return the string
		 */
		public String buildRegex(){
		String regex = tags[0] + "[" + tags[1] + "~=(?i)\\.";
		if(extensions.length > 1){
			regex = regex + "(";
			for(int i = 0; i < extensions.length; i++){
				if(i < extensions.length - 1){
					regex = regex + extensions[i] + "|";
				}else{
					regex = regex + extensions[i];
				}
			}
			regex = regex + ")";
		}else{
			regex = regex + extensions[0];
		}
		regex = regex + "]";
		System.out.println(regex);
		return regex;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	//utilizes buildRegex() to create a regular expression with which it parses the html document and transforms resulting Elements object into an array of strings
	@Override
	public ArrayList<String> call() throws Exception {

		ArrayList<String> links = new ArrayList<String>();
		// selector uses CSS selector with regular expression
		String toSelect = this.buildRegex();
		//parse html into elements
		Elements files = doc.select(toSelect);
		//transform Elements object into an array of strings
		for (Element file : files) {
			String urlstr = file.attr(tags[1]);
				if(urlstr.indexOf(webaddress)<=0){
				
						urlstr = file.attr("abs:" + tags[1]);
					
				}
			System.out.println(urlstr);

			links.add(urlstr);

		}
		
		return links;
	}

}
