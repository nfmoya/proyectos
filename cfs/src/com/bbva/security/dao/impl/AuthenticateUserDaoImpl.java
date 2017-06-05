package com.bbva.security.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;

import com.bbva.common.config.SamConfig;
import com.bbva.security.dao.AuthenticateUserDao;
import com.bbva.security.model.AuthenticatedUser;


/**
 * 
 * @author xah1198
 *
 */
public class AuthenticateUserDaoImpl implements AuthenticateUserDao {
	
	static final org.apache.commons.logging.Log log = LogFactory.getLog(AuthenticateUserDaoImpl.class);

	private SAMWebClient samWebClient;
	
	public AuthenticateUserDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
	}
	
	/**
	 * Retorna una lista de de usuarios.
	 */
	public void getAuthenticatedUserData(AuthenticatedUser user) throws Exception {
		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("pi_usu_d_user", user.getUsername());
		try {
			
			SamConfig.getSam().execute("TM_DBSECURITY.SECURITY_GET_USER",
			getSamWebClient().getSamContext(), parameters);        
			
			int error = ((BigDecimal) parameters.get("po_c_error")).intValue();
			String errorDesc = parameters.get("po_d_error") != null ? parameters.get("po_d_error").toString() : "";
			
			/*Aparentemente no tiene validaciones desde la base.*/
			
			ArrayList dynaListResult = (ArrayList) parameters.get("cursor");
			
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();
				
				user.setUsername(dynaBean.get("usu_d_user") != null? dynaBean.get("usu_d_user").toString() : "");
				user.setEnabledUser(dynaBean.get("usu_habilitado") != null? dynaBean.get("usu_habilitado").toString() : "");
				user.setStatus(dynaBean.get("usu_estado") != null? dynaBean.get("usu_estado").toString() : "");
				user.setLastAccess(dynaBean.get("usu_ult_acceso") != null? dynaBean.get("usu_ult_acceso").toString() : "");
				user.setAccessHour(dynaBean.get("usu_hora_acceso") != null? dynaBean.get("usu_hora_acceso").toString() : "");
				user.setPassword1(dynaBean.get("usu_clave1") != null? dynaBean.get("usu_clave1").toString() : "");
				user.setPassword2(dynaBean.get("usu_clave2") != null? dynaBean.get("usu_clave2").toString() : "");
				user.setPassword3(dynaBean.get("usu_clave3") != null? dynaBean.get("usu_clave3").toString() : "");
				user.setPassword4(dynaBean.get("usu_clave4") != null? dynaBean.get("usu_clave4").toString() : "");
				user.setFailedAttempts(dynaBean.get("usu_int_fallidos") != null? Long.valueOf(dynaBean.get("usu_int_fallidos").toString()).intValue() : 0);
				user.setPassword(dynaBean.get("usu_clave") != null? dynaBean.get("usu_clave").toString() : "");
			}
		} catch (Exception e) {
			log.error(e);
			/*TODO: CAPAZ DEBER�A SETAR EL ESTADO DEL USUARIO O ALGO.*/
			throw new Exception();
		}
	}
	
	public void updateAuthenticatedUserData(AuthenticatedUser user) throws Exception {
		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("pi_usu_d_user", user.getUsername());
		parameters.put("pi_usu_habilitado", user.getEnabledUser());
		parameters.put("pi_usu_estado", user.getStatus());
		parameters.put("pi_usu_ult_acceso", user.getLastAccess());
		parameters.put("pi_usu_hora_acceso", user.getAccessHour());
		parameters.put("pi_usu_clave1", user.getPassword1());
		parameters.put("pi_usu_clave2", user.getPassword2());
		parameters.put("pi_usu_clave3", user.getPassword3());
		parameters.put("pi_usu_clave4", user.getPassword4());
		parameters.put("pi_usu_int_fallidos", Long.valueOf(""+user.getFailedAttempts()));
		parameters.put("pi_usu_clave", user.getPassword());
/*
		try {
			
			SamConfig.getSam().execute("TM_DBSECURITY.SECURITY_UPDATE_USER",
			getSamWebClient().getSamContext(), parameters);        
			
			int error = ((BigDecimal) parameters.get("po_c_error")).intValue();
			String errorDesc = parameters.get("po_d_error") != null ? parameters.get("po_d_error").toString() : "";
		} catch (Exception e) {
			log.error(e);
			throw new Exception();
		}
*/	
	}
	
	public void saveAuthenticatedUserData(AuthenticatedUser user) throws Exception {
		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("pi_usu_d_user", user.getUsername());
		parameters.put("pi_usu_habilitado", user.getEnabledUser());
		parameters.put("pi_usu_estado", user.getStatus());
		parameters.put("pi_usu_clave1", user.getPassword1());
		parameters.put("pi_usu_clave2", user.getPassword2());
		parameters.put("pi_usu_clave3", user.getPassword3());
		parameters.put("pi_usu_clave4", user.getPassword4());
		parameters.put("pi_usu_int_fallidos", Long.valueOf(""+user.getFailedAttempts()));
		parameters.put("pi_usu_clave", user.getPassword());

		try {
			
			SamConfig.getSam().execute("TM_DBSECURITY.SECURITY_SAVE_USER",
			getSamWebClient().getSamContext(), parameters);        
			
			int error = ((BigDecimal) parameters.get("po_c_error")).intValue();
			String errorDesc = parameters.get("po_d_error") != null ? parameters.get("po_d_error").toString() : "";
			/*Aparentemente no tiene validaciones desde la base.*/
		} catch (Exception e) {
			log.error(e);
			/*TODO: CAPAZ DEBER�A SETAR EL ESTADO DEL USUARIO O ALGO.*/
			throw new Exception();
		}
	}
	
	public void editPassword(AuthenticatedUser user) throws Exception {
		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("pi_usu_d_user", user.getUsername());
		parameters.put("pi_usu_clave1", user.getPassword1());
		parameters.put("pi_usu_clave2", user.getPassword2());
		parameters.put("pi_usu_clave3", user.getPassword3());
		parameters.put("pi_usu_clave4", user.getPassword4());
		parameters.put("pi_usu_clave", user.getPassword());

		try {
			
			SamConfig.getSam().execute("TM_DBSECURITY.SECURITY_UPDATE_PASSWORD",
			getSamWebClient().getSamContext(), parameters);        
			
			int error = ((BigDecimal) parameters.get("po_c_error")).intValue();
			String errorDesc = parameters.get("po_d_error") != null ? parameters.get("po_d_error").toString() : "";
			/*Aparentemente no tiene validaciones desde la base.*/
		} catch (Exception e) {
			log.error(e);
			/*TODO: CAPAZ DEBER�A SETAR EL ESTADO DEL USUARIO O ALGO.*/
			throw new Exception();
		}
	}
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

}
