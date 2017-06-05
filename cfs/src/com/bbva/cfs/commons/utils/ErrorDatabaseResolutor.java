package com.bbva.cfs.commons.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author xah1257
 * 
 * Est� clase se comporta como un mapeador de mensajes, recibe como par�metro el
 * c�digo de error de base de datos y devuelve un mensaje personalizado que ser�
 * mostrado al usuario.
 */
public class ErrorDatabaseResolutor {

	private static String ERROR_MESSAGE_KEY_VALIDATION = "database.error-message.validation";

	private static String ERROR_MESSAGE_KEY_CRITICAL = "database.error-message.critical";

	/**
	 * M�todo que lee los messages properties de un archivo.
	 * 
	 * TODO: Logear las posibles excepciones
	 */
	private static Properties getProperties() {
		Properties prop = new Properties();

		try {
			prop.load(ResourceUtils.getRealPathResourceStream(
					"/error-messages.properties", ResourceUtils.CFG_DIRECTORY));
		} catch (FileNotFoundException e) {

			// TODO: Capturar est� excepci�n y loguearla
		} catch (IOException io) {

			// TODO: Capturar est� excepci�n y loguearla
		} catch (Exception e) {

			// TODO: Capturar est� excepci�n y loguearla
		}
		return prop;
	}

	public static String getErrorDatabaseMessage(int codeError) {
		Properties props = getProperties();

		String message = "";
		switch (codeError) {
		case 1:
			message = props.getProperty(ERROR_MESSAGE_KEY_VALIDATION);
			break;
		default:
			message = props.getProperty(ERROR_MESSAGE_KEY_CRITICAL);
			break;
		}
		return message;
	}

}
