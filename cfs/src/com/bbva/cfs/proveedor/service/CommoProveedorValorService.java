package com.bbva.cfs.proveedor.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.proveedor.dao.ProveedorValorDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProveedorValorService extends CommonService {

	private ProveedorValorDao proveedorValorDao;

	public CommoProveedorValorService(IWebClient iWebClient) {
		super(iWebClient);
		proveedorValorDao = daoFactory.getProveedorValorDao();
	}

	public ProveedorValorDao getProveedorValorDao() {
		return proveedorValorDao;
	}

	public void setProveedorValorDao(ProveedorValorDao proveedorValorDao) {
		this.proveedorValorDao = proveedorValorDao;
	}
}
