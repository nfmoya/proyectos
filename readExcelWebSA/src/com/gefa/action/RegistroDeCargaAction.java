package com.gefa.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.core.RestriccionAction;
import com.gefa.beans.RegistroCarga;
import com.gefa.dao.GefaRegistroCargaDao;
import com.gefa.model.GefaAlta;
import com.gefa.model.GefaModificaciones;

public class RegistroDeCargaAction extends  RestriccionAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			GefaRegistroCargaDao cargaDao = new GefaRegistroCargaDao();

			List<RegistroCarga> listaRegistrosAlta = cargaDao.getRegistrosCarga();

			request.setAttribute("registroList", listaRegistrosAlta);	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return mapping.findForward("success");
	}

}
