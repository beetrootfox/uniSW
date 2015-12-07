

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.smtp.SMTPAddressFailedException;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class MessageServlet.
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
     * Instantiates a new message servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Do get.
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
	 * POST method 
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = null;
		String password = null;
		//retrieve the cookies associated with user's login session
		Cookie[] cookies = request.getCookies();
		for(int i = 0; i < cookies.length; i++){
			if(cookies[i].getName().equals("username")){
				username = cookies[i].getValue();
			}else{
				if(cookies[i].getName().equals("password")){
					password = cookies[i].getValue();
				}
			}
		}
		
		//if session is invalid or expired -- redirect to the login page
		if(username == null || password == null){
			response.sendRedirect("login.html");
		}else{
			
			String to = request.getParameter("to");
			String cc = request.getParameter("cc");
			String subject = request.getParameter("subject");
			String body = request.getParameter("body");
			
			try{
				
				Compositor comp = new Compositor(username, password);
				comp.SendMessage("", subject, body, to, cc);
				response.sendRedirect("NewMessage.jsp");
				
			}catch(NullPointerException e){ //if some fields required to send an email are empty, inform the user
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/NewMessage.jsp");
		    	PrintWriter out = response.getWriter();
		    	out.println("<font color=red style=bold>Nothing to send!</font>\n"); 
		    	rd.include(request, response);
		    	
			}catch(RuntimeException e){//if unable to send a message to specified address, inform the user that its invalid 
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/NewMessage.jsp");
		    	PrintWriter out = response.getWriter();
		    	out.println("<font color=red style=bold>Invalid receiving address!</font>\n"); 
		    	rd.include(request, response);
			}			
		}
	}
}
