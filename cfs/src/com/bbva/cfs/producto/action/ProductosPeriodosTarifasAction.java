package com.bbva.cfs.producto.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.bbva.cfs.producto.form.ProductoPeriodoTarifaForm;
import com.bbva.cfs.producto.form.ProductoPrecioForm;
import com.bbva.cfs.producto.service.GetProductosPeriodosTarifasService;
//import com.bbva.cfs.proveedor.model.Proveedor;
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
public class ProductosPeriodosTarifasAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProductosPeriodosTarifasService getProductosPeriodosTarifasService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorProductoTarifaList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProductoPeriodoTarifaForm productoPeriodoTarifaForm = (ProductoPeriodoTarifaForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, productoPeriodoTarifaForm);	
		obtenerCombos(productoPeriodoTarifaForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProductoPeriodoTarifaForm productoPeriodoTarifaForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_TARIFAS_CREATE.getId())){
			log.info("Insertar Periodo Tarifa");
			productoPeriodoTarifaForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_TARIFAS_EDIT.getId())){
			log.info("Edit Periodo Tarifa");
			productoPeriodoTarifaForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_TARIFAS_DELETE.getId())){
			log.info("Delete Periodo Tarifa");
			productoPeriodoTarifaForm.setDeleteGrant("S");
		} 
	}
	
/*
 	@SuppressWarnings("unchecked")
 	public ActionForward getProductosPeriodosTarifas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProductoPeriodoTarifaForm productoPeriodoTarifaForm = (ProductoPeriodoTarifaForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProductosPeriodosTarifasService = new GetProductosPeriodosTarifasService(this.iWebClient);
			result = getProductosPeriodosTarifasService.execute();
			resp.put("ProveedoresPeriodosTarifasList", getProductosPeriodosTarifasService.getProductosPeriodosTarifasList());
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
*/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerCombos(ProductoPeriodoTarifaForm productoPeriodoTarifaForm) throws GlobalActionException {
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
			productoPeriodoTarifaForm.setHabilitadoProductoTarifaList(lista);
			
			lista = new ArrayList();
			param = new Parameter("tipval", null, null, null);
			param.setCod("1");
			param.setDesc("Valorización Normal");
			lista.add(param);
			
			param = new Parameter("tipval", null, null, null);
			param.setCod("2");
			param.setDesc("Rangos Cantidades");
			lista.add(param);

			param = new Parameter("tipval", null, null, null);
			param.setCod("3");
			param.setDesc("Rangos Importes y % Comisión");
			lista.add(param);
			productoPeriodoTarifaForm.setProductoTipValList(lista);			
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorProductoTarifaList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				productoPeriodoTarifaForm.setProveedorProductoTarifaList(proveedorProductoTarifaList);
				result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
				filtroProveedorList = getParamListService.getParameterList();
				if (result.isSuccesfull()) {
					productoPeriodoTarifaForm.setFiltroProveedorList(filtroProveedorList);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward getComboProductosPeriodosTarifas(ActionMapping mapping, ActionForm form,
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
			resp.put("ProductoPeriodoTarifaList", productoList);
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
			resp.put("ProductoPeriodoTarifaList", productoList);
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
	public ActionForward saveProductoPeriodoTarifa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion             = request.getParameter("opcion");
		String cdProveedor        = request.getParameter("cdProveedor");
		String cdProducto         = request.getParameter("cdProducto");
		String fhDesde            = request.getParameter("fhDesde");
		String nuCantDesde        = request.getParameter("nuCantDesde");
		String nuCantHasta        = request.getParameter("nuCantHasta");
		String nuPrecioUniVal     = request.getParameter("nuPrecioUniVal");
		String nuPorcTarifa       = request.getParameter("nuPorcTarifa");
		String stHabilitado       = request.getParameter("stHabilitado");
		String stPrecioFijo       = request.getParameter("stPrecioFijo");
				
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProductosPeriodosTarifasService getProductosPeriodosTarifasService = new GetProductosPeriodosTarifasService(this.iWebClient); 
			result = getProductosPeriodosTarifasService.saveProductoPeriodoTarifa(opcion, cdProveedor, cdProducto, fhDesde, nuCantDesde,
									 nuCantHasta, nuPrecioUniVal, nuPorcTarifa, stHabilitado, stPrecioFijo, this.getAutenticathedUserName(request));			
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
	public ActionForward deleteProductoPeriodoTarifa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor  = request.getParameter("cdProveedor");
		String cdProducto   = request.getParameter("cdProducto");
//		Date fhDesde        = DateFormatCustomize.parseDateStrToDateShort(request.getParameter("fhDesde"));
		String fhDesde      = request.getParameter("fhDesde");
		String nuCantDesde  = request.getParameter("nuCantDesde");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosPeriodosTarifasService getProductosPeriodosTarifasService = new GetProductosPeriodosTarifasService(this.iWebClient); 
			result = getProductosPeriodosTarifasService.deleteProductoPeriodoTarifa(cdProveedor, cdProducto, fhDesde, nuCantDesde);
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
	public ActionForward deleteProductoPeriodoTarifas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor  = request.getParameter("cdProveedor");
		String cdProducto   = request.getParameter("cdProducto");
		String fhDesde      = request.getParameter("fhDesde");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosPeriodosTarifasService getProductosPeriodosTarifasService = new GetProductosPeriodosTarifasService(this.iWebClient); 
			result = getProductosPeriodosTarifasService.deleteProductoPeriodoTarifas(cdProveedor, cdProducto, fhDesde);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward getNuevoCodigoTarifa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor  = request.getParameter("cdProveedor");
		String cdProducto   = request.getParameter("cdProducto");
		String fhDesde      = request.getParameter("fhDesde");
		PrintWriter out = null;
	
		Map resp = new HashMap();
		try {
			GetProductosPeriodosTarifasService getProductosPeriodosTarifasService = new GetProductosPeriodosTarifasService(this.iWebClient); 
			result = getProductosPeriodosTarifasService.getNuevoCodigoTarifa(cdProveedor, cdProducto, fhDesde);

//			BigDecimal codigoTarifa = getProductosPeriodosTarifasService.getNuevoCodigoTarifa();
			resp.put("CodigoTarifaList", getProductosPeriodosTarifasService.getNuevoCodigoTarifaList());
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
	
}
