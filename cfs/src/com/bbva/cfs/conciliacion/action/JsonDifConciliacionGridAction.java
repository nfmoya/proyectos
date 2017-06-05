package com.bbva.cfs.conciliacion.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GeneralException;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;
import com.bbva.cfs.conciliacion.service.DifConciliacionService;

public class JsonDifConciliacionGridAction extends CommonAction {

	private DifConciliacionService service;
	private List<DiferenciaConciliacionModel> list;
	private DiferenciaConciliacionForm form;
	private List allRows;
	private List rowsToShow;
	private JSONObject jsonObctForm;
	private JSONArray jsonArray;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		form = (DiferenciaConciliacionForm) form;
		super.initialise(request.getSession());
		list = new ArrayList<DiferenciaConciliacionModel>();

		// comienzo modif

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
		int rows = Integer.parseInt(request.getParameter("rows"));
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
			service = new DifConciliacionService(this.iWebClient);
			result = service.getDifConciliacionList(list, form);

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
		// Calcular el total de páginas para el query

		Integer total = new Integer(String.valueOf(Double.valueOf(
				String.valueOf(Math.ceil(allRows.size()
						/ Double.valueOf(request.getParameter("rows"))
								.doubleValue()))).intValue()));
		jsonObjectForm.put("gridModelToShow", rowsToShow);
		jsonObjectForm.put("total", total);
		jsonObjectForm.put("records", String.valueOf(allRows.size()));
		jsonObjectForm.put("rowsName", request.getParameter("rowsName"));
		jsonObjectForm.put("page",
				"" + Integer.parseInt(request.getParameter("page")));
		jsonObjectForm.put("rows",
				"" + Integer.parseInt(request.getParameter("rows")));

		jsonObjectForm.put(Constants.RESULT, super.result);
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
