package com.bbva.cfs.proveedor.dao.impl;

//import java.math.BigDecimal;
import java.math.BigDecimal;
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
import com.bbva.cfs.proveedor.dao.ProveedorValorDao;
import com.bbva.cfs.proveedor.model.ProveedorValor;
//import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
//import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.parameters.model.Parameter;

/**
 * 
 * @author xah1198
 * 
 */
public class ProveedorValorDaoImpl implements ProveedorValorDao {

	static final Log log = LogFactory.getLog(ProveedorValorDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProveedorValorDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProveedoresValores(Map parameters, List proveedoresValoresList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_VALOR_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProveedorValor proveedoresValores;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				proveedoresValores = new ProveedorValor();
				
				proveedoresValores
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				proveedoresValores
					.setCdPeriodoFact(dynaBean.get("CD_PERIODOFACT") != null 
					? dynaBean.get("CD_PERIODOFACT").toString()
					: "");

				proveedoresValores
					.setCdUniVal(dynaBean.get("CD_UNIVAL") != null 
					? dynaBean.get("CD_UNIVAL").toString()
					: "");
				
				proveedoresValores
					.setNuValBrutoUniVal(dynaBean.get("NU_VALBRUTOUNIVAL") != null 
					? dynaBean.get("NU_VALBRUTOUNIVAL").toString()
					: "");
				
				proveedoresValores
					.setNuValNetoUniVal(dynaBean.get("NU_VALNETOUNIVAL") != null 
					? dynaBean.get("NU_VALNETOUNIVAL").toString()
					: "");
		
				proveedoresValores
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
				
				proveedoresValoresList.add(proveedoresValores);
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
	public Result saveProveedorValor(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_VALOR_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
		} catch (TransactionException e) {
			log.error(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Result deleteProveedorValor(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PROVEEDOR_VALOR_INVOKER,
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
