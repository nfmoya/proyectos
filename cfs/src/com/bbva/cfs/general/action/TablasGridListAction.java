package com.bbva.cfs.general.action;

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

import com.bbva.cfs.general.form.TablaForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;

public class TablasGridListAction extends GridAction {

//	private String cdTabla;
	private String cdCodTabla;

	public TablasGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridTablas(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

//		cdTabla    = request.getParameter("cdTabla");
		cdCodTabla = request.getParameter("cdCodTabla");

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
		GetTablasService service = new GetTablasService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdCodTabla.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdCodTabla);
			}
			listaRet = this.fillData(service.getTablasList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listTablas) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listTablas.size(); i++) {  
	    	Object object = listTablas.get(i);		
	    	TablaForm frm = new TablaForm();
			Tabla tabla = (Tabla) object;

			frm.setCdTabla(tabla.getCdTabla());
			frm.setCdCodTabla(tabla.getCdCodTabla());
			frm.setNbCodTabla(tabla.getNbCodTabla());
			frm.setNbCodTablaCorto(tabla.getNbCodTablaCorto());
			frm.setNbAtributoTabla1(tabla.getNbAtributoTabla1().replace("%2B", "+"));
			frm.setNbAtributoTabla2(tabla.getNbAtributoTabla2().replace("%2B", "+"));
			frm.setNbAtributoTabla3(tabla.getNbAtributoTabla3().replace("%2B", "+"));
			frm.setStHabilitado(tabla.getStHabilitado());

			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
    	TablaForm frm = new TablaForm();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdTabla", frm.getCdTabla());
		jsonObject.put("cdCodTabla", frm.getCdCodTabla());
		jsonObject.put("nbCodTabla", frm.getNbCodTabla());
		jsonObject.put("nbCodTablaCorto", frm.getNbCodTablaCorto());
		jsonObject.put("nbAtributoTabla1", frm.getNbAtributoTabla1());
		jsonObject.put("nbAtributoTabla2", frm.getNbAtributoTabla2());
		jsonObject.put("nbAtributoTabla3", frm.getNbAtributoTabla3());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
