package com.bbva.cfs.proveedor.service;

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
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.CommoProveedorTipoCambioService;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProveedoresTipoCambioService extends CommoProveedorTipoCambioService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List proveedoresTipoCambioList;
	
	public GetProveedoresTipoCambioService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.proveedoresTipoCambioList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.proveedoresTipoCambioList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorTipoCambioDao().getProveedoresTipoCambio(this.parameters, this.proveedoresTipoCambioList);
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

		this.proveedoresTipoCambioList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorTipoCambioDao().getProveedoresTipoCambio(this.parameters, this.proveedoresTipoCambioList);
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
	public Result execute(String cdProveedor, String cdPeriodoFact) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);

		this.proveedoresTipoCambioList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorTipoCambioDao().getProveedoresTipoCambio(this.parameters, this.proveedoresTipoCambioList);
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
	public Result execute(String cdProveedor, String cdPeriodoFact, String cdMoneda) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		this.parameters.put("pi_CD_MONEDA"      , cdMoneda);

		this.proveedoresTipoCambioList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorTipoCambioDao().getProveedoresTipoCambio(this.parameters, this.proveedoresTipoCambioList);
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

	public Result saveProveedorTipoCambio(String opcion, String cdProveedor, String cdPeriodoFact, 
							String cdMoneda, String nuDias, String cdCotizacion, String stHabilitado, 
							String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"         , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"      , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT"    , cdPeriodoFact);
		this.parameters.put("pi_CD_MONEDA"         , cdMoneda);
		this.parameters.put("pi_NU_DIAS"           , nuDias);
		this.parameters.put("pi_CD_COTIZACION"     , cdCotizacion);
		this.parameters.put("pi_ST_HABILITADO"     , stHabilitado);
		this.parameters.put("pi_USU_MODI"          , usuario);	
		try {
			log.debug("Invocación al dao getProveedorDAO: saveProveedorTipoCambio()");
			result = getProveedorTipoCambioDao().saveProveedorTipoCambio(this.parameters);
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

	public Result deleteProveedorTipoCambio(String cdProveedor, String cdPeriodoFact, String cdMoneda) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		this.parameters.put("pi_CD_MONEDA"      , cdMoneda);
		
		try {
			log.debug("Invocación al dao getProveedorDAO: deleteProveedorTipoCambio()");
			result = getProveedorTipoCambioDao().deleteProveedorTipoCambio(this.parameters);
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
	
	public List getProveedoresTipoCambioList() {
		return proveedoresTipoCambioList;
	}

	public void setProveedoresTipoCambioList(List proveedoresTipoCambioList) {
		this.proveedoresTipoCambioList = proveedoresTipoCambioList;
	}
}
