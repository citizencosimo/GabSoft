package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import content.DBConnection;

public class DBUtil {
	public static void writeHeader(HttpServletResponse response, PrintWriter w) throws IOException {
			
			w.append(util.HTMLBuild.header);
			w.append("<body>");
			w.append(util.HTMLBuild.nav);
			
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
}
