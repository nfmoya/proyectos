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

import com.bbva.cfs.producto.form.ProductoAgrupadoForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
//import com.bbva.cfs.general.model.Tabla;
//import com.bbva.cfs.general.service.GetTablasService;
import com.bbva.cfs.producto.model.ProductoAgrupado;
import com.bbva.cfs.producto.service.GetProductosAgrupadosService;

public class ProductosAgrupadosGridListAction extends GridAction {

	private String cdProveedor;
	private String cdProductoOrig;
	private String cdProducto;
	private String stHabilitado;
	
	public ProductosAgrupadosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProductosAgrupados(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());
		
		cdProveedor    = request.getParameter("cdProveedor");
		cdProductoOrig = request.getParameter("cdProductoOrig");
		cdProducto     = request.getParameter("cdProducto");
		stHabilitado   = request.getParameter("stHabilitado");

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
		GetProductosAgrupadosService service = new GetProductosAgrupadosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor, cdProductoOrig, cdProducto, stHabilitado);
			}
//			GetTablasService srvTablas = new GetTablasService(iWebClient);
			listaRet = this.fillData(service.getProductosAgrupadosList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listProductosAgrupados) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listProductosAgrupados.size(); i++) {  
	    	Object object = listProductosAgrupados.get(i);
			ProductoAgrupadoForm frm = new ProductoAgrupadoForm();
			ProductoAgrupado productoAgrupado = (ProductoAgrupado) object;

			frm.setCdProveedor(productoAgrupado.getCdProveedor());
			frm.setCdProductoOrig(productoAgrupado.getCdProductoOrig());
			frm.setCdProducto(productoAgrupado.getCdProducto());
			frm.setDesItem(productoAgrupado.getDesItem());
			frm.setDesGrupo(productoAgrupado.getDesGrupo());
			frm.setStHabilitado(productoAgrupado.getStHabilitado());
			
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProductoAgrupadoForm frm = (ProductoAgrupadoForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdProductoOrig", frm.getCdProductoOrig());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("desItem", frm.getDesItem());
		jsonObject.put("desGrupo", frm.getDesGrupo());
		jsonObject.put("stHabilitado", frm.getStHabilitado());
		
		return jsonObject;
	}
}
