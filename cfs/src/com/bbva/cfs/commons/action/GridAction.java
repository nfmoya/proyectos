package com.bbva.cfs.commons.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ResultDatabase;


public abstract class GridAction extends CommonAction {
	
	final String FILTER =  getFilterClassName();

	final String FILTER_AUX = getFilterClassName()+"Aux";
	
	public abstract Class getFilterClass() throws ClassNotFoundException;

	public abstract void setParameters(HttpServletRequest request, ActionForm form) throws Exception;

	/**
	 * M�todo que retorna una lista de beans que luego ser�n parseados a json para llenar la grilla, 
	 * el bean no necesariamente tiene que contener la cantidad de properties que los definidos en el option colModel del plugin jq grid.
	 * 
	 * @param session
	 * @return {@link List<Object>}
	 * @throws GeneralException
	 */
	public abstract List fillRows(HttpSession session) throws GeneralException;
	
	public abstract JSONObject convertToJSONeachAction(ActionForm form);
	
	private  String getFilterClassName() {
		String name = StringUtils.EMPTY;
		try {
			name = ClassUtils.getShortClassName(getFilterClass());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	/** {@link List}: Lista de beans */
	protected List rowsToShow;
	
	public boolean cleanSession(HttpServletRequest request, ActionForm form)
	throws GeneralException {
	
		boolean resultado = false;
		
		Object institutionFilter;
		Object auxInstitutionFilter;
		try {
			this.setParameters(request, form);
			/** Recupero los datos de la session * */
			institutionFilter = request.getSession().getAttribute(FILTER);
		
			try {
				auxInstitutionFilter = request.getSession().getAttribute(FILTER_AUX);
				if (auxInstitutionFilter == null
						|| !auxInstitutionFilter.equals(institutionFilter)) {
					request.getSession().setAttribute(FILTER_AUX,
							institutionFilter);
					resultado = true;
				}
			} catch (Exception e) {
				request.getSession().setAttribute(FILTER_AUX,
						institutionFilter);
				resultado = false;
			}
		
		} catch (Exception e) {
			log.error("Error cleanSession", e);
			resultado = false;
		}
		
		return resultado;
	}
	
	public ActionForward executeGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List allRows = null;
		/** Limpio la session **/
		if(cleanSession(request,form)){
			request.getSession().removeAttribute(request.getParameter("rowsName"));
		}
		/** Recupero valores de la session **/
		allRows = (List) request.getSession().getAttribute(request.getParameter("rowsName"));

		if(allRows == null){
			/** Subo los datos a la session **/
			allRows = fillRows(request.getSession());
			request.getSession().setAttribute(request.getParameter("rowsName"), allRows);
		}

		sortByProperty(allRows, request.getParameter("sidx"), request.getParameter("sord"));

		List rowsToShow = this.askForValues(request);
		JSONObject jsonObjet = convertToJSONeachAction(form);
		jsonObjet = convertToJSONGrid(rowsToShow,request,allRows,jsonObjet);
		
//		response.encodeURL("application/x-www-form-urlencoded;charset=UTF-8");
		response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
		PrintWriter out = null;
		try {
			out = response.getWriter();	
		} catch (IOException e) {
			log.error(Constants.ERROR_PRINT_WRITER, e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		}finally {
			if(out != null){
				out.print(jsonObjet);
				out.flush();
				out.close();
			}
		}
		
		return null;
	}
	
	/**
	 * M�todo que ordena una lista de Objetos
	 * 
	 * @param rows: lista de beans a ordenar
	 * @param property: atributo a trav�s del cual ordenar
	 * @param order: establece el orden (ascendente/descendente)
	 */
	private void sortByProperty(List rows, String property, String order) {
		try{
			if (property!=null && !"".equals(property)){
				BeanComparator comp = new BeanComparator(property);
				Collections.sort(rows, comp);
				if (order.equalsIgnoreCase("desc")) {
					Collections.reverse(rows);
				}
			}
		}catch (Exception e) {
			log.error("Error sortByProperty", e);
		}
	}

	/**
	 * 
	 * @param rowsToShow
	 * @param request
	 * @param allRows
	 * @param jsonObjectForm
	 * @return
	 * @throws ParseException
	 */
	private JSONObject convertToJSONGrid(List rowsToShow,
										 HttpServletRequest request,
										 List allRows,
										 JSONObject jsonObjectForm) throws ParseException{

		// Calcular el total de p�ginas para el query
		//String total = new String(""+(Double.valueOf(""+Math.ceil(allRows.size() / Double.valueOf(request.getParameter("rows")).doubleValue())).intValue()));
		Integer total = new Integer (String.valueOf(Double.valueOf(String.valueOf(Math.ceil(allRows.size() / Double.valueOf(request.getParameter("rows")).doubleValue()))).intValue()));
		jsonObjectForm.put("rowsToShow", rowsToShow);
		jsonObjectForm.put("total", total);
		jsonObjectForm.put("records",String.valueOf(allRows.size()));
		jsonObjectForm.put("rowsName",request.getParameter("rowsName"));
		jsonObjectForm.put("page", ""+Integer.parseInt(request.getParameter("page")));
		jsonObjectForm.put("rows", ""+Integer.parseInt(request.getParameter("rows")));

		jsonObjectForm.put(Constants.RESULT, super.result);
		jsonObjectForm.put("sidx", request.getParameter("sidx"));
		jsonObjectForm.put("sord", request.getParameter("sord"));
		return jsonObjectForm;
	}

	protected List paginate(HttpServletRequest request) throws GeneralException {

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		int top = 0;
		List allRows = new ArrayList();
		try{
			allRows = (List) request.getSession().getAttribute(request.getParameter("rowsName"));				
			top = page * rows;
			if (top > allRows.size()) {
				top = allRows.size();}
			
		}catch (Exception e) {
			log.error("Error paginate", e);
		}
		return allRows.subList((page - 1) * rows, top);
	}
	
	private List askForValues(HttpServletRequest request) throws GeneralException {
		List filteredRows = new ArrayList();
		
		try {
			filteredRows = paginate(request);
		
		} catch (Exception e) {
			log.error("Error askForValues", e);
		}
		return filteredRows;
	}
}
