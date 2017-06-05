package com.bbva.cfs.parameters.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.dao.ParametersDao;
import com.bbva.cfs.parameters.model.Parameter;

/**
 * @deprecated
 * @author xah1257
 * 
 *         TODO: hacer de está clase un lindo factory de listas de beans que se
 *         corresponden con los tipos de parámetros.
 */

public class ParameterTypeServiceHelper extends CommonService {
	private Result result;
	private ParametersDao parametersDao;

	public ParameterTypeServiceHelper(IWebClient iWebClient) {
		super(iWebClient);
		this.parametersDao = daoFactory.getParametersDao();
	}

	public Result getParameterBeans(ParameterType parameterType,
			List parameterListGeneric) throws Exception {
		
		List parameters = new ArrayList();

		try {
			result = parametersDao.getParameterList(parameterType.getId(), "", "", parameters);

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

}
