package com.bbva.cfs.usuario.action;

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

import com.bbva.cfs.commons.action.GridActionWithoutPaginate;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.usuario.model.Usuario;
import com.bbva.cfs.usuario.service.GetUsuariosService;

public class UsuariosGridListAction extends GridActionWithoutPaginate {

	public UsuariosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridUsuarios(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

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
		GetUsuariosService service = new GetUsuariosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			result = service.execute();
			listaRet = this.fillData(service.getUsuariosList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listUsuarios) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listUsuarios.size(); i++) {  
	    	Object object = listUsuarios.get(i);		
			UsuarioForm frm = new UsuarioForm();
			Usuario usuario = (Usuario) object;

			frm.setUsuarioId(usuario.getUsuarioId());
			frm.setUserName(usuario.getUserName());
			frm.setPerfil(usuario.getPerfil());
			frm.setPerfilName(usuario.getPerfilName());
			frm.setNombre(usuario.getNombre());
			frm.setApellido(usuario.getApellido());
			frm.setCdSector(usuario.getCdSector());
			frm.setNbSector(usuario.getNbSector());
			frm.setEmail(usuario.getEmail());

			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		UsuarioForm frm = (UsuarioForm) form;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("usuarioId", frm.getUsuarioId());
		jsonObject.put("userName", frm.getUserName());
		jsonObject.put("perfil", frm.getPerfil());
		jsonObject.put("perfilName", frm.getPerfilName());
		jsonObject.put("nombre", frm.getNombre());
		jsonObject.put("apellido", frm.getApellido());
		jsonObject.put("cdSector", frm.getCdSector());
		jsonObject.put("nbSector", frm.getNbSector());
		jsonObject.put("email", frm.getEmail());

		return jsonObject;
	}
}
