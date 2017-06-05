package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoPeriodoDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoPeriodoService extends CommonService {

	private ProductoPeriodoDao productoPeriodoDao;

	public CommoProductoPeriodoService(IWebClient iWebClient) {
		super(iWebClient);
		productoPeriodoDao = daoFactory.getProductoPeriodoDao();
	}

	public ProductoPeriodoDao getProductoPeriodoDao() {
		return productoPeriodoDao;
	}

	public void setProductoPeriodoDao(ProductoPeriodoDao productoPeriodoDao) {
		this.productoPeriodoDao = productoPeriodoDao;
	}
}
