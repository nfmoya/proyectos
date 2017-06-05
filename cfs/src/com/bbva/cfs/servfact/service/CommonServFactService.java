package com.bbva.cfs.servfact.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.sector.dao.SectorDao;
import com.bbva.cfs.servfact.dao.ServFactDao;
import com.bbva.cfs.commons.service.CommonService;

public class CommonServFactService extends CommonService {

	private ServFactDao servFactDao;

	public CommonServFactService(IWebClient iWebClient) {
		super(iWebClient);
		servFactDao = daoFactory.getServFactDao();
	}

	public ServFactDao getServFactDao() {
		return servFactDao;
	}

	public void setServFactDao(ServFactDao sectorDao) {
		this.servFactDao = sectorDao;
	}
}
