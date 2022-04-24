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
		
		
		search("metoprolol", response);
		
		
	}
	
	
	void search(String keyword, HttpServletResponse response) throws IOException {
		PrintWriter w = response.getWriter();
		DBUtil.writeHeader(response, w);
		
		
		Connection connection = null;
		try {
			DBConnection.getDBConnection(getServletContext());
			connection = DBConnection.connection;
			String grabSql = "select * from DemoUser";
			Statement m = connection.createStatement();
			PreparedStatement ps = null;
			String pSql = "select * from DrugDB where NDC=?";
			ResultSet user = m.executeQuery(grabSql);
			String greeting = "Hello anonymous user!";
			String email = "";
			String pharm = "";
			ResultSet scripts;
			int i = 1;
			while (user.next()) {
				String lname = user.getString("LASTNAME").trim();
				String fname = user.getString("FIRSTNAME").trim();
				greeting = ("Hello " + fname + " " + lname + ".");
				email = user.getString("EMAIL").trim();
				pharm = user.getString("PHARMACY");
			}
			grabSql = "select * from Prescription";
			ResultSet rxList = m.executeQuery(grabSql);
				w.append("\n<h1>DEMONSTRATION</h1><div class='left'>\n<h2>" + greeting + "\n</h2>");
				w.append("<br>\n<p>\r\n"
						+ "               EMAIL:\t" + email
						+ "				 PHARMACY:\t" + pharm
						+ "            \r\n</p>\n</div>\n");
				w.append("\n<div class='center'>\n<h2>Prescription List</h2>\n");
				while (rxList.next()) {
					String ndc = rxList.getString("NDC").trim();
					ps = connection.prepareStatement(pSql);
					ps.setString(1, ndc);
					System.out.println(ps.toString());
					scripts = ps.executeQuery();
					System.out.println(ps.toString());
					while (scripts.next()) {
						w.append("<br>\n<p>" + i + ". ");
						w.append(scripts.getString("BRANDNAME") + " " + scripts.getString("STRENGTH") + ": Take " 
								+ rxList.getInt("TAKE") + " " + scripts.getString("FORM").trim().toLowerCase()
								+ " " + rxList.getInt("PERDAY") + " time(s) per day.\n<BR>"
								+ "Last Filled: " + rxList.getString("FILLDATE") + "</p>");
						i++;
					}
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
