package com.bbva.cfs.conciliacion.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.conciliacion.dao.ConciliacionDao;
import com.bbva.cfs.commons.service.CommonService;

/**
 * 
 * @author xah1198
 *
 */
public class CommoConciliacionService extends CommonService {

	private ConciliacionDao conciliacionDao;

	public CommoConciliacionService(IWebClient iWebClient) {
		super(iWebClient);
		conciliacionDao = daoFactory.getConciliacionDao();
	}

	public ConciliacionDao getConciliacionDao() {
		return conciliacionDao;
	}

	public void setConciliacionDao(ConciliacionDao conciliacionDao) {
		this.conciliacionDao = conciliacionDao;
	}
}
