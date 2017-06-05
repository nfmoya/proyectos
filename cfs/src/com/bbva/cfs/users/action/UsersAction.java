package com.bbva.cfs.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.users.form.UsersForm;
import com.bbva.cfs.users.service.CreateUserService;
import com.bbva.cfs.users.service.EditUserService;
import com.bbva.security.service.ResetPasswordService;

/**
 * @author jmanrique
 * 
 */
public class UsersAction extends CommonAction {

	private static final long serialVersionUID = 6967702419884332562L;

	static final Log log = LogFactory.getLog(UsersAction.class);

	private CreateUserService createUserService;
	private EditUserService editUserService;
	private ResetPasswordService resetPassService;

	private Result result;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		UsersForm userForm = (UsersForm) form;

		/* Verifico los permisos */
/*		
		if (!this.checkPrivilege(request, Grants.ABM_USERS_VIEW.getId())) {
			throw new GlobalGrantException();
		}
		if (this.checkPrivilege(request, Grants.ABM_USERS_CREATE.getId())) {
			userForm.setCreateGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_USERS_EDIT.getId())) {
			userForm.setEditGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_USERS_DELETE.getId())) {
			userForm.setDeleteGrant("S");
		}
*/
		return doFindSuccess(mapping);
	}

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		PrintWriter out = null;
		UsersForm userForm = (UsersForm) form;

		try {
			/* TODO: COMO VAMOS A MANEJAR EL RESULT, EXCEPCIONES Y ERRORES? */
			createUserService = new CreateUserService(this.iWebClient);
			result = createUserService.execute(
					this.getAutenticathedUserId(request),
					userForm.getUserName(),
					userForm.getStrPerfiles().replaceAll(",",
							this.getSepLista(request)),
					userForm.getTxtEstadoUser());
			
			JSONObject json = new JSONObject();
			json.put("result", result);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();

			out.print(json);

		} catch (JSONException e) {
			log.error("Error execute Json Array: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}

		return null;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		PrintWriter out = null;
		UsersForm userForm = (UsersForm) form;

		try {
			/* TODO: COMO VAMOS A MANEJAR EL RESULT, EXCEPCIONES Y ERRORES? */
			editUserService = new EditUserService(this.iWebClient);
			result = editUserService.execute(
					userForm.getUserName(),
					this.getAutenticathedUserId(request),
					userForm.getUserId(),
					userForm.getStrPerfiles().replaceAll(",",
							this.getSepLista(request)),
					userForm.getStrPerfilesDel().replaceAll(",",
							this.getSepLista(request)),userForm.getTxtEstadoUser());

			JSONObject json = new JSONObject();
			json.put("result", result);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();

			out.print(json);

		} catch (JSONException e) {
			log.error("Error execute Json Array: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}

		return null;
	}

	public ActionForward resetUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		PrintWriter out = null;
		UsersForm userForm = (UsersForm) form;

		if (!request.getSession().getAttribute("canReset")
				.equals(userForm.getUserName())) {
			throw new GlobalGrantException();
		}

		try {
			resetPassService = new ResetPasswordService(this.iWebClient);
			boolean r = resetPassService.execute(userForm.getUserName());
			result = new Result();
			if (r) {
				result.setErrorCode(ResultDatabase.SUCCESFULL.getCode());
				result.setErrorDesc("El usuario fue blanqueado con �xito.");
				request.getSession().removeAttribute("canReset");
			} else {
				result.setErrorCode(ResultDatabase.SERVICE_ERROR.getCode());
				result.setErrorDesc(ResultDatabase.SERVICE_ERROR.getMessage());
			}

			JSONObject json = new JSONObject();
			json.put("result", result);

			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();

			out.print(json);

		} catch (JSONException e) {
			log.error("Error execute Json Array: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}

		return null;
	}

}
