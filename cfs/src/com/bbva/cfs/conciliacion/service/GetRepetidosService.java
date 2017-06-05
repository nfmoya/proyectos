package com.bbva.cfs.conciliacion.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.conciliacion.service.CommoConciliacionService;

@SuppressWarnings("rawtypes")
public class GetRepetidosService extends CommoConciliacionService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List repetidosList;
	
	public GetRepetidosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.repetidosList = new ArrayList();
	}
	
	public Result getRepetidos(String cdProveedor, String cdProducto, String cdPeriodo, 
			                   String cdSector, Integer cdConciliacion) throws Exception {

		this.parameters.clear();
		this.parameters.put("pi_cd_proveedor"    , cdProveedor);
		this.parameters.put("pi_cd_producto"     , cdProducto);
		this.parameters.put("pi_cd_periodo"      , cdPeriodo);
		this.parameters.put("pi_cd_sector"       , cdSector);
		this.parameters.put("pi_cd_conciliacion" , cdConciliacion);
		
		this.repetidosList.clear();
		
		try {
			log.debug("Invocación al dao getConciliacionDao: getRepetidos()");
			result = getConciliacionDao().getRepetidos(this.parameters, this.repetidosList);
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
	
	public List getRepetidosList() {
		return repetidosList;
	}

	public void setRepetidosList(List repetidosList) {
		this.repetidosList = repetidosList;
	}
}
