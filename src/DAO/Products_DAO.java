package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;
import interfaces.products_inter;

public class Products_DAO implements products_inter {
	public ArrayList<Object> all()
	{
		ArrayList<Object> items =new ArrayList<Object>();
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode,name,company,description,price,quantity FROM product";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  items.add(rs.getInt(1));
		    	  items.add(rs.getString("name"));
		    	  items.add(rs.getString("company"));
		    	  items.add(rs.getString("description"));
		    	  items.add(rs.getFloat(5));
		    	  items.add(rs.getFloat(6));
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
		return items;
	}
	public static ArrayList<Object> get_names(ArrayList<Object> names)
	{
		ArrayList<Object> items =new ArrayList<Object>();
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode,name FROM product";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  int code= rs.getInt(1);
		    	  String name = rs.getString("name");
		    	  for(int j=0;j<names.size();j++)
		    		{
		    			if(code == (int)names.get(j))
		    			{
		    				items.add(code);
		    			    items.add(name);
		    			}
		    	  }
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
		return items;
	}
	
	public void addTuple(String company, String name, String description, float price, Integer quantity) 
	{
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement(
					"INSERT INTO product (company, name, description,price,quantity) VALUES(?,?,?,?,?);");
			
			st.setString(2,name);
			st.setString(1,company);
			st.setString(3,description);
			st.setFloat(4,price);
			st.setInt(5,quantity);
			st.executeUpdate();
		
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}

	public void deleteTuple( int productcode) 
	{
		
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement(
					"delete FROM product where productcode=?;");
			st.setInt(1,productcode);		
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
	}
	public boolean check_code(int code) {
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode FROM product;";
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

	
	public void updateTuple(int code,String company, String name, String description, float price, Integer quantity) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st;
			
			//update new storage size
			st  = (PreparedStatement) conn.prepareStatement("update product SET name=?,company=?,description=?,price=?,quantity=? where productcode=?;");
			st.setString(1, name);
			st.setString(2, description);
			st.setFloat(3, price);
			st.setInt(4, quantity);
			st.setInt(5, code);
			st.executeUpdate();
			
			}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}

	}
}
	

