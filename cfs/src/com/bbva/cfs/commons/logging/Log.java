package com.bbva.cfs.commons.logging;

import org.apache.commons.logging.LogFactory;



public class Log  {
	
	
	static private org.apache.commons.logging.Log log = null;
	 
    public static void log(String category, String szRequestUri) { 

        if (log == null) {
        	log = LogFactory.getLog(category + szRequestUri);
        }
        
    	
    }

    public static void debug (String arg0){
    	
    	log.debug(arg0);
    }
    
    public static void debug (String arg0, Throwable arg1){
    	
    	log.debug(arg0, arg1);
    }
    
    public static void info (String arg0){
    	
    	log.info(arg0);
    }
    
    public static void info (String arg0, Throwable arg1){
    	
    	log.info(arg0, arg1);
    }
    
    public static void error (String arg0){
    	
    	log.error(arg0);
    }
    
    public static void error (String arg0, Throwable arg1){
    	
    	log.error(arg0, arg1);
    }
    

    
    


}
