package com.bbva.cfs.usuario.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.usuario.dao.UsuarioDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoUsuarioService extends CommonService {

	private UsuarioDao UsuarioDao;

	public CommoUsuarioService(IWebClient iWebClient) {
		super(iWebClient);
		UsuarioDao = daoFactory.getUsuarioDao();
	}

	public UsuarioDao getUsuarioDao() {
		return UsuarioDao;
	}

	public void setUsuarioDao(UsuarioDao UsuarioDao) {
		this.UsuarioDao = UsuarioDao;
	}
}
