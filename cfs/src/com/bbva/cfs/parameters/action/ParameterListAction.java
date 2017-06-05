package com.bbva.cfs.parameters.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.form.ParameterForm;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.service.GetParameterListService;

/**
 * @author bsilvestre
 * 
 */
public class ParameterListAction extends CommonAction {
	
	static final Log log = LogFactory.getLog(ParameterListAction.class);
	
	private GetParameterListService getParamListService;
	
	private Result result;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		log.info("Inicializa ParameterListAction");
		PrintWriter out = null;
		ParameterForm paramForm = (ParameterForm)form;
		
		try {
			getParamListService = new GetParameterListService(this.iWebClient);
			// Manda 0 a traer todo para que solo traiga los parametricos
			result = getParamListService.execute(paramForm.getTipoParametro(), "", "");
			JSONObject jsonObject = new JSONObject();
			//List l = getParamListService.getParameterList();
			jsonObject.put("paramList",getParamListService.getParameterList());
			jsonObject.put("result",result);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
	
			out.print(jsonObject);
	
			if (out != null) {
				out.flush();
				out.close();
			}

		} catch (JSONException e) {
			log.error("Error execute Json Array: ", e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());	
		} catch (GlobalServiceException e) {
			log.error(e);
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} 
		log.info("RETORNO");
		return null;
	}
	
}
