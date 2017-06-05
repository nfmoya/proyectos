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
import com.bbva.cfs.producto.dao.ProductoPeriodoDao;
import com.bbva.cfs.producto.model.ProductoPeriodo;
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
public class ProductoPeriodoDaoImpl implements ProductoPeriodoDao {

	static final Log log = LogFactory.getLog(ProductoPeriodoDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProductoPeriodoDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductosPeriodos(Map parameters, List productosPeriodosList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_PERIODO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProductoPeriodo productosPeriodos;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productosPeriodos = new ProductoPeriodo();
				
				productosPeriodos
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				productosPeriodos
					.setCdProducto(dynaBean.get("CD_PRODUCTO") != null 
					? dynaBean.get("CD_PRODUCTO").toString()
					: "");

				productosPeriodos
					.setFhDesde(dynaBean.get("FH_DESDE") != null 
					? dynaBean.get("FH_DESDE").toString()
					: "");				

				productosPeriodos
					.setFhHasta(dynaBean.get("FH_HASTA") != null 
					? dynaBean.get("FH_HASTA").toString()
					: "");

				productosPeriodos
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
							
				productosPeriodosList.add(productosPeriodos);
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
	public Result saveProductoPeriodo(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_PERIODO_INVOKER,
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
	public Result deleteProductoPeriodo(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PERIODO_INVOKER,
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
