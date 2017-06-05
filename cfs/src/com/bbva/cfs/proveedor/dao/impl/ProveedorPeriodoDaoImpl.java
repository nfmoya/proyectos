package com.bbva.cfs.proveedor.dao.impl;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.bbva.cfs.proveedor.dao.ProveedorPeriodoDao;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;
//import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.ResultDatabase;

//import com.bbva.cfs.parameters.model.Parameter;

/**
 * 
 * @author xah1198
 * 
 */
public class ProveedorPeriodoDaoImpl implements ProveedorPeriodoDao {

	static final Log log = LogFactory.getLog(ProveedorPeriodoDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProveedorPeriodoDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProveedoresPeriodos(Map parameters,
			List proveedoresPeriodosList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_PERIODO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProveedorPeriodo proveedoresPeriodos;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				proveedoresPeriodos = new ProveedorPeriodo();

				proveedoresPeriodos
						.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null ? dynaBean
								.get("CD_PROVEEDOR").toString() : "");

				proveedoresPeriodos.setCdPeriodoFact(dynaBean
						.get("CD_PERIODOFACT") != null ? dynaBean.get(
						"CD_PERIODOFACT").toString() : "");

				proveedoresPeriodos.setNbPeriodoFact(dynaBean
						.get("NB_PERIODOFACT") != null ? dynaBean.get(
						"NB_PERIODOFACT").toString() : "");

				proveedoresPeriodos.setCdPerFactAlt(dynaBean
						.get("CD_PERFACTALT") != null ? dynaBean.get(
						"CD_PERFACTALT").toString() : "");

				proveedoresPeriodos
						.setFhDesde(dynaBean.get("FH_DESDE") != null ? dynaBean
								.get("FH_DESDE").toString() : "");

				proveedoresPeriodos
						.setFhHasta(dynaBean.get("FH_HASTA") != null ? dynaBean
								.get("FH_HASTA").toString() : "");

				/*
				 * proveedoresPeriodos .setFhDesde((Date)
				 * (dynaBean.get("fh_desde") != null ?
				 * Date.parse(dynaBean.get("fh_desde").toString()) : ""));
				 * 
				 * proveedoresPeriodos .setFhHasta((Date)
				 * (dynaBean.get("fh_hasta") != null ?
				 * Date.parse(dynaBean.get("fh_hasta").toString()) : ""));
				 */

				proveedoresPeriodos
						.setStEstado(dynaBean.get("ST_ESTADO") != null ? dynaBean
								.get("ST_ESTADO").toString() : "");

				proveedoresPeriodosList.add(proveedoresPeriodos);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
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
	public Result saveProveedorPeriodo(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_PERIODO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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
	public Result deleteProveedorPeriodo(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_PERIODO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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

	/**
	 * Metodo que trae los periodos que abarquen 2 periodos
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getPeriodosDesdeHasta(Map parameters, List proveedoresPeriodosList) throws Exception {
		try {
			System.out.println(parameters);
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_PERIODO_DH_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
			ProveedorPeriodo pp;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				pp = new ProveedorPeriodo();
				
				
				pp.setCdPeriodoFact(dynaBean.get("CD_PERIODOFACT") != null 
					? dynaBean.get("CD_PERIODOFACT").toString()
					: "");
				
				pp.setNbPeriodoFact(dynaBean.get("NB_PERIODOFACT") != null 
					? dynaBean.get("NB_PERIODOFACT").toString()
					: "");

				
				proveedoresPeriodosList.add(pp);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
}
