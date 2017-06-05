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
public class GlobalActionException extends Exception {
	
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
	public GlobalActionException() {
		// TODO Auto-generated constructor stub
	}
	
	public GlobalActionException(Long code, String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

	/**
	 * @param message
	 */
	public GlobalActionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public GlobalActionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GlobalActionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
