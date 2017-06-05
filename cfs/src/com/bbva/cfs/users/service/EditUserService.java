package com.bbva.cfs.users.service;

import java.util.HashMap;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * TODO:
 * @author xah1198
 *
 */
public class EditUserService extends CommonUserService {
	
	private Map parameters;
	
	private Result result;
	
	/**
	 * TODO:
	 * @param iWebClient
	 * @param loggedUserId
	 * @param personaId
	 * @param userId
	 * @param ongId
	 * @param perfilesIns
	 * @param perfilesDel
	 */
	public EditUserService(IWebClient iWebClient) {
		super(iWebClient);
		parameters = new HashMap();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String userName, Long loggedUserId, Long userId, 
							String perfilesIns, String perfilesDel, String estado) throws Exception {
		parameters.clear();
		parameters.put("pi_id_usuario_logg", loggedUserId);
		parameters.put("pi_id_usuario", userId);
		if(estado.equalsIgnoreCase("") || estado == null)
			parameters.put("pi_estado_usuario", estado);
		else
			parameters.put("pi_estado_usuario", estado.equalsIgnoreCase("1") ? "ACTIVO" : "INACTIVO");
		parameters.put("pi_l_perfiles_ins", perfilesIns);
		parameters.put("pi_l_perfiles_del", perfilesDel);
		parameters.put("pi_clave_defecto",AuthenticationUtils.encryptPassword(userName));
		
		try {			
			log.info("Invocaci�n al dao UserDAO: editUser()");
			result = getUserDao().editUser(this.parameters);
	
		} catch (DaoException e) {
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.error(e);
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
	
}

