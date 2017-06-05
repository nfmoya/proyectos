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
public class GetConciliacionConsultaService extends CommoConciliacionService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List conciliacionCabeceraList;
	
	public GetConciliacionConsultaService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.conciliacionCabeceraList = new ArrayList();
	}
	
	public Result getConsultaConciliacion(Integer cdConciliacion, String cdProveedor, String cdSector, String cdProducto, 
			                              String cdPeriodo) throws Exception {

		this.parameters.clear();
		this.parameters.put("pi_cd_conciliacion"  , cdConciliacion);
		this.parameters.put("pi_cd_proveedor"     , cdProveedor);
		this.parameters.put("pi_cd_sector"        , cdSector);
		this.parameters.put("pi_cd_producto"      , cdProducto);
		this.parameters.put("pi_cd_periodo"       , cdPeriodo);
		this.conciliacionCabeceraList.clear();
		
		try {
			log.debug("Invocación al dao getConciliacionDao: getConciliaciones()");
			result = getConciliacionDao().getConciliacionCabecera(this.parameters, this.conciliacionCabeceraList);
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
	
	public List getConciliacionCabeceraList() {
		return conciliacionCabeceraList;
	}

	public void setConciliacionCabeceraList(List conciliacionCabeceraList) {
		this.conciliacionCabeceraList = conciliacionCabeceraList;
	}
}
