package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoSectorDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoSectorService extends CommonService {

	private ProductoSectorDao productoSectorDao;

	public CommoProductoSectorService(IWebClient iWebClient) {
		super(iWebClient);
		productoSectorDao = daoFactory.getProductoSectorDao();
	}

	public ProductoSectorDao getProductoSectorDao() {
		return productoSectorDao;
	}

	public void setProductoSectorDao(ProductoSectorDao productoSectorDao) {
		this.productoSectorDao = productoSectorDao;
	}
}
