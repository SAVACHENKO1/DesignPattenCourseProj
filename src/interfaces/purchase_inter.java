package interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.ProxyConnection;

public interface purchase_inter {
	public ArrayList<Object> all();
	public void deleteTuple(int productcode,String ssn,String date);
	public void addTuple( String productcode,String paypalnumber, String hour, String date_field, String ssn_field);
	public boolean check_purchase(int code,String SSN,String date);
	public void updateTuple(int productcode,String paypalnumber, String hour, String date_field, String ssn_field);
}
