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

import com.bbva.cfs.producto.form.ProductoSectorForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.producto.model.ProductoSector;
import com.bbva.cfs.producto.service.GetProductosSectoresService;

public class ProductosSectoresGridListAction extends GridAction {

	private String cdProveedor;
	private String cdProducto;
	
	public ProductosSectoresGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProductosSectores(ActionMapping mapping,
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
		GetProductosSectoresService service = new GetProductosSectoresService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor,cdProducto);
			}
			listaRet = this.fillData(service.getProductosSectoresList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List lisProductosSectores) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < lisProductosSectores.size(); i++) {  
	    	Object object = lisProductosSectores.get(i);
			ProductoSectorForm frm = new ProductoSectorForm();
			ProductoSector productoSector = (ProductoSector) object;

			frm.setCdProveedor(productoSector.getCdProveedor());
			frm.setCdProducto(productoSector.getCdProducto());
			frm.setCdSector(productoSector.getCdSector());
			frm.setNbSector(productoSector.getNbSector());
			frm.setStHabilitado(productoSector.getStHabilitado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProductoSectorForm frm = (ProductoSectorForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("cdSector", frm.getCdSector());
		jsonObject.put("nbSector", frm.getNbSector());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
