

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class LoginServlet.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** maximum age of login session; after the specified number of seconds it will expire */
	private final int age = 60;

    /**
     * Instantiates a new login servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * GET HTTP method
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
	}

	/**
	 * POST HTTP method 
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");  
	    String password = request.getParameter("password");
	    
	    //response.setContentType("text/html");  
	    //PrintWriter out = response.getWriter();  	
	    try{
	    	//in order to verify username and password try to connect to users mailbox (gmail in my case)
	    	//in real systems this system will lookup provided username:password pair in the database 
	    	String smtphost = "smtp.gmail.com";
			
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");
			
			props.setProperty("mail.user", username);
			props.setProperty("mail.password", password);

			Session session = Session.getDefaultInstance(props);
			Transport tr = session.getTransport("smtp");			
			tr.connect(smtphost, username, password); 
			
			Cookie loginCookie = new Cookie("username", username);
	    	loginCookie.setMaxAge(age);
	    	response.addCookie(loginCookie);
	    	Cookie passwordCookie = new Cookie("password", password);
	    	passwordCookie.setMaxAge(age);
	    	response.addCookie(passwordCookie);
	    	response.sendRedirect("NewMessage.jsp");
	    	
	    }catch(AuthenticationFailedException e){
	    	RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
	    	PrintWriter out = response.getWriter();
	    	out.println("<font color=red style=bold>Invalid (username, password) pair, please enter again!</font>\n"); 
	    	rd.include(request, response);
	    }catch(MessagingException e){
	    	e.printStackTrace();
	    }
	    
	}

}
