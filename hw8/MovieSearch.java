import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import java.util.Iterator;
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Element;
import org.xml.sax.SAXException;

//  /home/scf-04/mayang/Tomcat/jakarta-tomcat-4.1.27/common/lib/servlet.jar

public class MovieSearch extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException {  
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// TODO
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		//out.println(title);
		//out.println(type);
		//out.print("hello");
		// construct query
		if (type.equals("All Types")) {
			type = "feature,tv_series,game";
		} else if (type.equals("Film")) {
			type = "feature";
		} else if (type.equals("TV")) {
			type = "tv_series";
		} else if (type.equals("Video Games")) {
			type = "game";
		}
		// Get XML conent from php script - This all seems so needlessly circumventive
		//  URLEncoder.encode(title, "UTF-8")
		String query = "http://cs-server.usc.edu:10173/cgi-bin/MovieSearch.php?title=" + URLEncoder.encode(title, "UTF-8") 
						+ "&type=" + type;
		//out.println(query);
		URL url = new URL(query); 
		URLConnection urlConnection = url.openConnection();
		urlConnection.setAllowUserInteraction(false);
		//InputStream urlStream = urlConnection.openStream();
		 InputStream urlStream = urlConnection.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlStream, "UTF-8"));
		// String xmlResults = "";
		// String inputLine; 
		// while ((inputLine = in.readLine()) != null) {
			// xmlResults += inputLine;
		// }
		// //in.close();
		// out.println(xmlResults);
		// translate xml to json
		// DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			
			Element root = doc.getRootElement();
			Element results = root.getChild("results");
			Iterator iter = results.getChildren("result").iterator(); 
			// TODO: CHECK FOR 0 RESULTS
			String jsonString = "{\"results\":{ \"result\":[";
			//String resultJson = "";
			while (iter.hasNext()) {
				Element next = (Element) iter.next();
				// Cover
				jsonString += "{\"cover\":\"" + next.getAttributeValue("cover") + "\",";
				// Title
				jsonString += "\"title\":\"" + next.getAttributeValue("title") + "\",";
				// Year
				jsonString += "\"year\":\"" + next.getAttributeValue("year") + "\",";
				// Director
				jsonString += "\"director\":\"" + next.getAttributeValue("director") + "\",";
				// Rating
				jsonString += "\"rating\":\"" + next.getAttributeValue("rating") + "\",";
				// Details
				jsonString += "\"details\":\"" + next.getAttributeValue("details") + "\"";
				
				if (iter.hasNext()) {
					jsonString += "},";
				} else {
					jsonString += "}";
				}
			} 
			jsonString += "]}}";
			out.print(jsonString);
			out.close();
		} catch (Exception e) {
			out.print(e);
			out.close();
		} 

			//DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Document doc = dBuilder.parse(xmlResults);
			//NodeList resultsList = doc.getElementsByTagName("result");
			// String jsonString = "{\"results\":{ \"result\":";
			// String arrayString = "[";
			// String resultJSON = "";
			//for (int i = 0; i < resultsList.getLength(); ++i) {
				
				// Element result = (Element) resultsList.item(i);
				// resultJSON = "";
				// String cover = result.getAttribute("cover");
				// String mediaTitle = result.getAttribute("title");
				// String year = result.getAttribute("year");
				// String director = result.getAttribute("director");
				// String rating = result.getAttribute("rating");
				// String details = result.getAttribute("details");
				
				// resultJSON = "{\"cover\":\"" + cover + "\", \"title\":\"" + mediaTitle;
				// resultJSON += "\", \"year\":\"" + year; 
				// resultJSON += "\", \"director\":\"" + director;
				// resultJSON += "\", \"details\":\"" + details + "\"},";
				// arrayString += resultJSON;
			// }
			// arrayString += "]";	
			// jsonString = jsonString + arrayString + "}}";
			// //responseText = jsonString;

			// out.print(jsonString);
		
		// } catch (ParserConfigurationException e) {
		// } catch (SAXException e) {
		// }

	}
}