package com.bbva.cfs.commons.validators;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class Validator {

	@SuppressWarnings("deprecation")
	protected static void addMessage(ValidatorAction va, Field field,
			ActionMessages errors, HttpServletRequest request) {

		errors.add(field.getKey(),
				Resources.getActionMessage(request, va, field));
	}
}
