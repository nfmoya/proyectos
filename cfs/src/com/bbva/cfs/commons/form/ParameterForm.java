package com.bbva.cfs.commons.form;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ParameterForm extends ActionForm {
	private String accion;
	private String tipoParametro;
	private String codParametro;
	
	private String tipoParametroDialog;
	private String txtCodigoParametro;
	private String txtDescParametro;
	private String txtCantCuotas;
	
	public String getTxtCantCuotas() {
		return txtCantCuotas;
	}

	public void setTxtCantCuotas(String txtCantCuotas) {
		this.txtCantCuotas = txtCantCuotas;
	}

	private List parameterTypes = new ArrayList();
	private String ParameterTypeId;
	
	private String createGrant = "N";
	private String editGrant = "N";
	private String deleteGrant = "N";
	
	public String getCreateGrant() {
		return createGrant;
	}

	public void setCreateGrant(String createGrant) {
		this.createGrant = createGrant;
	}

	public String getDeleteGrant() {
		return deleteGrant;
	}

	public void setDeleteGrant(String deleteGrant) {
		this.deleteGrant = deleteGrant;
	}

	public String getEditGrant() {
		return editGrant;
	}

	public void setEditGrant(String editGrant) {
		this.editGrant = editGrant;
	}

	public ParameterForm(){
		super();
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	
	public String getParameterTypeId() {
		return ParameterTypeId;
	}

	public void setParameterTypeId(String parameterTypeId) {
		ParameterTypeId = parameterTypeId;
	}

	public List getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(List parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public String getTxtCodigoParametro() {
		return txtCodigoParametro;
	}

	public void setTxtCodigoParametro(String txtCodigoParametro) {
		this.txtCodigoParametro = txtCodigoParametro;
	}

	public String getTxtDescParametro() {
		return txtDescParametro;
	}

	public void setTxtDescParametro(String txtDescParametro) {
		this.txtDescParametro = txtDescParametro;
	}

	public String getTipoParametroDialog() {
		return tipoParametroDialog;
	}

	public void setTipoParametroDialog(String tipoParametroDialog) {
		this.tipoParametroDialog = tipoParametroDialog;
	}

	public String getCodParametro() {
		return codParametro;
	}

	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}

}
