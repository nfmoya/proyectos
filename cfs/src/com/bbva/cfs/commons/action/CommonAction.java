package com.bbva.cfs.commons.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.IWebConstants;

import com.bbva.cfs.commons.exceptions.NotLoggedException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;

/**
 * 
 * @author xah1257
 * 
 *         Controlador que va a contener funcionalidad com�n a todos los
 *         controladores que se vayan a usar, as� como la definici�n de
 *         variables est�ticas, etc..
 */
public class CommonAction extends MappingDispatchAction {

	/**
	 * <p>
	 * The <code>Log</code> instance for this application.
	 * </p>
	 */
	protected Log log = LogFactory.getLog(Constants.PACKAGE);

	/**
	 * <p>
	 * The <code>IWebClient</code> object's for establish to sam connection.
	 * </p>
	 */
	protected IWebClient iWebClient;
	
	/**
	 * <p>
	 * Objeto <code>Result</code> para manipular resultados tanto exitosos como �rroneos de DB.
	 * </p>
	 */
	protected Result result;

	// protected Session session;
	// protected initSessionService sessionService;

	/**
	 * <p>
	 * Helper method to log event and cancel transaction.
	 * </p>
	 * 
	 * @param session
	 *            Our HttpSession
	 * @param method
	 *            Method being processed
	 * @param key
	 *            Attrkibute to remove from session, if any
	 */
	protected void doCancel(HttpSession session, String method, String key) {
		if (log.isTraceEnabled()) {
			StringBuffer sb = new StringBuffer(128);
			sb.append(Constants.LOG_CANCEL);
			sb.append(method);
			log.trace(sb.toString());
		}
		if (key != null) {
			session.removeAttribute(key);
		}
	}

	/**
	 * <p>
	 * Return the local or global forward named "failure" or null if there is no
	 * such forward.
	 * </p>
	 * 
	 * @param mapping
	 *            Our ActionMapping
	 * @return Return the mapping named "failure" or null if there is no such
	 *         mapping.
	 */
	protected ActionForward doFindFailure(ActionMapping mapping) {
		if (log.isTraceEnabled()) {
			log.trace(Constants.LOG_FAILURE);
		}
		return mapping.findForward(Constants.FAILURE);
	}

	/**
	 * <p>
	 * Return the local or global forward named "logon" or null if there is no
	 * such forward.
	 * </p>
	 * 
	 * @param mapping
	 *            Our ActionMapping
	 * @return Return the mapping named "logon" or null if there is no such
	 *         mapping.
	 */
	protected ActionForward doFindLogon(ActionMapping mapping) {
		if (log.isTraceEnabled()) {
			log.trace(Constants.LOG_LOGON);
		}
		return mapping.findForward(Constants.LOGON);
	}

	/**
	 * <p>
	 * Return the mapping labeled "success" or null if there is no such mapping.
	 * </p>
	 * 
	 * @param mapping
	 *            Our ActionMapping
	 * @return Return the mapping named "success" or null if there is no such
	 *         mapping.
	 */
	protected ActionForward doFindSuccess(ActionMapping mapping) {
		if (log.isTraceEnabled()) {
			log.trace(Constants.LOG_SUCCESS);
		}
		return mapping.findForward(Constants.SUCCESS);
	}

	/**
	 * Inicializaci�n de par�metros comunes a todas las acciones
	 * 
	 * TODO xah1257: buscar de que esto se haga a trav�s de alguna
	 * implementaci�n de struts 1
	 * 
	 * @param session
	 */
	protected void initialise(HttpSession session) throws NotLoggedException  {
		iWebClient = (IWebClient) session.getAttribute(IWebConstants.WEBCLIENT);
		iWebClient.setLoginOk(true);
		this.result = new Result();
		checkLogin(session);
	}

	/**
	 * Obtiene de sesi�n el usuario autenticado.
	 * 
	 * @param request
	 * @return Id de usuario autenticado en la aplicaci�n
	 */
	protected Long getAutenticathedUserId(HttpServletRequest request) {
		return ((Session) request.getSession().getAttribute(Constants.SESSION))
				.getUserId();
	}
	
	protected String getAutenticathedUserName(HttpServletRequest request) {
		return ((Session) request.getSession().getAttribute(Constants.SESSION))
				.getUserName();
	}
	
	protected String getAutenticathedUserFullname(HttpServletRequest request) {
		return ((Session) request.getSession().getAttribute(Constants.SESSION))
				.getFullname();
	}
	
	/**
	 * Obtiene de sesi�n el separador.
	 * 
	 * @param request
	 * @return Id de usuario autenticado en la aplicaci�n
	 */
	protected String getSepLista(HttpServletRequest request) {
		return ((Session)request.getSession().getAttribute(Constants.SESSION)).getSepLista();
	}
	
	protected String getSepSubLista(HttpServletRequest request) {
		return ((Session)request.getSession().getAttribute(Constants.SESSION)).getSepSubLista();
	}

	protected String getSector(HttpServletRequest request) {
		return ((Session)request.getSession().getAttribute(Constants.SESSION)).getSector();
	}
	
	protected Long getIdPerfil(HttpServletRequest request) {
		return ((Session)request.getSession().getAttribute(Constants.SESSION)).getIdPerfil();
	}
	
	/**
	 * Checkea si tiene los permisos cargados en sesi�n.
	 * @param request
	 * @param privilege
	 * @return
	 */
	protected boolean checkPrivilege(HttpServletRequest request, String privilege) {
		return ((Session)request.getSession().getAttribute(Constants.SESSION)).checkPrivilege(privilege);
	}
	
	 protected void checkLogin(HttpSession session) throws NotLoggedException {
		 if (session.getAttribute(Constants.SESSION) == null) {
			 throw new NotLoggedException();
		 }
	 }

}
