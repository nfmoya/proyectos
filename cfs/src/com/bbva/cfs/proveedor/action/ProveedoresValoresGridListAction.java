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

import com.bbva.cfs.proveedor.form.ProveedorValorForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.proveedor.model.ProveedorValor;
import com.bbva.cfs.proveedor.service.GetProveedoresValoresService;

public class ProveedoresValoresGridListAction extends GridAction {

	private String cdProveedor;
	private String cdPeriodo;

	public ProveedoresValoresGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProveedoresValores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		cdProveedor = request.getParameter("cdProveedor");
		cdPeriodo   = request.getParameter("cdPeriodo");
		
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
		GetProveedoresValoresService service = new GetProveedoresValoresService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor, cdPeriodo);
			}
			listaRet = this.fillData(service.getProveedoresValoresList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listProveedoresValores) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listProveedoresValores.size(); i++) {  
	    	Object object = listProveedoresValores.get(i);
			ProveedorValorForm frm = new ProveedorValorForm();
			ProveedorValor proveedorValor = (ProveedorValor) object;

			String nuValBrutoUniVal = (proveedorValor.getNuValBrutoUniVal()).replace(".", ",");
			String nuValNetoUniVal = (proveedorValor.getNuValNetoUniVal()).replace(".", ",");
			
			frm.setCdProveedor(proveedorValor.getCdProveedor());
			frm.setCdPeriodoFact(proveedorValor.getCdPeriodoFact());
			frm.setCdUniVal(proveedorValor.getCdUniVal());
			frm.setNuValBrutoUniVal(nuValBrutoUniVal);
			frm.setNuValNetoUniVal(nuValNetoUniVal);
			frm.setStHabilitado(proveedorValor.getStHabilitado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProveedorValorForm frm = (ProveedorValorForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdPeriodoFact", frm.getCdPeriodoFact());
		jsonObject.put("cdUniVal", frm.getCdUniVal());
		jsonObject.put("nuValBrutoUniVal", frm.getNuValBrutoUniVal());
		jsonObject.put("nuValNetoUniVal", frm.getNuValNetoUniVal());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
