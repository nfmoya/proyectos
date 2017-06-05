package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoService extends CommonService {

	private ProductoDao productoDao;

	public CommoProductoService(IWebClient iWebClient) {
		super(iWebClient);
		productoDao = daoFactory.getProductoDao();
	}

	public ProductoDao getProductoDao() {
		return productoDao;
	}

	public void setProductoDao(ProductoDao productoDao) {
		this.productoDao = productoDao;
	}
}
