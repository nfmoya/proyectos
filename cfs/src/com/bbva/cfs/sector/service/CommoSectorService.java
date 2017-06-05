package com.bbva.cfs.sector.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.sector.dao.SectorDao;
import com.bbva.cfs.commons.service.CommonService;

public class CommoSectorService extends CommonService {

	private SectorDao sectorDao;

	public CommoSectorService(IWebClient iWebClient) {
		super(iWebClient);
		sectorDao = daoFactory.getSectorDao();
	}

	public SectorDao getSectorDao() {
		return sectorDao;
	}

	public void setSectorDao(SectorDao sectorDao) {
		this.sectorDao = sectorDao;
	}
}
