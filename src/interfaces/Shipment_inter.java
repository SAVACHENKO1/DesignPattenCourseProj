package interfaces;

import java.util.ArrayList;

public interface Shipment_inter {
		public ArrayList<Object> all();
		
		public void deleteTuple(int productCode, String date, String supllier);
		
		public void addTuple(int code, int quantity, String date, String supllier);
			
		public void updateShipmentArrival(int productCode, String date, String supllier,int Shipment_quantity);
		
		public boolean shipmentExists(int code ,String date, String supllier);

}
