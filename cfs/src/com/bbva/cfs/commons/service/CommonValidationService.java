package com.bbva.cfs.commons.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.dao.CommonValidationDao;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

public class CommonValidationService extends CommonService{
	
	private CommonValidationDao dao;

	public CommonValidationService(IWebClient iWebClient) {
		super(iWebClient);
		dao = daoFactory.getCommonValidationDao();
	}
	
	
	public Result validatePeriodFact(String periodD, String periodH, String cdProveedor, Boolean busquedaConcil)
			throws Exception {
		try {
			result = dao.validatePeriodoFact(periodD, periodH, cdProveedor, busquedaConcil);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(
					ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

}
