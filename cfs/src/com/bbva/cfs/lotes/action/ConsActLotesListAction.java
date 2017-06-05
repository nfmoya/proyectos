package com.bbva.cfs.lotes.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GeneralException;
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
import com.bbva.cfs.lotes.form.ConsActLotesForm;
import com.bbva.cfs.lotes.model.ConsActLotesModel;
import com.bbva.cfs.lotes.service.ConsActLotesService;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.producto.service.GetProductosService;

public class ConsActLotesListAction extends CommonAction {

	private Result result;
	private Session session;
	private Long userId;
	private ConsActLotesService service;
	private List<ConsActLotesModel> list;
	private ConsActLotesForm calf;
	private List allRows;
	private List rowsToShow;
	private JSONObject jsonObctForm;
	private JSONArray jsonArray;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		ConsActLotesForm dcf = (ConsActLotesForm) form;
		userId = this.getAutenticathedUserId(request);

		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, dcf);
		obtenerCombos(dcf);

		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ConsActLotesForm scf) throws GlobalGrantException {

		if (this.checkPrivilege(request, Grants.ANULAR_LOTE_PREST.getId())) {
			scf.setAnularPrestGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ANULAR_LOTE_FACT.getId())) {
			scf.setAnularFactGrant("S");
		}
	}

	/**
	 * Obtenci�n de combos para la pantalla
	 * 
	 * @param calf
	 * @throws Exception
	 */
	private void obtenerCombos(ConsActLotesForm calf) throws Exception {

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		GetProductosService getProductosService = new GetProductosService(
				this.iWebClient);

		try {
			// Lista de todos los proveedores
			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			if (result.isSuccesfull()) {
				calf.setProveedorList(getParamListService.getParameterList());
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

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		calf = (ConsActLotesForm) form;
		super.initialise(request.getSession());
		list = new ArrayList<ConsActLotesModel>();

		allRows = fillRows();
		sortByProperty(allRows, request.getParameter("sidx"),
				request.getParameter("sord"));
		List rowsToShow = paginate(request);

		jsonObctForm = convertToJSONGrid(request);
		jsonObctForm = loadJSONArray(rowsToShow, jsonObctForm);
		this.print(jsonObctForm, response);

		return null;
	}

	/**
	 * Ordena la lista por las propiedades
	 * 
	 * @param rows
	 * @param property
	 * @param order
	 */
	private void sortByProperty(List rows, String property, String order) {
		try {
			BeanComparator comp = new BeanComparator(property);
			Collections.sort(rows, comp);
			if (order.equalsIgnoreCase("desc")) {
				Collections.reverse(rows);
			}
		} catch (Exception e) {
			log.error("Error sortByProperty", e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return List
	 * @throws GeneralException
	 */
	protected List paginate(HttpServletRequest request) throws GeneralException {

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = allRows.size(); //Integer.parseInt(request.getParameter("rows"));
		int top = 0;
		List allRows = new ArrayList();
		try {
			allRows = list;

			top = page * rows;
			if (top > allRows.size()) {
				top = allRows.size();
			}

		} catch (Exception e) {
			log.error("Error paginate", e);
		}
		return allRows.subList((page - 1) * rows, top);
	}

	/**
	 * Llama al service y devuelve la lista para mostrar en pantalla
	 * 
	 * @return List
	 * @throws GeneralException
	 */
	public List fillRows() throws GeneralException {

		try {
			service = new ConsActLotesService(this.iWebClient);
			result = service.getConsActLotesList(list, calf);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return JSONObject
	 * @throws ParseException
	 */
	private JSONObject convertToJSONGrid(HttpServletRequest request)
			throws ParseException {

		JSONObject jsonObjectForm = new JSONObject();
		// Calcular el total de p�ginas para el query

//		Integer total = new Integer(String.valueOf(Double.valueOf(
//				String.valueOf(Math.ceil(allRows.size()
//						/ Double.valueOf(request.getParameter("rows"))
//								.doubleValue()))).intValue()));
		jsonObjectForm.put("gridModelToShow", rowsToShow);
		jsonObjectForm.put("total", 1 ); //total);
		jsonObjectForm.put("records", String.valueOf(allRows.size()));
		jsonObjectForm.put("rowsName", request.getParameter("rowsName"));
		jsonObjectForm.put("page","" + 1); //Integer.parseInt(request.getParameter("page")));
		jsonObjectForm.put("rows","" + allRows.size() ); //Integer.parseInt(request.getParameter("rows")));

		jsonObjectForm.put(Constants.RESULT, this.result);
		jsonObjectForm.put("sidx", request.getParameter("sidx"));
		jsonObjectForm.put("sord", request.getParameter("sord"));

		return jsonObjectForm;
	}

	/**
	 * 
	 * 
	 * @param rowsToShow
	 * @param jsonObject
	 * @return JSONObject
	 */
	private JSONObject loadJSONArray(List rowsToShow, JSONObject jsonObject) {

		jsonArray = JSONArray.fromObject(rowsToShow);
		jsonObject.put(Constants.GRID_MODEL_TO_SHOW, jsonArray);
		return jsonObject;
	}

	/**
	 * 
	 * @param jsonObject
	 * @param response
	 */
	private void print(JSONObject jsonObject, HttpServletResponse response) {
		response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (out != null) {
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}