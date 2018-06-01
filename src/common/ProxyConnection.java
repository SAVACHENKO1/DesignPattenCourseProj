package common;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProxyConnection {
    
	
	//private final static doaConnection CreatedConnection = new doaConnection();
    private static Connection conn = null;
    //private static Connection instance = null;  

    
    private ProxyConnection() {
		String IP = "";
		 String PORT= "";
		 String USER = "";
		 String PASS = "";
		 String DB = "";
		 
		 try {
			 //STEP 1: Read XML settings
	         File inputFile = new File("c:/misc/credentials.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         Element Element = (Element)doc.getElementsByTagName("settings").item(0);
	         PASS = Element.getElementsByTagName("password").item(0).getTextContent();
	         IP = Element.getElementsByTagName("ip").item(0).getTextContent();
	         PORT = Element.getElementsByTagName("port").item(0).getTextContent();
			 USER  = Element.getElementsByTagName("username").item(0).getTextContent();
			 DB = Element.getElementsByTagName("db").item(0).getTextContent();
			 
	       //STEP 2: Register JDBC driver
	         Class.forName("com.mysql.jdbc.Driver").newInstance();
	         
	       //STEP 3: Open a connection
		      String url = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB + "?verifyServerCertificate=false&useSSL=false";
		      Properties info = new Properties();
		      info.setProperty("user", USER);
		      info.setProperty("password", PASS);
		      this.conn = DriverManager.getConnection(url, info);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	public static Connection getInstance()
	{
		
		//checks if connection was created
		if(conn==null){
			synchronized(ProxyConnection.class){
				if(conn==null)
					new ProxyConnection();
				}
		}
		
		//checks if connection was closed
		try {
			if(conn.isClosed()){
				synchronized(ProxyConnection.class){
					if(conn==null)
						new ProxyConnection();
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//returns connection
		return conn;
	}

}
