package com.bbva.cfs.proveedor.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.proveedor.dao.ProveedorPeriodoDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProveedorPeriodoService extends CommonService {

	private ProveedorPeriodoDao proveedorPeriodoDao;

	public CommoProveedorPeriodoService(IWebClient iWebClient) {
		super(iWebClient);
		proveedorPeriodoDao = daoFactory.getProveedorPeriodoDao();
	}

	public ProveedorPeriodoDao getProveedorPeriodoDao() {
		return proveedorPeriodoDao;
	}

	public void setProveedorPeriodoDao(ProveedorPeriodoDao proveedorPeriodoDao) {
		this.proveedorPeriodoDao = proveedorPeriodoDao;
	}
}
