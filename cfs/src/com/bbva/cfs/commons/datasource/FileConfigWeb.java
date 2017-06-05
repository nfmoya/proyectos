package com.bbva.cfs.commons.datasource;

import java.util.Properties;

public class FileConfigWeb extends BaseConfigWeb {

	public FileConfigWeb(Properties properties, ConfigWeb config) {
		super(properties, config);
	}


	public String getFolderProc() {

		return value("urlFilesProcesados");
	
	}
	public String getFolderLogs() {

		return value("urlLogs");
	
	}

}
