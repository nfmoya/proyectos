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
import com.bbva.cfs.producto.dao.ProductoPrecioDao;
import com.bbva.cfs.producto.model.ProductoPrecio;
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
public class ProductoPrecioDaoImpl implements ProductoPrecioDao {

	static final Log log = LogFactory.getLog(ProductoPrecioDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProductoPrecioDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductosPrecios(Map parameters, List productosPreciosList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_PRECIO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProductoPrecio productosPrecios;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productosPrecios = new ProductoPrecio();
				
				productosPrecios
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				productosPrecios
					.setCdProducto(dynaBean.get("CD_PRODUCTO") != null 
					? dynaBean.get("CD_PRODUCTO").toString()
					: "");

				productosPrecios
					.setFhDesde(dynaBean.get("FH_DESDE") != null 
					? dynaBean.get("FH_DESDE").toString()
					: "");				

				productosPrecios
					.setFhHasta(dynaBean.get("FH_HASTA") != null 
					? dynaBean.get("FH_HASTA").toString()
					: "");

				productosPrecios
					.setNuPrecioUniVal(dynaBean.get("NU_PRECIOUNIVAL") != null 
					? dynaBean.get("NU_PRECIOUNIVAL").toString()
					: "");
				
				productosPrecios
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
							
				productosPreciosList.add(productosPrecios);
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
	public Result saveProductoPrecio(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_PRECIO_INVOKER,
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
	public Result deleteProductoPrecio(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_PRECIO_INVOKER,
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
