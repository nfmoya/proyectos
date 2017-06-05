package com.bbva.ar.utils.utils;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bbva.cfs.commons.model.Result;

public abstract class Excel{
	
	protected Result result;
	protected String realPath;
	protected String filename;
	
	protected abstract void setFilename(String nomFile);

	protected abstract List<String> getTitle();
		
	public abstract HSSFWorkbook doExcel() throws Exception;

	public String getFileName(){
		return this.filename;
	}
	
}
