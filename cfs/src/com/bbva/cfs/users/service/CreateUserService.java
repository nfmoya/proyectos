package com.bbva.cfs.users.service;

import java.util.HashMap;
import java.util.Map;


import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * TODO:
 * @author xah1198
 *
 */
public class CreateUserService extends CommonUserService {
	
	private Map parameters;
	private Long userId; 
	private Result result;
	
	/**
	 * TODO:
	 * @param iWebClient
	 * @param loggedUserId
	 * @param personaId
	 * @param userName
	 * @param ongId
	 * @param perfiles
	 */
	public CreateUserService(IWebClient iWebClient) {
		super(iWebClient);
		parameters = new HashMap();
	};
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(Long  loggedUserId, String userName, String perfiles, String estado) throws Exception {
		parameters.clear();
		parameters.put("pi_id_usuario_logg",loggedUserId);
		parameters.put("pi_d_user",userName);
		if(estado.equalsIgnoreCase("") || estado == null)
			parameters.put("pi_estado_usuario", estado);
		else
			parameters.put("pi_estado_usuario", estado.equalsIgnoreCase("1") ? "ACTIVO" : "INACTIVO");
		parameters.put("pi_l_perfiles",perfiles);
		parameters.put("pi_clave_defecto",AuthenticationUtils.encryptPassword(userName));
		
		try {					
			log.debug("Invocaci�n al dao UserDAO: createUser()");
			result = getUserDao().createUser(this.parameters, this.userId);
	
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}

