package com.bbva.cfs.commons.exceptions;

/**
 * 
 * @author xah1257
 * 
 * Está clase va a manejar excepciones que puedan surgir en el DAO.
 */
public class DaoException extends Exception {
	
	private Long code;
	
	public DaoException() {
		super("Dao Exception");
	}
	
	public DaoException(Long code, String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

	public DaoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DaoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

}
