package com.bbva.cfs.producto.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.bbva.cfs.producto.form.ProductoForm;
import com.bbva.cfs.producto.form.ProductoPeriodoForm;
import com.bbva.cfs.producto.form.ProductoPrecioForm;
import com.bbva.cfs.producto.service.GetProductosPeriodosService;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.producto.service.GetProductosPreciosService;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.DateFormatCustomize;
import com.bbva.cfs.commons.utils.Grants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ProductosPeriodosAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProductosPeriodosService getProductosPeriodosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorProductoPeriodoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProductoPeriodoForm productoPeriodoForm = (ProductoPeriodoForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, productoPeriodoForm);	
		obtenerCombos(productoPeriodoForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProductoPeriodoForm productoPeriodoForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PERIODOS_CREATE.getId())){
			log.info("Insertar Periodo Producto");
			productoPeriodoForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PERIODOS_EDIT.getId())){
			log.info("Edit Periodo Producto");
			productoPeriodoForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PERIODOS_DELETE.getId())){
			log.info("Delete Periodo Producto");
			productoPeriodoForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProductosPeriodos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProductoPeriodoForm productoPeriodoForm = (ProductoPeriodoForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProductosPeriodosService = new GetProductosPeriodosService(this.iWebClient);
			result = getProductosPeriodosService.execute();
			resp.put("ProveedoresPeriodosList", getProductosPeriodosService.getProductosPeriodosList());
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
	private void obtenerCombos(ProductoPeriodoForm productoPeriodoForm) throws GlobalActionException {
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
				
			productoPeriodoForm.setHabilitadoProductoPeriodoList(lista);
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorProductoPeriodoList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				productoPeriodoForm.setProveedorProductoPeriodoList(proveedorProductoPeriodoList);
				result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
				filtroProveedorList = getParamListService.getParameterList();
				if (result.isSuccesfull()) {
					productoPeriodoForm.setFiltroProveedorList(filtroProveedorList);
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
	
	
	public ActionForward getComboProductosPeriodos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		PrintWriter out = null;
	
		Map resp = new HashMap();
		try {			
			result = getParamListService.execute(ParameterType.PRODUCTO.getId(), cdProveedor, "");			
			productoList = getParamListService.getParameterList();
			resp.put("ProductoPeriodoList", productoList);
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
	public ActionForward saveProductoPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion             = request.getParameter("opcion");
		String cdProveedor        = request.getParameter("cdProveedor");
		String cdProducto         = request.getParameter("cdProducto");
		String fhDesde            = request.getParameter("fhDesde");
		String fhHasta            = request.getParameter("fhHasta");
		String stHabilitado       = request.getParameter("stHabilitado");
		
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProductosPeriodosService getProductosPeriodosService = new GetProductosPeriodosService(this.iWebClient); 
			result = getProductosPeriodosService.saveProductoPeriodo(opcion, cdProveedor, cdProducto, fhDesde, fhHasta, 
					                 stHabilitado, this.getAutenticathedUserName(request));			
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
	public ActionForward deleteProductoPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor  = request.getParameter("cdProveedor");
		String cdProducto   = request.getParameter("cdProducto");
		Date fhDesde        = DateFormatCustomize.parseDateStrToDateShort(request.getParameter("fhDesde"));
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosPeriodosService getProductosPeriodosService = new GetProductosPeriodosService(this.iWebClient); 
			result = getProductosPeriodosService.deleteProductoPeriodo(cdProveedor, cdProducto, fhDesde);
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
