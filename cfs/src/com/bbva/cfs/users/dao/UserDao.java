package com.bbva.cfs.users.dao;

import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.users.model.User;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface UserDao {

	/**
	 * TODO:
	 * @param userList
	 * @param oWebClient
	 * @return
	 */
	public Result getUserData(Map parameters,
							User user) throws Exception;
	
	/**
	 * TODO:
	 * @param parameters
	 * @param userId
	 * @return
	 */
	public Result createUser( Map parameters,
			  Long userId	) throws Exception;
	
	/**
	 * TODO:
	 * @param parameters
	 * @param userId
	 * @return
	 */
	public Result editUser( Map parameters ) throws Exception;

}
