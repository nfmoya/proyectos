package com.bbva.cfs.producto.service;

import java.util.ArrayList;
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
public class GetProductosPeriodosTarifasService extends CommoProductoPeriodoTarifaService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List productosPeriodosTarifasList;
	private List nuevoCodigoTarifaList;	
	
	public GetProductosPeriodosTarifasService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.productosPeriodosTarifasList = new ArrayList();
		this.nuevoCodigoTarifaList = new ArrayList();		
	}

/*
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.productosPeriodosTarifasList.clear();
		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: GetProductosPeriodosTarifas()");
			result = getProductoPeriodoTarifaDao().getProductosPeriodosTarifas(this.parameters, this.productosPeriodosTarifasList);
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

	public Result execute(String cdProveedor) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.productosPeriodosTarifasList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoTarifaDao: getProductosPeriodosTarifas()");
			result = getProductoPeriodoTarifaDao().getProductosPeriodosTarifas(this.parameters, this.productosPeriodosTarifasList);
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

	public Result execute(String cdProveedor, String cdProducto) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.productosPeriodosTarifasList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoPeriodoTarifaDao: getProductosPeriodosTarifas()");
			result = getProductoPeriodoTarifaDao().getProductosPeriodosTarifas(this.parameters, this.productosPeriodosTarifasList);
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
*/	

	public Result execute(String cdProveedor, String cdProducto, String fhDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"    , cdProducto);
		this.parameters.put("pi_FH_DESDE"       , fhDesde);
		this.productosPeriodosTarifasList.clear();
		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: getProductosPeriodosTarifas()");
			result = getProductoPeriodoTarifaDao().getProductosPeriodosTarifas(this.parameters, this.productosPeriodosTarifasList);
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
	
	public Result getNuevoCodigoTarifa(String cdProveedor, String cdProducto, String fhDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_FH_DESDE"           , fhDesde);
		this.parameters.put("po_NU_CANTDESDE_NUEVO" , "0");

		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: getNuevoCodigoTarifa()");
			result = getProductoPeriodoTarifaDao().getNuevoCodigoTarifa(this.parameters, this.nuevoCodigoTarifaList);
		} catch (DaoException e) {
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR
					.getCode(), ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

	public Result saveProductoPeriodoTarifa(String opcion, String cdProveedor, String cdProducto, 
			                         String fhDesde, String nuCantDesde, String nuCantHasta, 
			                         String nuPrecioUniVal, String nuPorcTarifa, String stHabilitado, 
			                         String stPrecioFijo, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"        , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"     , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"      , cdProducto);
		this.parameters.put("pi_FH_DESDE"         , fhDesde);
		this.parameters.put("pi_NU_CANTDESDE"     , nuCantDesde);
		this.parameters.put("pi_NU_CANTHASTA"     , nuCantHasta);
		this.parameters.put("pi_NU_PRECIOUNIVAL"  , nuPrecioUniVal);
		this.parameters.put("pi_NU_PORCTARIFA"    , nuPorcTarifa);
		this.parameters.put("pi_ST_HABILITADO"    , stHabilitado);
		this.parameters.put("pi_ST_PRECIOFIJO"    , stPrecioFijo);
		this.parameters.put("pi_USU_MODI"         , usuario);	
				
		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: saveProductoPeriodoTarifa()");
			result = getProductoPeriodoTarifaDao().saveProductoPeriodoTarifa(this.parameters);
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

	public Result deleteProductoPeriodoTarifa(String cdProveedor, String cdProducto, String fhDesde, String nuCantDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"        , "4");
		this.parameters.put("pi_CD_PROVEEDOR"     , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"      , cdProducto);
		this.parameters.put("pi_FH_DESDE"         , fhDesde);
		this.parameters.put("pi_NU_CANTDESDE"     , nuCantDesde);
		this.parameters.put("pi_NU_CANTHASTA"     , "0");
		this.parameters.put("pi_NU_PRECIOUNIVAL"  , "0");	
		this.parameters.put("pi_NU_PORCTARIFA"    , "0");		
		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: deleteProductoPeriodoTarifa()");
			result = getProductoPeriodoTarifaDao().deleteProductoPeriodoTarifa(this.parameters);
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
	
	public Result deleteProductoPeriodoTarifas(String cdProveedor, String cdProducto, String fhDesde) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"        , "5");
		this.parameters.put("pi_CD_PROVEEDOR"     , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"      , cdProducto);
		this.parameters.put("pi_FH_DESDE"         , fhDesde);
		this.parameters.put("pi_NU_CANTDESDE"     , "0");
		this.parameters.put("pi_NU_CANTHASTA"     , "0");
		this.parameters.put("pi_NU_PRECIOUNIVAL"  , "0");	
		this.parameters.put("pi_NU_PORCTARIFA"    , "0");		
		try {
			log.debug("Invocación al dao getProductoPeriodoTarifaDao: deleteProductoPeriodoTarifa()");
			result = getProductoPeriodoTarifaDao().deleteProductoPeriodoTarifas(this.parameters);
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
	
	public List getProductosPeriodosTarifasList() {
		return productosPeriodosTarifasList;
	}

	public void setProductosPeriodosTarifasList(List productosPeriodosTarifaaList) {
		this.productosPeriodosTarifasList = productosPeriodosTarifasList;
	}
	
	public List getNuevoCodigoTarifaList() {
		return nuevoCodigoTarifaList;
	}

	public void setNuevoCodigoTarifaList(List nuevoCodigoTarifaList) {
		this.nuevoCodigoTarifaList = nuevoCodigoTarifaList;
	}
	
	
	
}
