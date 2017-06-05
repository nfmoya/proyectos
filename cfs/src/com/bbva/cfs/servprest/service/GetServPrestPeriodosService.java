package com.bbva.cfs.servprest.service;

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

/**
 * TODO:
 * @author xah1198
 *
 */
@SuppressWarnings("rawtypes")
public class GetServPrestPeriodosService extends CommonServPrestService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List servPrestPeriodosList;
	
	public GetServPrestPeriodosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.servPrestPeriodosList = new ArrayList();
	}
	
        public Result execute(String prov,String sector,String prod,String grupo, String desde,
			      String hasta, String comprobante,String remDesde,String remHasta, String stLoteDet, String lote) throws Exception {
            
		Integer loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));

		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , (prov.equals("0"))?null:prov );
		this.parameters.put("pi_CD_SECTOR"  , (sector.equals("0"))?null:sector );
		this.parameters.put("pi_CD_PRODUCTO"  , (prod.equals("0"))?null:prod );
		this.parameters.put("pi_CD_GRUPO"  , (grupo.equals("0"))?null:grupo );
		this.parameters.put("pi_DESDE"  , desde);
		this.parameters.put("pi_HASTA"  , hasta);
		this.parameters.put("pi_NM_COMPROBANTE"  , (comprobante.equals("0"))?null:comprobante );
		this.parameters.put("pi_NM_REMITO_DESDE"  , (remDesde.equals("0"))?null:remDesde );
		this.parameters.put("pi_NM_REMITO_HASTA"  , (remHasta.equals("0"))?null:remHasta );
		this.parameters.put("pi_ST_LOTEDET"  , (stLoteDet.equals("0"))?null:stLoteDet );
		this.parameters.put("pi_NM_LOTE"  , loteint );		
                
		this.servPrestPeriodosList.clear();
		
		System.out.println (" LISTA DE PERIODOS " + "pi_CD_PROVEEDOR"  + prov 
		+ "pi_CD_SECTOR"  + sector 
		+"pi_CD_PRODUCTO"  + prod 
		+"pi_CD_GRUPO"  + grupo 
		+"pi_DESDE"  + desde
		+"pi_HASTA"  + hasta
		+"pi_NM_COMPROBANTE"  + comprobante 
		+"pi_NM_REMITO_DESDE"  + remDesde 
		+"pi_NM_REMITO_HASTA"  + remHasta
		+"pi_ST_LOTEDET"  + stLoteDet 
		+"pi_NM_LOTE"  + loteint );
                
		try {
			log.debug("Invocación al dao servPrestDao: GetServPrestPeriodosService()");
			result = getServPrestDao().getServPrestPeriodos(this.parameters, this.servPrestPeriodosList);
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
        
	public List getServPrestPeriodosList() {
		return servPrestPeriodosList;
	}
}
