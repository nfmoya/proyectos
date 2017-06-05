package com.bbva.cfs.servprestdetalle.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.servprestdetalle.dao.ServPrestDetalleDao;

public class CommonServPrestDetalleService extends CommonService {

	private ServPrestDetalleDao servPrestDetalleDao;

	public CommonServPrestDetalleService(IWebClient iWebClient) {
		super(iWebClient);
		servPrestDetalleDao = daoFactory.getServPrestDetalleDao();
	}

	public ServPrestDetalleDao getServPrestDetalleDao() {
		return servPrestDetalleDao;
	}

	public void setServPrestDetalleDao(ServPrestDetalleDao sectorDao) {
		this.servPrestDetalleDao = sectorDao;
	}

}
