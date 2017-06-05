package com.bbva.cfs.commons.action;

import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.MethodUtils;

public class DispatchAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String execute() {
		try {

			if (action.indexOf(",")>=0) {
				StringTokenizer tokenizer = new StringTokenizer(action, ",");
				action = (String) tokenizer.nextElement();
			}

			return (String) MethodUtils.invokeMethod(this, action, null, null);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

}
