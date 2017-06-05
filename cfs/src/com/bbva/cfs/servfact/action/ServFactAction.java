package com.bbva.cfs.servfact.action;

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
//import com.bbva.cfs.sector.model.Sector;
import com.bbva.cfs.sector.service.GetSectoresService;
import com.bbva.cfs.servfact.form.ServFactForm;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ServFactAction extends CommonAction {
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
	private List pFactDesdeList = new ArrayList();
	private String sector;
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		ServFactForm servFactForm = (ServFactForm) form;
		userId = this.getAutenticathedUserId(request);
		sector = this.getSector(request);
		servFactForm.setSector(sector);		
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, servFactForm);
		obtenerCombos(servFactForm);
		if( servFactForm.getSelectedProveedor() != null
		&& !servFactForm.getSelectedProveedor().equals("")
		&& !servFactForm.getSelectedProveedor().equals("0"))
			obtenerCombosAlt(servFactForm);

		return doFindSuccess(mapping);
	}

	private void obtenerCombosAlt(ServFactForm servFactForm) {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		String sector = servFactForm.getSelectedSector().equalsIgnoreCase("0") ? "" : servFactForm.getSelectedSector();
		String prov = servFactForm.getSelectedProveedor().equalsIgnoreCase("0") ? "" : servFactForm.getSelectedProveedor();
		
		try {
			result = getParamListService.execute(ParameterType.CONC_PROVEEDOR_PERIODO.getId(),prov, sector);	
			servFactForm.setpFactDesdeList( getParamListService.getParameterList());			
			servFactForm.setpFactHastaList( getParamListService.getParameterList());
			result = getParamListService.execute(ParameterType.CONC_PROVEEDOR_PRODUCTO.getId(), prov, sector);			
			servFactForm.setFiltroProductoList( getParamListService.getParameterList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ServFactForm servFactForm) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ABM_SECTORES_CREATE.getId())) {
			log.info("Insertar Sector");
			servFactForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_EDIT.getId())) {
			log.info("Edit Sector");
			servFactForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_SECTORES_DELETE.getId())) {
			log.info("Delete Sector");
			servFactForm.setDeleteGrant("S");
		}
	}

	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getSectores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ServFactForm servFactForm = (ServFactForm) form;
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
	private void obtenerComboSector(ServFactForm servFactForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
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

			servFactForm.setHabilitadoList(lista);

			result = getParamListService.execute(ParameterType.SECTOR.getId(),
					"", "");
			filtroSectorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servFactForm.setFiltroSectorList(filtroSectorList);
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
	private void obtenerComboProveedor(ServFactForm servFactForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
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

			servFactForm.setHabilitadoList(lista);

			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servFactForm.setFiltroProveedorList(filtroProveedorList);
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
	private void obtenerComboProductos(ServFactForm servFactForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
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

			servFactForm.setHabilitadoList(lista);

			result = getParamListService.execute(ParameterType.SECTOR.getId(),
					"", "");
			filtroSectorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servFactForm.setFiltroSectorList(filtroSectorList);
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
	private void obtenerCombos(ServFactForm servFactForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
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

			servFactForm.setHabilitadoList(lista);

			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				servFactForm.setFiltroProveedorList(filtroProveedorList);
				result = getParamListService.execute(
						ParameterType.SECTOR.getId(), "", "");
				filtroSectorList = getParamListService.getParameterList();
				if (result.isSuccesfull()) {
					servFactForm.setFiltroSectorList(filtroSectorList);
					//
					// GetProductosService getProductosService = new
					// GetProductosService(this.iWebClient);
					//
					// Parameter prov = (Parameter) filtroProveedorList.get(0);
					// result = getProductosService.execute(prov.getCod());
					// filtroProductoList =
					// getProductosService.getProductosList();
					//
					// if (result.isSuccesfull()) {
					// servFactForm.setFiltroProductoList(filtroProductoList);
					//
					result = getParamListService.execute(
							ParameterType.TABLA_GRUPRO.getId(), "", "");

					grupoProductoList = getParamListService.getParameterList();
					if (result.isSuccesfull()) {
						// productoForm.setGrupoProductoList(grupoProductoList);
						servFactForm.setFilterGroupList(grupoProductoList);

						 result =
						 getParamListService.execute(ParameterType.TABLA_TIPCOM.getId(),
						 "", "");
//						 pFactDesdeList =
//						 getParamListService.getParameterList();
						 if (result.isSuccesfull()) {
						 servFactForm.setFilterCompList(getParamListService.getParameterList());
//						 servFactForm.setpFactHastaList(pFactDesdeList);
						//
					} else {
						throw new GlobalActionException(result.getErrorCode(),
								result.getErrorDesc());
					}
				} else {
					throw new GlobalActionException(result.getErrorCode(),
							result.getErrorDesc());
				}
				 } else {
				 throw new GlobalActionException(result.getErrorCode(),
				 result.getErrorDesc());
				 }
				// } else {
				// throw new GlobalActionException(result.getErrorCode(),
				// result.getErrorDesc());
				// }
			} else {
				throw new GlobalActionException(result.getErrorCode(),
						result.getErrorDesc());
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
