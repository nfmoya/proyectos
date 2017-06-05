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
import com.bbva.cfs.proveedor.service.CommoProveedorService;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProveedoresService extends CommoProveedorService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List proveedoresList;
	
	public GetProveedoresService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.proveedoresList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		this.parameters.put("pi_NU_CUIT"      , 0);
		
		this.proveedoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorDao().getProveedores(this.parameters, this.proveedoresList);
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
		this.parameters.put("pi_id_opcion"    , "3");
		this.parameters.put("pi_CD_PROVEEDOR" , cdProveedor);
		this.parameters.put("pi_NU_CUIT"      , 0);
		this.proveedoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorDao().getProveedores(this.parameters, this.proveedoresList);
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

	public Result saveProveedor(String opcion, String cdProveedor, String nbProveedor, 
							String nbProveedorCorto, Long nuCuit, String nbAtributoProv1, 
							String nbAtributoProv2, String nbAtributoProv3, String nbAtributoProv4,
							String nbAtributoProv5, String stHabilitado, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"         , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"      , cdProveedor);
		this.parameters.put("pi_NB_PROVEEDOR"      , nbProveedor);
		this.parameters.put("pi_NB_PROVEEDORCORTO" , nbProveedorCorto);
		this.parameters.put("pi_NU_CUIT"           , nuCuit);
		this.parameters.put("pi_NB_ATRIBUTOPROV1"  , nbAtributoProv1);
		this.parameters.put("pi_NB_ATRIBUTOPROV2"  , nbAtributoProv2);
		this.parameters.put("pi_NB_ATRIBUTOPROV3"  , nbAtributoProv3);
		this.parameters.put("pi_NB_ATRIBUTOPROV4"  , nbAtributoProv4);
		this.parameters.put("pi_NB_ATRIBUTOPROV5"  , nbAtributoProv5);
		this.parameters.put("pi_ST_HABILITADO"     , stHabilitado);
		this.parameters.put("pi_USU_MODI"          , usuario);	
		try {
			log.debug("Invocación al dao getProveedorDAO: saveProveedor()");
			result = getProveedorDao().saveProveedor(this.parameters);
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

	public Result deleteProveedor(String cdProveedor) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "4");
		this.parameters.put("pi_CD_PROVEEDOR" , cdProveedor);
		
		try {
			log.debug("Invocación al dao getProveedorDAO: deleteProveedor()");
			result = getProveedorDao().deleteProveedor(this.parameters);
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
	
	public List getProveedoresList() {
		return proveedoresList;
	}

	public void setProveedoresList(List proveedoresList) {
		this.proveedoresList = proveedoresList;
	}
}
