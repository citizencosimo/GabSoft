package content;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;
import util.HTMLBuild;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter w = response.getWriter();
		DBUtil.writeHeader(response, w);
		Connection connection = null;
		String user = request.getParameter("username");
		String pass = request.getParameter("pass");
		boolean result = false;
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        result = DBUtil.validate(connection, user, pass);
	        
	        if (result) {
	        	HttpSession session = request.getSession();
	        	session.setAttribute("username", user);
	        	session.setAttribute("update", false);
	        	response.sendRedirect("UserInfo");
	        	
	        } else {
	        	w.append("\n\t<h1>TEST</h1>\n"
	        			+ "<div class='left'><h2>Sorry, the username or password provided was incorrect</h2>"
	        			+ "<br><form><input type='button' value='Go back' onclick='history.back()'></form></div></body>");
	        	w.append(HTMLBuild.script);
	        	w.append("</html>");
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
