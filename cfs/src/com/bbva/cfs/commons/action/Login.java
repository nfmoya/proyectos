package com.bbva.cfs.commons.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.form.LoginForm;
import com.bbva.cfs.commons.service.GetMenuService;
import com.bbva.cfs.commons.service.InitSessionService;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ResultDatabase;

public class Login extends CommonAction {

	private GetMenuService getMenuService;
	//private InitSessionService sessionService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		this.initialise(request.getSession());
		try {
			getMenuService = new GetMenuService(this.iWebClient);
			result = getMenuService.execute(this.getAutenticathedUserId(request));

			request.setAttribute("menuCompleto", getMenuService.getMenuCompletoHtml());
			
			loginForm.setUsername(this.getAutenticathedUserName(request));
			loginForm.setUserFullname(this.getAutenticathedUserFullname(request));
			
			/*
			 * Agrego el perfil del usuario a las variables hidden de la aplicacion para que se pueda leer desde
			 * cualquier otra pantalla 
			 */
			loginForm.setUserIdPerfil(this.getIdPerfil(request));
		
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR.getCode(),
							ResultDatabase.ACTION_ERROR.getMessage());	
		} 
		return doFindSuccess(mapping);
	}
}
