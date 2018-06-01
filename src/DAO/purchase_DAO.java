package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;
import interfaces.purchase_inter;

public class purchase_DAO implements purchase_inter {
	
	public ArrayList<Object> all()
	{
		ArrayList<Object> items =new ArrayList<Object>();
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode,ssn,date,paypalnumber FROM purchases";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  items.add(rs.getInt(1));
		    	  items.add(rs.getString("ssn"));
		    	  items.add(rs.getString("date"));
		    	  items.add(rs.getString("paypalnumber"));
		    }
		    
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
		return items;
	}
	
	public void deleteTuple(int productcode,String ssn,String date)
	{
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement("delete FROM purchases where productcode=? and ssn=? and date=?;");
			st.setInt(1, productcode);
			st.setString(2,ssn);
			st.setString(3,date);
			st.executeUpdate();
		
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}

	public void addTuple( String productcode,String paypalnumber, String hour, String date_field, String ssn_field) 
	{
		
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement(""
					+ "INSERT INTO purchases (productcode, ssn, date, paypalnumber) VALUES(?,?,?,?);");
			st.setInt(1,Integer.parseInt(productcode));
			st.setString(2,ssn_field);
			st.setString(3,date_field+" "+hour);
			st.setString(4, paypalnumber);		
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}
	public boolean check_purchase(int code,String SSN,String date) {
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode FROM purchases where productcode=? and ssn=? and date=?;";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  if(rs.getInt("productcode")!=0)
						return true;
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
		return false;
	}

	
	public void updateTuple(int productcode,String paypalnumber, String hour, String date_field, String ssn_field) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st;
			
			//update new storage size
			st  = (PreparedStatement) conn.prepareStatement("update purchases SET productcode=?, ssn=?, date=?, paypalnumber=?;");
			st.setInt(1, productcode);
			st.setString(2, paypalnumber);
			st.setString(3, hour);
			st.setString(4, date_field);
			st.setString(5, ssn_field);
			st.executeUpdate();
			
			}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}

	}
}
