package com.gefa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ConfigurationFiles {
    private String filePath;
    Properties properties;
    File f1 = null;
    private static ConfigurationFiles instance;
    
    private ConfigurationFiles() throws Exception {
        properties = new Properties();
        
        //String executePath  = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
           f1 = new File( getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException ex) {
            Logger.getLogger(ConfigurationFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path2 = f1.getParent();


        //JOptionPane.showMessageDialog(null, executePath);
        filePath   = path2 + File.separator+"config.properties";
        try{
            FileInputStream file = new FileInputStream(filePath);
            properties.load(file);
        }
        catch(IOException e){
        	e.printStackTrace();
            System.out.println("no se encontro archivo");
        }
    }
    
    
    public synchronized static ConfigurationFiles get() throws Exception {
		if (instance == null) {
			instance = new ConfigurationFiles();
		}
		return instance;
	}

    public void setProperties(String prop, String data){
        try{
            properties.setProperty(prop, data);
            properties.store(new FileOutputStream(filePath), null);
        }
        catch(IOException e){
            e.printStackTrace();
        }   
    }
   
    public String getPathProperties(){
    	return f1.getParent()+ File.separator;
    }
    
    public String getProperties(String prop){
        return properties.getProperty(prop);
    }
}