package com.bbva.cfs.commons.service;

import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.dao.CommonDao;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

public class UserListService extends CommonService {
	private Result result;
	private CommonDao commonDao;

	public UserListService(IWebClient iWebClient) {
		super(iWebClient);
		this.commonDao = daoFactory.getCommonDao();
	}

	/**
	 * @author xah1257: Servicio que provee de la lista de usuarios perfilados
	 * 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public Result execute(List users) throws Exception {
		try {
			result = commonDao.getUsersAvailable(users);

		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

}
