package com.bbva.cfs.users.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.users.dao.UserDao;
import com.bbva.cfs.users.model.Perfil;
import com.bbva.cfs.users.model.User;


/**
 * 
 * @author xah1198
 *
 */
public class UserDaoImpl implements UserDao {
	
	static final Log log = LogFactory.getLog(UserDaoImpl.class);

	private SAMWebClient samWebClient;
	
	private Result result;

	public UserDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}
	
	/**
	 * Retorna una lista de de usuarios.
	 */
	public Result getUserData( Map parameters,
							   User user) throws Exception {
		try {

			SamConfig.getSam().execute(DatabaseConstants.GET_USER_INVOKER,
			getSamWebClient().getSamContext(), parameters);        
			
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			if (this.result.isSuccesfull() || this.result.isWarning()){
				Long id = parameters.get("po_id_usuario") != null ? new Long(((BigDecimal) parameters.get("po_id_usuario")).longValue()): Long.valueOf("0");
				user.setId(id);
				user.setName(parameters.get("po_nombre_usuario")!=null?parameters.get("po_nombre_usuario").toString():"");
				
				String estado = (String) parameters.get("estado_usuario");
				if(estado != null)
					user.setEstadoUser(estado.equalsIgnoreCase("ACTIVO") ? "1": "2");
				else
					user.setEstadoUser("0");
				
				Long idOng = parameters.get("po_id_ong") != null ? new Long(((BigDecimal) parameters.get("po_id_ong")).longValue()): Long.valueOf("0");
				user.setIdOng(idOng);
				
				// po_blanquea_usu: estado de blanqueo de clave
				user.setBlaqueoState(ObjectUtils.toString(parameters.get("po_blanquea_usu")));
				
				user.getPerfiles().clear();
				user.setStrPerfiles("");
				
				ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
				
				if(CollectionUtils.isNotEmpty(dynaListResult)){
					Perfil perfil;
					Iterator it = dynaListResult.iterator();
					while (it.hasNext()) {
						DynaBean dynaBean = (DynaBean) it.next();
						perfil = new Perfil();
						
						perfil.setId(dynaBean.get("id_perfil") != null? Long.valueOf(dynaBean.get("id_perfil").toString()) : Long.valueOf("0"));
						perfil.setDescripcion(dynaBean.get("d_perfil") != null? dynaBean.get("d_perfil").toString() : "");
						
						user.setStrPerfiles(user.getStrPerfiles()+","+perfil.getId());
						user.getPerfiles().add(perfil);
					}
				}
			}
			
		} catch (TransactionException e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());
	
		} catch (Exception e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}
	
	/**
	 * TODO:
	 * @param parameters
	 * @param userId
	 * @return
	 */
	public Result createUser( Map parameters,
							  Long userId	) throws Exception {

		try {

			SamConfig.getSam().execute(DatabaseConstants.SAVE_USER_INVOKER,
					getSamWebClient().getSamContext(), parameters);        

			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			userId = parameters.get("po_id_usuario") != null ? new Long(((BigDecimal) parameters.get("po_id_usuario")).longValue()): Long.valueOf("0");

		} catch (TransactionException e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());
	
		} catch (Exception e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}	
	
	/**
	 * TODO:
	 * @param parameters
	 * @param userId
	 * @return
	 */
	public Result editUser( Map parameters ) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.EDIT_USER_INVOKER,
					getSamWebClient().getSamContext(), parameters);        

			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
		} catch (TransactionException e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());
	
		} catch (Exception e) {
			log.error(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

}
