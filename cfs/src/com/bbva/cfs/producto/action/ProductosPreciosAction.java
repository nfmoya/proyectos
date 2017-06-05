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
import com.bbva.cfs.producto.form.ProductoPrecioForm;
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
public class ProductosPreciosAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProductosPreciosService getProductosPreciosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorProductoPrecioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProductoPrecioForm productoPrecioForm = (ProductoPrecioForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, productoPrecioForm);	
		obtenerCombos(productoPrecioForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProductoPrecioForm productoPrecioForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PRECIOS_CREATE.getId())){
			log.info("Insertar Precio Producto");
			productoPrecioForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PRECIOS_EDIT.getId())){
			log.info("Edit Precio Producto");
			productoPrecioForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_PRECIOS_DELETE.getId())){
			log.info("Delete Precio Producto");
			productoPrecioForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProductosPrecios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProductoPrecioForm productoPrecioForm = (ProductoPrecioForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProductosPreciosService = new GetProductosPreciosService(this.iWebClient);
			result = getProductosPreciosService.execute();
			resp.put("ProveedoresPeriodosList", getProductosPreciosService.getProductosPreciosList());
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
	private void obtenerCombos(ProductoPrecioForm productoPrecioForm) throws GlobalActionException {
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
				
			productoPrecioForm.setHabilitadoProductoPrecioList(lista);
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorProductoPrecioList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				productoPrecioForm.setProveedorProductoPrecioList(proveedorProductoPrecioList);
				result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
				filtroProveedorList = getParamListService.getParameterList();
				if (result.isSuccesfull()) {
					productoPrecioForm.setFiltroProveedorList(filtroProveedorList);
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
	
	
	public ActionForward getComboProductosPrecios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		PrintWriter out = null;
	
		Map resp = new HashMap();
		try {			
			String parameterType = request.getParameter("isConsConcil") != null ? ParameterType.PRODUCTO_CONS_CONCIL.getId() : ParameterType.PRODUCTO.getId();
			result = getParamListService.execute(parameterType, cdProveedor, "");			
			productoList = getParamListService.getParameterList();
			resp.put("ProductoPrecioList", productoList);
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
	public ActionForward saveProductoPrecio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion             = request.getParameter("opcion");
		String cdProveedor        = request.getParameter("cdProveedor");
		String cdProducto         = request.getParameter("cdProducto");
		String fhDesde            = request.getParameter("fhDesde");
		String fhHasta            = request.getParameter("fhHasta");
		String nuPrecioUniVal     = request.getParameter("nuPrecioUniVal");
		String stHabilitado       = request.getParameter("stHabilitado");
		
		
		nuPrecioUniVal = nuPrecioUniVal.replaceAll(",", ".");
		
		
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProductosPreciosService getProductosPreciosService = new GetProductosPreciosService(this.iWebClient); 
			result = getProductosPreciosService.saveProductoPrecio(opcion, cdProveedor, cdProducto, fhDesde, fhHasta, 
					                 nuPrecioUniVal, stHabilitado, this.getAutenticathedUserName(request));			
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
	public ActionForward deleteProductoPrecio(ActionMapping mapping, ActionForm form,
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
			GetProductosPreciosService getProductosPreciosService = new GetProductosPreciosService(this.iWebClient); 
			result = getProductosPreciosService.deleteProductoPrecio(cdProveedor, cdProducto, fhDesde);
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
