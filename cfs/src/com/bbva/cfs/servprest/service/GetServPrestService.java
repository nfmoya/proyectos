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
import com.bbva.cfs.servfact.service.CommonServFactService;

public class GetServPrestService extends CommonServPrestService {

	private Result result;
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List servPrestList;

	@SuppressWarnings("rawtypes")
	public GetServPrestService(IWebClient iWebClient) {
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.servPrestList = new ArrayList();
	}

	/**
	 * TODO:
	 * 
	 * @return
	 */
	public Result execute(String prov, String sector, String prod,
			String grupo, String desde, String hasta, String comprobante,
			String remDesde, String remHasta, String stLoteDet, String lote,
			int cant) throws Exception {

		Integer loteint = Integer.valueOf(lote == null ? "0"
				: (Validar.esVacio(lote) ? "0" : lote));

		this.parameters.clear();
		this.parameters
				.put("pi_CD_PROVEEDOR", (prov.equals("0")) ? null : prov);
		this.parameters.put("pi_CD_SECTOR", (sector.equals("0")) ? null
				: sector);
		this.parameters.put("pi_CD_PRODUCTO", (prod.equals("0")) ? null : prod);
		this.parameters.put("pi_CD_GRUPO", (grupo.equals("0")) ? null : grupo);
		this.parameters.put("pi_DESDE", desde);
		this.parameters.put("pi_HASTA", hasta);
		this.parameters.put("pi_NM_COMPROBANTE",
				(comprobante.equals("0")) ? null : comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE", (remDesde.equals("0")) ? null
				: remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA", (remHasta.equals("0")) ? null
				: remHasta);
		this.parameters.put("pi_NM_LOTE", loteint);
		this.parameters.put("pi_ST_LOTEDET", stLoteDet);

		System.out.println(this.parameters);
		this.servPrestList.clear();
		try {
			log.debug("Invocaci�n al dao getServPRESTDao: getServPrest(?,?,?,?,?,?,?,?,?,?)");
			result = getServPrestDao().getServPrest(this.parameters,
					this.servPrestList, cant);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(
					ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	public Result execute(String prov, String sector, String grupo, String desde, String hasta, String comprobante, String remDesde, String remHasta, String stLoteDet, String lote,
			int cant) throws Exception {

		Integer loteint = Integer.valueOf(lote == null ? "0"
				: (Validar.esVacio(lote) ? "0" : lote));

		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR", (prov.equals("0")) ? null : prov);
		this.parameters.put("pi_CD_SECTOR", (sector.equals("0")) ? null	: sector);
		this.parameters.put("pi_CD_PRODUCTO", "");
		this.parameters.put("pi_CD_GRUPO", (grupo.equals("0")) ? null : grupo);
		this.parameters.put("pi_DESDE", desde);
		this.parameters.put("pi_HASTA", hasta);
		this.parameters.put("pi_NM_COMPROBANTE",(comprobante.equals("0")) ? null : comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE", (remDesde.equals("0")) ? null	: remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA", (remHasta.equals("0")) ? null	: remHasta);
		this.parameters.put("pi_NM_LOTE", loteint);
		this.parameters.put("pi_ST_LOTEDET", stLoteDet);

		System.out.println(this.parameters);
		this.servPrestList.clear();
		try {
			log.debug("Invocaci�n al dao getServPRESTDao: getServPrestRed(?,?,?,?,?,?,?,?,?,?)");
			result = getServPrestDao().getServPrestReduc(this.parameters, this.servPrestList, cant);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(
					ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}


	public Result execute(String prov,String sector,String prod,String grupo,
			String hab,String perDesde
			,String perHasta,String tipComp , String comprobante,String remDesde,String remHasta,String stLote, String lote, int reducida) throws Exception {
//		sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
//		sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), sfForm.getHabilitadoSel(),
//		sfForm.getFhDesde(), sfForm.getFhHasta(),sfForm.getSelectedComp(), sfForm.getCdComprobante(), 
//		sfForm.getRemitoDesde(), sfForm.getRemitoHasta(), sfForm.getCdLote(), 0
		int loteint = Integer.valueOf(lote == null ? "0" : (Validar.esVacio(lote) ? "0" : lote));
//		if(tipComp.equalsIgnoreCase("0"))
//			tipComp = "";
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR", (prov.equals("0")) ? null : prov);
		this.parameters.put("pi_CD_SECTOR", (sector.equals("0")) ? null	: sector);
		this.parameters.put("pi_CD_PRODUCTO", prod);
		this.parameters.put("pi_CD_GRUPO", (grupo.equals("0")) ? null : grupo);
		this.parameters.put("pi_DESDE", perDesde);
		this.parameters.put("pi_HASTA", perHasta);
		this.parameters.put("pi_NM_COMPROBANTE",(comprobante.equals("0")) ? null : comprobante);
		this.parameters.put("pi_NM_REMITO_DESDE", (remDesde.equals("0")) ? null	: remDesde);
		this.parameters.put("pi_NM_REMITO_HASTA", (remHasta.equals("0")) ? null	: remHasta);
		this.parameters.put("pi_ST_LOTEDET", stLote);
		this.parameters.put("pi_NM_LOTE", loteint);
		this.parameters.put("pi_REDUCIDA"  , reducida);
		System.out.println(this.parameters);
		this.servPrestList.clear();
		try {
			log.debug("Invocaci�n al dao getServPresDao: getServPres(?,?,?,?,?,?,?,?,?,?,?)");
			result = getServPrestDao().getServPrest2(this.parameters, this.servPrestList);
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
		return servPrestList;
	}

	@SuppressWarnings("rawtypes")
	public void setSectoresList(List sectoresList) {
		this.servPrestList = sectoresList;
	}

}
