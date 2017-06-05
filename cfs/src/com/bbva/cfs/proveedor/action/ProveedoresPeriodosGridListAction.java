package com.bbva.cfs.proveedor.action;

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

import com.bbva.cfs.proveedor.form.ProveedorPeriodoForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;

public class ProveedoresPeriodosGridListAction extends GridAction {

	private String cdProveedor;

	public ProveedoresPeriodosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProveedoresPeriodos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());
		
		cdProveedor = request.getParameter("cdProveedor");

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
		GetProveedoresPeriodosService service = new GetProveedoresPeriodosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor);
			}
			listaRet = this.fillData(service.getProveedoresPeriodosList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listProveedores) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listProveedores.size(); i++) {  
	    	Object object = listProveedores.get(i);
			ProveedorPeriodoForm frm = new ProveedorPeriodoForm();
			ProveedorPeriodo proveedorPeriodo = (ProveedorPeriodo) object;

			frm.setCdProveedor(proveedorPeriodo.getCdProveedor());
			frm.setCdPeriodoFact(proveedorPeriodo.getCdPeriodoFact());
			frm.setNbPeriodoFact(proveedorPeriodo.getNbPeriodoFact());
			frm.setCdPerFactAlt(proveedorPeriodo.getCdPerFactAlt());
			frm.setFhDesde(proveedorPeriodo.getFhDesde());
			frm.setFhHasta(proveedorPeriodo.getFhHasta());
			frm.setStEstado(proveedorPeriodo.getStEstado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProveedorPeriodoForm frm = (ProveedorPeriodoForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdPeriodoFact", frm.getCdPeriodoFact());
		jsonObject.put("nbPeriodoFact", frm.getNbPeriodoFact());
		jsonObject.put("cdPerFactAlt", frm.getCdPerFactAlt());
		jsonObject.put("fhDesde", frm.getFhDesde());
		jsonObject.put("fhHasta", frm.getFhHasta());
		jsonObject.put("stEstado", frm.getStEstado());

		return jsonObject;
	}
}
