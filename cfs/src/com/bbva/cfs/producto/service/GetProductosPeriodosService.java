package com.bbva.cfs.producto.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProductosPeriodosService extends CommoProductoPeriodoService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List productosPeriodosList;
	
	public GetProductosPeriodosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.productosPeriodosList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.productosPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: GetProductosPeriodos()");
			result = getProductoPeriodoDao().getProductosPeriodos(this.parameters, this.productosPeriodosList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String cdProveedor) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.productosPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: getProductosPeriodos()");
			result = getProductoPeriodoDao().getProductosPeriodos(this.parameters, this.productosPeriodosList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String cdProveedor, String cdProducto) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.productosPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: getProductosPeriodos()");
			result = getProductoPeriodoDao().getProductosPeriodos(this.parameters, this.productosPeriodosList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String cdProveedor, String cdProducto, Date fhDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.parameters.put("pi_FH_DESDE"       , fhDesde);
		this.productosPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: getProductosPeriodos()");
			result = getProductoPeriodoDao().getProductosPeriodos(this.parameters, this.productosPeriodosList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	public Result saveProductoPeriodo(String opcion, String cdProveedor, String cdProducto, 
			                         String fhDesde, String fhHasta, String stHabilitado, 
			                         String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"        , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"     , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"      , cdProducto);
		this.parameters.put("pi_FH_DESDE"         , fhDesde);
		this.parameters.put("pi_FH_HASTA"         , fhHasta);
		this.parameters.put("pi_ST_HABILITADO"    , stHabilitado);
		this.parameters.put("pi_USU_MODI"         , usuario);	
				
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: saveProductoPeriodo()");
			result = getProductoPeriodoDao().saveProductoPeriodo(this.parameters);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	public Result deleteProductoPeriodo(String cdProveedor, String cdProducto, Date fhDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.parameters.put("pi_FH_DESDE"       , fhDesde);
		
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoDao: deleteProductoPeriodo()");
			result = getProductoPeriodoDao().deleteProductoPeriodo(this.parameters);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}
	
	public List getProductosPeriodosList() {
		return productosPeriodosList;
	}

	public void setProductosPeriodosList(List productosPeriodosList) {
		this.productosPeriodosList = productosPeriodosList;
	}
}
