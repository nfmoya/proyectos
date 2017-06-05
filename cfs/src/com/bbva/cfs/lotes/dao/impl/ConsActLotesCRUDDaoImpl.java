package com.bbva.cfs.lotes.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.bbva.cfs.lotes.dao.ConsActLotesCRUDDao;
import com.bbva.cfs.lotes.form.ConsActLotesForm;
import com.bbva.cfs.lotes.model.ConsActLotesModel;
import com.bbva.cfs.producto.dao.impl.ProductoPrecioDaoImpl;
import com.bbva.common.config.SamConfig;

public class ConsActLotesCRUDDaoImpl implements ConsActLotesCRUDDao {

	static final Log log = LogFactory.getLog(ProductoPrecioDaoImpl.class);

	private Result result;
	private SAMWebClient samWebClient;
	private Map<String, Object> parameters;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	/**
	 * Constructor
	 * 
	 * @param samWebClient
	 */
	public ConsActLotesCRUDDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
		this.parameters = new HashMap<String, Object>();
	}

	/**
	 * 
	 */
	public Result getConsActLotes(List<ConsActLotesModel> lotesList,
			ConsActLotesForm form) throws Exception {

		// Armo el hashMap de parametros
		this.parameters.clear();

		// Si ingreso nro de lote descarto los demas filtros
		boolean var = form.getCdLote() == null || Validar.esVacio(form.getCdLote()) || form.getCdLote().equals('0')? true
				: false;

		this.parameters.put("pi_tp_lote", form.getTipoLote());
		this.parameters
				.put("pi_cd_proveedor", form.getCdProveedor());//var ? form.getCdProveedor() : "");
		this.parameters.put("pi_fa_desde", form.getFhAltaDesde()); //var ? form.getFhAltaDesde() : "");
		this.parameters.put("pi_fa_hasta", form.getFhAltaHasta()); //var ? form.getFhAltaHasta() : "");
		this.parameters.put("pi_st_lote", form.getStLote()); //var ? form.getStLote() : "");
		this.parameters.put("pi_cd_nro_lote",
				var ? 0 : Integer.parseInt(form.getCdLote()));

		lotesList.clear();

		// Objeto de la tabla
		ConsActLotesModel model;

		try {

			model = new ConsActLotesModel();

			SamConfig.getSam().execute(
					DatabaseConstants.GET_CONS_ACT_LOTES_INVOKER,
					getSamWebClient().getSamContext(), this.parameters);

			this.result.setErrorCode(0L);
			this.result.setErrorDesc("Consulta realizada");

			// procesamiento del resultado retornado por el SP
			DaoUtils.proccessServiceResponse(parameters, this.result, log);

			// Obtenci�n de par�metros
			ArrayList<DynaBean> dynaListResult = (ArrayList<DynaBean>) parameters
					.get(DatabaseConstants.PO_CURSOR);

			// Recorro el cursor
			for (DynaBean dynaBean : dynaListResult) {
				model = new ConsActLotesModel();

				model.setCdLote(Long
						.valueOf(dynaBean.get("CD_LOTE").toString()));
				model.setTpInterfaz(dynaBean.get("TP_INTERFAZ").toString());
				model.setNbArchivo(dynaBean.get("NB_ARCHIVO").toString());
				model.setCdProveedor(dynaBean.get("CD_PROVEEDOR").toString());
				model.setStLotecab(dynaBean.get("ST_LOTECAB").toString());
				model.setNbObservaciones(dynaBean.get("NB_OBSERVACIONES")
						.toString());

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date fhAlta = formatter.parse(dynaBean.get("FH_ALTA")
						.toString());
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
				model.setFhAlta(df.format(fhAlta));
				
				model.setNbUsuarioAlta(dynaBean.get("NB_USUARIOALTA")
						.toString());
				model.setAnular(Integer.parseInt(dynaBean.get("ANULAR")
						.toString()));

				lotesList.add(model);
			}

		} catch (TransactionException e) {
			log.debug(e);
			e.printStackTrace();
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());

		} catch (Exception e) {
			log.debug(e);
			e.printStackTrace();
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}


	public Result anular(String tipoLote, String nroLote, String user)
			throws Exception {

		try {

			parameters.clear();
			// Seteo de par�metros
			parameters.put("pi_tp_lote", tipoLote);
			parameters.put("pi_cd_lote", Integer.parseInt(nroLote));
			parameters.put("pi_id_usuario", user);

			// Ejecuci�n del SP
			SamConfig.getSam().execute(
					DatabaseConstants.ANULAR_CONS_ACT_LOTES_INVOKER,
					getSamWebClient().getSamContext(), parameters);

			// Obtenci�n de par�metros
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