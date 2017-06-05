package com.bbva.cfs.commons.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.exceptions.NotLoggedException;
import com.bbva.cfs.commons.service.InitSessionService;
import com.bbva.cfs.commons.service.UserListService;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * Controlador que inicializa la configuración necesaria para conectarse vía SAM
 * y carga la lista de usuarios temporales de prúeba.
 * 
 * @author xah1257
 * 
 */
public class IndexAction extends CommonAction {

	private UserListService userListService;
	private InitSessionService sessionService;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			super.initialise(request.getSession());
		} catch(NotLoggedException e){
			if (request.getSession().getAttribute("authenticatedUsername")==null ||
				SamConfig.getSam() == null){
				throw new NotLoggedException();
			}
		}

//		List users = new ArrayList();
		try {
			
			sessionService = new InitSessionService(this.iWebClient);
			result = sessionService.execute(request.getSession().getAttribute("authenticatedUsername").toString());
			request.getSession().setAttribute(Constants.SESSION, sessionService.getSession());
			
//			userListService = new UserListService(this.iWebClient);
//			result = userListService.execute(users);
//
//			if (result.isSuccesfull())
//				request.setAttribute("userList", users);
//			else
//				throw new GlobalActionException(result.getErrorCode(), result
//						.getErrorDesc());

		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		}

		return doFindSuccess(mapping);
	}

}
