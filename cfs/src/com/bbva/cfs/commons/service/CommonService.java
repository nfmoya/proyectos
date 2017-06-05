package com.bbva.cfs.commons.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.dao.DaoFactory;
import com.bbva.cfs.commons.dao.DaoFactoryManager;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.Constants;

public class CommonService {

	/**
	 * <p>
	 * The <code>Log</code> instance for this module.
	 * </p>
	 */
	protected Log log = LogFactory.getLog(Constants.PACKAGE_COMMON);

	protected DaoFactory daoFactory;

	protected Result result;

	public CommonService(IWebClient iWebClient) {
		daoFactory = DaoFactoryManager.getInstance(iWebClient);
		this.result = new Result();
	}

	protected DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
