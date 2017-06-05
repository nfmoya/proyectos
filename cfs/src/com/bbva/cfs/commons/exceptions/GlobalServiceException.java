/**
 * 
 */
package com.bbva.cfs.commons.exceptions;

/**
 * @author xah1257
 * 
 * <class>GlobalServiceException</class> va a manejar todos las excepciones que
 * se identifiquen dentro de la capa de servicios.
 * 
 * TODO: Si es necesario armar jerarquía de excepciones para llevar el control
 * de las mismas.
 */
public class GlobalServiceException extends Exception {
	
	private Long code;
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public GlobalServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GlobalServiceException(Long code, String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}
	public GlobalServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GlobalServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public GlobalServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
