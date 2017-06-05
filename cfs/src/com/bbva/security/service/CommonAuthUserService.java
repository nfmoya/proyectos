package com.bbva.security.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.service.CommonService;
import com.bbva.security.dao.AuthenticateUserDao;

/**
 * 
 * @author xah1198
 *
 */
public class CommonAuthUserService extends CommonService {

	private AuthenticateUserDao authenticateUserDao;

	public CommonAuthUserService(IWebClient iWebClient) {
		super(iWebClient);
		authenticateUserDao = daoFactory.getAuthenticateUserDao();
	}

	public AuthenticateUserDao getAuthenticateUserDao() {
		return authenticateUserDao;
	}

	public void setAuthenticateUserDao(AuthenticateUserDao authenticateUserDao) {
		this.authenticateUserDao = authenticateUserDao;
	}

}
