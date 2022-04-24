package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class DBUtil {
	public static void writeHeader(HttpServletResponse response, PrintWriter w) throws IOException {
			
			w.append(util.HTMLBuild.header);
			w.append("<body>");
			w.append(util.HTMLBuild.nav);
			
		}
}
