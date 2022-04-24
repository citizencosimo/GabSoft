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
@WebServlet("/DBSearch")
public class DBSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBSearch() {
    	super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("ndc");
		System.out.println(key);
		search(response, key);
	}
	
	void search(HttpServletResponse response, String key) throws IOException {
		response.setContentType("text/html");
	      PrintWriter w = response.getWriter();
	      DBUtil.writeHeader(response, w);

	      Connection connection = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         ResultSet rs = null;
	         String selectSQL = "SELECT * FROM DrugDB WHERE NDC=?";
	         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	         preparedStatement.setString(1, key);
	         if (key == null || key.isEmpty()) {
	        	 w.append("<h1>GOttahaveit</h1><div class='left'><h2>No drug entered.</h2></div>");
	         } else {
	            rs = preparedStatement.executeQuery();
	         }
	         

	         while (rs != null && rs.next()) {
	            String ndc = rs.getString("NDC");
	            w.append("<div class='left'><h2>Drug Added</h2></div>");
	            w.append("<div class='center'><h2>Drug Info</h2>"
	            		+ rs.getString("BRANDNAME") + " " + rs.getString("STRENGTH") + "<br>"
	            				+ "NDC: " + ndc);
	            String insertSql = "insert into Prescription (NDC, TAKE, PERDAY, DS, FILLDATE, PHARM) values (? , 1, 2, 30, '04-14-2022', 'Hy-Vee')";
	            PreparedStatement in = connection.prepareStatement(insertSql);
	            in.setString(1, ndc);
	            in.execute();
	            in.close();
	         }
	         w.println("<a href=/webproject-ex-0307-siciliano/simpleFormSearch.html>Search Data</a> <br>");
	         w.println("</body></html>");
	         if (rs != null)
	        	 rs.close();
	         if (preparedStatement != null)
	        	 preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
