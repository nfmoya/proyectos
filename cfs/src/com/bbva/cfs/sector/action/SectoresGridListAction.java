package com.bbva.cfs.sector.action;

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

import com.bbva.cfs.sector.form.SectorForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.sector.model.Sector;
import com.bbva.cfs.sector.service.GetSectoresService;

public class SectoresGridListAction extends GridAction {

	private String cdSector;
	
	public SectoresGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridSectores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		cdSector = request.getParameter("cdSector");
		
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
		GetSectoresService service = new GetSectoresService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdSector.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdSector);
			}
			listaRet = this.fillData(service.getSectoresList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listSectores) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listSectores.size(); i++) {  
	    	Object object = listSectores.get(i);		
			SectorForm frm = new SectorForm();
			Sector sector = (Sector) object;

			frm.setCdSector(sector.getCdSector());
			frm.setNbSector(sector.getNbSector());
			frm.setNbSectorAbrev(sector.getNbSectorAbrev());
			frm.setCdSectorAlt(sector.getCdSectorAlt());
			frm.setStHabilitado(sector.getStHabilitado());

			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		SectorForm frm = (SectorForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cdSector", frm.getCdSector());
		jsonObject.put("nbSector", frm.getNbSector());
		jsonObject.put("nbSectorAbrev", frm.getNbSectorAbrev());
		jsonObject.put("cdSectorAlt", frm.getCdSectorAlt());
		jsonObject.put("stHabilitado", frm.getStHabilitado());

		return jsonObject;
	}
}
