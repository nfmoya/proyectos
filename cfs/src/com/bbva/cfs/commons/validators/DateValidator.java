package com.bbva.cfs.commons.validators;

import java.text.SimpleDateFormat;

/**
 * 
 * @author xah1257
 * 
 * Validador de fecha, checkea los siguientes puntos:<br>
 * <ul>
 * <li>Valida que el objeto sea un Date</li>
 * <li>Que cumpla con un formato predeterminado</li>
 * <li>TODO: es posible validar rangos de fecha(Opcional)</li>
 * </ul>
 * 
 */
public class DateValidator {

	private static String DATE_FORMAT = "DD/mm/yy";

	private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

}
