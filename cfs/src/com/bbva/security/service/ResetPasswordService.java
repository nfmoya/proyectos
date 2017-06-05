package com.bbva.security.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.model.AuthenticatedUser;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * Blanquear password
 * @author xah1198
 *
 */
public class ResetPasswordService extends CommonAuthUserService {
	private AuthenticatedUser user;
	
	public ResetPasswordService(IWebClient iWebClient) {
		super(iWebClient);
	};
	
	/**
	 * Llama al dao y ejecuta el blanqueo de la clave.
	 * @param userName
	 * @return
	 */
	public boolean execute(String userName) {
		try {					
			user = new AuthenticatedUser();
			user.setUsername(userName);
			user.setEnabledUser("S");
			user.setStatus("I");
			user.setPassword1("");
			user.setPassword2("");
			user.setPassword3("");
			user.setPassword4("");
			user.setFailedAttempts(0);
			user.setPassword(AuthenticationUtils.encryptPassword(userName));
			
			log.debug("Invocación al dao AuthenticateUserDao: saveAuthenticatedUserData()");
			getAuthenticateUserDao().saveAuthenticatedUserData(user);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
}

