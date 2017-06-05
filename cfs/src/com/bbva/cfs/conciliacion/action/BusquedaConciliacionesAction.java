package com.bbva.cfs.conciliacion.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
import com.bbva.cfs.conciliacion.form.BusquedaConciliacionesForm;
import com.bbva.cfs.conciliacion.form.ConciliacionForm;
import com.bbva.cfs.conciliacion.service.DifConciliacionService;
import com.bbva.cfs.conciliacion.service.GetConciliacionesService;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.producto.service.GetProductosService;

//import net.sf.json.JSONArray;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class BusquedaConciliacionesAction extends CommonAction {
	// private GetParameterListService getParamListService;

	private GetConciliacionesService getConciliacionesService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroPeriodoList = new ArrayList();
	private String sector;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		BusquedaConciliacionesForm conciliacionForm = (BusquedaConciliacionesForm) form;
		userId = this.getAutenticathedUserId(request);
		conciliacionForm.setSector(this.getSector(request));
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, conciliacionForm);
		obtenerCombos(conciliacionForm);
		
		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			BusquedaConciliacionesForm conciliacionForm)
			throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ANULAR_CONCILIACION.getId())) {
			conciliacionForm.setAnularConcil("S");
		}
	}

	/**
	 * Obtengo los combos para la pantalla
	 * 
	 * @param scf
	 * @throws Exception
	 */
	private void obtenerCombos(BusquedaConciliacionesForm bcf) throws Exception {

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		GetProductosService getProductosService = new GetProductosService(
				this.iWebClient);

		try {

			// Lista de todos los proveedores
			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			if (result.isSuccesfull()) {
				bcf.setProveedorList(getParamListService.getParameterList());

				// Lista de todos los sectores
				result = getParamListService.execute(
						ParameterType.SECTOR.getId(),"", "");
				if (result.isSuccesfull()) {
					bcf.setSectorList(getParamListService.getParameterList());

					// Lista de todos los productos
					// result = getProductosService.execute();
					if (result.isSuccesfull()) {
						// dcf.setProductosList(getProductosService.getProductosList());

						// result =
						// getParamListService.execute(ParameterType.PROVEEDOR_PERIODO.getId(),
						// "", "");
						// if (result.isSuccesfull()) {
						// bcf.setpFactDesdeList(getParamListService.getParameterList());
						// bcf.setpFactHastaList(getParamListService.getParameterList());
						// } else {
						// throw new
						// GlobalActionException(result.getErrorCode(),
						// result.getErrorDesc());
						// }
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
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	public ActionForward anularConciliacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.initialise(request.getSession());
		BusquedaConciliacionesForm conciliacionForm = (BusquedaConciliacionesForm) form;
		userId = this.getAutenticathedUserId(request);

		PrintWriter out = null;
		JSONObject jsonObject = null;
		JSONArray jsonArrayApplications = null;

		try {
			GetConciliacionesService service = new GetConciliacionesService(
					iWebClient);

			String user = this.getAutenticathedUserName(request);

			result = service.anularConciliacion(user,
					conciliacionForm.getCdConciliacion(),
					conciliacionForm.getObservaciones(), Boolean.valueOf(conciliacionForm.getCheckProdNoMedible()));

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			// jsonArrayApplications =
			// JSONArray.fromObject(diferenciaConciliacionList);

			jsonObject = new JSONObject();
			// jsonObject.put(Constants.GRID_MODEL_TO_SHOW,
			// jsonArrayApplications);
			jsonObject.put(Constants.RESULT, result);
			out = response.getWriter();

		} catch (JSONException e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());

		} catch (IOException e) {
			log.error(Constants.ERROR_PRINT_WRITER, e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());

		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} finally {
			if (out != null) {
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		}
		return null;
	}
}
