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

import com.bbva.cfs.producto.form.ProductoPeriodoForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.producto.model.ProductoPeriodo;
import com.bbva.cfs.producto.service.GetProductosPeriodosService;

public class ProductosPeriodosGridListAction extends GridAction {

	private String cdProveedor;
	private String cdProducto;
	
	public ProductosPeriodosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProductosPeriodos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());
		
		cdProveedor = request.getParameter("cdProveedor");
		cdProducto  = request.getParameter("cdProducto");

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
		GetProductosPeriodosService service = new GetProductosPeriodosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor,cdProducto);
			}
			listaRet = this.fillData(service.getProductosPeriodosList());
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
	    	ProductoPeriodoForm frm = new ProductoPeriodoForm();
			ProductoPeriodo productoPeriodo = (ProductoPeriodo) object;
						
			frm.setCdProveedor(productoPeriodo.getCdProveedor());
			frm.setCdProducto(productoPeriodo.getCdProducto());
			frm.setFhDesde(productoPeriodo.getFhDesde());
			frm.setFhHasta(productoPeriodo.getFhHasta());
			frm.setStHabilitado(productoPeriodo.getStHabilitado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProductoPeriodoForm frm = (ProductoPeriodoForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("fhDesde", frm.getFhHasta());
		jsonObject.put("fhHasta", frm.getFhHasta());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
