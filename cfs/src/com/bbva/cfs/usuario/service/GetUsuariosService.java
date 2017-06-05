package com.bbva.cfs.usuario.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;

//import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.usuario.model.Usuario;
import com.bbva.cfs.usuario.service.CommoUsuarioService;

/**
 * TODO:
 * @author xah1198
 *
 */
public class GetUsuariosService extends CommoUsuarioService {
	private Result result;	
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List usuariosList;
	
	@SuppressWarnings("rawtypes")
	public GetUsuariosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.usuariosList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_id_usuario"     , 0);
		this.parameters.put("pi_des_usuario"    , "");
		this.parameters.put("pi_id_perfil"      , 0);
		this.parameters.put("pi_d_nombre"       , "");
		this.parameters.put("pi_d_apellido"     , "");
		this.parameters.put("pi_d_sector"       , "");
		this.parameters.put("pi_clave_defecto"  , "");
		this.parameters.put("pi_id_usua_modi"   , 0);
		this.usuariosList.clear();
		try {
			log.debug("Invocación al dao getUsuarioDAO: GetUsuariosService()");
			result = getUsuarioDao().getUsuarios(this.parameters, this.usuariosList);
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

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(Long usuarioId) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"  , "3");
		this.parameters.put("pi_id_usuario" , usuarioId);
		this.usuariosList.clear();
		try {
			log.debug("Invocación al dao getUsuarioDAO: GetUsuariosService()");
			result = getUsuarioDao().getUsuarios(this.parameters, this.usuariosList);
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

	public Result saveUsuario(String opcion, Long usuarioId, String userName, 
			                Long perfil, String nombre, String apellido, 
			                String cdSector, String clave, Long userId) throws Exception {
		
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , opcion);
		this.parameters.put("pi_id_usuario"     , usuarioId);
		this.parameters.put("pi_des_usuario"    , userName);
		this.parameters.put("pi_id_perfil"      , perfil);
		this.parameters.put("pi_d_nombre"       , nombre);
		this.parameters.put("pi_d_apellido"     , apellido);
		this.parameters.put("pi_d_sector"       , cdSector);
		this.parameters.put("pi_clave_defecto"  , clave);
		this.parameters.put("pi_id_usua_modi"   , userId);
		
		try {
			log.debug("Invocación al dao getUsuarioDAO: saveUsuario()");
			result = getUsuarioDao().saveUsuario(this.parameters);
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

	public Result deleteUsuario(Long usuarioId, Long userId) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_id_usuario"     , usuarioId);
		this.parameters.put("pi_des_usuario"    , "");
		this.parameters.put("pi_id_perfil"      , 0);
		this.parameters.put("pi_d_nombre"       , "");
		this.parameters.put("pi_d_apellido"     , "");
		this.parameters.put("pi_d_sector"       , "");
		this.parameters.put("pi_clave_defecto"  , "");
		this.parameters.put("pi_id_usua_modi"   , userId);

		try {
			log.debug("Invocación al dao getUsuarioDAO: deleteUsuario()");
			result = getUsuarioDao().deleteUsuario(this.parameters);
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
	
	@SuppressWarnings("rawtypes")
	public List getUsuariosList() {
		return usuariosList;
	}

	@SuppressWarnings("rawtypes")
	public void setUsuariosList(List usuariosList) {
		this.usuariosList = usuariosList;
	}
}
