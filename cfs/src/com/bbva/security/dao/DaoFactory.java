package com.bbva.security.dao;

import com.bbva.security.dao.AuthenticateUserDao;

/**
 * @author xah1198
 * 
 */
public abstract class DaoFactory {

	public abstract AuthenticateUserDao getAuthenticateUserDao();
}
