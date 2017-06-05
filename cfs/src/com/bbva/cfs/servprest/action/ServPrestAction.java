package com.bbva.cfs.servprest.action;

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
import com.bbva.cfs.servprest.form.ServPrestForm;
import com.bbva.cfs.servprestdetalle.form.ServPrestDetalleForm;

public class ServPrestAction extends CommonAction {

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
	private String sector;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		ServPrestForm servPrestForm = (ServPrestForm) form;
		servPrestForm.setStLoteDet("C");
		userId = this.getAutenticathedUserId(request);
		sector = this.getSector(request);
		servPrestForm.setSector(sector);		
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, servPrestForm);
		obtenerCombos(servPrestForm);
		if( servPrestForm.getSelectedProducto() != null
				&& !servPrestForm.getSelectedProducto().equals("")
				&& !servPrestForm.getSelectedProducto().equals("0")){
			obtenerComboProductos(servPrestForm);                
		}

		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ServPrestForm servPrestForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_SECTORES_CREATE.getId())) {
			log.info("Insertar Sector");
			servPrestForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_EDIT.getId())) {
			log.info("Edit Sector");
			servPrestForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_DELETE.getId())) {
			log.info("Delete Sector");
			servPrestForm.setDeleteGrant("S");
		}
	}

	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getSectores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ServPrestForm servPrestForm = (ServPrestForm) form;
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
	private void obtenerComboSector(ServPrestForm servPrestForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
		
			servPrestForm.setEstadoList(obtenerEstados());

			result = getParamListService.execute(ParameterType.SECTOR.getId(),
					"", "");
			filtroSectorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestForm.setFiltroSectorList(filtroSectorList);
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
	private void obtenerComboProveedor(ServPrestForm servPrestForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestForm.setFiltroProveedorList(filtroProveedorList);
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
	private void obtenerComboProductos(ServPrestForm servPrestForm)
			throws GlobalActionException {
		GetProductosService getProductosService = new GetProductosService(
				this.iWebClient);
		Result result;
		try {
			result = getProductosService.execute( servPrestForm.getSelectedProveedor(), servPrestForm.getSelectedProducto() );
			servPrestForm.setFiltroProductoList( getProductosService.getProductosList() );
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
	private void obtenerCombos(ServPrestForm servPrestForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
			servPrestForm.setEstadoList(obtenerEstados());

			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servPrestForm.setFiltroProveedorList(filtroProveedorList);
				result = getParamListService.execute(ParameterType.SECTOR.getId(), "", "");
				filtroSectorList = getParamListService.getParameterList();
				
				if (result.isSuccesfull()) {
					servPrestForm.setFiltroSectorList(filtroSectorList);
					
					result = getParamListService.execute(ParameterType.TABLA_GRUPRO.getId(), "", "");

					grupoProductoList = getParamListService.getParameterList();
					if (result.isSuccesfull()) {
						servPrestForm.setFilterGroupList(grupoProductoList);

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
