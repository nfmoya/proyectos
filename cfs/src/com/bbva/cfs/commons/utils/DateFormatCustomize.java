/**
 * 
 */
package com.bbva.cfs.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xah1257
 * 
 * Contiene patrones de formato de fechas que se manejan:<br>
 * 1-.Cuando se requiere pasar un parámetro u obtener una fecha al llamar a un
 * stored procedure en el cual en lugar de manejar datetime y smalldatetime, se
 * pasará un varchar con un formato predefinido en está clase.<br>
 * 2-.a nivel de vista
 * 
 * 
 * <class>PatternDateAndTime</class>
 */
public class DateFormatCustomize {

	private static SimpleDateFormat formatter;

	// Formato de fecha se usa para buscar una lista de registros que filtren
	// por fecha
	public static String DB_PATTERN_DATE_FORMAT_SHORT = "yyyyMMdd";

	// Formato de fecha se usa al insertar/editar un registro que tenga un campo
	// fecha.
	public static String DB_PATTERN_DATE_FORMAT_MEDIUM = "yyyy/MM/dd HH:mm";

	// Formato de fecha que se usará para mostrar en la vista
	public static String VIEW_PATTERN_DATE_FORMAT_SHORT = "dd/MM/yyyy";

	// Formato de fechas que requieran especificar hora y minuto
	public static String VIEW_PATTERN_DATE_FORMAT_MEDIUM = "dd/MM/yyyy HH:mm";

	// Formato de fecha recibido desde la base de datos
	public static String DB_PATTERN_DATE_FORMAT_RECEIVED = "yyyy-MM-dd HH:mm";
	
//	 Formato de fecha recibido desde la base de datos
	public static String DB_PATTERN_DATE_FORMAT_LONG = "yyyy/MM/dd HH:mm:ss";
	
	public static Date parseDateStrToDateShort(String dateStr)
			throws ParseException {
		formatter = new SimpleDateFormat(DB_PATTERN_DATE_FORMAT_SHORT);
		return formatter.parse(dateStr);
	}

	public static Date parseDateStrToDateLong(String dateStr)
			throws ParseException {
		formatter = new SimpleDateFormat(DB_PATTERN_DATE_FORMAT_LONG);
		return formatter.parse(dateStr);
	}

	public static String formatDateToStrFormatShortDB(Date date) {
		formatter = new SimpleDateFormat(DB_PATTERN_DATE_FORMAT_SHORT);
		return formatter.format(date);
	}

	public static String formatDateToStrFormatShort(Date date) {
		formatter = new SimpleDateFormat(VIEW_PATTERN_DATE_FORMAT_SHORT);
		return formatter.format(date);
	}

	public static Date parseDateStrToDateShortView(String dateStr)
			throws ParseException {
		formatter = new SimpleDateFormat(VIEW_PATTERN_DATE_FORMAT_SHORT);
		return formatter.parse(dateStr);
	}
}
