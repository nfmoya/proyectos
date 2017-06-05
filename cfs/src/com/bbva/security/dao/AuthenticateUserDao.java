package com.bbva.security.dao;

import com.bbva.security.model.AuthenticatedUser;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface AuthenticateUserDao {

	/**
	 * TODO:
	 * @param userList
	 * @param oWebClient
	 * @return
	 */
	public void getAuthenticatedUserData(AuthenticatedUser user) throws Exception;
	
	/**
	 * TODO:
	 * @param user
	 * @throws Exception
	 */
	public void updateAuthenticatedUserData(AuthenticatedUser user) throws Exception;

	/**
	 * TODO:
	 * @param user
	 * @throws Exception
	 */
	public void saveAuthenticatedUserData(AuthenticatedUser user) throws Exception;
	
	/**
	 * TODO:
	 * @param user
	 * @throws Exception
	 */
	public void editPassword(AuthenticatedUser user) throws Exception;
}
