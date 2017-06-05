package com.bbva.cfs.parameters.service;

import java.util.HashMap;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.service.CommonParamService;


/**
 * TODO:
 * @author xah1198
 *
 */
public class AddParameterService extends CommonParamService {
	private Result result;	
	
	private Map parameters;

	public AddParameterService(IWebClient iWebClient) {
		super(iWebClient);
		this.parameters = new HashMap();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(Long id_usuario, String id_tipo_param, String valor,Long cantCuotas) throws Exception {
		/*
		System.out.println("aaaaaa");
		System.out.println("aaaaaa");
		System.out.println("aaaaaa");
		System.out.println(id_usuario);
		System.out.println(id_tipo_param);
		System.out.println(valor);
		System.out.println(cantCuotas);
		System.out.println(cantCuotas!=null?-1:cantCuotas);
		System.out.println("aaaaaa");
		System.out.println("aaaaaa");
		System.out.println("aaaaaa");
		*/
		
		this.parameters.clear();
		this.parameters.put("pi_c_usua_alta",id_usuario);
		this.parameters.put("pi_id_tabla",id_tipo_param);
		this.parameters.put("pi_d_valor",valor==null?"":valor);
		//* nuevo
		this.parameters.put("c_cuotas",cantCuotas==null?Long.valueOf("-1"):cantCuotas);
		//this.parameters.put("c_cuotas",cantCuotas);
		//* nuevo 		
		try {					
			log.debug("Invocaci�n al dao ParametersDAO: addParameter()");
			result = getParamDao().addParameter(this.getParameters());
	
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
