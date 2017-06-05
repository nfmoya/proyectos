package com.bbva.cfs.conciliacion.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

//import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.action.GridActionWithoutPaginate;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.conciliacion.form.BusquedaConciliacionesForm;
import com.bbva.cfs.conciliacion.form.ConciliacionForm;
import com.bbva.cfs.conciliacion.model.Conciliacion;
import com.bbva.cfs.conciliacion.service.GetConciliacionesService;

public class BusquedaConciliacionesGridListAction extends GridActionWithoutPaginate {

	private String cdProveedor;
	private String cdSector;
	private String fhDesde;
	private String fhHasta;
	private String cdProducto;
	// private String cdGrupo;
	private String estadoDif;
	private String stDiferencia;
	private Boolean checkNoMedible;
	private String cdConciliacion;
	// private Integer cantBilletes;
	// private String tipoCambio;

	public BusquedaConciliacionesGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridConcil(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		/*
		 * String realpath =
		 * request.getSession().getServletContext().getRealPath(
		 * "/configFolders.properties"); File f = new File(realpath); File f2 =
		 * f.getParentFile().getParentFile(); String pathfinal =
		 * f2.getAbsolutePath() + "/syscfg/configFolders.properties"; File f3 =
		 * new File(pathfinal);
		 * 
		 * FileInputStream input = new FileInputStream(f3); Properties config =
		 * new Properties(); config.load(input);
		 * 
		 * cantBilletes =
		 * Integer.parseInt(config.getProperty("concmedibles_cantbilletes"));
		 * tipoCambio = config.getProperty("concmedibles_tipocambio");
		 * 
		 * ConciliacionForm frm = (ConciliacionForm) form;
		 * frm.setCantBilletes(cantBilletes); frm.setTipoCambio(tipoCambio);
		 */

		cdProveedor = request.getParameter("cdProveedor");
		cdSector = request.getParameter("cdSector");
		fhDesde = request.getParameter("fhDesde");
		fhHasta = request.getParameter("fhHasta");
		cdProducto = request.getParameter("cdProducto");
		estadoDif = request.getParameter("estadoDif");
		stDiferencia = request.getParameter("stDiferencia");
		cdConciliacion = request.getParameter("cdConciliacion");
		checkNoMedible = Boolean.valueOf(request.getParameter("checkNoMedible"));

		return super.executeGrid(mapping, form, request, response);
	}

	@SuppressWarnings("rawtypes")
	public Class getFilterClass() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParameters(HttpServletRequest request, ActionForm form) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public List fillRows(HttpSession session) throws GeneralException {
		// Se obtiene Session del que esta realizando la consulta.
		@SuppressWarnings("unused")
		Session sessionApp = (Session) session.getAttribute(Constants.SESSION);
		GetConciliacionesService service = new GetConciliacionesService(iWebClient);

		List listaRet = null;
		try {
			result = service.executeConciliaciones(cdProveedor, cdSector, fhDesde, fhHasta, cdProducto, estadoDif,
					stDiferencia, cdConciliacion, checkNoMedible); // ,
																	// cantBilletes,

			listaRet = service.getBusqConciliacionesList();

		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listConciliaciones) {
		List listaRet = new ArrayList();
		for (int i = 0; i < listConciliaciones.size(); i++) {
			Object object = listConciliaciones.get(i);
			BusquedaConciliacionesForm frm = new BusquedaConciliacionesForm();
			Conciliacion conciliacion = (Conciliacion) object;

			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		BusquedaConciliacionesForm frm = (BusquedaConciliacionesForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdConciliacion", frm.getCdConciliacion());
		jsonObject.put("cdSector", frm.getCdSector());
		jsonObject.put("cdPeriodoFact", frm.getPeriodoFacturacion());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("descProducto", frm.getDescProducto());
		jsonObject.put("estadoConciliacion", frm.getEstadoConciliacion());
		jsonObject.put("stIgnoraVal", frm.getStIgnoraVal());
		jsonObject.put("stDiferencia", frm.getStDiferencia());
		jsonObject.put("observaciones", frm.getObservaciones());
		jsonObject.put("fhDesde", frm.getFechaConciliacion());
		jsonObject.put("usuarioConciliacion", frm.getUsuarioConciliacion());

		// this.result.setErrorCode(0L);
		return jsonObject;
	}
}
