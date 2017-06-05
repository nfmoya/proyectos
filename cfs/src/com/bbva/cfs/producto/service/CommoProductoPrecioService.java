package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoPrecioDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoPrecioService extends CommonService {

	private ProductoPrecioDao productoPrecioDao;

	public CommoProductoPrecioService(IWebClient iWebClient) {
		super(iWebClient);
		productoPrecioDao = daoFactory.getProductoPrecioDao();
	}

	public ProductoPrecioDao getProductoPrecioDao() {
		return productoPrecioDao;
	}

	public void setProductoPrecioDao(ProductoPrecioDao productoPrecioDao) {
		this.productoPrecioDao = productoPrecioDao;
	}
}
