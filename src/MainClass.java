import common.factoryOfGUI;

public class MainClass {
	
	//here we start the program
	public static void main(String[] args) {
		factoryOfGUI.getGui_by_title("Employee_window");	
		
		//LoggerWrapper loggerWrapper = LoggerWrapper.getInstance();
		//loggerWrapper.myLogger.log(Level.FINE, "user started the program");
	}
		
}

