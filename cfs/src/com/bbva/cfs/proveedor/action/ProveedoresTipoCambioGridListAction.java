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

import com.bbva.cfs.proveedor.form.ProveedorTipoCambioForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;
import com.bbva.cfs.proveedor.model.ProveedorTipoCambio;
import com.bbva.cfs.proveedor.service.GetProveedoresTipoCambioService;

public class ProveedoresTipoCambioGridListAction extends GridAction {

	private String cdProveedor;
	private String cdPeriodo;

	public ProveedoresTipoCambioGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProveedoresTipoCambio(ActionMapping mapping,
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
		GetProveedoresTipoCambioService service = new GetProveedoresTipoCambioService(iWebClient);
		GetTablasService monedaService = new GetTablasService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor, cdPeriodo);
			}
			result = monedaService.execute("MONEDA");
			
			listaRet = this.fillData(service.getProveedoresTipoCambioList(), (List<Tabla>)monedaService.getTablasList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listProveedoresValores, List<Tabla> monedas) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listProveedoresValores.size(); i++) {  
	    	Object object = listProveedoresValores.get(i);
			ProveedorTipoCambioForm frm = new ProveedorTipoCambioForm();
			ProveedorTipoCambio proveedorTipoCambio = (ProveedorTipoCambio) object;
			String nuDias = (proveedorTipoCambio.getNuDias()).replace(".", ",");	
			frm.setCdProveedor(proveedorTipoCambio.getCdProveedor());
			frm.setCdPeriodoFact(proveedorTipoCambio.getCdPeriodoFact());
			frm.setCdMoneda(proveedorTipoCambio.getCdMoneda());
			for (Tabla	moneda : monedas) {
				if (proveedorTipoCambio.getCdMoneda().trim().equals(moneda.getCdCodTabla().trim())) {
					frm.setNbMoneda(moneda.getNbCodTablaCorto());
				}
			}			
			frm.setMonedaTipoCambioList(monedas);
			frm.setNuDias(nuDias);
			frm.setCdCotizacion(proveedorTipoCambio.getCdCotizacion());
			frm.setStHabilitado(proveedorTipoCambio.getStHabilitado());
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProveedorTipoCambioForm frm = (ProveedorTipoCambioForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdPeriodoFact", frm.getCdPeriodoFact());
		jsonObject.put("cdMoneda", frm.getCdMoneda());
		jsonObject.put("nbMoneda", frm.getNbMoneda());
		jsonObject.put("nuDias", frm.getNuDias());
		jsonObject.put("cdCotizacion", frm.getCdCotizacion());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
