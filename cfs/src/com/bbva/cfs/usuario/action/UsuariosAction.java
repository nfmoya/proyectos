package com.bbva.cfs.usuario.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.usuario.service.GetUsuariosService;
//import com.bbva.cfs.pieza.model.Usuario;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * @author jmanrique
 * 
 */
public class UsuariosAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetUsuariosService getUsuariosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List perfilList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List sectorList;
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		UsuarioForm usuarioForm = (UsuarioForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
//		verificarPermisos(request, piezaForm);	
		obtenerCombos(usuarioForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			UsuarioForm usuarioForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
/*		
		if (this.checkPrivilege(request, Grants.ABMC_PROD_CONS.getId())) {
			piezaForm.setEnableGrant("Y");
			piezaForm.setAddGrant("Y");
			piezaForm.setDeleteGrant("Y");
			piezaForm.setDisableGrant("Y");
			piezaForm.setEditGrant("Y");
		}
*/
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getUsuarios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		@SuppressWarnings("unused")
		UsuarioForm usuarioForm = (UsuarioForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getUsuariosService = new GetUsuariosService(this.iWebClient);
			result = getUsuariosService.execute();
			resp.put("UsuariosList", getUsuariosService.getUsuariosList());
			resp.put("result", result);
			JSONObject jsonObject = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(jsonObject);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerCombos(UsuarioForm usuarioForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		try {
			List lista = new ArrayList();
				
			Parameter param = new Parameter("PRF", null, null, null);
			param.setCod("1");
			param.setDesc("ADMINISTRADOR");				
			lista.add(param);

			param = new Parameter("PRF", null, null, null);
			param.setCod("2");
			param.setDesc("CONCILIADOR");				
			lista.add(param);

			param = new Parameter("PRF", null, null, null);
			param.setCod("3");
			param.setDesc("CARGAR LOTES");				
			lista.add(param);
			
			param = new Parameter("PRF", null, null, null);
			param.setCod("4");
			param.setDesc("CONSULTA");				
			lista.add(param);
			
 			usuarioForm.setPerfilList(lista);

			result = getParamListService.execute(ParameterType.SECTOR.getId(), "", "");			
			sectorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				usuarioForm.setSectorList(sectorList);
			} else {
				throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
			}
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	/**
	 * Metodo que se encarga de editar el pieza seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion    = request.getParameter("opcion");
		Long usuarioId   = Long.valueOf(request.getParameter("usuarioId"));
		String userName  = request.getParameter("userName");
		Long perfil      = Long.valueOf(request.getParameter("perfil"));
		String nombre    = request.getParameter("nombre");
		String apellido  = request.getParameter("apellido");
		String cdSector  = request.getParameter("cdSector");
		
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetUsuariosService getUsuariosService = new GetUsuariosService(this.iWebClient); 
			result = getUsuariosService.saveUsuario(opcion, usuarioId, userName, perfil, 
					    nombre, apellido, cdSector, AuthenticationUtils.encryptPassword(userName),
					    this.getAutenticathedUserId(request));
			
			resp.put("result", result);
			JSONObject json = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(json);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		Long usuarioId = Long.valueOf(request.getParameter("usuarioId"));
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetUsuariosService getUsuariosService = new GetUsuariosService(this.iWebClient); 
			result = getUsuariosService.deleteUsuario(usuarioId, this.getAutenticathedUserId(request));
			resp.put("result", result);
			JSONObject json = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(json);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
}
