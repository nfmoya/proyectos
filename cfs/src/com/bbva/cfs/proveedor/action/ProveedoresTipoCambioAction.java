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
import com.bbva.cfs.proveedor.form.ProveedorTipoCambioForm;
//import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.proveedor.service.GetProveedoresTipoCambioService;
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
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;

@SuppressWarnings("unused")
public class ProveedoresTipoCambioAction extends CommonAction {
	// private GetParameterListService getParamListService;

	private GetProveedoresTipoCambioService getProveedoresTipoCambioService;

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
	private List filtroProveedorList = new ArrayList();

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {

		this.initialise(request.getSession());
		ProveedorTipoCambioForm proveedorTipoCambioForm = (ProveedorTipoCambioForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, proveedorTipoCambioForm);
		obtenerCombos(proveedorTipoCambioForm);

		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request, ProveedorTipoCambioForm proveedorTipoCambioForm)
			throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_TIPOCAMBIO_CREATE.getId())) {
			log.info("Insertar Tipo Cambio Proveedor");
			proveedorTipoCambioForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_TIPOCAMBIO_EDIT.getId())) {
			log.info("Edit Tipo Cambio Proveedor");
			proveedorTipoCambioForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PROVEEDORES_TIPOCAMBIO_DELETE.getId())) {
			log.info("Delete Tipo Cambio Proveedor");
			proveedorTipoCambioForm.setDeleteGrant("S");
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward getProveedoresTipoCambio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		ProveedorTipoCambioForm proveedorTipoCambioForm = (ProveedorTipoCambioForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProveedoresTipoCambioService = new GetProveedoresTipoCambioService(this.iWebClient);
			result = getProveedoresTipoCambioService.execute();
			resp.put("ProveedoresTipoCambioList", getProveedoresTipoCambioService.getProveedoresTipoCambioList());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void obtenerCombos(ProveedorTipoCambioForm proveedorTipoCambioForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		GetTablasService tablaService = new GetTablasService(iWebClient);
		try {
			// ESTADO HABILITADO
			List lista = new ArrayList();
			Parameter param = new Parameter("S_N", null, null, null);
			param.setCod("S");
			param.setDesc("S&iacute;");
			lista.add(param);

			param = new Parameter("S_N", null, null, null);
			param.setCod("N");
			param.setDesc("No");
			lista.add(param);
			proveedorTipoCambioForm.setHabilitadoTipoCambioList(lista);
			
			// TIPOS DE COTIZACIONES
			lista = new ArrayList();
			param = new Parameter("cotizacion", null, null, null);
			param.setCod("ALTO");
			param.setDesc("ALTO");
			lista.add(param);
			
			param = new Parameter("cotizacion", null, null, null);
			param.setCod("MEDIO");
			param.setDesc("MEDIO");
			lista.add(param);

			param = new Parameter("cotizacion", null, null, null);
			param.setCod("BAJO");
			param.setDesc("BAJO");
			lista.add(param);
			proveedorTipoCambioForm.setCotizacionTipoCambioList(lista);
			
			// TIPOS DE MONEDA
			tablaService.execute("MONEDA");
			lista = new ArrayList();
			List<Tabla> listMonedas = (List<Tabla>) tablaService.getTablasList();

			for (Tabla tabla : listMonedas) {
				param = new Parameter("moneda", null, null, null);
				param.setCod(tabla.getCdCodTabla().trim());
				param.setDesc(tabla.getNbCodTablaCorto().trim());
				lista.add(param);
			}
			// param = new Parameter("moneda", null, null, null);
			// param.setCod("PES");
			// param.setDesc("$");
			// lista.add(param);
			//
			// param = new Parameter("moneda", null, null, null);
			// param.setCod("USD");
			// param.setDesc("u$s BNA");
			// lista.add(param);
			//
			// param = new Parameter("moneda", null, null, null);
			// param.setCod("EUR");
			// param.setDesc("eur BNA");
			// lista.add(param);
			proveedorTipoCambioForm.setMonedaTipoCambioList(lista);

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			proveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				proveedorTipoCambioForm.setProveedorTipoCambioList(proveedorList);
				proveedorTipoCambioForm.setFiltroProveedorList(proveedorList);

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

	@SuppressWarnings("unchecked")
	public ActionForward getComboProveedoresPeriodos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
						  							 HttpServletResponse response) throws Exception {
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
	public ActionForward saveProveedorTipoCambio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());

		String opcion = request.getParameter("opcion");
		String cdProveedor = request.getParameter("cdProveedor");
		String cdPeriodoFact = request.getParameter("cdPeriodoFact");
		String cdMoneda = request.getParameter("cdMoneda");
		String nuDias = request.getParameter("nuDias");
		String cdCotizacion = request.getParameter("cdCotizacion");
		String stHabilitado = request.getParameter("stHabilitado");
		nuDias = nuDias.replaceAll(",", ".");

		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresTipoCambioService getProveedoresTipoCambioService = new GetProveedoresTipoCambioService(
					this.iWebClient);
			result = getProveedoresTipoCambioService.saveProveedorTipoCambio(opcion, cdProveedor, cdPeriodoFact,
					cdMoneda, nuDias, cdCotizacion, stHabilitado, this.getAutenticathedUserName(request));
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
	public ActionForward deleteProveedorTipoCambio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdPeriodoFact = request.getParameter("cdPeriodoFact");
		String cdMoneda = request.getParameter("cdMoneda");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresTipoCambioService getProveedoresTipoCambioService = new GetProveedoresTipoCambioService(
					this.iWebClient);
			result = getProveedoresTipoCambioService.deleteProveedorTipoCambio(cdProveedor, cdPeriodoFact, cdMoneda);
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
}
