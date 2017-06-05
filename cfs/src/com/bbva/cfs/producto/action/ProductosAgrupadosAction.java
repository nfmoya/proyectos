package com.bbva.cfs.producto.action;

import java.io.PrintWriter;
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
import com.bbva.cfs.producto.form.ProductoAgrupadoForm;
import com.bbva.cfs.producto.service.GetProductosAgrupadosService;
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
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ProductosAgrupadosAction extends CommonAction {
	// private GetParameterListService getParamListService;

	private GetProductosAgrupadosService getProductosAgrupadosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoOrigList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroHabilitadoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List proveedorProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List productoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List originalList = new ArrayList();

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.initialise(request.getSession());
		ProductoAgrupadoForm productoAgrupadoForm = (ProductoAgrupadoForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, productoAgrupadoForm);
		obtenerCombos(productoAgrupadoForm);

		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request, ProductoAgrupadoForm productoAgrupadoForm)
			throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_CREATE.getId())) {
			log.info("Insertar Producto");
			productoAgrupadoForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_EDIT.getId())) {
			log.info("Edit Producto");
			productoAgrupadoForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_DELETE.getId())) {
			log.info("Delete Producto");
			productoAgrupadoForm.setDeleteGrant("S");
		}
	}

	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProductosAgrupados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		ProductoAgrupadoForm productoAgrupadoForm = (ProductoAgrupadoForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProductosAgrupadosService = new GetProductosAgrupadosService(this.iWebClient);
			result = getProductosAgrupadosService.execute();
			resp.put("ProductosAgrupadosList", getProductosAgrupadosService.getProductosAgrupadosList());
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerCombos(ProductoAgrupadoForm productoAgrupadoForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		GetTablasService tablasService = new GetTablasService(iWebClient);
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
			productoAgrupadoForm.setFiltroHabilitadoList(lista);
			productoAgrupadoForm.setHabilitadoProductoList(lista);

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			proveedorProductoList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				productoAgrupadoForm.setFiltroProveedorList(proveedorProductoList);
				productoAgrupadoForm.setProveedorProductoList(proveedorProductoList);

				if (result.isSuccesfull()) {
					// result =
					// getParamListService.execute(ParameterType.PRODUCTO.getId(),
					// "", "");
					result = getParamListService.execute(ParameterType.PRODUCTO_AGRUP_ORIG.getId(), "", "");
					productoList = getParamListService.getParameterList();
					if (result.isSuccesfull()) {
						productoAgrupadoForm.setFiltroProductoOrigList(productoList);
//						productoList.clear();
						result = getParamListService.execute(ParameterType.PRODUCTO_AGRUP.getId(), "", "");
						productoList = getParamListService.getParameterList();
						if (result.isSuccesfull()) {
							productoAgrupadoForm.setFiltroProductoList(productoList);
						} else {
							throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
						}
						// productoAgrupadoForm.setProductoList(productoList);
						// productoAgrupadoForm.setOriginalList(productoList);

					} else {
						throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
					}
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	/**
	 * Metodo que se encarga de editar el producto seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveProductoAgrupado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String opcion = request.getParameter("opcion");

		String cdProveedor = request.getParameter("cdProveedor");
		String cdProductoOrig = request.getParameter("cdProductoOrig");
		String cdProducto = request.getParameter("cdProducto");
		String desItem = request.getParameter("desItem");
		String desGrupo = request.getParameter("desGrupo");
		String stHabilitado = request.getParameter("stHabilitado");

		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosAgrupadosService getProductosAgrupadosService = new GetProductosAgrupadosService(
					this.iWebClient);

			result = getProductosAgrupadosService.saveProductoAgrupado(opcion, cdProveedor, cdProductoOrig, cdProducto,
					desItem, desGrupo, stHabilitado, this.getAutenticathedUserName(request));

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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteProductoAgrupado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdProductoOrig = request.getParameter("cdProductoOrig");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosAgrupadosService getProductosAgrupadosService = new GetProductosAgrupadosService(
					this.iWebClient);
			result = getProductosAgrupadosService.deleteProductoAgrupado(cdProveedor, cdProductoOrig);
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward getCombosProductos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdSector = "";

		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getParamListService.execute(ParameterType.PRODUCTO_AGRUP_ALTA.getId(), cdProveedor, "");
			productoList = getParamListService.getParameterList();
			resp.put("ProductoList", productoList);
			result = getParamListService.execute(ParameterType.PRODUCTO_AGRUP_ORIG_ALTA.getId(), cdProveedor, "");
			productoList = getParamListService.getParameterList();
			resp.put("ProductoListOrig", productoList);
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
}
