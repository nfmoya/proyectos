package com.bbva.cfs.general.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class TablaForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdTabla;
	private String cdCodTabla;
	private String nbCodTabla;
	private String nbCodTablaCorto;
	private String nbAtributoTabla1;
	private String nbAtributoTabla2;
	private String nbAtributoTabla3;
	private String stHabilitado;
	@SuppressWarnings("rawtypes")
	private List habilitadoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroTablaList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List tablaList = new ArrayList();

	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String enableGrant = "N";	
	private String disableGrant = "N";	
	private String addGrant= "N";	
	private String editGrant = "N";	
	private String deleteGrant = "N";
	
	private String agregarGrant;
	private String modificarGrant;
	
	private String callBackFunction;
	
	/** ========================*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getSaveGrant() {
		return saveGrant;
	}

	public void setSaveGrant(String saveGrant) {
		this.saveGrant = saveGrant;
	}
	
	public String getEnableGrant() {
		return enableGrant;
	}

	public void setEnableGrant(String enableGrant) {
		this.enableGrant = enableGrant;
	}

	public String getDisableGrant() {
		return disableGrant;
	}

	public void setDisableGrant(String disableGrant) {
		this.disableGrant = disableGrant;
	}

	public String getAddGrant() {
		return addGrant;
	}

	public void setAddGrant(String addGrant) {
		this.addGrant = addGrant;
	}

	public String getEditGrant() {
		return editGrant;
	}

	public void setEditGrant(String editGrant) {
		this.editGrant = editGrant;
	}

	public String getDeleteGrant() {
		return deleteGrant;
	}

	public void setDeleteGrant(String deleteGrant) {
		this.deleteGrant = deleteGrant;
	}

	public String getCallBackFunction() {
		return callBackFunction;
	}

	public void setCallBackFunction(String callBackFunction) {
		this.callBackFunction = callBackFunction;
	}

	public String getAgregarGrant() {
		return agregarGrant;
	}

	public void setAgregarGrant(String agregarGrant) {
		this.agregarGrant = agregarGrant;
	}

	public String getModificarGrant() {
		return modificarGrant;
	}

	public void setModificarGrant(String modificarGrant) {
		this.modificarGrant = modificarGrant;
	}
	
	public String getCdTabla() {
		return cdTabla;
	}

	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCdCodTabla() {
		return cdCodTabla;
	}

	public void setCdCodTabla(String cdCodTabla) {
		this.cdCodTabla = cdCodTabla;
	}

	public String getNbCodTabla() {
		return nbCodTabla;
	}

	public void setNbCodTabla(String nbCodTabla) {
		this.nbCodTabla = nbCodTabla;
	}

	public String getNbCodTablaCorto() {
		return nbCodTablaCorto;
	}

	public void setNbCodTablaCorto(String nbCodTablaCorto) {
		this.nbCodTablaCorto = nbCodTablaCorto;
	}

	public String getNbAtributoTabla1() {
		return nbAtributoTabla1;
	}

	public void setNbAtributoTabla1(String nbAtributoTabla1) {
		this.nbAtributoTabla1 = nbAtributoTabla1;
	}

	public String getNbAtributoTabla2() {
		return nbAtributoTabla2;
	}

	public void setNbAtributoTabla2(String nbAtributoTabla2) {
		this.nbAtributoTabla2 = nbAtributoTabla2;
	}

	public String getNbAtributoTabla3() {
		return nbAtributoTabla3;
	}

	public void setNbAtributoTabla3(String nbAtributoTabla3) {
		this.nbAtributoTabla3 = nbAtributoTabla3;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoList() {
		return habilitadoList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoList(List habilitadoList) {
		this.habilitadoList = habilitadoList;
	}

	public List getFiltroTablaList() {
		return filtroTablaList;
	}

	public void setFiltroTablaList(List filtroTablaList) {
		this.filtroTablaList = filtroTablaList;
	}

	public List getTablaList() {
		return tablaList;
	}

	public void setTablaList(List tablaList) {
		this.tablaList = tablaList;
	}

}
