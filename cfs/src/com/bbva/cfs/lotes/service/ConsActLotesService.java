package com.bbva.cfs.lotes.service;

import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.service.CommonService;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.lotes.dao.ConsActLotesCRUDDao;
import com.bbva.cfs.lotes.form.ConsActLotesForm;
import com.bbva.cfs.lotes.model.ConsActLotesModel;

/**
 * Clase que contiene los service de Consulta y Actualización de Lotes
 * 
 * @author Alexis Comerci
 *
 */
public class ConsActLotesService extends CommonService {

	private ConsActLotesCRUDDao dao;

	public ConsActLotesService(IWebClient iWebClient) {
		super(iWebClient);
		dao = daoFactory.getConsActLotesCRUDDao();
	}

	
	/**
	 * Obtiene la lista de lotes
	 * 
	 * @param lotesList
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Result getConsActLotesList(List<ConsActLotesModel> lotesList, ConsActLotesForm form)
			throws Exception {
		try {
			result = dao.getConsActLotes(lotesList, form);
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
	 * Anular un lote
	 * 
	 * @param ConsActLotesModel
	 * @return Result
	 * @throws Exception
	 */
	public Result anular(String tipoLote, String nroLote, String user)
			throws Exception {
		try {
			result = dao.anular(tipoLote, nroLote, user);
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