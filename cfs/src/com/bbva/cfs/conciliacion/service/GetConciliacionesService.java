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
import com.bbva.cfs.conciliacion.form.ConciliacionForm;

@SuppressWarnings("rawtypes")
public class GetConciliacionesService extends CommoConciliacionService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List conciliacionesList;
	private List conciliacionCabeceraList;
	@SuppressWarnings("unused")
	private Integer conciliacion;
	private List conciliacionList;
	@SuppressWarnings("unused")
	private List<Integer> conciliacionNro;
	private List busqConciliacionesList;
	private List productosList;
	
	public GetConciliacionesService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.conciliacionesList = new ArrayList();
		this.conciliacionCabeceraList = new ArrayList();
		this.conciliacionList = new ArrayList();
		this.busqConciliacionesList = new ArrayList();
		this.productosList = new ArrayList();
		this.conciliacion = 0;
	}
	
	public Result execute(String cdProveedor, String cdSector, String cdProducto, String cdPeriodo,
                          String fhRemitoDesde, String fhRemitoHasta, String fhFinServDesde, String fhFinServHasta,	
                          String stConciliacion, Integer cdConciliacion, Integer cantBilletes) 
                        		  throws Exception {

		this.parameters.clear();

		this.parameters.put("pi_cd_proveedor"    , cdProveedor);
		this.parameters.put("pi_cd_sector"       , cdSector);
		this.parameters.put("pi_cd_producto"     , cdProducto);
		this.parameters.put("pi_cd_periodo"      , cdPeriodo);
		this.parameters.put("pi_fh_remitodesde"  , fhRemitoDesde);
		this.parameters.put("pi_fh_remitohasta"  , fhRemitoHasta);
		this.parameters.put("pi_fh_finservdesde" , fhFinServDesde);
		this.parameters.put("pi_fh_finservhasta" , fhFinServHasta);
		this.parameters.put("pi_st_conciliacion" , stConciliacion);
		this.parameters.put("pi_cd_conciliacion" , cdConciliacion);
		this.parameters.put("pi_nu_billetes"     , cantBilletes);
//		this.parameters.put("pi_cd_tipocambio"   , tipoCambio);
		
		this.conciliacionesList.clear();
		try {
			log.debug("Invocación al dao getConciliacionDao: getConciliaciones()");
			result = getConciliacionDao().getConciliaciones(this.parameters, this.conciliacionesList);
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
		
	public Result execute(ConciliacionForm cf) throws Exception {

		this.parameters.clear();

		this.parameters.put("pi_cd_proveedor"    , cf.getCdProveedor());
		this.parameters.put("pi_cd_sector"       , cf.getCdSector());
		this.parameters.put("pi_cd_producto"     , cf.getCdProducto());
		this.parameters.put("pi_cd_periodo"      , cf.getCdPeriodoFact());
		this.parameters.put("pi_fh_remitodesde"  , cf.getFhRemitoDesde());
		this.parameters.put("pi_fh_remitohasta"  , cf.getFhRemitoHasta());
		this.parameters.put("pi_fh_finservdesde" , cf.getFhFinServicioDesde());
		this.parameters.put("pi_fh_finservhasta" , cf.getFhFinServicioHasta());
		this.parameters.put("pi_cd_conciliacion" , cf.getCdConciliacion());
		this.parameters.put("pi_nu_billetes"     , cf.getCantBilletes());
//		this.parameters.put("pi_cd_tipocambio"   , cf.getTipoCambio());
		
		this.conciliacionesList.clear();
		try {
			log.debug("Invocación al dao getConciliacionDao: getConciliaciones()");
			result = getConciliacionDao().getConciliaciones(this.parameters, this.conciliacionesList);
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
	
	public Result saveConciliacion(Integer cdConciliacion, String cdProveedor, String cdSector, String cdProducto, 
			String cdPeriodo, String stIgnoraval, String nbObservaciones, String stConciliacion, String fhRemitoDesde,
		    String fhRemitoHasta, String fhFinSerDesde, String fhFinSerHasta, List listaServPres, List listaServFact, 
		    String listaServDife, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_cd_conciliacion"  , cdConciliacion);
		this.parameters.put("pi_cd_proveedor"     , cdProveedor);
		this.parameters.put("pi_cd_sector"        , cdSector);
		this.parameters.put("pi_cd_producto"      , cdProducto);
		this.parameters.put("pi_cd_periodo"       , cdPeriodo);
		this.parameters.put("pi_st_ignoraval"     , stIgnoraval);
		this.parameters.put("pi_nb_observaciones" , nbObservaciones);
		this.parameters.put("pi_st_conciliacion"  , stConciliacion);
		this.parameters.put("pi_fh_remitodesde"   , fhRemitoDesde);
		this.parameters.put("pi_fh_remitohasta"   , fhRemitoHasta);
		this.parameters.put("pi_fh_finservdesde"  , fhFinSerDesde);
		this.parameters.put("pi_fh_finservhasta"  , fhFinSerHasta);
		this.parameters.put("pi_ls_servdife"      , listaServDife);
		this.parameters.put("pi_usu_modi"         , usuario);	

		Integer cdConciliacionCab = 0;
		
		try {
			log.debug("Invocación al dao getConciliacionDao: saveConciliacion()");
			result = getConciliacionDao().saveConciliacion(this.parameters, this.conciliacionList);
			
			cdConciliacionCab = (Integer)getConciliacionList().get(0);
			
			saveConciliacionPrestados(cdConciliacionCab, stConciliacion, listaServPres);
			saveConciliacionFacturados(cdConciliacionCab, listaServFact);
			
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
	
	private Result saveConciliacionPrestados(Integer cdConciliacion, String stConciliacion, List listaServPres) throws Exception {
		for (int i=0;i<listaServPres.size();i++) {
           this.parameters.clear();
           this.parameters.put("pi_cd_conciliacion"  , cdConciliacion);
           this.parameters.put("pi_st_conciliacion"  , stConciliacion);
           this.parameters.put("pi_ls_servpres"      , listaServPres.get(i).toString());
		
           try {
              log.debug("Invocación al dao getConciliacionDao: saveConciliacion()");
              result = getConciliacionDao().saveConciliacionPrestados(this.parameters);
           } catch (DaoException e) {
              result = new Result();
              result.setErrorCode(e.getCode());
              result.setErrorDesc(e.getMessage());
           } catch (Exception e) {
              log.debug(e);
              throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
           }
		}
		return result;
	}

	private Result saveConciliacionFacturados(Integer cdConciliacion, List listaServFact) throws Exception {
		for (int i=0;i<listaServFact.size();i++) {
           this.parameters.clear();
           this.parameters.put("pi_cd_conciliacion"  , cdConciliacion);
           this.parameters.put("pi_ls_servfact"      , listaServFact.get(i).toString());
		
           try {
              log.debug("Invocación al dao getConciliacionDao: saveConciliacion()");
              result = getConciliacionDao().saveConciliacionFacturados(this.parameters);
           } catch (DaoException e) {
              result = new Result();
              result.setErrorCode(e.getCode());
              result.setErrorDesc(e.getMessage());
           } catch (Exception e) {
              log.debug(e);
              throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
           }
		}
		return result;
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result getDatosProducto(String cdProveedor, String cdSector, String cdProducto) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"  , cdProveedor);
		this.parameters.put("pi_CD_SECTOR"     , cdSector);
		this.parameters.put("pi_CD_PRODUCTO"   , cdProducto);
		this.productosList.clear();
		try {
			log.debug("Invocación al dao getProductoDAO: GetProductosService()");
			result = getConciliacionDao().getProductosConciliacion(this.parameters, this.productosList);
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
	
	
	public Result getConsultaConciliacion(Integer cdConciliacion) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_cd_conciliacion" , cdConciliacion);	
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
	
	public Result executeConciliaciones(String cdProveedor, String cdSector,
			String fhDesde, String fhHasta, String cdProducto,
			String estadoDif, String stDiferencia, String cdConciliacion, Boolean check) throws Exception { 
//			,Integer cantBilletes, String tipoCambio
		
		this.parameters.clear();

		this.parameters.put("pi_cdProveedor",cdProveedor.equalsIgnoreCase("0") ? "" : cdProveedor);
		this.parameters.put("pi_cdSector", cdSector.equalsIgnoreCase("0") ? "" : cdSector);
		this.parameters.put("pi_fhDesde", fhDesde.equalsIgnoreCase("0")? "" : fhDesde);
		this.parameters.put("pi_fhHasta", fhHasta.equalsIgnoreCase("0")? "" : fhHasta);
		this.parameters.put("cdProducto", cdProducto.equalsIgnoreCase("0") ? "" : cdProducto);
		this.parameters.put("estadoDif", estadoDif.equalsIgnoreCase("0") ? "" : estadoDif);
		this.parameters.put("stDiferencia", stDiferencia.equalsIgnoreCase("0") ? "" : stDiferencia);
		this.parameters.put("cdConciliacion", cdConciliacion.equals("") ? -1 : Integer.parseInt(cdConciliacion));
		this.parameters.put("isCheck",check);
/*
		this.parameters.put("cantBilletes", cantBilletes);
		this.parameters.put("tipoCambio"  , tipoCambio);
*/		
		try {
			log.debug("Invocación al dao getConciliacionDao: buscarConciliaciones()");
			result = getConciliacionDao().buscarConciliaciones(this.parameters,
					busqConciliacionesList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new GlobalServiceException(
					ResultDatabase.SERVICE_ERROR.getCode(),
					ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	public Result anularConciliacion(String user, String cdConciliacion,
			String observaciones,Boolean check) throws Exception {
		// TODO Auto-generated method stub
		this.parameters.clear();

		this.parameters.put("cdConciliacion", Integer.parseInt(cdConciliacion));
		this.parameters.put("observaciones", observaciones);
		this.parameters.put("userAnulacion", user);
		this.parameters.put("isCheck",check);
		try {
			log.debug("Invocación al dao getConciliacionDao: anulacionConciliacion()");

			result = getConciliacionDao()
					.anulacionConciliacion(this.parameters);

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
	
	public List getConciliacionesList() {
		return conciliacionesList;
	}

	public void setConciliacionesList(List conciliacionesList) {
		this.conciliacionesList = conciliacionesList;
	}

	public List getConciliacionCabeceraList() {
		return conciliacionCabeceraList;
	}

	public void setConciliacionCabeceraList(List conciliacionCabeceraList) {
		this.conciliacionCabeceraList = conciliacionCabeceraList;
	}
	
	public List getConciliacionList() {
		return conciliacionList;
	}

	public void setConciliacionList(List conciliacionList) {
		this.conciliacionList = conciliacionList;
	}
	
	public List getProductosList() {
		return productosList;
	}

	public void setProductosList(List productosList) {
		this.productosList = productosList;
	}
	
	public List getBusqConciliacionesList() {
		return busqConciliacionesList;
	}

	public void setBusqConciliacionesList(List busqConciliacionesList) {
		this.busqConciliacionesList = busqConciliacionesList;
	}
}
