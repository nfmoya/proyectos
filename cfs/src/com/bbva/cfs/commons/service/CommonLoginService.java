package com.bbva.cfs.commons.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.dao.LoginDao;

/**
 * 
 * @author xah1198
 *
 */
public class CommonLoginService extends CommonService {

	private LoginDao loginDao;

	public CommonLoginService(IWebClient iWebClient) {
		super(iWebClient);
		loginDao = daoFactory.getLoginDao();
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDAO) {
		this.loginDao = loginDAO;
	}
}
