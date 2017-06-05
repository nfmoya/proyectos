package com.bbva.cfs.servprest.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.servprest.dao.ServPrestDao;

public class CommonServPrestService extends CommonService {

	private ServPrestDao servPrestDao;

	public CommonServPrestService(IWebClient iWebClient) {
		super(iWebClient);
		servPrestDao = daoFactory.getServPrestDao();
	}

	public ServPrestDao getServPrestDao() {
		return servPrestDao;
	}

	public void setServPrestDao(ServPrestDao sectorDao) {
		this.servPrestDao = sectorDao;
	}

}
