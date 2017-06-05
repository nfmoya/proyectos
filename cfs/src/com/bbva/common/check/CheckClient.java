package com.bbva.common.check;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Perl5Matcher;


import ar.com.bbva.web.IWebApplication;
import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.IWebConstants;
//import com.consolidar.web.exception.NotLoginException;
//import com.consolidar.web.log.Log;
import com.bbva.cfs.commons.logging.Log;

public class CheckClient {
	private static final String LOG_CATEGORY_PREFIX = "web.jsp.";
	private static Perl5Matcher 						matcher = null;
	private static	org.apache.oro.text.regex.Pattern	pattern = null;

	public static String getAppStringProperty(HttpServletRequest request,String szPropName) 
	{
		return getAppStringProperty(request, szPropName, null);
	}
	
	public static String getAppStringProperty(HttpServletRequest request, String szPropName, String szDfltValue)
	{
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String	szPropValue = null;
		szPropValue = (String)((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getAttribute(szPropName);
		return (szPropValue != null ? szPropValue : szDfltValue);
	}

	public static String getLoginPage(ServletContext context)
	{
		return (String)((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getAttribute(IWebConstants.LOGIN, "login.jsp");
	}
	
	public static String getErrorPage(ServletContext context)
	{
		return (String)((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getAttribute(IWebConstants.ERROR, 
														"error.jsp");
	}
	
	public static IWebClient getWebClient(HttpSession mySession) 
	{
		return (IWebClient)mySession.getAttribute(IWebConstants.WEBCLIENT);
	}
	
	private static boolean isLoginRequiredFor(ServletContext context, String szRequestUri)
	{
		GlobCompiler 	compiler = null;
		String			szNoLoginPageExpr = null;
		boolean			bLoginRequired = false;
		if (matcher == null || pattern == null) {
			szNoLoginPageExpr = (String)((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getAttribute(
												IWebConstants.NOLOGINPAGEEXPR);
			if (szNoLoginPageExpr == null) {
				szNoLoginPageExpr = (String)((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getAttribute(
													IWebConstants.WEBPREFIX + 
													IWebConstants.NOLOGINPAGEEXPR);
			}
			szNoLoginPageExpr = szNoLoginPageExpr.toUpperCase();
			compiler = new GlobCompiler();
			matcher = new Perl5Matcher();
			try {
				pattern = compiler.compile(szNoLoginPageExpr);
			} catch (MalformedPatternException e) {
				Log.error("No se pudo compilar la expresión regular " +
							"para páginas de no login :" + szNoLoginPageExpr, e);
			}
		}
		bLoginRequired = (matcher != null && pattern != null &&
						!matcher.matches(szRequestUri.toUpperCase(),pattern) && 
			szRequestUri.toUpperCase().indexOf(getLoginPage(context).toUpperCase()) < 0 && 
			szRequestUri.toUpperCase().indexOf(getErrorPage(context).toUpperCase()) < 0);

		return bLoginRequired;
	}
	
	public static void check(HttpServletRequest request) throws Exception{
		String 		szSessionId = "LA SESION ES NULA!";
		boolean 	bSessionNew = false;
		String 		szRequestUri = "OBJETO REQUEST ES NULO!";
		String 		szRemoteAddr = "";
		IWebClient	oWebClient = null;
		if (request != null) {
			szRequestUri = request.getRequestURI();
			szRemoteAddr = request.getRemoteAddr();
		}
		Log.log(LOG_CATEGORY_PREFIX,szRequestUri);
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		if (session != null) {
			szSessionId = session.getId();
			bSessionNew = session.isNew();
		}
		oWebClient = (IWebClient)session.getAttribute(IWebConstants.WEBCLIENT);
		if (isLoginRequiredFor(context, szRequestUri)) {
			Log.info("Se requiere login para: " + szSessionId +
							" la sesion es nueva: " + bSessionNew +
							" oWebClient=" + oWebClient);
			if (oWebClient == null || !oWebClient.isLoginOk()) {
				Log.info("Se lo redirigira a " + request.getContextPath() + "/" + getLoginPage(context));
				//throw new NotLoginException();
				throw new Exception();
			}
		} else {
			if (oWebClient == null) {
				Log.info("No se requiere login y el WebClient no estaba creado");
				oWebClient = ((IWebApplication)context.getAttribute(IWebConstants.WEBAPP)).getClient(session);
				session.setAttribute(IWebConstants.WEBCLIENT, oWebClient);
			} else {
				Log.info("No se requiere login");
			}
		}
		if (oWebClient == null) {
			throw new Exception("WebClient nulo");
		} else{
			Log.info("Estamos en: " + szRequestUri +
							" para: " + szSessionId +
							" desde: " + szRemoteAddr +
							" la sesion es nueva: " + bSessionNew);
		}
	}
}
