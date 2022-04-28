package content;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;

/**
 * Servlet implementation class add
 */
@WebServlet("/add")
public class add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String ndc = request.getParameter("ndc");
			int take = Integer.parseInt(request.getParameter("take"));
			int perday = Integer.parseInt(request.getParameter("perday"));
			int qty = Integer.parseInt(request.getParameter("ds"));
			int ds = qty / (take * perday);
			String lastfill = request.getParameter("lastfill").replace("-", "");
			String user = session.getAttribute("username").toString();
			String pharm = session.getAttribute("pharmacy").toString();
			
			String[] prep = {user, ndc, Integer.toString(take), Integer.toString(perday), Integer.toString(ds), lastfill, pharm};
			System.out.println(Arrays.toString(prep));
			

			DBConnection.getDBConnection(getServletContext());
		    Connection connection = DBConnection.connection;
		    String addSQL = "insert into Prescription (USERNAME, NDC, TAKE, PERDAY, DS, FILLDATE, PHARM) values (?, ?, ?, ?, ?, ?, ?)";
		    PreparedStatement ps = DBUtil.prepareStatement(connection, addSQL, prep);
		    ps.execute();
		    response.sendRedirect("DBList");
		    

			
		}
		catch(Exception e) {
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
