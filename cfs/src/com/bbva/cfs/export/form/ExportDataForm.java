/**
 * @author david
 * 
 * Clase que contiene los datos de la grilla a exportar
 * 
 */
package com.bbva.cfs.export.form;

import org.apache.struts.validator.ValidatorForm;

public class ExportDataForm extends  ValidatorForm{

	private static final long serialVersionUID = -7169994441050076131L;
	
	private String html;
	private String nameFile;
	private String fileType;
	private String filePath;
	
	
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the nameFile
	 */
	public String getNameFile() {
		return nameFile;
	}
	/**
	 * @param nameFile the nameFile to set
	 */
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

}
