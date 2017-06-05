package com.bbva.cfs.commons.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.dao.CommonDao;

/**
 * 
 * @author xah1198
 *
 */
public class CommonComService extends CommonService {

	private CommonDao commonDao;

	public CommonComService(IWebClient iWebClient) {
		super(iWebClient);
		commonDao = daoFactory.getCommonDao();
	}

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDAO) {
		this.commonDao = commonDAO;
	}
}
