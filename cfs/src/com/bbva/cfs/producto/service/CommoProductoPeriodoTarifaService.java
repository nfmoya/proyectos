package com.bbva.cfs.producto.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.producto.dao.ProductoPeriodoTarifaDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoProductoPeriodoTarifaService extends CommonService {

	private ProductoPeriodoTarifaDao productoPeriodoTarifaDao;

	public CommoProductoPeriodoTarifaService(IWebClient iWebClient) {
		super(iWebClient);
		productoPeriodoTarifaDao = daoFactory.getProductoPeriodoTarifaDao();
	}

	public ProductoPeriodoTarifaDao getProductoPeriodoTarifaDao() {
		return productoPeriodoTarifaDao;
	}

	public void setProductoPeriodoTarifaDao(ProductoPeriodoTarifaDao productoPeriodoTarifaDao) {
		this.productoPeriodoTarifaDao = productoPeriodoTarifaDao;
	}
}
