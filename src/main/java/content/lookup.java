package content;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;

/**
 * Servlet implementation class lookup
 */
@WebServlet("/lookup")
public class lookup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public lookup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("TEST");
		try {
			HttpSession session = request.getSession();
			String user = session.getAttribute("username").toString();
			returnResult(request, response, session);
			
		}
		catch (Exception e) {
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
	
	void returnResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String[] ndc = {request.getParameter("ndc")};
		if (ndc[0] != null && !ndc[0].equals("")) {
			try {
				DBConnection.getDBConnection(getServletContext());
		        Connection connection = DBConnection.connection;
		        String sqlSearch = "select * from DrugDB where NDC=?";
		        PreparedStatement ps = DBUtil.prepareStatement(connection, sqlSearch, ndc);
		        ResultSet rs = ps.executeQuery();
		        
		        if (rs.next()) {
		        	//System.out.println(rs.toString());
		        	session.setAttribute("searchresult", rs);
		        	session.setAttribute("searchstatus", true);
		        }
		        else {
		        	session.setAttribute("searchstatus", false);
		        }
		        response.sendRedirect("AddPrescription");
		        
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
