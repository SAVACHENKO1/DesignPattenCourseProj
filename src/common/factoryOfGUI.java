package common;

import java.util.HashMap;

import javax.swing.JFrame;

import GUI.Employee_window;
import GUI.Products_window;
import GUI.custumers_window;
import GUI.purchase_window;
import GUI.supplies_window;

public class factoryOfGUI {
	private static HashMap<String,JFrame> available = new HashMap<String,JFrame>(); 
	private static JFrame currFrame =null;
	public static void getGui_by_title(String title)
	{
		JFrame inStore = available.get(title);
		if (inStore==null)
		switch( title){
			case "Employee_window":
				inStore=new Employee_window();
				available.put(title, inStore);
				break;
				
			case "purchase_window":
				inStore=new purchase_window();
				available.put(title, inStore);
				available.get("Employee_window").setVisible(false);
				break;
				
			case "custumers_window":
				inStore=new custumers_window();
				available.put(title, inStore);
				available.get("Employee_window").setVisible(false);
				break;
				
			case "supplies_window":
				inStore=new supplies_window();
				available.put(title, inStore);
				available.get("Employee_window").setVisible(false);
				break;
				
			case "products_window":
				inStore=new Products_window();
				available.put(title, inStore);
				available.get("Employee_window").setVisible(false);
				break;
		}
		
		if(currFrame != null){
			currFrame.setVisible(false);
			inStore.setLocationRelativeTo(currFrame);
		}
		currFrame=inStore;
		inStore.setVisible(true);
	}
}
