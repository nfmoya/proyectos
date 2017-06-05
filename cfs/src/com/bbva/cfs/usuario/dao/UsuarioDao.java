package com.bbva.cfs.usuario.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface UsuarioDao {

	@SuppressWarnings("rawtypes")
	public Result getUsuarios( Map parameters, List usuariosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveUsuario(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteUsuario( Map parameters ) throws Exception;

}
