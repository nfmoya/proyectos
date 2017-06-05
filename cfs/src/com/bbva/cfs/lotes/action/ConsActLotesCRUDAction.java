package com.bbva.cfs.lotes.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.lotes.form.ConsActLotesForm;
import com.bbva.cfs.lotes.service.ConsActLotesService;

/**
 * Clase que procesa los actions de Consulta y Actualización de Lotes
 * 
 * @author Alexis Comerci
 */

public class ConsActLotesCRUDAction extends CommonAction{
	
	static final Log log = LogFactory.getLog(ConsActLotesCRUDAction.class);
	
	private ConsActLotesService service;
	
	/**
  	 * @author Alexis Comerci
  	 * 
  	 * Accion que se encarga de anular el lote
  	 * 
  	 * @param mapping
  	 * @param form
  	 * @param request
  	 * @param response
  	 * @return ActionForward
  	 * @throws Exception
  	 */
	public ActionForward anular(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());

		ConsActLotesForm calf = (ConsActLotesForm) form;
		
		PrintWriter out = null;
		JSONObject jsonObject = null;
		JSONArray jsonArrayApplications = null;

		try {
			service = new ConsActLotesService(this.iWebClient);
			
			String tipoLote = calf.getTipoLote();
			String nroLote = calf.getCdLote();
			String user = this.getAutenticathedUserName(request);
			
 			result = service.anular(tipoLote, nroLote, user);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);

			jsonObject = new JSONObject();
			jsonObject.put(Constants.GRID_MODEL_TO_SHOW, jsonArrayApplications);
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

		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);

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