package com.bbva.cfs.conciliacion.service;

import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.conciliacion.dao.DifConciliacionCRUDDao;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;

/**
 * Clase que contiene los service de DIferenciacion de Conciliaciones
 * 
 * @author David!
 *
 */
public class DifConciliacionService extends CommonService {

	private DifConciliacionCRUDDao dao;

	public DifConciliacionService(IWebClient iWebClient) {
		super(iWebClient);
		dao = daoFactory.getDifConciliacionCRUDDao();
	}

	
	/**
	 * Obtiene la lista de diferencias de conciliaciones
	 * 
	 * @param dcList
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Result getDifConciliacionList(List<DiferenciaConciliacionModel> dcList, DiferenciaConciliacionForm form)
			throws Exception {
		try {
			result = dao.getDifConciliacion(dcList, form);
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
	
	/**
	 * Edita una conciliacion
	 * 
	 * @param DiferenciaConciliacionModel
	 * @return Result
	 * @throws Exception
	 */
	public Result edit(DiferenciaConciliacionForm dcf, String user)
			throws Exception {
		try {
			result = dao.edit(dcf, user);
		} catch (DaoException e) {
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