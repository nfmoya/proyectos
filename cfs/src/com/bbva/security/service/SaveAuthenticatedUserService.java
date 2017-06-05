package com.bbva.security.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.model.AuthenticatedUser;

/**
 * TODO:
 * @author xah1198
 *
 */
public class SaveAuthenticatedUserService extends CommonAuthUserService {
	
	private AuthenticatedUser user;
	
	public SaveAuthenticatedUserService(IWebClient iWebClient) {
		super(iWebClient);
	};
	
	/**
	 * TODO:
	 * @return
	 */
	public void execute(AuthenticatedUser user) throws Exception {
		try {					
			log.debug("Invocación al dao UserDAO: getUserData()");
			getAuthenticateUserDao().saveAuthenticatedUserData(user);
			
		} catch (Exception e) {
			/*TODO: ESTA REPITIENDOSE SIN SENTIDO... REVISAR*/
			log.debug(e);
			throw new Exception();
		}
	}
	
}

