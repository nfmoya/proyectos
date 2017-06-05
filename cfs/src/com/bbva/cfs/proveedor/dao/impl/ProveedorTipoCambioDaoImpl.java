package com.bbva.cfs.proveedor.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.proveedor.dao.ProveedorTipoCambioDao;
import com.bbva.cfs.proveedor.model.ProveedorTipoCambio;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * 
 * @author xah1198
 * 
 */
public class ProveedorTipoCambioDaoImpl implements ProveedorTipoCambioDao {

	static final Log log = LogFactory.getLog(ProveedorTipoCambioDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProveedorTipoCambioDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProveedoresTipoCambio(Map parameters, List proveedoresTipoCambioList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_TIPOCAMBIO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProveedorTipoCambio proveedoresTipoCambio;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				proveedoresTipoCambio = new ProveedorTipoCambio();
				
				proveedoresTipoCambio
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				proveedoresTipoCambio
					.setCdPeriodoFact(dynaBean.get("CD_PERIODOFACT") != null 
					? dynaBean.get("CD_PERIODOFACT").toString()
					: "");

				proveedoresTipoCambio
					.setCdMoneda(dynaBean.get("CD_MONEDA") != null 
					? dynaBean.get("CD_MONEDA").toString()
					: "");
				
				proveedoresTipoCambio
					.setNuDias(dynaBean.get("NU_DIAS") != null 
					? dynaBean.get("NU_DIAS").toString()
					: "");
				
				proveedoresTipoCambio
					.setCdCotizacion(dynaBean.get("CD_COTIZACION") != null 
					? dynaBean.get("CD_COTIZACION").toString()
					: "");
				
				proveedoresTipoCambio
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
				
				proveedoresTipoCambioList.add(proveedoresTipoCambio);
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
	public Result saveProveedorTipoCambio(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PROVEEDOR_TIPOCAMBIO_INVOKER,
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
	public Result deleteProveedorTipoCambio(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PROVEEDOR_TIPOCAMBIO_INVOKER,
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
