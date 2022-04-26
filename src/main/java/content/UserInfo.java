package content;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;
import util.HTMLBuild;

/**
 * Servlet implementation class UserInfo
 */
@WebServlet("/UserInfo")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = "";
		
		try {
			user = session.getAttribute("username").toString();
		} catch (Exception e) {
			response.sendRedirect("login.html");
		}
		PrintWriter w = response.getWriter();
		DBUtil.writeHeader(response, w);
		getInfo(response, session, w, user);
		if ((boolean) session.getAttribute("update")) {
			w.append("<br><em>Information updated!</em><br></div></body>" + HTMLBuild.script);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void getInfo(HttpServletResponse response, HttpSession session, PrintWriter w, String user) {
		try {
			DBConnection.getDBConnection(getServletContext());
	        Connection connection = DBConnection.connection;
	        ResultSet rs = null;
	        String searchSQL = "select * from DemoUser where USERNAME=?";
	        String[] arguments = {user};
	        
	        PreparedStatement ps = DBUtil.prepareStatement(connection, searchSQL, arguments);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	
	        	session.setAttribute("fname", rs.getString("FIRSTNAME"));
	        	session.setAttribute("lname", rs.getString("LASTNAME"));
	        	session.setAttribute("email", rs.getString("EMAIL"));
	        	session.setAttribute("phone", rs.getString("PHONE"));
	        	session.setAttribute("pharmacy", rs.getString("PHARMACY"));
	        	
		        w.append("<h1>TEST PAGE</h1>");
		        w.append("\n<div class='left'>\n<h2>User Info:</h2>\n");
		        w.append("\t<form action='UserUpdate' method='POST'>\n"
		        		+ "\t\t<label for 'fname'>First name:</label><br>\n"
		        		+ "\t\t<input type='text' name='FIRSTNAME' value='" + rs.getString("FIRSTNAME") + "'>\n<br>"
		        		+ "\t\t<label for 'lname'>Last name:</label><br>\n"
		        		+ "\t\t<input type='text' name='LASTNAME' value='" + rs.getString("LASTNAME") + "'>\n<br>"
		        		+ "\t\t<label for 'email'>Email address:</label><br>\n"
		        		+ "\t\t<input type='text' name='EMAIL' value='" + rs.getString("EMAIL") + "'>\n<br>"
		        		+ "\t\t<label for 'phone'>Phone number:</label><br>\n"
		        		+ "\t\t<input type='text' name='PHONE' value='" + rs.getString("PHONE") + "'>\n<br>"
		        		+ "\t\t<label for 'pharm'>Preferred pharmacy:</label><br>\n"
		        		+ "\t\t<input type='text' name='PHARMACY' value='" + rs.getString("PHARMACY") + "'>\n<br>"
		        		+ "\t\t<input type='submit' value='Update Info'></form></div>");
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
