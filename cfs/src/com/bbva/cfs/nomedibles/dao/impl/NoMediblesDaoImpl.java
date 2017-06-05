package com.bbva.cfs.nomedibles.dao.impl;

//import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.nomedibles.dao.NoMediblesDao;
//import com.bbva.cfs.nomedibles.form.BusquedaConciliacionesForm;
import com.bbva.cfs.nomedibles.model.Conciliacion;
import com.bbva.cfs.nomedibles.model.ConciliacionCabecera;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.conciliacion.model.ProductoConciliacion;

/**
 * 
 * @author xah1198
 * 
 */
public class NoMediblesDaoImpl implements NoMediblesDao {

	static final Log log = LogFactory.getLog(NoMediblesDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public NoMediblesDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getConciliaciones(Map parameters, List conciliacionesList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.NOMEDIBLES_LISTA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			Conciliacion conciliaciones;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				conciliaciones = new Conciliacion();
				
				conciliaciones
					.setCdProducto(dynaBean.get("CD_PRODUCTO") != null 
					? dynaBean.get("CD_PRODUCTO").toString()
					: "");

				conciliaciones
					.setCdPeriodoAnt(dynaBean.get("CD_PERIODO_ANT") != null 
					? dynaBean.get("CD_PERIODO_ANT").toString()
					: "");
				
				conciliaciones
   				    .setCtServFactAnt(dynaBean.get("CT_SERVFACT_ANT") != null 
				    ? new BigDecimal(dynaBean.get("CT_SERVFACT_ANT").toString()) 
				    : BigDecimal.ZERO);
				
				conciliaciones
					.setCdUniValAnt(dynaBean.get("CD_UNIVAL_ANT") != null 
					? dynaBean.get("CD_UNIVAL_ANT").toString()
					: "");				

				conciliaciones
					.setImPrecioTotalAnt(dynaBean.get("IM_PRECIOTOTAL_ANT") != null 
				    ? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL_ANT").toString()) 
				    : BigDecimal.ZERO);

				conciliaciones
					.setCdConciliacionAnt(dynaBean.get("CD_CONCILIACION_ANT") != null 
				    ? new Integer(dynaBean.get("CD_CONCILIACION_ANT").toString()) 
				    : 0);

				conciliaciones
				    .setCtServFactAct(dynaBean.get("CT_SERVFACT_ACT") != null 
				    ? new BigDecimal(dynaBean.get("CT_SERVFACT_ACT").toString()) 
				    : BigDecimal.ZERO);

				conciliaciones
					.setCdUniValAct(dynaBean.get("CD_UNIVAL_ACT") != null 
					? dynaBean.get("CD_UNIVAL_ACT").toString()
					: "");				

				conciliaciones
					.setImPrecioTotalAct(dynaBean.get("IM_PRECIOTOTAL_ACT") != null 
				    ? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL_ACT").toString()) 
				    : BigDecimal.ZERO);
			
				conciliaciones
					.setNuPorcVarMax(dynaBean.get("NU_PORCVARMAX") != null 
				    ? new BigDecimal(dynaBean.get("NU_PORCVARMAX").toString()) 
				    : BigDecimal.ZERO);
				
				conciliaciones
					.setNuPorcVarVal(dynaBean.get("NU_PORCVARVAL") != null 
				    ? new BigDecimal(dynaBean.get("NU_PORCVARVAL").toString()) 
				    : BigDecimal.ZERO);
				
				conciliaciones
					.setCdConciliacionAct(dynaBean.get("CD_CONCILIACION_ACT") != null 
				    ? new Integer(dynaBean.get("CD_CONCILIACION_ACT").toString()) 
				    : 0);
			
				conciliaciones
					.setImDiferencia(dynaBean.get("IM_DIFERENCIA") != null 
				    ? new BigDecimal(dynaBean.get("IM_DIFERENCIA").toString()) 
				    : BigDecimal.ZERO);
				
				conciliaciones
					.setNbObservaciones(dynaBean.get("NB_OBSERVACIONES") != null 
					? dynaBean.get("NB_OBSERVACIONES").toString()
					: "");

				conciliaciones
					.setTpSolucion(dynaBean.get("TP_SOLUCION") != null 
					? dynaBean.get("TP_SOLUCION").toString()
					: "");

			    conciliacionesList.add(conciliaciones);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getConciliacionCabecera(Map parameters, List conciliacionCabeceraList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.NOMEDIBLES_CONSULTA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			ConciliacionCabecera conciliacion;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				conciliacion = new ConciliacionCabecera();

				conciliacion
					.setCdConciliacion(new Integer(dynaBean.get("CD_CONCILIACION").toString()));

				conciliacion
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				conciliacion
					.setCdSector(dynaBean.get("CD_SECTOR") != null 
					? dynaBean.get("CD_SECTOR").toString()
					: "");
			
				conciliacion
   				    .setCdPeriodoFact(dynaBean.get("CD_PERIODOFACT") != null 
				    ? dynaBean.get("CD_PERIODOFACT").toString()
				    : "");

			    conciliacion
				    .setStConciliacion(dynaBean.get("ST_CONCILIACION") != null 
				    ? dynaBean.get("ST_CONCILIACION").toString()
				    : "");

			    conciliacionCabeceraList.add(conciliacion);
		    
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductosConciliacion(Map parameters, List productosList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.CONCILIACION_PRODUCTO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			ProductoConciliacion productos;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productos = new ProductoConciliacion();
				
				productos
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				productos
					.setCdProducto(dynaBean.get("CD_PRODUCTO") != null 
					? dynaBean.get("CD_PRODUCTO").toString()
					: "");

				productos
					.setNbProducto(dynaBean.get("NB_PRODUCTO") != null 
					? dynaBean.get("NB_PRODUCTO").toString()
					: "");
 
				productos
					.setCdUniVal(dynaBean.get("CD_UNIVAL") != null 
					? dynaBean.get("CD_UNIVAL").toString()
					: "");

				productos
					.setStConcilSinVal(dynaBean.get("ST_CONCILSINVAL") != null 
					? dynaBean.get("ST_CONCILSINVAL").toString()
					: "");

				productos
					.setStServSinConcil(dynaBean.get("ST_SERVSINCONCIL") != null 
					? dynaBean.get("ST_SERVSINCONCIL").toString()
					: "");

				productos
					.setStFactSinConcil(dynaBean.get("ST_FACTSINCONCIL") != null 
					? dynaBean.get("ST_FACTSINCONCIL").toString()
					: "");

				productos
					.setNuDiaEmiFDesde(dynaBean.get("NU_DIAEMIFDESDE") != null 
					? Long.valueOf(dynaBean.get("NU_DIAEMIFDESDE").toString())
					: Long.valueOf("0"));
				
				productos
					.setNuDiaEmiFHasta(dynaBean.get("NU_DIAEMIFHASTA") != null 
					? Long.valueOf(dynaBean.get("NU_DIAEMIFHASTA").toString())
					: Long.valueOf("0"));
			
				productos
					.setNuDiaCierreFDesde(dynaBean.get("NU_DIACIERREFDESDE") != null 
					? Long.valueOf(dynaBean.get("NU_DIACIERREFDESDE").toString())
					: Long.valueOf("0"));
			
				productos
					.setNuDiaCierreFHasta(dynaBean.get("NU_DIACIERREFHASTA") != null 
					? Long.valueOf(dynaBean.get("NU_DIACIERREFHASTA").toString())
					: Long.valueOf("0"));

				productos
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
				
				productos
				    .setStRelacionSector(dynaBean.get("ST_RELACIONSECTOR") != null 
			   	    ? dynaBean.get("ST_RELACIONSECTOR").toString()
				    : "");
			
				productosList.add(productos);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result saveConciliacion(Map parameters, List conciliacionNro) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.NOMEDIBLES_SAVE_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			Integer conciliacion = ((BigDecimal) parameters.get(DatabaseConstants.PO_CD_CONCILIACION)).intValue();
			conciliacionNro.add(conciliacion);
			
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionDetalle(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.NOMEDIBLES_SAVE_DET_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
}
