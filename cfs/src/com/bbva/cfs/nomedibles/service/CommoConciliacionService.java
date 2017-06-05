package com.bbva.cfs.nomedibles.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.nomedibles.dao.NoMediblesDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoConciliacionService extends CommonService {

	private NoMediblesDao noMediblesDao;

	public CommoConciliacionService(IWebClient iWebClient) {
		super(iWebClient);
		noMediblesDao = daoFactory.getNoMediblesDao();
	}

	public NoMediblesDao getNoMediblesDao() {
		return noMediblesDao;
	}

	public void setNoMediblesDao(NoMediblesDao noMediblesDao) {
		this.noMediblesDao = noMediblesDao;
	}
}
