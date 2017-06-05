package com.bbva.cfs.conciliacion.action;

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

import com.bbva.cfs.conciliacion.form.RepetidoForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.conciliacion.model.Repetido;
import com.bbva.cfs.conciliacion.service.GetRepetidosService;

public class RepetidosGridListAction extends GridAction {

	private String  cdProveedor;
	private String  cdProducto;
	private String  cdPeriodo;
	private String  cdSector;
	private Integer cdConciliacion;
	
	public RepetidosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridRepetidos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		cdProveedor    = request.getParameter("cdProveedor");
		cdProducto     = request.getParameter("cdProducto");
		cdPeriodo      = request.getParameter("cdPeriodo");
		cdSector       = request.getParameter("cdSector");
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
		GetRepetidosService service = new GetRepetidosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			result = service.getRepetidos(cdProveedor, cdProducto, cdPeriodo, cdSector, cdConciliacion);
			listaRet = this.fillData(service.getRepetidosList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listRepetidos) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listRepetidos.size(); i++) {  
	    	Object object = listRepetidos.get(i);
	    	RepetidoForm frm = new RepetidoForm();
	    	Repetido repetido = (Repetido) object;

	        frm.setCdRemito(repetido.getCdRemito());
	        frm.setCdPeriodoFact(repetido.getCdPeriodoFact());
	        frm.setTpComprobante(repetido.getTpComprobante());
	        frm.setNuComprobante(repetido.getNuComprobante());
	        frm.setCtServFact(repetido.getCtServFact());
	        frm.setImPrecioUnit(repetido.getImPrecioUnit());
	        frm.setImPrecioTotal(repetido.getImPrecioTotal());
	    	
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		RepetidoForm frm = (RepetidoForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdRemito", frm.getCdRemito());
		jsonObject.put("cdPeriodoFact", frm.getCdPeriodoFact());
		jsonObject.put("tpComprobante", frm.getTpComprobante());
        jsonObject.put("nuComprobante", frm.getNuComprobante());
        jsonObject.put("ctServFact", frm.getCtServFact());
        jsonObject.put("imPrecioUnit", frm.getImPrecioUnit());
        jsonObject.put("imPrecioTotal", frm.getImPrecioTotal());
		
		return jsonObject;
	}
}
