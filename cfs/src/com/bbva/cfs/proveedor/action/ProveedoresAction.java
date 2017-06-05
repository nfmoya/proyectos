package com.bbva.cfs.proveedor.action;

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

import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.proveedor.form.ProveedorForm;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.GetProveedoresService;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.Grants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ProveedoresAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProveedoresService getProveedoresService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProveedorForm proveedorForm = (ProveedorForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, proveedorForm);	
		obtenerCombos(proveedorForm);
		
		return doFindSuccess(mapping);
	}
	@SuppressWarnings("unchecked")
	public ActionForward getComboProveedores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProveedorForm proveedorForm = (ProveedorForm) form;
		System.out.println("entro");
		obtenerCombos(proveedorForm);

		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			PrintWriter out = null;
			GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			filtroProveedorList = getParamListService.getParameterList();
			for (int i = 0; i < filtroProveedorList.size(); i++) {
				Parameter param = (Parameter) filtroProveedorList.get(i);
				System.out.println(param.getDesc());
			}
			resp.put("filtroProveedorList", filtroProveedorList);
			resp.put("result", result);
			JSONObject jsonObject = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(jsonObject);
			if (out != null) {
				out.flush();
				out.close();
			}
			System.out.println(result.getErrorDesc());
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
		
		
		
		System.out.println("tomo y salio!");
		
		return null;
	}
	
	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProveedorForm proveedorForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_CREATE.getId())){
			log.info("Insertar Proveedor");
			proveedorForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_EDIT.getId())){
			log.info("Edit Proveedor");
			proveedorForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_DELETE.getId())){
			log.info("Delete Proveedor");
			proveedorForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProveedores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProveedorForm proveedorForm = (ProveedorForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProveedoresService = new GetProveedoresService(this.iWebClient);
			result = getProveedoresService.execute();
			resp.put("ProveedoresList", getProveedoresService.getProveedoresList());
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
	private void obtenerCombos(ProveedorForm proveedorForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		try {
			List lista = new ArrayList();
				
			Parameter param = new Parameter("S_N", null, null, null);
			param.setCod("S");
			param.setDesc("S&iacute;");					
			lista.add(param);

			param = new Parameter("S_N", null, null, null);
			param.setCod("N");
			param.setDesc("No");				
			lista.add(param);
				
			proveedorForm.setHabilitadoList(lista);
			proveedorForm.setNbAtributoProv1List(lista);
			proveedorForm.setNbAtributoProv2List(lista);
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				proveedorForm.setFiltroProveedorList(filtroProveedorList);
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
	 * Metodo que se encarga de editar el proveedor seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveProveedor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion           = request.getParameter("opcion");
		String cdProveedor      = request.getParameter("cdProveedor");
		String nbProveedor      = request.getParameter("nbProveedor");
		String nbProveedorCorto = request.getParameter("nbProveedorCorto");
		Long nuCuit             = Long.valueOf(request.getParameter("nuCuit"));
		String nbAtributoProv1  = request.getParameter("nbAtributoProv1");
		String nbAtributoProv2  = request.getParameter("nbAtributoProv2");
		String nbAtributoProv3  =  this.formatNumberDecimal(request.getParameter("nbAtributoProv3"));
		String nbAtributoProv4  =  this.formatNumberDecimal(request.getParameter("nbAtributoProv4"));
		String nbAtributoProv5  = request.getParameter("nbAtributoProv5");
		String stHabilitado		= request.getParameter("stHabilitado");
			
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProveedoresService getProveedoresService = new GetProveedoresService(this.iWebClient); 
			result = getProveedoresService.saveProveedor(opcion, cdProveedor, nbProveedor, nbProveedorCorto, 
					nuCuit, nbAtributoProv1, nbAtributoProv2, nbAtributoProv3, nbAtributoProv4,
					nbAtributoProv5, stHabilitado, this.getAutenticathedUserName(request));			
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

	private String formatNumberDecimal(String parameter) {
		// TODO Auto-generated method stub
		String[] split = parameter.split(",");
		String strDecimal = split[1].length() > 1 ? split[1] : split[1]+"0";
		String strEntero = String.format("%04d",Integer.valueOf(split[0]));
		
		return strEntero+strDecimal;
	}
	@SuppressWarnings("unchecked")
	public ActionForward deleteProveedor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor      = request.getParameter("cdProveedor");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresService getProveedoresService = new GetProveedoresService(this.iWebClient); 
			result = getProveedoresService.deleteProveedor(cdProveedor);
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
