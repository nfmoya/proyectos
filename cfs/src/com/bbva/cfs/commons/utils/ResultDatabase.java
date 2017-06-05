/** Cambiado por bsilvestre el 21 de Noviembre del 2011 */
package com.bbva.cfs.commons.utils;

/**
 * 
 * @author xah1257
 * 
 *         Enumeración que representa un resultado ya sea exitoso, érroneo o el
 *         otro caso que se podría presentar es haya un error de conexión vía
 *         SAM
 * 
 */
public class ResultDatabase {
	
	private final Long code;
	private final String message;
	
	private ResultDatabase(Long code, String message){
		this.code = code;
		this.message = message;
	}
	
	public static final ResultDatabase SUCCESFULL = 
			new ResultDatabase(Long.valueOf("0"), "Grabado exitosamente");
	public static final ResultDatabase WARNING = 
			new ResultDatabase(Long.valueOf("1"), "Error de Validación");
	public static final ResultDatabase VALIDATION_ERROR = 
		new ResultDatabase(Long.valueOf("2"), "Error de Validación");
	public static final ResultDatabase FATAL_ERROR = 
			new ResultDatabase(Long.valueOf("3"), "Ha ocurrido un error en la base de datos.");
	public static final ResultDatabase SAM_CONECTION_ERROR = 
			new ResultDatabase(Long.valueOf("4"), "Se ha producido un error. Contacte con el Administrador del Sistema.");
	public static final ResultDatabase SERVICE_ERROR = 
			new ResultDatabase(Long.valueOf("5"), "El servicio no se encuentra disponible.");
	public static final ResultDatabase ACTION_ERROR = 
			new ResultDatabase(Long.valueOf("6"), "El servicio no se encuentra disponible.");
	
	public Long getCode() {
	    return code;
	}
	
	public String getMessage() {
	    return message;
	}

}
