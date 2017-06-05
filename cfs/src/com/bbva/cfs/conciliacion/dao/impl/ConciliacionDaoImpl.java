package com.bbva.cfs.conciliacion.dao.impl;

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
import com.bbva.cfs.conciliacion.dao.ConciliacionDao;
import com.bbva.cfs.conciliacion.form.BusquedaConciliacionesForm;
import com.bbva.cfs.conciliacion.model.Conciliacion;
import com.bbva.cfs.conciliacion.model.ConciliacionCabecera;
import com.bbva.cfs.conciliacion.model.Repetido;
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
public class ConciliacionDaoImpl implements ConciliacionDao {

	static final Log log = LogFactory.getLog(ConciliacionDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ConciliacionDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getConciliaciones(Map parameters, List conciliacionesList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_LISTA_INVOKER, getSamWebClient().getSamContext(),
					parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			Conciliacion conciliaciones;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				conciliaciones = new Conciliacion();

				conciliaciones.setCdRemitoPres(
						dynaBean.get("CD_REMITO_PRES") != null ? dynaBean.get("CD_REMITO_PRES").toString() : "");

				conciliaciones
						.setFhRemito(dynaBean.get("FH_REMITO") != null ? dynaBean.get("FH_REMITO").toString() : "");

				conciliaciones.setFhFinServ(
						dynaBean.get("FH_FIN_SERV") != null ? dynaBean.get("FH_FIN_SERV").toString() : "");

				conciliaciones.setCtServPrest(dynaBean.get("CT_SERVPREST") != null
						? new BigDecimal(dynaBean.get("CT_SERVPREST").toString()) : BigDecimal.ZERO);

				conciliaciones.setImPrecioTotalPres(dynaBean.get("IM_PRECIOTOTAL_PRES") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL_PRES").toString()) : BigDecimal.ZERO);
				
				conciliaciones.setImPrecioTotalPresConv(dynaBean.get("IM_PRECIOTOTAL_PRES_CONV") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL_PRES_CONV").toString()) : BigDecimal.ZERO);

				conciliaciones
						.setCdTipVal(dynaBean.get("CD_TIPVAL") != null ? dynaBean.get("CD_TIPVAL").toString() : "");

				conciliaciones
						.setCdMoneda(dynaBean.get("CD_MONEDA") != null ? dynaBean.get("CD_MONEDA").toString() : "");

				conciliaciones.setCdConciliacionPres(dynaBean.get("CD_CONCILIACION_PRES") != null
						? dynaBean.get("CD_CONCILIACION_PRES").toString() : "");

				conciliaciones.setChkPres(dynaBean.get("CHK_PRES") != null ? dynaBean.get("CHK_PRES").toString() : "");

				conciliaciones.setCdRemitoFact(
						dynaBean.get("CD_REMITO_FACT") != null ? dynaBean.get("CD_REMITO_FACT").toString() : "");

				conciliaciones.setTpComprobante(
						dynaBean.get("TP_COMPROBANTE") != null ? dynaBean.get("TP_COMPROBANTE").toString() : "");

				conciliaciones.setNuComprobante(
						dynaBean.get("NU_COMPROBANTE") != null ? dynaBean.get("NU_COMPROBANTE").toString() : "");

				conciliaciones.setCtServFact(dynaBean.get("CT_SERVFACT") != null
						? new BigDecimal(dynaBean.get("CT_SERVFACT").toString()) : BigDecimal.ZERO);

				conciliaciones.setImPrecioTotalFact(dynaBean.get("IM_PRECIOTOTAL_FACT") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL_FACT").toString()) : BigDecimal.ZERO);

				conciliaciones.setCdConciliacionFact(dynaBean.get("CD_CONCILIACION_FACT") != null
						? dynaBean.get("CD_CONCILIACION_FACT").toString() : "");

				conciliaciones.setChkFact(dynaBean.get("CHK_FACT") != null ? dynaBean.get("CHK_FACT").toString() : "");

				conciliaciones.setCtServFactDife(dynaBean.get("CT_SERVFACT_DIFE") != null
						? new BigDecimal(dynaBean.get("CT_SERVFACT_DIFE").toString()) : BigDecimal.ZERO);

				conciliaciones.setImServFactDife(dynaBean.get("IM_SERVFACT_DIFE") != null
						? new BigDecimal(dynaBean.get("IM_SERVFACT_DIFE").toString()) : BigDecimal.ZERO);

				conciliaciones.setCdLoteServ(new Integer(dynaBean.get("CD_LOTESERV").toString()));

				conciliaciones.setCdOrdenServ(new Integer(dynaBean.get("CD_ORDENSERV").toString()));

				conciliaciones.setCdSectorSol(
						dynaBean.get("CD_SECTORSOL") != null ? dynaBean.get("CD_SECTORSOL").toString() : "");

				conciliaciones.setCdSectorControl(
						dynaBean.get("CD_SECTORCONTROL") != null ? dynaBean.get("CD_SECTORCONTROL").toString() : "");

				conciliaciones.setCdProductoPres(
						dynaBean.get("CD_PRODUCTOPRES") != null ? dynaBean.get("CD_PRODUCTOPRES").toString() : "");

				conciliaciones
						.setCdUniValPres(dynaBean.get("CD_UNIVAL") != null ? dynaBean.get("CD_UNIVAL").toString() : "");

				conciliaciones.setImPrecioUnitPres(dynaBean.get("IM_PRECIOUNITPRES") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOUNITPRES").toString()) : BigDecimal.ZERO);

				conciliaciones.setNbPiezaDesdePres(
						dynaBean.get("NB_PIEZADESDEPRES") != null ? dynaBean.get("NB_PIEZADESDEPRES").toString() : "");

				conciliaciones.setNbPiezaHastaPres(
						dynaBean.get("NB_PIEZAHASTAPRES") != null ? dynaBean.get("NB_PIEZAHASTAPRES").toString() : "");

				conciliaciones.setCdRemitoPadre(
						dynaBean.get("CD_REMITOPADRE") != null ? dynaBean.get("CD_REMITOPADRE").toString() : "");

				conciliaciones.setNbAtributoRef1(
						dynaBean.get("NB_ATRIBUTOREF1") != null ? dynaBean.get("NB_ATRIBUTOREF1").toString() : "");

				conciliaciones.setNbAtributoRef2(
						dynaBean.get("NB_ATRIBUTOREF2") != null ? dynaBean.get("NB_ATRIBUTOREF2").toString() : "");

				conciliaciones.setNbObservaciones(
						dynaBean.get("NB_OBSERVACIONES") != null ? dynaBean.get("NB_OBSERVACIONES").toString() : "");

				conciliaciones
						.setStLoteDet(dynaBean.get("ST_LOTEDET") != null ? dynaBean.get("ST_LOTEDET").toString() : "");

				conciliaciones.setFhModificacion(
						dynaBean.get("FH_MODIFICACION") != null ? dynaBean.get("FH_MODIFICACION").toString() : "");

				conciliaciones.setNbUsuarioModif(
						dynaBean.get("NB_USUARIOMODIF") != null ? dynaBean.get("NB_USUARIOMODIF").toString() : "");

				conciliaciones.setCdLoteFact(new Integer(dynaBean.get("CD_LOTEFACT").toString()));

				conciliaciones.setCdOrdenFact(new Integer(dynaBean.get("CD_ORDENFACT").toString()));
		
				conciliacionesList.add(conciliaciones);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getConciliacionCabecera(Map parameters, List conciliacionCabeceraList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_CONSULTA_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			ConciliacionCabecera conciliacion;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				conciliacion = new ConciliacionCabecera();

				conciliacion.setCdConciliacion(new Integer(dynaBean.get("CD_CONCILIACION").toString()));

				conciliacion.setCdProveedor(
						dynaBean.get("CD_PROVEEDOR") != null ? dynaBean.get("CD_PROVEEDOR").toString() : "");

				conciliacion.setCdSector(dynaBean.get("CD_SECTOR") != null ? dynaBean.get("CD_SECTOR").toString() : "");

				conciliacion.setCdProducto(
						dynaBean.get("CD_PRODUCTO") != null ? dynaBean.get("CD_PRODUCTO").toString() : "");

				conciliacion.setCdPeriodoFact(
						dynaBean.get("CD_PERIODOFACT") != null ? dynaBean.get("CD_PERIODOFACT").toString() : "");

				conciliacion.setStIgnoraVal(
						dynaBean.get("ST_IGNORAVAL") != null ? dynaBean.get("ST_IGNORAVAL").toString() : "");

				conciliacion.setNbObservaciones(
						dynaBean.get("NB_OBSERVACIONES") != null ? dynaBean.get("NB_OBSERVACIONES").toString() : "");

				conciliacion.setStConciliacion(
						dynaBean.get("ST_CONCILIACION") != null ? dynaBean.get("ST_CONCILIACION").toString() : "");

				conciliacion.setFhRemitoDesde(
						dynaBean.get("FH_REMITO_DESDE") != null ? dynaBean.get("FH_REMITO_DESDE").toString() : "");

				conciliacion.setFhRemitoHasta(
						dynaBean.get("FH_REMITO_HASTA") != null ? dynaBean.get("FH_REMITO_HASTA").toString() : "");

				conciliacion.setFhFinSerDesde(
						dynaBean.get("FH_FIN_SERV_DESDE") != null ? dynaBean.get("FH_FIN_SERV_DESDE").toString() : "");

				conciliacion.setFhFinSerHasta(
						dynaBean.get("FH_FIN_SERV_HASTA") != null ? dynaBean.get("FH_FIN_SERV_HASTA").toString() : "");

				conciliacionCabeceraList.add(conciliacion);

			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductosConciliacion(Map parameters, List productosList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_PRODUCTO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			ProductoConciliacion productos;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productos = new ProductoConciliacion();

				productos.setCdProveedor(
						dynaBean.get("CD_PROVEEDOR") != null ? dynaBean.get("CD_PROVEEDOR").toString() : "");

				productos.setCdProducto(
						dynaBean.get("CD_PRODUCTO") != null ? dynaBean.get("CD_PRODUCTO").toString() : "");

				productos.setNbProducto(
						dynaBean.get("NB_PRODUCTO") != null ? dynaBean.get("NB_PRODUCTO").toString() : "");

				productos.setCdUniVal(dynaBean.get("CD_UNIVAL") != null ? dynaBean.get("CD_UNIVAL").toString() : "");

				productos.setStConcilSinVal(
						dynaBean.get("ST_CONCILSINVAL") != null ? dynaBean.get("ST_CONCILSINVAL").toString() : "");

				productos.setStServSinConcil(
						dynaBean.get("ST_SERVSINCONCIL") != null ? dynaBean.get("ST_SERVSINCONCIL").toString() : "");

				productos.setStFactSinConcil(
						dynaBean.get("ST_FACTSINCONCIL") != null ? dynaBean.get("ST_FACTSINCONCIL").toString() : "");

				productos.setNuDiaEmiFDesde(dynaBean.get("NU_DIAEMIFDESDE") != null
						? Long.valueOf(dynaBean.get("NU_DIAEMIFDESDE").toString()) : Long.valueOf("0"));

				productos.setNuDiaEmiFHasta(dynaBean.get("NU_DIAEMIFHASTA") != null
						? Long.valueOf(dynaBean.get("NU_DIAEMIFHASTA").toString()) : Long.valueOf("0"));

				productos.setNuDiaCierreFDesde(dynaBean.get("NU_DIACIERREFDESDE") != null
						? Long.valueOf(dynaBean.get("NU_DIACIERREFDESDE").toString()) : Long.valueOf("0"));

				productos.setNuDiaCierreFHasta(dynaBean.get("NU_DIACIERREFHASTA") != null
						? Long.valueOf(dynaBean.get("NU_DIACIERREFHASTA").toString()) : Long.valueOf("0"));

				productos.setStHabilitado(
						dynaBean.get("ST_HABILITADO") != null ? dynaBean.get("ST_HABILITADO").toString() : "");

				productos.setCdGrupoProducto(
						dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean.get("CD_GRUPOPRODUCTO").toString() : "");

				productos.setStRelacionSector(
						dynaBean.get("ST_RELACIONSECTOR") != null ? dynaBean.get("ST_RELACIONSECTOR").toString() : "");

				productosList.add(productos);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result saveConciliacion(Map parameters, List conciliacionNro) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_SAVE_INVOKER, getSamWebClient().getSamContext(),
					parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			Integer conciliacion = ((BigDecimal) parameters.get(DatabaseConstants.PO_CD_CONCILIACION)).intValue();
			conciliacionNro.add(conciliacion);

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionPrestados(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_SAVE_PRES_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionFacturados(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_SAVE_FACT_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result buscarConciliaciones(Map parameters, List busqConciliacionesList) throws Exception {
		try {
			Boolean isCheck = (Boolean) parameters.get("isCheck");
			parameters.remove("isCheck");

			SamConfig.getSam().execute(
					!isCheck ? DatabaseConstants.BUSQUEDA_CONCILIACIONES_INVOKER
							: DatabaseConstants.BUSQUEDA_CONCIL_PROD_NOMED_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result, log);

			if (this.result.isSuccesfull()) {
				ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

				Iterator it = dynaListResult.iterator();

				while (it.hasNext()) {
					DynaBean dynaBean = (DynaBean) it.next();
					BusquedaConciliacionesForm frm = new BusquedaConciliacionesForm();
					frm.setCdConciliacion(dynaBean.get("CD_CONCILIACION").toString());
					frm.setCdSector(dynaBean.get("CD_SECTOR").toString());
					frm.setPeriodoFacturacion(dynaBean.get("CD_PERIODOFACT").toString());
					frm.setStPeriodoFact(dynaBean.get("ST_PERIODOFACT").toString());

					frm.setCdProducto(dynaBean.get("CD_PRODUCTO").toString());
					frm.setDescProducto(dynaBean.get("descripcion").toString());

					String estado = dynaBean.get("ST_CONCILIACION").toString();
					if (estado.equalsIgnoreCase("GRA"))
						estado = "Grabada sin Aprobación";
					else if (estado.equalsIgnoreCase("APR"))
						estado = "Aprobada";
					else
						estado = "Anulada";

					frm.setEstadoConciliacion(estado);
					frm.setStIgnoraVal(dynaBean.get("ST_IGNORAVAL").toString());
					frm.setStDiferencia(dynaBean.get("estadoDif").toString());
					frm.setObservaciones(dynaBean.get("NB_OBSERVACIONES") != null
							? dynaBean.get("NB_OBSERVACIONES").toString() : "");
					frm.setFechaConciliacion(dynaBean.get("FH_CONCILIACION").toString());
					frm.setUsuarioConciliacion(dynaBean.get("NB_USUARIOCONCIL").toString());

					busqConciliacionesList.add(frm);
				}
			}

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result anulacionConciliacion(Map parameters) throws Exception {
		// TODO Auto-generated method stub
		try {
			Boolean isCheck = (Boolean) parameters.get("isCheck");
			parameters.remove("isCheck");

			SamConfig.getSam().execute(
					isCheck ? DatabaseConstants.ANULAR_CONCILIACION_NO_MED_INVOKER
							: DatabaseConstants.ANULAR_CONCILIACION_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			DaoUtils.proccessServiceResponse(parameters, this.result, log);

		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getRepetidos(Map parameters, List repetidosList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_REPETIDOS_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			Repetido repetido;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				repetido = new Repetido();

				repetido.setCdRemito(dynaBean.get("CD_REMITO") != null ? dynaBean.get("CD_REMITO").toString() : "");

				repetido.setCdPeriodoFact(
						dynaBean.get("CD_PERIODOFACT") != null ? dynaBean.get("CD_PERIODOFACT").toString() : "");

				repetido.setTpComprobante(
						dynaBean.get("TP_COMPROBANTE") != null ? dynaBean.get("TP_COMPROBANTE").toString() : "");

				repetido.setNuComprobante(
						dynaBean.get("NU_COMPROBANTE") != null ? dynaBean.get("NU_COMPROBANTE").toString() : "");

				repetido.setCtServFact(dynaBean.get("CT_SERVFACT") != null
						? new BigDecimal(dynaBean.get("CT_SERVFACT").toString()) : BigDecimal.ZERO);

				repetido.setImPrecioUnit(dynaBean.get("IM_PRECIOUNIT") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOUNIT").toString()) : BigDecimal.ZERO);

				repetido.setImPrecioTotal(dynaBean.get("IM_PRECIOTOTAL") != null
						? new BigDecimal(dynaBean.get("IM_PRECIOTOTAL").toString()) : BigDecimal.ZERO);

				repetidosList.add(repetido);
			}
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
}
