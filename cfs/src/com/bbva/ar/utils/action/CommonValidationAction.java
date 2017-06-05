package com.bbva.ar.utils.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.service.CommonValidationService;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ResultDatabase;

public class CommonValidationAction extends CommonAction {

	private CommonValidationService service;

	/**
	 * @author david
	 * 
	 *         Metodo que valida que el periodo de Facturacion Desde sea Menor
	 *         al Hasta
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward validatePeriodFact(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		log.debug("Ingresa a validatePeriodFact de la clase ValidatePeriodAction");

		PrintWriter out = null;
		JSONObject jsonObject = null;
		// JSONArray jsonArrayApplications = null;
		result = null;

		try {

			String periodD = request.getParameter("periodD").toString();
			String periodH = request.getParameter("periodH").toString();
			String cdProveedor = request.getParameter("codProveedor").toString();
			Boolean validBusqConcil = request.getParameter("invoker") != null ? true : false;
			service = new CommonValidationService(this.iWebClient);

			result = service.validatePeriodFact(periodD, periodH, cdProveedor, validBusqConcil);

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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());

		} catch (IOException e) {
			log.error(Constants.ERROR_PRINT_WRITER, e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());

		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);

		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
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
