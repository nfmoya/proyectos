package com.bbva.cfs.commons.datasource;

import java.util.Properties;
import com.bbva.cfs.commons.datasource.BaseConfigWeb;
import com.bbva.cfs.commons.datasource.ConfigWeb;

public class BaseConfigWeb {
	protected Properties properties;
	protected ConfigWeb c;
	
	public BaseConfigWeb(Properties properties, ConfigWeb c){
		this.properties = properties;
		this.c = c;
	}
	
	public void setProperty(String key, String value){
		this.properties.put(key, value);
	}
	
	protected String value (String key){
		String v = this.properties.getProperty(key);
		
		if (v == null){
			String message = "Property "
				+ key
				+ " could not be found in "
				+ this.getClass()
				+ " .Check the property file with a similar name for the property value. ";
		}
		return v;
	}
	
	protected String valueAfterBasepath(String key) {
		return c.getProjectFolder() + value(key);
	}

	protected String valueAfterWebContentPath(String key) {
		return getWebContentPath() + value(key);
	}

	protected String getWebContentPath() {
		return c.getWebContentPath();
	}

	protected boolean booleanValue(String string) {
		return Boolean.parseBoolean(value(string));
	}
	
	protected String getProperty(String key){
		return value(key);
	}
}