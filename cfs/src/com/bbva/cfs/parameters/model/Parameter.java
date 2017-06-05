package com.bbva.cfs.parameters.model;

/**
 * @author jmanrique
 * 
 * Este objeto representa el dominio de un par�metro.
 * <class>Parameter</class>
 * 
 */
public class Parameter {

	private String parentId;
	private String parentDesc;
	private String cod;
	private String desc;
	
	/*public Parameter() {
	}*/

	public Parameter(String parentId, String parentDesc, String cod, String desc) {
		this.parentId = parentId;
		this.parentDesc = parentDesc;
		this.cod = cod;
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc - the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * @return the cod
	 */
	public String getCod() {
		return cod;
	}

	/**
	 * @param cod - the cod to set
	 */
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	/**
	 * @return the parentDesc
	 */
	public String getParentDesc() {
		return parentDesc;
	}
	
	/**
	 * @param parentDesc - the parentDesc to set
	 */
	public void setParentDesc(String parentDesc) {
		this.parentDesc = parentDesc;
	}
	
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * @param parentId - the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
