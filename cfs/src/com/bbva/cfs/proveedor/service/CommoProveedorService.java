package com.bbva.cfs.proveedor.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.proveedor.dao.ProveedorDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProveedorService extends CommonService {

	private ProveedorDao proveedorDao;

	public CommoProveedorService(IWebClient iWebClient) {
		super(iWebClient);
		proveedorDao = daoFactory.getProveedorDao();
	}

	public ProveedorDao getProveedorDao() {
		return proveedorDao;
	}

	public void setProveedorDao(ProveedorDao proveedorDao) {
		this.proveedorDao = proveedorDao;
	}
}
