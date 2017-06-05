/**
 * 
 */
package com.bbva.cfs.commons.exceptions;

/**
 * @author xah1257
 * 
 * <class>GlobalActionException</class> se encarga de manejar las excepciones a
 * nivel de Acciones de la aplicación.
 * 
 */
public class NotLoggedException extends Exception {
	
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
	public NotLoggedException() {
		// TODO Auto-generated constructor stub
	}
	
	public NotLoggedException(Long code, String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

	/**
	 * @param message
	 */
	public NotLoggedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NotLoggedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotLoggedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
