package com.bbva.cfs.users.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.users.dao.UserDao;

/**
 * 
 * @author xah1198
 *
 */
public class CommonUserService extends CommonService {

	private UserDao userDao;

	public CommonUserService(IWebClient iWebClient) {
		super(iWebClient);
		userDao = daoFactory.getUserDao();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDAO) {
		this.userDao = userDAO;
	}

}
