package com.bbva.cfs.commons.model;

import java.util.ArrayList;

import com.bbva.cfs.commons.utils.ResultDatabase;

public class Result {

	private Long errorCode;
	private String errorDesc;

	private ArrayList errorList;

	public Result() {
		super();
		// TODO Auto-generated constructor stub
		errorList = new ArrayList();
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public boolean isSuccesfull() {
		return getErrorCode().equals(ResultDatabase.SUCCESFULL.getCode());
	}
	
	public boolean isWarning() {
		return getErrorCode().equals(ResultDatabase.WARNING.getCode());
	}

	public void setErrorDesc(String errorDesc) {
		ArrayList localErrorList = new ArrayList();
		
		try {
			if (!"".equalsIgnoreCase(errorDesc) && errorDesc != null
					&& errorDesc.length() > 0) {
				String cadena = errorDesc.indexOf("|") >= 0 ? errorDesc.replaceFirst("|", "") : errorDesc;
				cadena = cadena.indexOf("(") >= 0 ? cadena.replaceFirst("(", " ") : cadena;
				cadena = cadena.indexOf(")") >= 0 ? cadena.replaceFirst(")", " ") : cadena;
				cadena = cadena.indexOf("[") >= 0 ? cadena.replaceFirst("[", " ") : cadena;
				cadena = cadena.indexOf("]") >= 0 ? cadena.replaceFirst("]", " ") : cadena;
				cadena = cadena.indexOf("_") >= 0 ? cadena.replaceFirst("_", " ") : cadena;
				cadena = cadena.indexOf("-") >= 0 ? cadena.replaceFirst("-", " ") : cadena;
				errorDesc = cadena.trim();
				
				/** Generar lista de errores **/
				String str = errorDesc;
				// String str = "one#two#three";
				String[] lista;
				/* Delimitador */
				String delimitador = "#";
				lista = str.split(delimitador);
				for (int i = 0; i < lista.length; i++) {
					localErrorList.add((lista[i]));
				}
			}
		} catch(Exception e){
			localErrorList.add(errorDesc);
		}
		this.errorDesc = errorDesc;
		this.errorList = localErrorList;
	}

	public ArrayList getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList errorList) {
		this.errorList = errorList;
	}

}
