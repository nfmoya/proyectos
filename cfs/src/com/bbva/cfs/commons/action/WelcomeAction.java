package com.bbva.cfs.commons.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.helpers.InitialisePrivilegesAction;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.form.WelcomeForm;
import com.bbva.cfs.commons.utils.General;
import com.bbva.cfs.commons.utils.Grants;

public class WelcomeAction extends CommonAction implements InitialisePrivilegesAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialisePrivileges(request, form);

		this.initialise(request.getSession());

		request.setAttribute("userFullname", this
				.getAutenticathedUserFullname(request));
		
		return doFindSuccess(mapping);
	}

	/**
	 * Permisos a validar: <br>
	 * 
	 * @see Grants class
	 *      <ol>
	 *      <li>WELCOME_VIEW</li>
	 *      <li>WELCOME_ALERTS_VIEW</li>
	 *      <li>WELCOME_NOTIFICATIONS_VIEW (Este permiso no se est� usando)</li>
	 *      <li>WELCOME_TASKS_VIEW</li>
	 *      <li>WELCOME_TASKS_ABM</li>
	 *      </ol>
	 */
	public void initialisePrivileges(HttpServletRequest request,
			ActionForm form) throws GlobalGrantException {
		WelcomeForm welcomeForm = (WelcomeForm) form;
		if (super.checkPrivilege(request, Grants.WELCOME_VIEW.getId())) {
			welcomeForm.setGrantWelcomeView(General.CAN_DO_ACTION);
		} else
			throw new GlobalGrantException();
	}
}
