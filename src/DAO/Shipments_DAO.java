package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;
import interfaces.Shipment_inter;

public class Shipments_DAO implements Shipment_inter{
	
	//get all tuples
	public ArrayList<Object> all()
	{
		ArrayList<Object> items =new ArrayList<Object>();
		Statement stmt = null;
		java.sql.Connection conn = ProxyConnection.getInstance();
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT productcode,quantity,duedate,supllier,arived FROM shipments";
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
		         //Retrieve by column name
		    	  items.add(rs.getInt(1));
		    	  items.add(rs.getString("quantity"));
		    	  items.add(rs.getString("duedate"));
		    	  items.add(rs.getString("supllier"));
		    	  items.add(rs.getInt(5));
		    }
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
			      
		return items;
	}

	public void deleteTuple(int productCode, String supllier, String date ) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement("delete FROM shipments where productcode=? and supllier=? and duedate=?;");
			st.setInt(1, productCode);
			st.setString(2,supllier);
			st.setString(3,date);
			st.executeUpdate();
		
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
		
	}

	public void addTuple(int code, int quantity, String date, String supllier) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st  = (PreparedStatement) conn.prepareStatement("INSERT INTO shipments (productcode, quantity, duedate, supllier) VALUES(?,?,?,?);");
			st.setInt(1,code);
			st.setInt(2,quantity);
			st.setString(3,date);
			st.setString(4, supllier);		
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
	}

	
	public void updateShipmentArrival(int productCode, String date, String supllier,int Shipment_quantity) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st;
			
			//read how much in storage
			int Existing_Quantity=0;
			st  = (PreparedStatement) conn.prepareStatement("SELECT quantity FROM product where productcode=?;");
			st.setInt(1, productCode);
			ResultSet rs = st.executeQuery(); 
			while(rs.next())
				Existing_Quantity=rs.getInt("quantity");
			
			//update new storage size
			st  = (PreparedStatement) conn.prepareStatement("update product SET quantity=? where productcode=?;");
			st.setInt(1, productCode);
			st.setInt(2,Shipment_quantity + Existing_Quantity);
			st.executeUpdate();
			
			//update shipment DB
			st  = (PreparedStatement) conn.prepareStatement("update shipments SET arived=1 where productcode=? and duedate=? and supllier=?;");
			st.setInt(1, productCode);
			st.setString(2, date);
			st.setString(3, supllier);
			st.executeUpdate();
			
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
		
	}

	

	public boolean shipmentExists(int code ,String date, String supllier) {
		java.sql.Connection conn = ProxyConnection.getInstance();
		try {
			PreparedStatement  st;
			
			//read how much in storage
			st  = (PreparedStatement) conn.prepareStatement("SELECT productcode FROM product where productcode=? and duedate=? and supllier=?;");
			st.setInt(1, code);
			st.setString(2, date);
			st.setString(3, supllier);
			ResultSet rs = st.executeQuery(); 
			while(rs.next())
				if(rs.getInt("productcode")>=0)
					return true;
		    
		}
		catch (SQLException e) 
		{
		   	e.printStackTrace();
		}
		
		return false;
	}

					
}
