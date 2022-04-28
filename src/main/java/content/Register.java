package content;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;
import util.HTMLBuild;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("pass");
		String password2 = request.getParameter("pass2");
		String lname = request.getParameter("lname");
		String fname = request.getParameter("fname");
		String email = request.getParameter("email");
		String phone = request.getParameter("pnum");
		String pharm = request.getParameter("pharm");
		boolean success = false;
		if (username != null && password != null) {
			success = reg(response, username, password, password2, lname, fname, email, phone, pharm);
		}
		
		response.setContentType("text/html");
	    PrintWriter w = response.getWriter();
	    DBUtil.writeHeader(response, w);
		
		if (success) {
			w.append("<h1>TestPage</h1>");
			w.append("<div class='left'>"
					+ "<h2>Account Successfully Created!</h2>"
					+ "<a href='UserInfo'>Click Here</a> to verify your user information.</div></body>");
			w.append(HTMLBuild.script + "</html>");
			
		} else {
			w.append("<h1>TestPage</h1>");
			w.append("<div class='left'>"
					+ "<h2>Something went wrong</h2>"
					+ "<a href='registration.html'>Click here</a> to try again.</div></body>");
			w.append(HTMLBuild.script + "</html>");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	boolean reg(HttpServletResponse response, String user, String pass, String p2, String l, String f, String em, String ph, String pp ) throws IOException {
		
	    Connection connection = null;
	    
	    if (!pass.equals(p2)) {
        	return false;
        }
	    
	    try {
	    	DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        
	        if (!pass.equals(p2)) {
	        	return false;
	        }
	    	
	        String genpass = DBUtil.hashPass(pass); // Creates a hash from the input password.
	        
	        ResultSet rs = null;
	        String selectSQL = "SELECT * FROM login WHERE USERNAME=?";
	        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setString(1, user);
	        rs = preparedStatement.executeQuery();
	        
	        if (rs.next() && rs.getString("USERNAME").equals(user)) {
	        	
	        	return false;
	        }
	        else {
	        	String addSQL = "INSERT INTO login (USERNAME, PASSWORD) VALUES (?, ?)";
	        	PreparedStatement insertStatement = connection.prepareStatement(addSQL);
	        	insertStatement.setString(1, user);
	        	insertStatement.setString(2, genpass);
	        	insertStatement.execute();
	        	
	        	addSQL = "insert into DemoUser (USERNAME, LASTNAME, FIRSTNAME, EMAIL, PHONE, PHARMACY) values (?, ?, ?, ?, ?, ?)";
	        	insertStatement = connection.prepareStatement(addSQL);
	        	insertStatement.setString(1, user);
	        	insertStatement.setString(2, l);
	        	insertStatement.setString(3, f);
	        	insertStatement.setString(4, em);
	        	insertStatement.setString(5, ph);
	        	insertStatement.setString(6, pp);
	        	insertStatement.execute();
	        	
	        	return true;
	        }
	        
	        
	    	
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return false;
	    
	    
	}

}
