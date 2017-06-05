package com.bbva.cfs.general.dao.impl;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.general.dao.TablaDao;
import com.bbva.cfs.general.model.Tabla;
//import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
//import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.parameters.model.Parameter;

public class TablaDaoImpl implements TablaDao {

	static final Log log = LogFactory.getLog(TablaDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public TablaDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getTablas(Map parameters, List tablasList)
			throws Exception {
		try {
//			System.out.println("parameters");
//			System.out.println(parameters);
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_TABLA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			Tabla tablas;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				tablas = new Tabla();

				tablas
					.setCdTabla(dynaBean.get("CD_TABLA") != null 
					? dynaBean.get("CD_TABLA").toString()
					: "");

				tablas
					.setCdCodTabla(dynaBean.get("CD_CODTABLA") != null 
					? dynaBean.get("CD_CODTABLA").toString()
					: "");

				tablas
					.setNbCodTabla(dynaBean.get("NB_CODTABLA") != null 
					? dynaBean.get("NB_CODTABLA").toString()
					: "");
			
				tablas
					.setNbCodTablaCorto(dynaBean.get("NB_CODTABLACORTO") != null 
					? dynaBean.get("NB_CODTABLACORTO").toString()
					: "");

				tablas
					.setNbAtributoTabla1(dynaBean.get("NB_ATRIBUTOTABLA1") != null 
					? dynaBean.get("NB_ATRIBUTOTABLA1").toString()
					: "");
				
				tablas
					.setNbAtributoTabla2(dynaBean.get("NB_ATRIBUTOTABLA2") != null 
					? dynaBean.get("NB_ATRIBUTOTABLA2").toString()
					: "");
			
				tablas
					.setNbAtributoTabla3(dynaBean.get("NB_ATRIBUTOTABLA3") != null 
					? dynaBean.get("NB_ATRIBUTOTABLA3").toString()
					: "");
			
				tablas
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");

				tablasList.add(tablas);
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
	
	@SuppressWarnings("rawtypes")
	public Result saveTabla(Map parameters) throws Exception {
		try {
			System.out.println("saveTabla");
			System.out.println("parameters");
			System.out.println(parameters);
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_TABLA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
		} catch (TransactionException e) {
			System.out.println("FIIIIIIII!");
			System.out.println("TransactionException");
			log.debug(e);
			e.printStackTrace();
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			System.out.println("FOOOOOOO!");
			System.out.println("Exception");
			e.printStackTrace();
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Result deleteTabla( Map parameters ) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_TABLA_INVOKER,
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
}
