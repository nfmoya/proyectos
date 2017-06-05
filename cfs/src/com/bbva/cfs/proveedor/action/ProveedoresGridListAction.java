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

import com.bbva.cfs.proveedor.form.ProveedorForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.GetProveedoresService;

public class ProveedoresGridListAction extends GridAction {

	private String cdProveedor;

	public ProveedoresGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProveedores(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

	public void setParameters(HttpServletRequest request, ActionForm form) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public List fillRows(HttpSession session) throws GeneralException {
		// Se obtiene Session del que esta realizando la consulta.
		@SuppressWarnings("unused")
		Session sessionApp = (Session) session.getAttribute(Constants.SESSION);
		GetProveedoresService service = new GetProveedoresService(iWebClient);

		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor);
			}
			listaRet = this.fillData(service.getProveedoresList());
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
			ProveedorForm frm = new ProveedorForm();
			Proveedor proveedor = (Proveedor) object;

			frm.setCdProveedor(proveedor.getCdProveedor());
			frm.setNbProveedor(proveedor.getNbProveedor());
			frm.setNbProveedorCorto(proveedor.getNbProveedorCorto());
			frm.setNuCuit(proveedor.getNuCuit());
			frm.setNbAtributoProv1(proveedor.getNbAtributoProv1());
			frm.setNbAtributoProv2(proveedor.getNbAtributoProv2());
			String attr3 = (proveedor.getNbAtributoProv3() != null && proveedor.getNbAtributoProv3().length() == 6)
					? proveedor.getNbAtributoProv3().substring(0, 4) + ","
							+ proveedor.getNbAtributoProv3().substring(4, 6)
					: proveedor.getNbAtributoProv3();
			frm.setNbAtributoProv3(attr3);
			String attr4 = (proveedor.getNbAtributoProv4() != null && proveedor.getNbAtributoProv4().length() == 6)
					? proveedor.getNbAtributoProv4().substring(0, 4) + ","
							+ proveedor.getNbAtributoProv4().substring(4, 6)
					: proveedor.getNbAtributoProv4();
			frm.setNbAtributoProv4(attr4);
			frm.setNbAtributoProv5(proveedor.getNbAtributoProv5());
			frm.setStHabilitado(proveedor.getStHabilitado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProveedorForm frm = (ProveedorForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("nbProveedor", frm.getNbProveedor());
		jsonObject.put("nbProveedorCorto", frm.getNbProveedorCorto());
		jsonObject.put("nuCuit", frm.getNuCuit());
		jsonObject.put("nbAtributoProv1", frm.getNbAtributoProv1());
		jsonObject.put("nbAtributoProv2", frm.getNbAtributoProv2());
		jsonObject.put("nbAtributoProv3", frm.getNbAtributoProv3());
		jsonObject.put("nbAtributoProv4", frm.getNbAtributoProv4());
		jsonObject.put("nbAtributoProv5", frm.getNbAtributoProv5());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
