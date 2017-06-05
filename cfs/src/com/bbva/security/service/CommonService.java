package com.bbva.security.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.dao.DaoFactory;
import com.bbva.security.dao.DaoFactoryManager;

public class CommonService {

	protected Log log = LogFactory.getLog("com.bbva.common");

	protected DaoFactory daoFactory;

	public CommonService(IWebClient iWebClient) {
		daoFactory = DaoFactoryManager.getInstance(iWebClient);
	}

	protected DaoFactory getDaoFactory() {
		return daoFactory;
	}
}
