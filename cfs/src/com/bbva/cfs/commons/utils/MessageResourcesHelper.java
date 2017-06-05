package com.bbva.cfs.commons.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author XAH1257 - bsilvestre
 * 
 * @see si es necesario también pasar como parámetro el locale
 *      <code>java.util.Locale</code>
 */
public class MessageResourcesHelper {

	private static ResourceBundle bundle = ResourceBundle
			.getBundle("ApplicationResources");

	public static String geti18nMsg(Locale locale, String msgKey,
			String paramValue) {
		String msgValue = bundle.getString(msgKey);
		MessageFormat messageFormat = new MessageFormat(msgValue);
		Object[] args = { paramValue };
		return messageFormat.format(args);
	}

	public static String geti18nMsg(ResourceBundle bundle, String msgKey,
			String paramValue) {
		String msgValue = bundle.getString(msgKey);
		MessageFormat messageFormat = new MessageFormat(msgValue);
		Object[] args = { paramValue };
		return messageFormat.format(args);
	}

}
