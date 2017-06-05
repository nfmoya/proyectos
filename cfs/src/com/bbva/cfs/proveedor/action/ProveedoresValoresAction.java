package com.bbva.cfs.proveedor.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.bbva.cfs.proveedor.form.ProveedorPeriodoForm;
import com.bbva.cfs.proveedor.form.ProveedorValorForm;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.GetProveedoresValoresService;
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
public class ProveedoresValoresAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProveedoresValoresService getProveedoresValoresService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorPeriodoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List uniValList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProveedorValorForm proveedorValorForm = (ProveedorValorForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, proveedorValorForm);	
		obtenerCombos(proveedorValorForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProveedorValorForm proveedorValorForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_VALORES_CREATE.getId())){
			log.info("Insertar Valor Proveedor");
			proveedorValorForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_VALORES_EDIT.getId())){
			log.info("Edit Valor Proveedor");
			proveedorValorForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_VALORES_DELETE.getId())){
			log.info("Delete Valor Proveedor");
			proveedorValorForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProveedoresValores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProveedorValorForm proveedorValorForm = (ProveedorValorForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProveedoresValoresService = new GetProveedoresValoresService(this.iWebClient);
			result = getProveedoresValoresService.execute();
			resp.put("ProveedoresValoresList", getProveedoresValoresService.getProveedoresValoresList());
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void obtenerCombos(ProveedorValorForm proveedorValorForm) throws GlobalActionException {
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
				
			proveedorValorForm.setHabilitadoValorList(lista);
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				proveedorValorForm.setProveedorValorList(proveedorList);
				proveedorValorForm.setFiltroProveedorList(proveedorList);

				result = getParamListService.execute(ParameterType.TABLA_UNIVAL.getId(), "", "");			
				uniValList = getParamListService.getParameterList();
				if (result.isSuccesfull()) {
					proveedorValorForm.setUniValList(uniValList);

				} else {
					throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
				}
			} else {
				throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
			}
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward getComboProveedoresPeriodos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdPeriodo   = request.getParameter("cdPeriodo");
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getParamListService.execute(ParameterType.PROVEEDOR_PERIODO.getId(), cdProveedor, cdPeriodo);			
			proveedorPeriodoList = getParamListService.getParameterList();
			resp.put("ProveedorPeriodoList", proveedorPeriodoList);
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
	public ActionForward saveProveedorValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		
		String opcion               = request.getParameter("opcion");
		String cdProveedor          = request.getParameter("cdProveedor");
		String cdPeriodoFact        = request.getParameter("cdPeriodoFact");
		String cdUniVal             = request.getParameter("cdUniVal");
		String nuValBrutoUniVal     = request.getParameter("nuValBrutoUniVal");
		String nuValNetoUniVal      = request.getParameter("nuValNetoUniVal");
		String stHabilitado	        = request.getParameter("stHabilitado");
		
		nuValBrutoUniVal = nuValBrutoUniVal.replaceAll(",", ".");
		nuValNetoUniVal = nuValNetoUniVal.replaceAll(",", ".");
			
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProveedoresValoresService getProveedoresValoresService = new GetProveedoresValoresService(this.iWebClient); 
			result = getProveedoresValoresService.saveProveedorValor(opcion, cdProveedor, cdPeriodoFact, cdUniVal, 
					nuValBrutoUniVal, nuValNetoUniVal, stHabilitado, this.getAutenticathedUserName(request));			
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
	public ActionForward deleteProveedorValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor   = request.getParameter("cdProveedor");
		String cdPeriodoFact = request.getParameter("cdPeriodoFact");
		String cdUniVal      = request.getParameter("cdUniVal");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresValoresService getProveedoresValoresService = new GetProveedoresValoresService(this.iWebClient); 
			result = getProveedoresValoresService.deleteProveedorValor(cdProveedor, cdPeriodoFact, cdUniVal);
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
