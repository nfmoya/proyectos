/**
 * 
 */
package com.bbva.cfs.commons.exceptions;

/**
 * @author xah1257
 * 
 * <class>GlobalGrantException</class> se encarga de manejar las excepciones a
 * nivel de Persmisos de la aplicación.
 * 
 */
public class GlobalGrantException extends Exception {
	
	private Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * 
	 */
	public GlobalGrantException() {
		// TODO Auto-generated constructor stub
	}
	
	public GlobalGrantException(Long code, String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

	/**
	 * @param message
	 */
	public GlobalGrantException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public GlobalGrantException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GlobalGrantException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
