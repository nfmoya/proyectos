package com.bbva.cfs.commons.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import ar.com.itrsa.GeneralException;

import com.bbva.cfs.commons.utils.Constants;


public abstract class GridActionWithoutPaginate extends GridAction {
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private JSONObject convertToJSONGrid(List rowsToShow,
										 HttpServletRequest request,
										 List allRows,
										 JSONObject jsonObjectForm) throws ParseException{

		jsonObjectForm.put("rowsToShow", rowsToShow);
		jsonObjectForm.put("total"     , 1);
		jsonObjectForm.put("records"   , String.valueOf(allRows.size()));
		jsonObjectForm.put("rowsName"  , request.getParameter("rowsName"));
		jsonObjectForm.put("page"      , 1);
		jsonObjectForm.put("rows"      , String.valueOf(allRows.size()));

		jsonObjectForm.put(Constants.RESULT, super.result);
		jsonObjectForm.put("sidx", request.getParameter("sidx"));
		jsonObjectForm.put("sord", request.getParameter("sord"));
		return jsonObjectForm;
	}

	@SuppressWarnings("rawtypes")
	protected List paginate(HttpServletRequest request) throws GeneralException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = 2000;
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
}
