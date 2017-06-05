package com.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.security.AuthenticatedUser;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gefa.utils.ConfigurationFiles;
import com.gefa.utils.TokenSOAPClient;


/**
 * Clase base para los Action's del perfil de analista. El execute() define un
 * Template Method para la logica comun como ser validacion de login, permisos.
 */
public abstract class RestriccionAction extends Action {

//	public static final Logger log = Logger.getLogger(RestriccionAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("ISO-8859-1");
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");

		String wsurl = ConfigurationFiles.get().getProperties("wsurl");
		String serverURI = ConfigurationFiles.get().getProperties("serverURI");

		System.out.println("***************************" + wsurl);
		System.out.println("***************************" + serverURI);
		
		
		
		//Consulta al webService si tiene token activo.		
		boolean result = TokenSOAPClient.authenticate(wsurl,serverURI,token); 

		if(!result){
			
			return mapping.findForward("logout");
		}
		
		
		
		
		
		return executeAction(mapping, form, request, response);
	}

	/**
	 * Los Action clientes deben utilizar este metodo en lugar del execute()
	 * regular.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception;

	

}