package content;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;

/**
 * Servlet implementation class AddPrescription
 */
@WebServlet("/AddPrescription")
public class AddPrescription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPrescription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String user = session.getAttribute("username").toString();
			addDrug(response, session, user);
			
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
	
	void addDrug (HttpServletResponse response, HttpSession session, String user) throws IOException {
		PrintWriter w = response.getWriter();
		DBUtil.writeHeader(response, w);
		w.append("<div class='left'><h2>Add Drug</h2>\n"
				+ "	<form action='lookup'>\n"
				+ "<label for='ndc'>Enter the NDC of your drug:</label><br>\n"
				+ "<input type='text' name='ndc'><br>\n"
				+ "<input type='submit' value='Search'>\n"
				+ "</form>\n"
				+ "<br><p>*This information should be located on your prescription's paperwork.</p>"
				+ "		</div>");
		
		
		w.append("<div class='center'><h2>Drug information</h2><br>");
		try {
			ResultSet rs = (ResultSet) session.getAttribute("searchresult");
			Boolean success = (boolean) session.getAttribute("searchstatus");
			
			if(!success) {
				w.append("<p>No result found for that ndc.</p>");
			}
			else {
				session.setAttribute("searchstatus", null);
				String s = "<p><b>" + rs.getString("BRANDNAME") + "</b><em> " + rs.getString("STRENGTH") + "</em><br>"
						+ rs.getString("FORM") + "<br>Manufactured by: " + rs.getString("MANUFACTURER");
				w.append(s);
				w.append("<p>If this is the correct drug, please complete the form and hit submit:</p><br>");
				w.append("<form>"
						+ "<label for='take'>How many do you take at a time?</label><br>"
						+ "<input type='number' name='take'><br>"
						+ "<label for='perday'>How many times a day do you take this?</label><br>"
						+ "<input type='number' name='perday'><br>"
						+ "<label for='ds'>How many pills are in your prescription?</label><br>"
						+ "<input type='number' name='ds'><br>"
						+ "<label for='lastfill'>When did you last fill this?</label><br>"
						+ "<input type='date' name='lastfill'><br>"
						+ "<input type='hidden' name='ndc' value='" + rs.getString("NDC") + "'>"
						+ "<input type='submit' formaction='add'>"
						+ "</form>");
				
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		w.append("</div>");
		
		
		
		w.append("</body></html>");
	}

}
