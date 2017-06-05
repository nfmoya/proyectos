package com.bbva.cfs.conciliacion.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;
import com.bbva.cfs.conciliacion.service.DifConciliacionService;
import com.bbva.cfs.parameters.service.GetParameterListService;

/**
 * Clase que procesa los acctions de Edit de Diferenciacion de Concilacion
 * 
 * @author David
 */

public class DifConciliacionCRUDAction extends CommonAction{
	
	static final Log log = LogFactory.getLog(DifConciliacionCRUDAction.class);
	
	private DifConciliacionService service;
	private GetParameterListService getParamListService;
	private List<DiferenciaConciliacionModel> diferenciaConciliacionList;
	
	/**
  	 * @author David
  	 * 
  	 * Accion que se encarga de editar la diferencia de Conciliacion
  	 * 
  	 * @param mapping
  	 * @param form
  	 * @param request
  	 * @param response
  	 * @return ActionForward
  	 * @throws Exception
  	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());

		DiferenciaConciliacionForm dcf = (DiferenciaConciliacionForm) form;
		
		PrintWriter out = null;
		JSONObject jsonObject = null;
		JSONArray jsonArrayApplications = null;
		result = null;

		try {
			service = new DifConciliacionService(this.iWebClient);
			String user = this.getAutenticathedUserName(request);
						
 			result = service.edit(dcf, user);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			jsonArrayApplications = JSONArray.fromObject(diferenciaConciliacionList);

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
	
	/**
	 * @author david
	 * 
	 * Metodo que devuele la lista de productos filtrados por Sector y proveedor
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward searchProductosByFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());

		DiferenciaConciliacionForm dcf = (DiferenciaConciliacionForm) form;
		
		PrintWriter out = null;
		JSONObject jsonObject = null;
		result = null;
//		JSONArray jsonArrayApplications = null;

		try {
			getParamListService = new GetParameterListService(this.iWebClient);
			
			//Se pone 014 por que es el filtro de productos por prov y sector
			result = getParamListService.execute("014", dcf.getCdProveedor(), dcf.getCdSector());

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
//			jsonArrayApplications = JSONArray.fromObject(getParamListService.getParameterList());
			jsonObject = new JSONObject();
			jsonObject.put("paramList",getParamListService.getParameterList());
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
	
	/**
	 * @author david
	 * 
	 * 
	 * Metodo que devuelve la lista de periodos por proveedor
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward searchPeriodByFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		DiferenciaConciliacionForm dcf = (DiferenciaConciliacionForm) form;
		
		PrintWriter out = null;
		JSONObject jsonObject = null;	
		result = null;
		
		try {
			getParamListService = new GetParameterListService(this.iWebClient);
			result = getParamListService.execute(ParameterType.PROVEEDOR_PERIODO.getId(), dcf.getCdProveedor(), "");
						
			jsonObject = new JSONObject();
			jsonObject.put("paramList",getParamListService.getParameterList());
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