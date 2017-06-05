package com.bbva.cfs.proveedor.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.proveedor.dao.ProveedorTipoCambioDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProveedorTipoCambioService extends CommonService {

	private ProveedorTipoCambioDao proveedorTipoCambioDao;

	public CommoProveedorTipoCambioService(IWebClient iWebClient) {
		super(iWebClient);
		proveedorTipoCambioDao = daoFactory.getProveedorTipoCambioDao();
	}

	public ProveedorTipoCambioDao getProveedorTipoCambioDao() {
		return proveedorTipoCambioDao;
	}

	public void setProveedorTipoCambioDao(ProveedorTipoCambioDao proveedorTipoCambioDao) {
		this.proveedorTipoCambioDao = proveedorTipoCambioDao;
	}
}
