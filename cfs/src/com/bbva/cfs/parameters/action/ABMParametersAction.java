package com.bbva.cfs.parameters.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Grants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.service.GetParameterTypeListService;
import com.bbva.cfs.parameters.service.AddParameterService;
import com.bbva.cfs.parameters.service.ModParameterService;
import com.bbva.cfs.parameters.service.DelParameterService;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.form.ParameterForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbva.cfs.commons.action.CommonAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;


/**
 * @author bsilvestre
 * 
 */
public class ABMParametersAction extends CommonAction {
	
	private static final long serialVersionUID = 6967702419884332562L;

	static final Log log = LogFactory.getLog(ABMParametersAction.class);
	
	private GetParameterTypeListService getParamTypeService;
	private AddParameterService addParameterService;
	private ModParameterService modParameterService;
	private DelParameterService delParameterService;
	
	private Result result;
	
	/**
	 * TODO:
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTypes(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response)
		throws Exception {
		log.info("inicio listTypes");
		this.initialise(request.getSession());
		ParameterForm paramForm = (ParameterForm)form;
		
		/*Verifico los permisos*/
/*		
		log.info("Verificacion de permisos");
		if (!this.checkPrivilege(request, Grants.ABM_PARAMETERS_VIEW.getId())){
			log.info("1er if");
			throw new GlobalGrantException();
		} 
		if (this.checkPrivilege(request, Grants.ABM_PARAMETERS_CREATE.getId())){
			log.info("2do if");
			paramForm.setCreateGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PARAMETERS_EDIT.getId())){
			log.info("3er if");
			paramForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PARAMETERS_DELETE.getId())){
			log.info("4er if");
			paramForm.setDeleteGrant("S");
		} 
*/		
		try {
			log.info("Entrada al try para instanciar el Service.");
			getParamTypeService = new GetParameterTypeListService(this.iWebClient);
			result = getParamTypeService.execute();
			log.info("Resultado: "+ result.isSuccesfull());
			if (result.isSuccesfull()){
				paramForm.setParameterTypes(getParamTypeService.getParameterTypes());
			} else {
				log.error(result.getErrorDesc());
				throw new GlobalActionException(result.getErrorCode(),
												result.getErrorDesc());
			}
		
		} catch (GlobalServiceException e) {
			log.error(e);
			log.error(e.getMessage());
			
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
							ResultDatabase.ACTION_ERROR.getMessage());	
		} 
		
		return doFindSuccess(mapping);
	}

	/**
	 * TODO:
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response)
		throws Exception {
		
		this.initialise(request.getSession());
		PrintWriter out = null;
		ParameterForm paramForm = (ParameterForm)form;
		
		/*Verifico los permisos*/
/*		
		if (!this.checkPrivilege(request, Grants.ABM_PARAMETERS_CREATE.getId())){
			throw new GlobalGrantException();
		} 
*/		
		Map resp = new HashMap();
		try {
			log.error("Long.valueOf(paramForm.getTxtCantCuotas()==");
			addParameterService = new AddParameterService(this.iWebClient);
			result = addParameterService.execute(this.getAutenticathedUserId(request)
														, paramForm.getTipoParametro()
														, paramForm.getTxtDescParametro()
														, Long.valueOf(paramForm.getTxtCantCuotas().equals("")?"-1":paramForm.getTxtCantCuotas())
														);
			
			resp.put("result",result);
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());	
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} 
		
		return null;
	}
	
	public ActionForward update(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response)
		throws Exception {
		this.initialise(request.getSession());
		PrintWriter out = null;
		ParameterForm paramForm = (ParameterForm)form;
		
		/*Verifico los permisos*/
/*		
		if (!this.checkPrivilege(request, Grants.ABM_PARAMETERS_EDIT.getId())){
			throw new GlobalGrantException();
		} 
*/		
		Map resp = new HashMap();
		try {
			modParameterService = new ModParameterService( this.iWebClient );
			result = modParameterService.execute(this.getAutenticathedUserId(request)
														, paramForm.getCodParametro()
														, paramForm.getTipoParametro()
														, paramForm.getTxtDescParametro());
			
			resp.put("result",result);
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());	
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} 
		
		return null;
	}
	
	public ActionForward delete(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response)
		throws Exception {
		this.initialise(request.getSession());
		PrintWriter out = null;
		ParameterForm paramForm = (ParameterForm)form;
		
		/*Verifico los permisos*/
/*		
		if (!this.checkPrivilege(request, Grants.ABM_PARAMETERS_DELETE.getId())){
			throw new GlobalGrantException();
		} 
*/		
		Map resp = new HashMap();
		
		try {
			delParameterService = new DelParameterService(this.iWebClient);
			result = delParameterService.execute(this.getAutenticathedUserId(request)
														, paramForm.getTipoParametro()
														, paramForm.getCodParametro());
			
			resp.put("result",result);
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
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());	
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} 
		
		return null;
	}
	
}
