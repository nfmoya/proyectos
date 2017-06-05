package com.bbva.cfs.conciliacion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.ar.utils.Validar;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.conciliacion.dao.DifConciliacionCRUDDao;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;
import com.bbva.cfs.producto.dao.impl.ProductoPrecioDaoImpl;
import com.bbva.common.config.SamConfig;

public class DifConciliacionCRUDDaoImpl implements DifConciliacionCRUDDao {

	static final Log log = LogFactory.getLog(ProductoPrecioDaoImpl.class);

	private Result result;
	private SAMWebClient samWebClient;
	private Map<String, Object> parameters;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	/**
	 * Constructor!
	 * 
	 * @param samWebClient
	 */
	public DifConciliacionCRUDDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
		this.parameters = new HashMap<String, Object>();
	}

	@SuppressWarnings("unchecked")
	public Result getDifConciliacion(List<DiferenciaConciliacionModel> dcList,
			DiferenciaConciliacionForm form) throws Exception {

		// Limpio y Armo el hashMap de parametros
		this.parameters.clear();
		dcList.clear();

		// Si ingreso nro de conciliacion decarto los demas filtros set NULL
		boolean var = form.getCdConciliacion() == null
				|| Validar.esVacio(form.getCdConciliacion()) ? true : false;
		
		this.parameters.put("pi_cd_proveedor", form.getCdProveedor()); //var ? form.getCdProveedor() : "0");
		this.parameters.put("pi_cd_sector", form.getCdSector()); //var ? form.getCdSector() : "0");
		this.parameters.put("pi_pf_desde", form.getPfDesde()); //var ? form.getPfDesde() : "");
		this.parameters.put("pi_pf_hasta", form.getPfHasta()); //var ? form.getPfHasta() : "");
		this.parameters.put("pi_cd_producto", form.getCdProducto()); //var ? form.getCdProducto() : "0");
		this.parameters.put("pi_st_diferencia", form.getStDiferencia()); //var ? form.getStDiferencia() : "0");
		this.parameters.put("pi_tp_solucion", form.getTpSolucion());//var ? form.getTpSolucion() : "0");
		this.parameters.put("pi_cd_conciliacion",var ?  -1 : Integer.parseInt((form.getCdConciliacion())));

		// Objeto de la tabla
		DiferenciaConciliacionModel model;

		try {

//			model = new DiferenciaConciliacionModel();
//			dcList.add(model);			
//			this.result.setErrorCode(0L);
//			this.result.setErrorDesc("Grabado Exitosamente");
			
			if (form.isFromConciliacion()) {
				 SamConfig.getSam().execute(DatabaseConstants.CONCILIACION_DIF_LIST_INVOKER,
						 getSamWebClient().getSamContext(), this.parameters);
			} else {
				 SamConfig.getSam().execute(DatabaseConstants.GET_DIF_CONCILIA_LIST_INVOKER,				
						 getSamWebClient().getSamContext(), this.parameters);
			}						 
	
//			 procesamiento del resultado retornado por el SP
			 DaoUtils.proccessServiceResponse(parameters, this.result, log);
				
			 // Obtencion de parametros
			 ArrayList<DynaBean> dynaListResult = (ArrayList<DynaBean>)
						 parameters.get(DatabaseConstants.PO_CURSOR);
	
			// Recorro el cursor
				 
			 Long id = 0L;
				 
			 for (DynaBean dynaBean : dynaListResult) {
				 model = new DiferenciaConciliacionModel();
				 
				 model.setIdDif(id);
				 model.setCdConciliacion(Long.valueOf(dynaBean.get("CD_CONCILIACION").toString()));
				 model.setCdOrden(dynaBean.get("CD_ORDEN").toString());
				 model.setCdRemito(dynaBean.get("CD_REMITO") != null ? dynaBean.get("CD_REMITO").toString()	: "");				 
				 model.setCdSector(dynaBean.get("CD_SECTOR").toString());
				 model.setCdPeriodoFact(dynaBean.get("CD_PERIODOFACT").toString());
				 model.setStPeriodoFact(dynaBean.get("ST_PERIODOFACT").toString());
				 model.setCdProducto(dynaBean.get("CD_PRODUCTO").toString());
				 model.setDescProducto(dynaBean.get("NB_DESCRIPCION").toString());
				 model.setPzaDesde(dynaBean.get("NB_PIEZADESDE") != null ? dynaBean.get("NB_PIEZADESDE").toString()	: "");
				 model.setPzaHasta(dynaBean.get("NB_PIEZAHASTA") != null ? dynaBean.get("NB_PIEZAHASTA").toString()	: "");
				 model.setCtDiferencia(dynaBean.get("CT_DIFERENCIA").toString());
				 model.setCdUniVal(dynaBean.get("CD_UNIVAL") != null ? dynaBean.get("CD_UNIVAL").toString()	: "");
				 model.setImPrecioTot(dynaBean.get("IM_PRECIOTOTAL").toString());
				 model.setStDiferencia(dynaBean.get("ST_DIFERENCIA").toString());
				 model.setFhALta(dynaBean.get("FH_ALTA").toString());
				 model.setDescUsuAlta(dynaBean.get("NB_USUARIOALTA").toString());
				 model.setObservacion(dynaBean.get("NB_OBSERVACIONES") != null ? dynaBean.get("NB_OBSERVACIONES").toString() : "");
				 model.setTpSolucion(dynaBean.get("TP_SOLUCION").toString());
				 model.setCdTipVal(dynaBean.get("CD_TIPVAL").toString() != null ? dynaBean.get("CD_TIPVAL").toString() : "");
				
				 dcList.add(model);
				 
				 id++;
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
	
	

	/**
	 * Edita una conciliacion
	 * 
	 * @param DiferenciaConciliacionModel
	 * @param user
	 * 
	 * @return Result
	 * @throws Exception
	 */
	public Result edit(DiferenciaConciliacionForm dcf, String user)
			throws Exception {

		try {

			this.parameters.clear();
			// Seteo de par�metros
			this.parameters.put("pi_cd_conciliacion", Integer.parseInt(dcf.getCdConciliacion()));
			this.parameters.put("pi_cd_orden", Integer.parseInt(dcf.getCdOrden()));
			this.parameters.put("pi_cd_remito", dcf.getCdRemito());
			this.parameters.put("pi_ct_diferencia", dcf.getCtDiferencia());
			this.parameters.put("pi_im_precioTotal", dcf.getImPrecioTot());
			this.parameters.put("pi_tp_solucion", dcf.getTpSolucion());
			this.parameters.put("pi_st_diferencia", dcf.getStDiferencia());
			this.parameters.put("pi_observacion", dcf.getObservaciones());
			this.parameters.put("pi_cod_event", "UPDDIFCONSOL");
			this.parameters.put("pi_id_usuario",user);

			// Ejecuci�n del SP
			SamConfig.getSam().execute(
					DatabaseConstants.EDIT_DIF_CONCILIA_INVOKER,
					getSamWebClient().getSamContext(), this.parameters);

			// Obtenci�n de par�metros
			DaoUtils.proccessServiceResponse(this.parameters, this.result, log);

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