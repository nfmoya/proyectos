package com.bbva.cfs.commons.utils;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;

/**
 * 
 * @author xah1198
 * 
 */
public class DaoUtils {

	private static String errorDesc;
	private static int error;
	
	/**
	 * Procesa la respuesta del servicio para determinar si se trata de una salida exitosa,
	 * con errores de validación o con errores de base. 
	 * @param parameters
	 * @param result
	 * @param log
	 */
	public static void proccessServiceResponse(Map parameters, Result result, Log log) throws Exception {
		error = ((BigDecimal) parameters.get(DatabaseConstants.PO_C_ERROR)).intValue();
		errorDesc = parameters
			.get(DatabaseConstants.PO_D_ERROR) != null ? parameters
			.get(DatabaseConstants.PO_D_ERROR).toString() : "";
		if (error == ResultDatabase.SUCCESFULL.getCode().intValue()) {
			result.setErrorCode(ResultDatabase.SUCCESFULL.getCode());
			result.setErrorDesc(ResultDatabase.SUCCESFULL.getMessage());
		} else if (error == ResultDatabase.WARNING.getCode().intValue()) {
			result.setErrorCode(ResultDatabase.WARNING.getCode());
			result.setErrorDesc(parameters
					.get(DatabaseConstants.PO_D_ERROR) != null ? parameters
					.get(DatabaseConstants.PO_D_ERROR).toString() : "");
		} else if (error == ResultDatabase.VALIDATION_ERROR.getCode().intValue()) {
			result.setErrorCode(ResultDatabase.VALIDATION_ERROR.getCode());
			result.setErrorDesc(parameters
					.get(DatabaseConstants.PO_D_ERROR) != null ? parameters
					.get(DatabaseConstants.PO_D_ERROR).toString() : "");
		} else {
			throw new Exception(parameters.get(DatabaseConstants.PO_D_ERROR) != null ? 
								parameters.get(DatabaseConstants.PO_D_ERROR).toString() : "");
		}
	}

}
