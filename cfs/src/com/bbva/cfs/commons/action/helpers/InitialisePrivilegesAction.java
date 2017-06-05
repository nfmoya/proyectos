package com.bbva.cfs.commons.action.helpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.bbva.cfs.commons.exceptions.GlobalGrantException;

/**
 * Se valida que el usuario el permiso de visualización y además se inicializa
 * otros permisos
 * 
 * @author xah1257
 */

public interface InitialisePrivilegesAction {

	public void initialisePrivileges(HttpServletRequest request, ActionForm form)
			throws GlobalGrantException;
}
