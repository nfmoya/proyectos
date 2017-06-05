package com.bbva.cfs.commons.datasource;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class ConfigWeb {

	private static ConfigWeb instance;
	
	/*
	 * Clases de configuracion de properties
	 */
	

	FileConfigWeb fileConfig;
	String fileSeparator = System.getProperty("file.separator");
	
	/*
	 * Variables
	 */
	
	static String basepath;
	String webContentPath;
	
	public static void setBasepath(String basepath1) {
		basepath = basepath1;
	}
	public synchronized static ConfigWeb get() throws Exception{
		if (instance == null){
			instance = new ConfigWeb();
		}
		
		return instance;
	}
	

	
	private ConfigWeb() throws Exception{
		
	
		fileConfig = new FileConfigWeb(getPropertiesFor("configFolders"),this);
	}
	
	public String getSysCfg(){
		return getProjectFolder() + File.separator+ "syscfg";
	}
	
	public String getProjectFolder(){
		return (basepath.equals("") ? "" : basepath );
	}
	
	public String getFilesFolderProc(){
//		System.out.println(getProjectFolder() + fileConfig.getFolderProc());
//		return getProjectFolder() + fileConfig.getFolderProc();
		return fileConfig.getFolderProc();
		
	}
	public String getFilesFolderLogs(){
//		System.out.println(getProjectFolder() + fileConfig.getFolderLogs());
//		return getProjectFolder() + fileConfig.getFolderLogs();
		return fileConfig.getFolderLogs();
		
	}
	
	public String getWebContentPath() {
		return webContentPath;
	}
	
	private Properties getPropertiesFor(String nameProperties) throws Exception{
		String path = getSysCfg() + File.separator + nameProperties + ".properties";
		try{
			Properties properties = new Properties();
			properties.load(new FileInputStream(path));
			return properties;
		}catch(IOException e){
			String message = "could not load property field from: " + path;
			throw new Exception(message);
		}
	}

		
	public FileConfigWeb getFileConfig() {
		return fileConfig;
	}


	
}
