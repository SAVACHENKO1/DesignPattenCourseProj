package interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;

public interface products_inter {
	
	public ArrayList<Object> all();
	
	//static because all other needs access
	//public ArrayList<Object> get_names(ArrayList<Object> names);
	
	public void addTuple(String company, String name, String description, float price, Integer quantity);
	
	public void deleteTuple( int productcode); 
	
	public boolean check_code(int code);
	
	public void updateTuple(int code,String company, String name, String description, float price, Integer quantity);
	
}
