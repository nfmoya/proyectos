package com.bbva.cfs.commons.exceptions;

import java.io.IOException;
import java.net.MalformedURLException;

public class GeneralException extends Exception {

	private GeneralException() {
		super();
	}

	public GeneralException(String message, MalformedURLException cause) {
		super(message, cause);
	}

	public GeneralException(String message, IOException cause) {
		super(message, cause);
	}

	private GeneralException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	private GeneralException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
