package com.bbva.security.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.IWebConstants;

import com.bbva.common.config.SamConfig;
import com.bbva.security.form.AuthenticateForm;
import com.bbva.security.service.EditPasswordService;
import com.bbva.security.service.GetAuthenticatedUserService;

public class AuthenticateAction extends MappingDispatchAction {

	private Log log = LogFactory.getLog("com.bbva.cfs");
	
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String CHANGE_PASSWORD = "changePass";
	
	private static final String ERROR_MSG = "errors";
	private static final String AUTHENTICATED_USER_NAME = "authenticatedUsername";
	private final static String SESSION = "session";
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	private EditPasswordService editPasswordService;
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AuthenticateForm authenticateForm = (AuthenticateForm) form;
		authenticateForm.setUsername("");
		authenticateForm.setPassword("");
		request.getSession().setAttribute(ERROR_MSG, "");
		return mapping.findForward(SUCCESS);
	}
	
	public ActionForward authenticate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AuthenticateForm authenticateForm = (AuthenticateForm) form;
		String ivUser = (String) request.getSession().getAttribute("ivUser");
		IWebClient iWebClient = (IWebClient) request.getSession().getAttribute(IWebConstants.WEBCLIENT);
		iWebClient.setLoginOk(true);
		SamConfig.config(request.getSession());
		
		if(ivUser != null)
			authenticateForm.setUsername(ivUser);
		
		/*SUBIR A SESSION EL USUARIO LOGGEADO Y HABILITAR LOGOUT*/
		if (authenticateForm.getUsername().equals("") ){ 
				//|| authenticateForm.getPassword().equals("")){
		
			request.getSession().setAttribute(ERROR_MSG, "Faltan datos");
			return mapping.findForward(ERROR);
		}
		
		try {
			getAuthenticatedUserService = new GetAuthenticatedUserService(iWebClient);
			getAuthenticatedUserService.init(authenticateForm.getUsername());
			
//			if (getAuthenticatedUserService.existUser()){
//				if(getAuthenticatedUserService.isFirstTime() || 
//					getAuthenticatedUserService.isExpiredPassword()){
//					request.getSession().setAttribute(ERROR_MSG, "CLAVE INICIAL O VENCIDA");
//					//request.getSession().removeAttribute(ERROR_MSG);
//					return mapping.findForward(CHANGE_PASSWORD);
//				} else {
					if(getAuthenticatedUserService.isEnabledUser()){
//						if(getAuthenticatedUserService.isValidPassword(authenticateForm.getPassword())){
							request.getSession().removeAttribute(ERROR_MSG);
							request.getSession().setAttribute(AUTHENTICATED_USER_NAME, authenticateForm.getUsername());
							return mapping.findForward(SUCCESS);
//						} else {
//							request.getSession().setAttribute(ERROR_MSG, "CLAVE o USUARIO ERRONEO");
//							return mapping.findForward(ERROR);
//						}
					} else {
						request.getSession().setAttribute(ERROR_MSG, "USUARIO BLOQUEADO o INHABILITADO");
						return mapping.findForward(ERROR);
					}
//				}
//			} 
//			else {
//				request.getSession().setAttribute(ERROR_MSG, "CLAVE o USUARIO ERRONEO");
//				return mapping.findForward(ERROR);
//			}
		} catch (Exception e) {
			log.error(e);
			request.getSession().setAttribute(ERROR_MSG, "El servicio no se encuentra disponible.");
			return mapping.findForward(ERROR);
		} 
	}
	
//	public ActionForward changePass(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		AuthenticateForm authenticateForm = (AuthenticateForm) form;
//		
//		IWebClient iWebClient = (IWebClient) request.getSession().getAttribute(IWebConstants.WEBCLIENT);
//		iWebClient.setLoginOk(true);
//			
//		if (authenticateForm.getCurrentPassword().equals("") ||
//				authenticateForm.getNewPassword().equals("") ||
//				authenticateForm.getConfirmPassword().equals("")) {
//			request.getSession().setAttribute(ERROR_MSG, "Faltan datos");
//		} else {
//			if(!authenticateForm.getNewPassword().equals(authenticateForm.getConfirmPassword())){
//				request.getSession().setAttribute(ERROR_MSG, "Las claves nuevas no coinciden");
//			} else {
//				try {
//					getAuthenticatedUserService = new GetAuthenticatedUserService(iWebClient);
//					getAuthenticatedUserService.init(authenticateForm.getUsername());
//					
//					if (getAuthenticatedUserService.isValidPassword(authenticateForm.getCurrentPassword())){
//						editPasswordService = new EditPasswordService(iWebClient);
//						boolean result = editPasswordService.update(getAuthenticatedUserService.getUser(), authenticateForm.getNewPassword());
//						if(result){
//							request.getSession().removeAttribute(ERROR_MSG);
//							request.getSession().setAttribute(AUTHENTICATED_USER_NAME, authenticateForm.getUsername());
//							return mapping.findForward(SUCCESS);
//						} else {
//							request.getSession().setAttribute(ERROR_MSG, editPasswordService.getValidateMesssage());
//						}
//					} else {
//						request.getSession().setAttribute(ERROR_MSG, "CLAVE ACTUAL ERRONEA"); 
//					}
//				} catch (Exception e) {
//					log.error(e);
//					request.getSession().setAttribute(ERROR_MSG, "El servicio no se encuentra disponible.");
//				} 
//			}
//		}
//		
//		return mapping.findForward(CHANGE_PASSWORD);
//	}
//	
//	public ActionForward changePassInit(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		AuthenticateForm authenticateForm = (AuthenticateForm) form;
//		authenticateForm.setCurrentPassword(authenticateForm.getPassword());
//		authenticateForm.setPassword("");
//		return mapping.findForward(SUCCESS);
//	}
//	
//	public ActionForward changePassLoggedUser(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		AuthenticateForm authenticateForm = (AuthenticateForm) form;
//		authenticateForm.setUsername(request.getSession().getAttribute(AUTHENTICATED_USER_NAME).toString());
//		authenticateForm.setCurrentPassword("");
//		authenticateForm.setPassword("");
//		return mapping.findForward(SUCCESS);
//	}
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String parametro = request.getParameter("origen");
		System.out.println(parametro);	
		request.getSession().removeAttribute(SESSION);
		request.getSession().removeAttribute(AUTHENTICATED_USER_NAME);
		request.getSession().removeAttribute(ERROR_MSG);
		request.setAttribute("origen", parametro);
		return mapping.findForward(SUCCESS);
	}
	
}
