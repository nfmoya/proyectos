package com.bbva.cfs.producto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
//import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.producto.service.CommoProductoSectorService;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProductosSectoresService extends CommoProductoSectorService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List productosSectoresList;
	
	public GetProductosSectoresService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.productosSectoresList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.productosSectoresList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorDAO: GetProveedoresService()");
			result = getProductoSectorDao().getProductosSectores(this.parameters, this.productosSectoresList);
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

		this.productosSectoresList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorDAO: GetProveedoresService()");
			result = getProductoSectorDao().getProductosSectores(this.parameters, this.productosSectoresList);
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

		this.productosSectoresList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorDAO: GetProveedoresService()");
			result = getProductoSectorDao().getProductosSectores(this.parameters, this.productosSectoresList);
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
	public Result execute(String cdProveedor, String cdProducto, String cdSector) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.parameters.put("pi_CD_SECTOR"      , cdSector);

		this.productosSectoresList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorDAO: GetProveedoresService()");
			result = getProductoSectorDao().getProductosSectores(this.parameters, this.productosSectoresList);
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

	public Result saveProductoSector(String opcion, String cdProveedor, String cdProducto, 
							String cdSector, String stHabilitado, String usuario ,String cdSectorOld) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"         , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"      , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"       , cdProducto);
		this.parameters.put("pi_CD_SECTOR"         , cdSector);
		this.parameters.put("pi_ST_HABILITADO"     , stHabilitado);
		this.parameters.put("pi_USU_MODI"          , usuario);	
		this.parameters.put("pi_CD_SECTOR_OLD"          , cdSectorOld);	
		try {
			log.debug("Invocaci�n al dao getProveedorDAO: saveProveedorValor()");
			result = getProductoSectorDao().saveProductoSector(this.parameters);
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

	public Result deleteProductoSector(String cdProveedor, String cdProducto, String cdSector) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.parameters.put("pi_CD_SECTOR"      , cdSector);
		
		try {
			log.debug("Invocaci�n al dao getProductoSectorDao: deleteProductoSector()");
			result = getProductoSectorDao().deleteProductoSector(this.parameters);
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
	
	public List getProductosSectoresList() {
		return productosSectoresList;
	}

	public void setProductosSectoresList(List productosSectoresList) {
		this.productosSectoresList = productosSectoresList;
	}
}
