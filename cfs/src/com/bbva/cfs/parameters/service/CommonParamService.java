package com.bbva.cfs.parameters.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.parameters.dao.ParametersDao;

/**
 * 
 * @author xah1198
 *
 */
public class CommonParamService extends CommonService {

	private ParametersDao paramDao;

	public CommonParamService(IWebClient iWebClient) {
		super(iWebClient);
		paramDao = daoFactory.getParametersDao();
	}

	public ParametersDao getParamDao() {
		return paramDao;
	}

	public void setParamDao(ParametersDao paramDao) {
		this.paramDao = paramDao;
	}
}
