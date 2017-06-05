package com.bbva.cfs.proveedor.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProveedorForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String nbProveedor;
	private String nbProveedorCorto; 
	private Long nuCuit;
    private String nbAtributoProv1;
    private String nbAtributoProv2;
    private String nbAtributoProv3; 
    private String nbAtributoProv4; 
    private String nbAtributoProv5; 
    private String stHabilitado;
	
    @SuppressWarnings("rawtypes")
	private List nbAtributoProv1List = new ArrayList();
    @SuppressWarnings("rawtypes")
  	private List nbAtributoProv2List = new ArrayList();	
    
	@SuppressWarnings("rawtypes")
	private List habilitadoList = new ArrayList();	
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

	public String getNbProveedor() {
		return nbProveedor;
	}

	public void setNbProveedor(String nbProveedor) {
		this.nbProveedor = nbProveedor;
	}

	public String getNbProveedorCorto() {
		return nbProveedorCorto;
	}

	public void setNbProveedorCorto(String nbProveedorCorto) {
		this.nbProveedorCorto = nbProveedorCorto;
	}

	public Long getNuCuit() {
		return nuCuit;
	}

	public void setNuCuit(Long nuCuit) {
		this.nuCuit = nuCuit;
	}

	public String getNbAtributoProv1() {
		return nbAtributoProv1;
	}

	public void setNbAtributoProv1(String nbAtributoProv1) {
		this.nbAtributoProv1 = nbAtributoProv1;
	}

	public String getNbAtributoProv2() {
		return nbAtributoProv2;
	}

	public void setNbAtributoProv2(String nbAtributoProv2) {
		this.nbAtributoProv2 = nbAtributoProv2;
	}

	public String getNbAtributoProv3() {
		return nbAtributoProv3;
	}

	public void setNbAtributoProv3(String nbAtributoProv3) {
		this.nbAtributoProv3 = nbAtributoProv3;
	}

	public String getNbAtributoProv4() {
		return nbAtributoProv4;
	}

	public void setNbAtributoProv4(String nbAtributoProv4) {
		this.nbAtributoProv4 = nbAtributoProv4;
	}

	public String getNbAtributoProv5() {
		return nbAtributoProv5;
	}

	public void setNbAtributoProv5(String nbAtributoProv5) {
		this.nbAtributoProv5 = nbAtributoProv5;
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

	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	public List getNbAtributoProv1List() {
		return nbAtributoProv1List;
	}

	public void setNbAtributoProv1List(List nbAtributoProv1List) {
		this.nbAtributoProv1List = nbAtributoProv1List;
	}

	public List getNbAtributoProv2List() {
		return nbAtributoProv2List;
	}

	public void setNbAtributoProv2List(List nbAtributoProv2List) {
		this.nbAtributoProv2List = nbAtributoProv2List;
	}
	
	/** ========================*/

	
}
