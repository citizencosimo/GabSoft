package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import content.DBConnection;

public class DBUtil {
	public static void writeHeader(HttpServletResponse response, PrintWriter w) throws IOException {
			
			w.append(util.HTMLBuild.header);
			w.append("<body>");
			w.append(util.HTMLBuild.nav);
			w.append("<h1>!</h1>");
			
		}
	
	public static String hashPass(String k) {
		try {

	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	md.update(k.getBytes());
	    	byte[] bytes = md.digest();
	    	
	    	StringBuilder sb = new StringBuilder();
	    	for (int i = 0; i < bytes.length; i++) {
	    		sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)).substring(1);
	    	}
	    	
	    	String genpass = sb.toString();
	    	
	    	return genpass;
	    } 
	    catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    }
		
		return null;
		
	}
	
	public static boolean validate(Connection c, String u, String p) throws SQLException {
		ResultSet rs = null;
		String userLookup = "select PASSWORD from login where USERNAME=?";
		PreparedStatement ps = c.prepareStatement(userLookup);
		ps.setString(1, u);
		rs = ps.executeQuery();
		if (rs.next()) {
			String g = hashPass(p);
			if (g.equals(rs.getString("PASSWORD"))) {
				System.out.println("MATCHED: " + p + " / " + g);
				return true;
			}
			else {
				System.out.println("Password did not match!");
				return false;
			}
		}
		return false;
		
	}
	
	public static PreparedStatement prepareStatement(Connection c, String s, String[] a) throws SQLException {
		
		PreparedStatement ps = c.prepareStatement(s);
		for (int i = 1; i <= a.length; i++ ) {
			ps.setString(i, a[i - 1]);
		}
		
		return ps;
	}
}
