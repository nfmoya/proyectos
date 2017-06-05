package com.bbva.cfs.commons.service;

import java.util.HashMap;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;

import com.bbva.cfs.commons.model.Session;

import com.bbva.cfs.commons.service.CommonLoginService;
import com.bbva.cfs.commons.utils.ResultDatabase;
/**
 * TODO:
 * @author xah1198
 *
 */
public class InitSessionService extends CommonLoginService {

	private Session session;
	private Result result;
	private Map parameters;
	
	public InitSessionService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap();
	}
	
	
	public Result execute(String username) throws Exception {
		parameters.clear();
		parameters.put("d_user", username);
		
		try {				
			log.debug("Invocación al dao LoginDAO: getSession()");
			
			result = getLoginDao().getSession(parameters);
			this.session = (Session)parameters.get("session");
	
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

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
