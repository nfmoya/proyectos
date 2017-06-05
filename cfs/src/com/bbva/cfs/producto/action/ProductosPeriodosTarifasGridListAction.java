package com.bbva.cfs.producto.action;

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

import com.bbva.cfs.producto.form.ProductoPeriodoTarifaForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.producto.model.ProductoPeriodoTarifa;
import com.bbva.cfs.producto.service.GetProductosPeriodosTarifasService;

public class ProductosPeriodosTarifasGridListAction extends GridAction {

	private String cdProveedor;
	private String cdProducto;
	private String fhDesde;

	public ProductosPeriodosTarifasGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProductosPeriodosTarifas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		cdProveedor = request.getParameter("cdProveedor");
		cdProducto = request.getParameter("cdProducto");
		fhDesde = request.getParameter("fhDesde");
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
		GetProductosPeriodosTarifasService service = new GetProductosPeriodosTarifasService(iWebClient);

		List listaRet = new ArrayList();
		try {
			result = service.execute(cdProveedor, cdProducto, fhDesde);

			listaRet = this.fillData(service.getProductosPeriodosTarifasList());
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
			ProductoPeriodoTarifaForm frm = new ProductoPeriodoTarifaForm();
			ProductoPeriodoTarifa productoPeriodoTarifa = (ProductoPeriodoTarifa) object;

			frm.setCdProveedor(productoPeriodoTarifa.getCdProveedor());
			frm.setCdProducto(productoPeriodoTarifa.getCdProducto());
			frm.setFhDesde(productoPeriodoTarifa.getFhDesde());
			frm.setFhHasta(productoPeriodoTarifa.getFhHasta());
			frm.setNuCantDesde(productoPeriodoTarifa.getNuCantDesde());
			frm.setNuCantHasta(productoPeriodoTarifa.getNuCantHasta());
			frm.setNuPrecioUniVal(productoPeriodoTarifa.getNuPrecioUniVal());
			frm.setNuPorcTarifa(productoPeriodoTarifa.getNuPorcTarifa());
			frm.setStHabilitado(productoPeriodoTarifa.getStHabilitado());
			frm.setStPrecioFijo(productoPeriodoTarifa.getStPrecioFijo());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProductoPeriodoTarifaForm frm = (ProductoPeriodoTarifaForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("fhDesde", frm.getFhDesde());
		jsonObject.put("nuCantDesde", frm.getNuCantDesde());
		jsonObject.put("nuCantHasta", frm.getNuCantHasta());
		jsonObject.put("nuPrecioUniVal", frm.getNuPrecioUniVal());
		jsonObject.put("nuPorcTarifa", frm.getNuPorcTarifa());
		jsonObject.put("stHabilitado", frm.getStHabilitado());
		jsonObject.put("stPrecioFijo", frm.getStPrecioFijo());

		return jsonObject;
	}
}
