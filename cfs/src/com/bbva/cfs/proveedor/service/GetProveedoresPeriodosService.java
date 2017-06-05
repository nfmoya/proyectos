package com.bbva.cfs.proveedor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.ar.utils.Validar;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
//import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.CommoProveedorPeriodoService;

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetProveedoresPeriodosService extends CommoProveedorPeriodoService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List proveedoresPeriodosList;
	
	public GetProveedoresPeriodosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.proveedoresPeriodosList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"    , "3");
		
		this.proveedoresPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: GetProveedoresService()");
			result = getProveedorPeriodoDao().getProveedoresPeriodos(this.parameters, this.proveedoresPeriodosList);
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
		this.proveedoresPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: GetProveedoresService()");
			result = getProveedorPeriodoDao().getProveedoresPeriodos(this.parameters, this.proveedoresPeriodosList);
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
		this.proveedoresPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: GetProveedoresService()");
			result = getProveedorPeriodoDao().getProveedoresPeriodos(this.parameters, this.proveedoresPeriodosList);
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
	public Result execute(String cdProveedor, String cdPerDesde, String cdPerHasta, String lote) throws Exception {
		int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));
		
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PER_DESDE"   , cdPerDesde);
		this.parameters.put("pi_CD_PER_HASTA" , cdPerHasta);
		this.parameters.put("pi_NM_LOTE" , loteint);
		this.proveedoresPeriodosList.clear();
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: getPeriodosDesdeHasta()");
			result = getProveedorPeriodoDao().getPeriodosDesdeHasta(this.parameters, this.proveedoresPeriodosList);
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
	public Result saveProveedorPeriodo(String opcion, String cdProveedor, String cdPeriodoFact, 
									   String nbPeriodoFact, String cdPerFactAlt, String fhDesde,
									   String fhHasta, String stEstado, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , opcion);
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		this.parameters.put("pi_NB_PERIODOFACT" , nbPeriodoFact);
		this.parameters.put("pi_CD_PERFACTALT"  , cdPerFactAlt);
		this.parameters.put("pi_FH_DESDE"       , fhDesde);
		this.parameters.put("pi_FH_HASTA"       , fhHasta);
		this.parameters.put("pi_ST_ESTADO"      , stEstado);
		this.parameters.put("pi_USU_MODI"       , usuario);	
				
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: saveProveedorPeriodo()");
			result = getProveedorPeriodoDao().saveProveedorPeriodo(this.parameters);
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

	public Result deleteProveedorPeriodo(String cdProveedor, String cdPeriodoFact) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"      , "4");
		this.parameters.put("pi_CD_PROVEEDOR"   , cdProveedor);
		this.parameters.put("pi_CD_PERIODOFACT" , cdPeriodoFact);
		
		try {
			log.debug("Invocaci�n al dao getProveedorPeriodoDAO: deleteProveedorPeriodo()");
			result = getProveedorPeriodoDao().deleteProveedorPeriodo(this.parameters);
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
	
	public List getProveedoresPeriodosList() {
		return proveedoresPeriodosList;
	}

	public void setProveedoresPeriodosList(List proveedoresPeriodosList) {
		this.proveedoresPeriodosList = proveedoresPeriodosList;
	}
}
