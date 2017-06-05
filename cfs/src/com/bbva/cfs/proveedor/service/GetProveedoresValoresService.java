package com.bbva.cfs.proveedor.service;

import java.math.BigDecimal;
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
import com.bbva.cfs.proveedor.service.CommoProveedorValorService;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProveedoresValoresService extends CommoProveedorValorService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List proveedoresValoresList;
	
	public GetProveedoresValoresService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.proveedoresValoresList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.proveedoresValoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorValorDao().getProveedoresValores(this.parameters, this.proveedoresValoresList);
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

		this.proveedoresValoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorValorDao().getProveedoresValores(this.parameters, this.proveedoresValoresList);
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

		this.proveedoresValoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorValorDao().getProveedoresValores(this.parameters, this.proveedoresValoresList);
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
	public Result execute(String cdProveedor, String cdPeriodoFact, String cdUniVal) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "3");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		this.parameters.put("pi_CD_UNIVAL"      , cdUniVal);

		this.proveedoresValoresList.clear();
		try {
			log.debug("Invocación al dao getProveedorDAO: GetProveedoresService()");
			result = getProveedorValorDao().getProveedoresValores(this.parameters, this.proveedoresValoresList);
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

	public Result saveProveedorValor(String opcion, String cdProveedor, String cdPeriodoFact, 
							String cdUniVal, String nuValBrutoUniVal, String nuValNetoUniVal, 
							String stHabilitado, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"         , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"      , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT"    , cdPeriodoFact);
		this.parameters.put("pi_CD_UNIVAL"         , cdUniVal);
		this.parameters.put("pi_NU_VALBRUTOUNIVAL" , nuValBrutoUniVal);
		this.parameters.put("pi_NU_VALNETOUNIVAL"  , nuValNetoUniVal);
		this.parameters.put("pi_ST_HABILITADO"     , stHabilitado);
		this.parameters.put("pi_USU_MODI"          , usuario);	
		try {
			log.debug("Invocación al dao getProveedorDAO: saveProveedorValor()");
			result = getProveedorValorDao().saveProveedorValor(this.parameters);
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

	public Result deleteProveedorValor(String cdProveedor, String cdPeriodoFact, String cdUniVal) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		this.parameters.put("pi_CD_UNIVAL"      , cdUniVal);
		
		try {
			log.debug("Invocación al dao getProveedorDAO: deleteProveedorValor()");
			result = getProveedorValorDao().deleteProveedorValor(this.parameters);
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
	
	public List getProveedoresValoresList() {
		return proveedoresValoresList;
	}

	public void setProveedoresValoresList(List proveedoresValoresList) {
		this.proveedoresValoresList = proveedoresValoresList;
	}
}
