package com.bbva.cfs.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.cfs.commons.dao.CommonValidationDao;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.common.config.SamConfig;

public class CommonValidationDaoImpl implements CommonValidationDao {

	static final Log log = LogFactory.getLog(CommonValidationDaoImpl.class);

	private Result result;
	private SAMWebClient samWebClient;
	private Map<String, Object> parameters;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	/**
	 * Constructor!
	 * 
	 * @param samWebClient
	 */
	public CommonValidationDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
		this.parameters = new HashMap<String, Object>();
	}

	public Result validatePeriodoFact(String periodD, String periodH, String cdProveedor, Boolean busquedaConcil)
			throws Exception {

		try {

			this.parameters.clear();
			// Seteo de par�metros
			this.parameters.put("pi_pf_desde", periodD);
			this.parameters.put("pi_pf_hasta", periodH);
			this.parameters.put("pi_cd_proveedor", cdProveedor);

			// Ejecuci�n del SP
			if (!busquedaConcil) {
				SamConfig.getSam().execute(DatabaseConstants.VALIDATE_PERIOD_FACT_INVOKER,
						getSamWebClient().getSamContext(), this.parameters);
			} else {
				SamConfig.getSam().execute(DatabaseConstants.VALIDATE_PERIOD_FACT2_INVOKER,
						getSamWebClient().getSamContext(), this.parameters);
			}

			// Obtenci�n de par�metros
			DaoUtils.proccessServiceResponse(this.parameters, this.result, log);

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}

}
