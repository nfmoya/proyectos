package com.bbva.cfs.producto.dao.impl;

//import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.Date;
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
import com.bbva.cfs.producto.dao.ProductoPeriodoTarifaDao;
import com.bbva.cfs.producto.model.ProductoPeriodoTarifa;
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
public class ProductoPeriodoTarifaDaoImpl implements ProductoPeriodoTarifaDao {

	static final Log log = LogFactory.getLog(ProductoPeriodoTarifaDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProductoPeriodoTarifaDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductosPeriodosTarifas(Map parameters, List productosPeriodosTarifasList) throws Exception {
		try {
			// Se le coloca un sleep ya que es necesario para que pueda invocar
			// el xml, sino lanza error en la ejecucion del sp.
			Thread.sleep(300);
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PERIODO_TARIFA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
			ProductoPeriodoTarifa productosPeriodosTarifas;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productosPeriodosTarifas = new ProductoPeriodoTarifa();

				productosPeriodosTarifas.setCdProveedor(
						dynaBean.get("CD_PROVEEDOR") != null ? dynaBean.get("CD_PROVEEDOR").toString() : "");

				productosPeriodosTarifas.setCdProducto(
						dynaBean.get("CD_PRODUCTO") != null ? dynaBean.get("CD_PRODUCTO").toString() : "");

				productosPeriodosTarifas
						.setFhDesde(dynaBean.get("FH_DESDE") != null ? dynaBean.get("FH_DESDE").toString() : "");

				productosPeriodosTarifas
						.setFhHasta(dynaBean.get("FH_HASTA") != null ? dynaBean.get("FH_HASTA").toString() : "");

				productosPeriodosTarifas.setNuCantDesde(
						dynaBean.get("NU_CANTDESDE") != null ? dynaBean.get("NU_CANTDESDE").toString() : "");

				productosPeriodosTarifas.setNuCantHasta(
						dynaBean.get("NU_CANTHASTA") != null ? dynaBean.get("NU_CANTHASTA").toString() : "");

				productosPeriodosTarifas.setNuPrecioUniVal(
						dynaBean.get("NU_PRECIOUNIVAL") != null ? dynaBean.get("NU_PRECIOUNIVAL").toString() : "");

				productosPeriodosTarifas.setNuPorcTarifa(
						dynaBean.get("NU_PORCTARIFA") != null ? dynaBean.get("NU_PORCTARIFA").toString() : "");

				productosPeriodosTarifas.setStHabilitado(
						dynaBean.get("ST_HABILITADO") != null ? dynaBean.get("ST_HABILITADO").toString() : "");

				productosPeriodosTarifas.setStPrecioFijo(
						dynaBean.get("ST_PRECIOFIJO") != null ? dynaBean.get("ST_PRECIOFIJO").toString() : "");

				productosPeriodosTarifasList.add(productosPeriodosTarifas);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result getNuevoCodigoTarifa(Map parameters, List nuevoCodigoTarifaList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_TARIFA_NUEVO_CODIGO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			BigDecimal nuevoCodigoTarifa = (BigDecimal) parameters.get(DatabaseConstants.PO_NU_CANTDESDE_NUEVO);
			nuevoCodigoTarifaList.add(nuevoCodigoTarifa);
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

	@SuppressWarnings("rawtypes")
	public Result saveProductoPeriodoTarifa(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PERIODO_TARIFA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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

	@SuppressWarnings("rawtypes")
	public Result deleteProductoPeriodoTarifa(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PERIODO_TARIFA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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

	@SuppressWarnings("rawtypes")
	public Result deleteProductoPeriodoTarifas(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PERIODO_TARIFA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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
