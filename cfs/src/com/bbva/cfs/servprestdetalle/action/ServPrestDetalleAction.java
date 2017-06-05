package com.bbva.cfs.servprestdetalle.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bbva.cfs.producto.service.GetProductosService;
import com.bbva.cfs.sector.service.GetSectoresService;
import com.bbva.cfs.servfact.form.ServFactForm;
import com.bbva.cfs.servprestdetalle.form.ServPrestDetalleForm;

public class ServPrestDetalleAction extends CommonAction {

	// private GetParameterListService getParamListService;

	private GetSectoresService getSectoresService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List grupoProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List pPrestDesdeList = new ArrayList();
	private String sector;
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		ServPrestDetalleForm servPrestDetalleForm = (ServPrestDetalleForm) form;
		servPrestDetalleForm .setSelectedProducto(request.getParameter("selectedProducto"));
		
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, servPrestDetalleForm);
		obtenerCombos(servPrestDetalleForm);
		sector = this.getSector(request);
		servPrestDetalleForm.setSector(sector);
		
		if( servPrestDetalleForm.getSelectedProducto() != null
				&& !servPrestDetalleForm.getSelectedProducto().equals("")
				&& !servPrestDetalleForm.getSelectedProducto().equals("0")){
			obtenerComboProductos(servPrestDetalleForm);                
		}
		return doFindSuccess(mapping);
	}
	
	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ServPrestDetalleForm servPrestDetalleForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_SECTORES_CREATE.getId())) {
			log.info("Insertar Sector");
			servPrestDetalleForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_EDIT.getId())) {
			log.info("Edit Sector");
			servPrestDetalleForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_DELETE.getId())) {
			log.info("Delete Sector");
			servPrestDetalleForm.setDeleteGrant("S");
		}
	}

	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getSectores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ServPrestDetalleForm servPrestDetalleForm = (ServPrestDetalleForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getSectoresService = new GetSectoresService(this.iWebClient);
			result = getSectoresService.execute();
			resp.put("SectoresList", getSectoresService.getSectoresList());
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerComboSector(ServPrestDetalleForm servPrestDetalleForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
		
			servPrestDetalleForm.setEstadoList(obtenerEstados());

			result = getParamListService.execute(ParameterType.SECTOR.getId(),
					"", "");
			filtroSectorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestDetalleForm.setFiltroSectorList(filtroSectorList);
			} else {
				throw new GlobalActionException(result.getErrorCode(),
						result.getErrorDesc());
			}

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerComboProveedor(ServPrestDetalleForm servPrestDetalleForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestDetalleForm.setFiltroProveedorList(filtroProveedorList);
			} else {
				throw new GlobalActionException(result.getErrorCode(),
						result.getErrorDesc());
			}
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}
	
	
	private void obtenerCombosAlt(ServPrestDetalleForm servPrestDetalleForm) {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		String sector = servPrestDetalleForm.getSelectedSector().equalsIgnoreCase("0") ? "" : servPrestDetalleForm.getSelectedSector();
		String prov = servPrestDetalleForm.getSelectedProveedor().equalsIgnoreCase("0") ? "" : servPrestDetalleForm.getSelectedProveedor();
		
		try {
			result = getParamListService.execute(ParameterType.CONC_PROVEEDOR_PERIODO.getId(),prov, sector);	
			result = getParamListService.execute(ParameterType.CONC_PROVEEDOR_PRODUCTO.getId(), prov, sector);			
			servPrestDetalleForm.setFiltroProductoList( getParamListService.getParameterList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerComboProductos(ServPrestDetalleForm servPrestDetalleForm)
			throws GlobalActionException {
		GetProductosService getProductosService = new GetProductosService(
				this.iWebClient);
		Result result;
		try {
			result = getProductosService.execute( servPrestDetalleForm.getSelectedProveedor(), servPrestDetalleForm.getSelectedProducto() );
			servPrestDetalleForm.setFiltroProductoList( getProductosService.getProductosList() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List obtenerEstados() {
		
		List lista = new ArrayList();
		Parameter param = new Parameter("A_C_N", null, null, null);
		param.setCod("A");
		param.setDesc("Abierto");
		lista.add(param);

		param = new Parameter("A_C_N", null, null, null);
		param.setCod("C");
		param.setDesc("Cerrado");
		lista.add(param);
		
		param = new Parameter("A_C_N", null, null, null);
		param.setCod("N");
		param.setDesc("Anulado");
		lista.add(param);
		
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerCombos(ServPrestDetalleForm servPrestDetalleForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
			servPrestDetalleForm.setEstadoList(obtenerEstados());

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestDetalleForm.setFiltroProveedorList(filtroProveedorList);
				result = getParamListService.execute(ParameterType.SECTOR.getId(), "", "");
				filtroSectorList = getParamListService.getParameterList();
				
				if (result.isSuccesfull()) {
					servPrestDetalleForm.setFiltroSectorList(filtroSectorList);
					
					result = getParamListService.execute(ParameterType.TABLA_GRUPRO.getId(), "", "");

					grupoProductoList = getParamListService.getParameterList();
					if (result.isSuccesfull()) {
						servPrestDetalleForm.setFilterGroupList(grupoProductoList);

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
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	/**
	 * Metodo que se encarga de editar el sector seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveSector(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion = request.getParameter("opcion");
		String cdSector = request.getParameter("cdSector");
		String nbSector = request.getParameter("nbSector");
		String nbSectorAbrev = request.getParameter("nbSectorAbrev");
		String cdSectorAlt = request.getParameter("cdSectorAlt");
		String stHabilitado = request.getParameter("stHabilitado");

		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetSectoresService getSectoresService = new GetSectoresService(
					this.iWebClient);
			result = getSectoresService.saveSector(opcion, cdSector, nbSector,
					nbSectorAbrev, cdSectorAlt, stHabilitado,
					this.getAutenticathedUserName(request));
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
	public ActionForward deleteSector(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdSector = request.getParameter("cdSector");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetSectoresService getSectoresService = new GetSectoresService(
					this.iWebClient);
			result = getSectoresService.deleteSector(cdSector);
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
