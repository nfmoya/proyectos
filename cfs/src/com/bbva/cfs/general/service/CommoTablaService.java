package com.bbva.cfs.general.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.general.dao.TablaDao;
import com.bbva.cfs.commons.service.CommonService;

public class CommoTablaService extends CommonService {

	private TablaDao tablaDao;

	public CommoTablaService(IWebClient iWebClient) {
		super(iWebClient);
		tablaDao = daoFactory.getTablaDao();
	}

	public TablaDao getTablaDao() {
		return tablaDao;
	}

	public void setTablaDao(TablaDao tablaDao) {
		this.tablaDao = tablaDao;
	}
}
