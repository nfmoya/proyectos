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
import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.proveedor.form.ProveedorPeriodoForm;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ProveedoresPeriodosAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProveedoresPeriodosService getProveedoresPeriodosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorPeriodoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProveedorPeriodoForm proveedorPeriodoForm = (ProveedorPeriodoForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, proveedorPeriodoForm);	
		obtenerCombos(proveedorPeriodoForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProveedorPeriodoForm proveedorPeriodoForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_PERIODOS_CREATE.getId())){
			log.info("Insertar Periodo Proveedor");
			proveedorPeriodoForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_PERIODOS_EDIT.getId())){
			log.info("Edit Periodo Proveedor");
			proveedorPeriodoForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_PERIODOS_DELETE.getId())){
			log.info("Delete Periodo Proveedor");
			proveedorPeriodoForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProveedoresPeriodos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProveedorPeriodoForm proveedorPeriodoForm = (ProveedorPeriodoForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProveedoresPeriodosService = new GetProveedoresPeriodosService(this.iWebClient);
			result = getProveedoresPeriodosService.execute();
			resp.put("ProveedoresPeriodosList", getProveedoresPeriodosService.getProveedoresPeriodosList());
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
	private void obtenerCombos(ProveedorPeriodoForm proveedorPeriodoForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		try {
			List lista = new ArrayList();
			
			Parameter param = new Parameter("ESTADO", null, null, null);
			param.setCod("ABI");
			param.setDesc("Abierto");				
			lista.add(param);

			param = new Parameter("ESTADO", null, null, null);
			param.setCod("ANU");
			param.setDesc("Anulado");				
			lista.add(param);
			
			param = new Parameter("ESTADO", null, null, null);
			param.setCod("CER");
			param.setDesc("Cerrado");				
			lista.add(param);
			
			param = new Parameter("ESTADO", null, null, null);
			param.setCod("PEN");
			param.setDesc("Pendiente");				
			lista.add(param);
			proveedorPeriodoForm.setEstadoList(lista);
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorPeriodoList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				proveedorPeriodoForm.setProveedorPeriodoList(proveedorPeriodoList);
				proveedorPeriodoForm.setFiltroProveedorList(proveedorPeriodoList);
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
	public ActionForward saveProveedorPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion           = request.getParameter("opcion");
		String cdProveedor      = request.getParameter("cdProveedor");
		String cdPeriodoFact    = request.getParameter("cdPeriodoFact");
		String nbPeriodoFact    = request.getParameter("nbPeriodoFact");
		String cdPerFactAlt     = request.getParameter("cdPerFactAlt");
//		Date fhDesde            = DateFormatCustomize.parseDateStrToDateShort(request.getParameter("fhDesde"));
//		Date fhHasta            = DateFormatCustomize.parseDateStrToDateShort(request.getParameter("fhHasta"));
		String fhDesde          = request.getParameter("fhDesde");
		String fhHasta          = request.getParameter("fhHasta");
		String stEstado         = request.getParameter("stEstado");
		
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProveedoresPeriodosService getProveedoresPeriodosService = new GetProveedoresPeriodosService(this.iWebClient); 
			result = getProveedoresPeriodosService.saveProveedorPeriodo(opcion, cdProveedor, cdPeriodoFact, nbPeriodoFact, 
					cdPerFactAlt, fhDesde, fhHasta, stEstado, this.getAutenticathedUserName(request));			
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
	public ActionForward deleteProveedorPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor      = request.getParameter("cdProveedor");
		String cdPeriodoFact    = request.getParameter("cdPeriodoFact");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresPeriodosService getProveedoresService = new GetProveedoresPeriodosService(this.iWebClient); 
			result = getProveedoresPeriodosService.deleteProveedorPeriodo(cdProveedor, cdPeriodoFact);
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
