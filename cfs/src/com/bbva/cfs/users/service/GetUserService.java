package com.bbva.cfs.users.service;

import java.util.HashMap;
import java.util.Map;


import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.users.model.User;

/**
 * TODO:
 * @author xah1198
 *
 */
public class GetUserService extends CommonUserService {
	
	private Map parameters;
	private User user;
	private Result result;
	
	public GetUserService(IWebClient iWebClient) {
		super(iWebClient);
		this.parameters = new HashMap();
	};
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(Long  loggedUserId, Long personaId) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_usuario_loggeado",loggedUserId);
		this.parameters.put("pi_id_persona",personaId);
		this.user = new User();
		try {					
			log.debug("Invocación al dao UserDAO: getUserData()");
			result = getUserDao().getUserData(this.parameters, this.user);
	
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}

