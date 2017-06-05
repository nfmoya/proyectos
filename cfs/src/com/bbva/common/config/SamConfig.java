package com.bbva.common.config;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.IWebConstants;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;

public class SamConfig implements Serializable {
	/**
	 * Servial Version ID
	 */
	private static final long serialVersionUID = 1817294859331906678L;

	public static final String samConfiguration = "samContext";

	private static IServiceAccessManager sam;

	private IContext samContext;

	/**
	 * como es una variable de application no hay problema que sea estatica
	 * 
	 * @return
	 */
	public static IServiceAccessManager getSam() {
		return sam;
	}

	public static void config(HttpSession session) throws Exception {

		SamConfig samC = new SamConfig();
		IWebClient oWebClient = (IWebClient) session
				.getAttribute(IWebConstants.WEBCLIENT);
		SAMWebClient oSAMWebClient = (SAMWebClient) oWebClient;
		
		sam = new TemplateIServiceAccessManager((IServiceAccessManager) session.getServletContext().getAttribute(
				IWebConstants.SAM));

		if (oSAMWebClient != null)
			samC.setSamContext(oSAMWebClient.getSamContext());
		if (sam == null || samC.getSamContext() == null)
			throw new Exception("Algun objeto nulo: sam o samContext");

		session.setAttribute(samConfiguration, samC);

	}

	public IContext getSamContext() {
		return samContext;
	}

	public void setSamContext(IContext samContext) {
		this.samContext = samContext;
	}

}
