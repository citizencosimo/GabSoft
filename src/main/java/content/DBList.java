package content;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;

/**
 * Servlet implementation class DBSearch
 */
@WebServlet("/DBList")
public class DBList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			HttpSession session = request.getSession();
			String user = session.getAttribute("username").toString();
			System.out.println("!");
			search(user, session, response);
		}
		catch (Exception e) {
			response.sendRedirect("login.html");
		}
		
		
	}
	
	
	void search(String user, HttpSession session, HttpServletResponse response) throws IOException {
		PrintWriter w = response.getWriter();
		DBUtil.writeHeader(response, w);
		
		
		Connection connection = null;
		try {
			DBConnection.getDBConnection(getServletContext());
			connection = DBConnection.connection;
			//String grabSql = "select * from Prescription where USERNAME=?";
			String[] vals = {user};
			System.out.println(user);
			//PreparedStatement ps = DBUtil.prepareStatement(connection, grabSql, vals);
			PreparedStatement ps;
			//ResultSet rs = ps.executeQuery();
			ResultSet scripts;
			int i = 1;
			w.append("\n<h1>DEMONSTRATION</h1><div class='left'>\n<h2>Hello " + session.getAttribute("fname") + "!</h2>");
			w.append("<br>\n<p>\r\n"
					+ "               EMAIL:\t" + session.getAttribute("email").toString()
					+ "				 PHARMACY:\t" + session.getAttribute("pharmacy").toString()
					+ "            \r\n</p>\n</div>\n");
			w.append("\n<div class='center'>\n<h2>Prescription List</h2>\n");
			String joinSQL = "select Prescription.NDC, DrugDB.BRANDNAME, DrugDB.STRENGTH, DrugDB.FORM, Prescription.TAKE, Prescription.PERDAY, "
					+ "Prescription.DS, Prescription.FILLDATE, Prescription.PHARM from Prescription inner join DrugDB on Prescription.NDC=DrugDB.NDC "
					+ "where Prescription.USERNAME=?";
			ps = DBUtil.prepareStatement(connection, joinSQL, vals);
			scripts = ps.executeQuery();
				
			while (scripts.next()) {
				w.append("<br>\n<p>" + i + ". ");
				w.append(scripts.getString("BRANDNAME") + " " + scripts.getString("STRENGTH") + ": Take " 
						+ scripts.getString("TAKE") + " " + scripts.getString("FORM").trim().toLowerCase()
						+ " " + scripts.getInt("PERDAY") + " time(s) per day.\n<BR>"
						+ "Last Filled: " + scripts.getString("FILLDATE") + " for a " + scripts.getString("DS") + " days supply.</p>");
				i++;
			}
			
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		w.append("</body>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
