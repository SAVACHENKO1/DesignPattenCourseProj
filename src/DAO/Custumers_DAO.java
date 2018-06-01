package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;
import interfaces.custumers_inter;

public class Custumers_DAO implements custumers_inter {
	public ArrayList<Object> all()
	{
		ArrayList<Object> items =new ArrayList<Object>();
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT SSN,phone,adress,city,country FROM customer;";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  items.add(rs.getString("SSN"));
		    	  items.add(rs.getString("phone"));
		    	  items.add(rs.getString("adress"));
		    	  items.add(rs.getString("city"));
		    	  items.add(rs.getString("country"));
		    	  
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
		return items;
	}

	public void deleteTuple(String ssn)
{
		
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement(
					"delete FROM customer where ssn=?;");
			st.setString(1,ssn);		
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}
	
	public void addTuple(String country, String SSN, String phone, String adress, String city) 
	{
		
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement(""
					+ "INSERT INTO customer (SSN, phone, adress, city,country) VALUES(?,?,?,?,?);");
			st.setString(1,SSN);
			st.setString(2,phone);
			st.setString(3,adress);
			st.setString(4, city);	
			st.setString(5, country);	
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}

	public boolean checkSSN(String SSN) {
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT SSN FROM customer;";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  if(rs.getString("SSN").isEmpty()==false)
						return true;
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
		return false;
	}

	
	public void updateTuple(String country, String SSN, String phone, String adress, String city) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st;
			
			//update new storage size
			st  = (PreparedStatement) conn.prepareStatement("update customer SET phone=?, adress=?, city=?, country=? where SSN=?;");
			
			st.setString(1, phone);
			st.setString(2, adress);
			st.setString(3, city);
			st.setString(4, country);
			st.setString(5, SSN);
			st.executeUpdate();
			
			}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}

	}
}
