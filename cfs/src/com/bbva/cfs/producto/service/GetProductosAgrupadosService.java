package com.bbva.cfs.producto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
//import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.producto.model.Producto;
import com.bbva.cfs.producto.service.CommoProductoAgrupadoService;

@SuppressWarnings("rawtypes")
public class GetProductosAgrupadosService extends CommoProductoAgrupadoService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List productosAgrupadosList;
	
	public GetProductosAgrupadosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.productosAgrupadosList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "3");
		
		this.productosAgrupadosList.clear();
		try {
			log.debug("Invocación al dao getProductoAgrupadoDAO: GetProductosAgrupadosService()");
			result = getProductoAgrupadoDao().getProductosAgrupados(this.parameters, this.productosAgrupadosList);
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
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		
		this.productosAgrupadosList.clear();
		try {
			log.debug("Invocación al dao getProductoAgrupadoDAO: GetProductosAgrupadosService()");
			result = getProductoAgrupadoDao().getProductosAgrupados(this.parameters, this.productosAgrupadosList);
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
	public Result execute(String cdProveedor, String cdProductoOrig) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO_ORIG"   , cdProductoOrig);
		
		this.productosAgrupadosList.clear();
		try {
			log.debug("Invocación al dao getProductoAgrupadoDAO: GetProductosAgrupadosService()");
			result = getProductoAgrupadoDao().getProductosAgrupados(this.parameters, this.productosAgrupadosList);
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
	public Result execute(String cdProveedor, String cdProductoOrig, String cdProducto, String stHabilitado) throws Exception {
		this.parameters.clear();

        cdProveedor    = (cdProveedor.equals("0")    ? "" : cdProveedor);
        cdProductoOrig = (cdProductoOrig.equals("0") ? "" : cdProductoOrig);
        cdProducto     = (cdProducto.equals("0")     ? "" : cdProducto);
        stHabilitado   = (stHabilitado.equals("0")   ? "" : stHabilitado);
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO_ORIG"   , cdProductoOrig);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_ST_HABILITADO"      , stHabilitado);
		
		this.productosAgrupadosList.clear();
		try {
			log.debug("Invocación al dao getProductoDAO: GetProductosAgrupadosService()");
			result = getProductoAgrupadoDao().getProductosAgrupados(this.parameters, this.productosAgrupadosList);
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

	public Result saveProductoAgrupado(String opcion, String cdProveedor, String cdProductoOrig, 
							String cdProducto, String desItem, String desGrupo, 
							String stHabilitado, String usuario) throws Exception {
		this.parameters.clear();
		
		this.parameters.put("pi_id_opcion"          , opcion);	
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO_ORIG"   , cdProductoOrig);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_DES_ITEM"           , desItem);
		this.parameters.put("pi_DES_GRUPO"          , desGrupo);
		this.parameters.put("pi_ST_HABILITADO"      , stHabilitado);
		this.parameters.put("pi_USU_MODI"           , usuario);	
		try {
			log.debug("Invocación al dao getProductoAgrupadoDAO: saveProductoAgrupado()");
			result = getProductoAgrupadoDao().saveProductoAgrupado(this.parameters);
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

	public Result deleteProductoAgrupado(String cdProveedor, String cdProductoOrig) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "4");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO_ORIG"   , cdProductoOrig);
	
		try {
			log.debug("Invocación al dao getProductoAgrupadoDAO: deleteProductoAgrupado()");
			result = getProductoAgrupadoDao().deleteProductoAgrupado(this.parameters);
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
	
	public List getProductosAgrupadosList() {
		return productosAgrupadosList;
	}

	public void setProductosAgrupadosList(List productosAgrupadosList) {
		this.productosAgrupadosList = productosAgrupadosList;
	}	
}
