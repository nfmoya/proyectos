package com.bbva.cfs.parameters.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.dao.ParametersDao;
import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.model.ParameterType;


/**
 * 
 * @author xah1198
 *
 */
public class ParametersDaoImpl implements ParametersDao {
	
	static final Log log = LogFactory.getLog(ParametersDaoImpl.class);

	private SAMWebClient samWebClient;
	
	private Result result;

	public ParametersDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}
	/**
	 * Retorna una lista de de tipos de par�metros.
	 */
	public Result getParameterTypeList( List parameterTypeList) throws Exception {
		
        Map parameters = new HashMap();
        parameters.clear();
        
		try {
			log.info("ANTES DE HACER LA CONSULTA");
			SamConfig.getSam().execute(DatabaseConstants.GET_PARAMETER_TYPE_LIST_INVOKER,
					getSamWebClient().getSamContext(), parameters);        

			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			//ArrayList<DynaBean> dynaListResult = (ArrayList<DynaBean>) parameters.get(DatabaseConstants.PO_CURSOR);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
			DynaBean dynaBean;

			ParameterType paramType;
			//for (DynaBean dynaBean : dynaListResult) {
			for (int i=0; i<dynaListResult.size(); i++) {
				dynaBean = (DynaBean)dynaListResult.get(i);
				paramType = new ParameterType(null, null);
				log.info("DATO 1 "+dynaBean.get("codigo").toString() );
				log.info("DATO 2 "+dynaBean.get("valor1").toString() );
				paramType.setId(dynaBean.get("id_tabla") != null ? dynaBean.get("id_tabla").toString() : "");
				paramType.setDesc(dynaBean.get("valor1") != null ? dynaBean.get("valor1").toString() : "");				
				parameterTypeList.add(paramType);
			}
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
	
	/**
	 * Retorna una lista de de tipos de par�metros.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result getParameterList( String parameterTypeId, String pi_filtro_1,  String pi_filtro_2
									, List parameterList) throws Exception {
	    Map parameters = new HashMap();
        parameters.clear();
        parameters.put("pi_id_tabla", parameterTypeId);
        parameters.put("pi_filtro_1", pi_filtro_1);
        parameters.put("pi_filtro_2", pi_filtro_2);
     	try {
			log.info("ANTES DE HACER LA CONSULTA");
			SamConfig.getSam().execute(DatabaseConstants.GET_PARAMETER_LIST_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
			DynaBean dynaBean;

			Parameter param;
			parameterList.clear();
			for (int i=0; i<dynaListResult.size(); i++) {
				dynaBean = (DynaBean)dynaListResult.get(i);
				param = new Parameter(parameterTypeId, null, null, null);
				param.setCod(dynaBean.get("codigo").toString());
				param.setDesc(dynaBean.get("valor1") != null ? dynaBean.get("valor1").toString() : "");				
				parameterList.add(param);
			}

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
	
	/**
	 * TODO:
	 */
	public Result addParameter( Map parameters ) throws Exception {
		
		try {
			SamConfig.getSam().execute(DatabaseConstants.SAVE_PARAMETER_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result,log);

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
	
	/**
	 * TODO:
	 */
	public Result modParameter( Map parameters ) throws Exception {
		
		try {
			SamConfig.getSam().execute(DatabaseConstants.EDIT_PARAMETER_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result,log);

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
	
	/**
	 * TODO:
	 */
	public Result delParameter( Map parameters ) throws Exception {
		
		try {
			SamConfig.getSam().execute(DatabaseConstants.DELETE_PARAMETER_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result,log);

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
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

}
