package common;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerClass {
public static final Logger myLogger = Logger.getLogger("Test");
private static LoggerClass instance = null;  

 public static LoggerClass getInstance() {  
    if(instance == null) {  
        prepareLogger();  
        instance = new LoggerClass ();  
    }  
    return instance;  
 }  

private static void prepareLogger() {
try {
	
   FileHandler myFileHandler = new FileHandler("c:/misc/myLogFile.log", true);  
   myFileHandler.setFormatter(new SimpleFormatter());  
   myLogger.addHandler(myFileHandler);  
   myLogger.setUseParentHandlers(false);  
   myLogger.setLevel(Level.ALL);
} catch (Exception e) {
	e.printStackTrace();
	throw new RuntimeException("Problems with creating the log"); 
}
}    
} 
