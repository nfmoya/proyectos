package com.bbva.cfs.usuario.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.usuario.dao.UsuarioDao;
import com.bbva.cfs.usuario.model.Usuario;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * 
 * @author xah1198
 * 
 */
public class UsuarioDaoImpl implements UsuarioDao {

	static final Log log = LogFactory.getLog(UsuarioDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public UsuarioDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result getUsuarios(Map parameters, List usuariosList)
			throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_USUARIO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			Usuario usuarios;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				usuarios = new Usuario();
				
				usuarios
					.setUsuarioId(dynaBean.get("id_usuario") != null 
					? Long.valueOf(dynaBean.get("id_usuario").toString())
					: Long.valueOf("0"));

				usuarios
					.setUserName(dynaBean.get("d_user") != null 
					? dynaBean.get("d_user").toString()
					: "");
				
				usuarios
					.setPerfil(dynaBean.get("id_perfil") != null 
					? Long.valueOf(dynaBean.get("id_perfil").toString())
					: Long.valueOf("0"));
			
				usuarios
					.setPerfilName(dynaBean.get("perfil_name") != null 
					? dynaBean.get("perfil_name").toString()
					: "");

				usuarios
 					.setNombre(dynaBean.get("d_nombre") != null 
 					? dynaBean.get("d_nombre").toString()
 					: "");

				usuarios
					.setApellido(dynaBean.get("d_apellido") != null 
					? dynaBean.get("d_apellido").toString()
					: "");

				usuarios
					.setCdSector(dynaBean.get("CD_SECTOR") != null 
					? dynaBean.get("CD_SECTOR").toString()
					: "");
				
				usuarios
				.setNbSector(dynaBean.get("NB_SECTOR") != null 
				? dynaBean.get("NB_SECTOR").toString()
				: "");
				
				usuarios
				.setEmail(dynaBean.get("NB_CORREO") != null 
				? dynaBean.get("NB_CORREO").toString()
				: "");

				usuariosList.add(usuarios);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Result saveUsuario(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_USUARIO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Result deleteUsuario( Map parameters ) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_USUARIO_INVOKER,
			getSamWebClient().getSamContext(), parameters);        
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
}
