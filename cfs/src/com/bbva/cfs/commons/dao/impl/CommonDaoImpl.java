package com.bbva.cfs.commons.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.LogFactory;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.dao.CommonDao;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.DateFormatCustomize;
import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.EntityType;
import com.bbva.cfs.commons.utils.General;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.users.model.User;

/**
 * 
 * @author xah1198
 * 
 */
public class CommonDaoImpl implements CommonDao {

	static final org.apache.commons.logging.Log log = LogFactory
			.getLog(CommonDaoImpl.class);

	private SAMWebClient samWebClient;

	private Result result;

	private Map parameterMap;

	public CommonDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
		this.parameterMap = new HashMap();
	}

	public Result getUsersAvailable(List users) throws Exception {
		Map parameters = new HashMap();
		try {
			// Ejecuci�n del SP
			SamConfig.getSam().execute("TM_DBCFS.GET_USERS_AVAILABLES",
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result, log);

			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			User user;
			DynaBean dynaBean;
			for (int i=0; i<dynaListResult.size(); i++) {
				dynaBean = (DynaBean)dynaListResult.get(i);
				user = new User();

				// Identificador de usuario
				user.setUserName(dynaBean.get("d_user").toString());

				// Nombre de usuario
				user.setName(dynaBean.get("nombre_usuario").toString());

				users.add(user);
			}

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;

	}

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

}
