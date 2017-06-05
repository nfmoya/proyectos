package com.bbva.cfs.servprestdetalle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.ar.utils.Validar;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.servfact.service.CommonServFactService;

public class GetServPrestDetalleService extends CommonServPrestDetalleService {

	private Result result;	
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List servFactList;
	
	@SuppressWarnings("rawtypes")
	public GetServPrestDetalleService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.servFactList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String prov,String sector,String grupo,String producto,String fhRemito,String fhFinServ,String remDesde,String remHasta,String lote,String stLoteDet) throws Exception {
		
		int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));

		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , prov);
		this.parameters.put("pi_CD_SECTOR"  , sector);
		this.parameters.put("pi_CD_GRUPOPRODUCTO"  , grupo);
        this.parameters.put("pi_CD_PRODUCTO"  , producto);
		this.parameters.put("pi_FH_REMITO"  , fhRemito);
		this.parameters.put("pi_FH_FIN_SERV"  , fhFinServ);
		this.parameters.put("pi_NM_REMITO_DESDE"  , remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA"  , remHasta);
		this.parameters.put("pi_NM_LOTE"  , loteint);
        this.parameters.put("pi_ST_LOTEDET"  , stLoteDet);

		System.out.println(this.parameters);
		this.servFactList.clear();
		try {
			log.debug("Invocaci�n al dao getServPrestDetalleDao: getServPrestDetalle(?,?,?,?,?,?,?,?,?,?)");
			result = getServPrestDetalleDao().getServPrestDetalle(this.parameters, this.servFactList);
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
	public List getSectoresList() {
		return servFactList;
	}

	@SuppressWarnings("rawtypes")
	public void setSectoresList(List sectoresList) {
		this.servFactList = sectoresList;
	}
}
