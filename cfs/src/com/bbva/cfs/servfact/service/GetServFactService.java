package com.bbva.cfs.servfact.service;


import java.util.ArrayList;
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
import com.bbva.cfs.servfact.dao.ServFactDao;


/**
 * TODO:
 * @author xah1198
 *
 */
public class GetServFactService extends CommonServFactService {
	private Result result;	
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List servFactList;
	
	@SuppressWarnings("rawtypes")
	public GetServFactService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.servFactList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
/*	public Result execute(String prov,String sector,String prod,String grupo,String hab,String perDesde
			,String perHasta,String tipComp , String comprobante,String remDesde,String remHasta,String lote,int cant) throws Exception {
		
		int loteint = Integer.valueOf(lote == null ? "0" : (lote.isEmpty() ? "0" : lote));
		if(tipComp.equalsIgnoreCase("0"))
			tipComp = "";
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , prov);
		this.parameters.put("pi_CD_SECTOR"  , sector);
		this.parameters.put("pi_CD_PRODUCTO"  , prod);
		this.parameters.put("pi_CD_GRUPO"  , grupo);
		this.parameters.put("pi_CD_HABILITADO"  , hab);
		this.parameters.put("pi_CD_PERIODO_DESDE"  , perDesde);
		this.parameters.put("pi_CD_PERIODO_HASTA"  , perHasta);
		this.parameters.put("pi_CD_TIPCOMP"  , tipComp);
		this.parameters.put("pi_NM_COMPROBANTE"  , comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE"  , remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA"  , remHasta);
		this.parameters.put("pi_NM_LOTE"  , loteint);
		System.out.println(this.parameters);
		this.servFactList.clear();
		try {
			log.debug("Invocaci�n al dao getServFactDao: getServFact(?,?,?,?,?,?,?,?,?,?,?)");
			result = getServFactDao().getServFact(this.parameters, this.servFactList, cant);
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
	/**
	 * Metodo que llama al Stored procedure que trae los listados hacia abajo.
	 * @return
	 */
	public Result execute(String prov,String sector,String prod,String grupo,String hab,String perDesde
			,String perHasta,String tipComp , String comprobante,String remDesde,String remHasta,String lote, int reducida) throws Exception {
		
		int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));
		if(tipComp.equalsIgnoreCase("0"))
			tipComp = "";
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , prov);
		this.parameters.put("pi_CD_SECTOR"  , sector);
		this.parameters.put("pi_CD_PRODUCTO"  , prod);
		this.parameters.put("pi_CD_GRUPO"  , grupo);
		this.parameters.put("pi_CD_HABILITADO"  , hab);
		this.parameters.put("pi_CD_PERIODO_DESDE"  , perDesde);
		this.parameters.put("pi_CD_PERIODO_HASTA"  , perHasta);
		this.parameters.put("pi_CD_TIPCOMP"  , tipComp);
		this.parameters.put("pi_NM_COMPROBANTE"  , comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE"  , remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA"  , remHasta);
		this.parameters.put("pi_NM_LOTE"  , loteint);
		this.parameters.put("pi_REDUCIDA"  , reducida);
		System.out.println(this.parameters);
		this.servFactList.clear();
		try {
			log.debug("Invocaci�n al dao getServFactDao: getServFact(?,?,?,?,?,?,?,?,?,?,?)");
			result = getServFactDao().getServFact2(this.parameters, this.servFactList);
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
	
	@SuppressWarnings("rawtypes")
	public List getServFactList() {
		return servFactList;
	}

	@SuppressWarnings("rawtypes")
	public void setSectoresList(List sectoresList) {
		this.servFactList = sectoresList;
	}

	public Result execute(String prov,String sector,String grupo,String hab,String perDesde
			,String perHasta,String tipComp, String comprobante,String remDesde,String remHasta,String lote,int cant, String producto) throws Exception {
	int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));
	if(tipComp.equalsIgnoreCase("0"))
		tipComp = "";
		
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , prov);
		this.parameters.put("pi_CD_SECTOR"  , sector);
		this.parameters.put("pi_CD_PRODUCTO"  , producto);
		this.parameters.put("pi_CD_GRUPO"  , grupo);
		this.parameters.put("pi_CD_HABILITADO"  , hab);
		this.parameters.put("pi_CD_PERIODO_DESDE"  , perDesde);
		this.parameters.put("pi_CD_PERIODO_HASTA"  , perHasta);
		this.parameters.put("pi_CD_TIPCOMP"  , tipComp);
		this.parameters.put("pi_NM_COMPROBANTE"  , comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE"  , remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA"  , remHasta);
		this.parameters.put("pi_NM_LOTE"  , loteint);
		System.out.println(this.parameters);
		this.servFactList.clear();
		try {
			log.debug("Invocaci�n al dao getServFactDao: getServFactReduc(?,?,?,?,?,?,?,?,?,?,?)");
			result = getServFactDao().getServFactReduc(this.parameters, this.servFactList, cant);
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

	public Result executeDet(String prov,String sector,String prod,String grupo,String hab,String perDesde
			,String perHasta,String tipComp , String comprobante,String remDesde,String remHasta,String lote)  throws Exception {
	int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));
	if(tipComp.equalsIgnoreCase("0"))
		tipComp = "";

	if(prod.equalsIgnoreCase("0"))
		prod = "";
		
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , prov);
		this.parameters.put("pi_CD_SECTOR"  , sector);
		this.parameters.put("pi_CD_PRODUCTO"  , prod);
		this.parameters.put("pi_CD_GRUPO"  , grupo);
		this.parameters.put("pi_CD_HABILITADO"  , hab);
		this.parameters.put("pi_CD_PERIODO_DESDE"  , perDesde);
		this.parameters.put("pi_CD_PERIODO_HASTA"  , perHasta);
		this.parameters.put("pi_CD_TIPCOMP"  , tipComp);
		this.parameters.put("pi_NM_COMPROBANTE"  , comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE"  , remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA"  , remHasta);
		this.parameters.put("pi_NM_LOTE"  , loteint);
		System.out.println(this.parameters);
		this.servFactList.clear();
		try {
			log.debug("Invocaci�n al dao getServFactDao: getServFactDet(?,?,?,?,?,?,?,?,?,?,?)");
			result = getServFactDao().getServFactDet(this.parameters, this.servFactList);
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
}
