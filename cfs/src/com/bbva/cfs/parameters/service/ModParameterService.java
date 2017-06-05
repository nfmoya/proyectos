package com.bbva.cfs.parameters.service;

import java.util.HashMap;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

import com.bbva.cfs.parameters.service.CommonParamService;

/**
 * TODO:
 * @author xah1198
 *
 */
public class ModParameterService extends CommonParamService {
	private Result result;	
	
	private Map parameters;

	public ModParameterService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(Long id_usuario, String codigo, String tabla, String valor) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_parametro",codigo);
		this.parameters.put("pi_id_tabla",tabla);
		this.parameters.put("pi_d_valor",valor==null?"":valor);
		this.parameters.put("pi_id_usuario",id_usuario);
		try {					
			log.debug("Invocaci�n al dao ParametersDAO: modParameter()");
			result = getParamDao().modParameter(this.getParameters());
	
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
	
	
}
