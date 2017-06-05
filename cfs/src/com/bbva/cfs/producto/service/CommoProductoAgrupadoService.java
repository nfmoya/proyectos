package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoAgrupadoDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoAgrupadoService extends CommonService {

	private ProductoAgrupadoDao productoAgrupadoDao;

	public CommoProductoAgrupadoService(IWebClient iWebClient) {
		super(iWebClient);
		productoAgrupadoDao = daoFactory.getProductoAgrupadoDao();
	}

	public ProductoAgrupadoDao getProductoAgrupadoDao() {
		return productoAgrupadoDao;
	}

	public void setProductoAgrupadoDao(ProductoAgrupadoDao productoAgrupadoDao) {
		this.productoAgrupadoDao = productoAgrupadoDao;
	}
}
