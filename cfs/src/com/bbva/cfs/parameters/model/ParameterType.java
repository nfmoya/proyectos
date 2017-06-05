package com.bbva.cfs.parameters.model;

/**
 * @author bsilvestre
 * 
 * Este objeto representa el dominio de un tipo de parametro.
 * <class>ParameterType</class>
 * 
 */
public class ParameterType {

	private String id;
	private String desc;
	
	public ParameterType() {
	}

	public ParameterType(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id - the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
}
