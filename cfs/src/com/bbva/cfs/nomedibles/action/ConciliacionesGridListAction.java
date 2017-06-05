package com.bbva.cfs.nomedibles.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

import com.bbva.cfs.nomedibles.form.ConciliacionForm;
import com.bbva.cfs.commons.action.GridActionWithoutPaginate;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.nomedibles.model.Conciliacion;
import com.bbva.cfs.nomedibles.service.GetConciliacionesService;

public class ConciliacionesGridListAction extends GridActionWithoutPaginate {

	private String cdProveedor;
	private String cdSector;
	private String cdPeriodo;
	private String stConciliacion;
	private Integer cdConciliacion;
	
	public ConciliacionesGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridConciliaciones(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		cdProveedor    = request.getParameter("cdProveedor");
		cdSector       = request.getParameter("cdSector");
		cdPeriodo      = request.getParameter("cdPeriodoFact");
		stConciliacion = request.getParameter("stConciliacion");
		cdConciliacion = Integer.parseInt(request.getParameter("cdConciliacion"));

		return super.executeGrid(mapping, form, request, response);
	}

	@SuppressWarnings("rawtypes")
	public Class getFilterClass() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParameters(HttpServletRequest request, ActionForm form)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public List fillRows(HttpSession session) throws GeneralException {
		// Se obtiene Session del que esta realizando la consulta.
		@SuppressWarnings("unused")
		Session sessionApp = (Session) session.getAttribute(Constants.SESSION);
		GetConciliacionesService service = new GetConciliacionesService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			result = service.execute(cdProveedor, cdSector, cdPeriodo, stConciliacion, cdConciliacion);
			listaRet = this.fillData(service.getConciliacionesList());
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
	    	ConciliacionForm frm = new ConciliacionForm();
	    	Conciliacion conciliacion = (Conciliacion) object;
	        frm.setCdProducto(conciliacion.getCdProducto());
	        frm.setCdPeriodoAnt(conciliacion.getCdPeriodoAnt());
	        frm.setCtServFactAnt(conciliacion.getCtServFactAnt());
	        frm.setCdUniValAnt(conciliacion.getCdUniValAnt());
	        frm.setImPrecioTotalAnt(conciliacion.getImPrecioTotalAnt());
	        frm.setCdConciliacionAnt(conciliacion.getCdConciliacionAnt());
	        frm.setCtServFactAct(conciliacion.getCtServFactAct());
	        frm.setCdUniValAct(conciliacion.getCdUniValAct());
	        frm.setImPrecioTotalAct(conciliacion.getImPrecioTotalAct());
	        frm.setNuPorcVarMax(conciliacion.getNuPorcVarMax());
	        frm.setNuPorcVarVal(conciliacion.getNuPorcVarVal());
	        frm.setCdConciliacionAct(conciliacion.getCdConciliacionAct());
	        frm.setImDiferencia(conciliacion.getImDiferencia());
	        frm.setNbObservaciones(conciliacion.getNbObservaciones());
	        frm.setTpSolucion(conciliacion.getTpSolucion());
	    	
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ConciliacionForm frm = (ConciliacionForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("cdPeriodoAnt", frm.getCdPeriodoAnt());
		jsonObject.put("ctServFactAnt", frm.getCtServFactAnt());
		jsonObject.put("cdUniValAnt", frm.getCdUniValAnt());
		jsonObject.put("imPrecioTotalAnt", frm.getImPrecioTotalAnt());
		jsonObject.put("cdConciliacionAnt", frm.getCdConciliacionAnt());
		jsonObject.put("ctServFactAct", frm.getCtServFactAct());
		jsonObject.put("cdUniValAct", frm.getCdUniValAct());
		jsonObject.put("imPrecioTotalAct", frm.getImPrecioTotalAct());
		jsonObject.put("nuPorcVarMax", frm.getNuPorcVarMax());
		jsonObject.put("nuPorcVarVal", frm.getNuPorcVarVal());
        jsonObject.put("cdConciliacionAct", frm.getCdConciliacionAct());
		jsonObject.put("chkDif", "0");        
        jsonObject.put("imDiferencia", frm.getImDiferencia());
        jsonObject.put("nbObservaciones", frm.getNbObservaciones());
        jsonObject.put("tpSolucion", frm.getTpSolucion());

		return jsonObject;
	}
}
