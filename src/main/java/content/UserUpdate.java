package content;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdate() {
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
			System.out.println("HALP");
		}
		catch (Exception e) {
			response.sendRedirect("login.html");
		}
		
		String[] names = {"FIRSTNAME", "LASTNAME", "EMAIL", "PHONE", "PHARMACY"};
		Connection connection = null;
		
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        
			
			String updateSQL = "";
			
			for(int i = 0; i < names.length; i++) {
				String update = request.getParameter(names[i]);
				if(!update.equals(session.getAttribute(names[i]))) {
					String[] updateval = {update};
					updateSQL = "update DemoUser set " + names[i] + "=? where USERNAME ='" + user + "'";
					DBUtil.prepareStatement(connection, updateSQL, updateval).execute();
					session.setAttribute("update", true);
				}
			}
			response.sendRedirect("UserInfo");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.html");
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
