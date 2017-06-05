package com.bbva.cfs.proveedor.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProveedorValorForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String cdUniVal;
	private String cdPeriodoFact; 
    private String nuValBrutoUniVal;
    private String nuValNetoUniVal;
    private String stHabilitado;
	
	@SuppressWarnings("rawtypes")
	private List habilitadoValorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorValorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List periodoValorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List uniValList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();	
	
	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String enableGrant = "N";	
	private String disableGrant = "N";	
	private String addGrant = "N";	
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

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public String getCdUniVal() {
		return cdUniVal;
	}

	public void setCdUniVal(String cdUniVal) {
		this.cdUniVal = cdUniVal;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public String getNuValBrutoUniVal() {
		return nuValBrutoUniVal;
	}

	public void setNuValBrutoUniVal(String nuValBrutoUniVal) {
		this.nuValBrutoUniVal = nuValBrutoUniVal;
	}

	public String getNuValNetoUniVal() {
		return nuValNetoUniVal;
	}

	public void setNuValNetoUniVal(String nuValNetoUniVal) {
		this.nuValNetoUniVal = nuValNetoUniVal;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoValorList() {
		return habilitadoValorList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoValorList(List habilitadoValorList) {
		this.habilitadoValorList = habilitadoValorList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getProveedorValorList() {
		return proveedorValorList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorValorList(List proveedorValorList) {
		this.proveedorValorList = proveedorValorList;
	}

	@SuppressWarnings("rawtypes")
	public List getPeriodoValorList() {
		return periodoValorList;
	}

	@SuppressWarnings("rawtypes")
	public void setPeriodoValorList(List periodoValorList) {
		this.periodoValorList = periodoValorList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	public List getUniValList() {
		return uniValList;
	}

	public void setUniValList(List uniValList) {
		this.uniValList = uniValList;
	}
	
	/** ========================*/
	
}
