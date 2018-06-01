package interfaces;

import java.util.ArrayList;

public interface custumers_inter {

		public ArrayList<Object> all();
		public void deleteTuple(String ssn);
		public void addTuple(String country, String SSN, String phone, String adress, String city) ;
		public boolean checkSSN(String SSN);
		public void updateTuple(String country, String SSN, String phone, String adress, String city);
}
