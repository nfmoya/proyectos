package com.bbva.cfs.proveedor.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProveedorPeriodoForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String cdPeriodoFact;
	private String nbPeriodoFact; 
	private String cdPerFactAlt;
	private String fhDesde;
	private String fhHasta;
    private String stEstado;   
	
	@SuppressWarnings("rawtypes")
	private List estadoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorPeriodoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();	
	
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

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public String getNbPeriodoFact() {
		return nbPeriodoFact;
	}

	public void setNbPeriodoFact(String nbPeriodoFact) {
		this.nbPeriodoFact = nbPeriodoFact;
	}

	public String getCdPerFactAlt() {
		return cdPerFactAlt;
	}

	public void setCdPerFactAlt(String cdPerFactAlt) {
		this.cdPerFactAlt = cdPerFactAlt;
	}

	public String getFhDesde() {
		return fhDesde;
	}

	public void setFhDesde(String fhDesde) {
		this.fhDesde = fhDesde;
	}

	public String getFhHasta() {
		return fhHasta;
	}

	public void setFhHasta(String fhHasta) {
		this.fhHasta = fhHasta;
	}

	public String getStEstado() {
		return stEstado;
	}

	public void setStEstado(String stEstado) {
		this.stEstado = stEstado;
	}

	@SuppressWarnings("rawtypes")
	public List getEstadoList() {
		return estadoList;
	}

	@SuppressWarnings("rawtypes")
	public void setEstadoList(List estadoList) {
		this.estadoList = estadoList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getProveedorPeriodoList() {
		return proveedorPeriodoList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorPeriodoList(List proveedorPeriodoList) {
		this.proveedorPeriodoList = proveedorPeriodoList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}
	
	/** ========================*/

	
}
