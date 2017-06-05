package com.bbva.cfs.commons.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.utils.General;

/**
 * 
 * @author XAH1197
 * 
 */
public class WelcomeForm extends ActionForm {

	/**
	 * Atributos que represetan permisos del usuario a través de los cuales se
	 * va a habilitar o a desabilitar ciertas acciones y/o pantallas.
	 */
	private String grantWelcomeView;

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		this.grantWelcomeView = General.NOT_CAN_DO_ACTION;
		super.reset(arg0, arg1);
	}

	public String getGrantWelcomeView() {
		return grantWelcomeView;
	}

	public void setGrantWelcomeView(String grantWelcomeView) {
		this.grantWelcomeView = grantWelcomeView;
	}
}
