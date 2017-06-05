package com.bbva.cfs.parameters.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * TODO:
 * @author xah1198
 *
 */
public class GetParameterTypeListService extends CommonParamService {
	private Result result;	
	private List parameterTypes;
	
	public GetParameterTypeListService(IWebClient iWebClient){
		super(iWebClient);
		this.parameterTypes = new ArrayList();
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		try {					
			log.debug("Invocación al dao ParametersDAO: getParameterTypeList()");
			result = getParamDao().getParameterTypeList(this.parameterTypes);
	
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

	public List getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(List parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
}
