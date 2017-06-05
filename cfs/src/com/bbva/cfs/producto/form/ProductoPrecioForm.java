package com.bbva.cfs.producto.form;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProductoPrecioForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String cdProducto;
	private String fhDesde;
	private String fhHasta;
    private String nuPrecioUniVal;
    private String stHabilitado;
	
	@SuppressWarnings("rawtypes")
	private List habilitadoProductoPrecioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorProductoPrecioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoPrecioList = new ArrayList();	
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

	public String getCdProducto() {
		return cdProducto;
	}

	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
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

	public String getNuPrecioUniVal() {
		return nuPrecioUniVal;
	}

	public void setNuPrecioUniVal(String nuPrecioUniVal) {
		this.nuPrecioUniVal = nuPrecioUniVal;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoProductoPrecioList() {
		return habilitadoProductoPrecioList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoProductoPrecioList(List habilitadoProductoPrecioList) {
		this.habilitadoProductoPrecioList = habilitadoProductoPrecioList;
	}

	@SuppressWarnings("rawtypes")
	public List getProveedorProductoPrecioList() {
		return proveedorProductoPrecioList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorProductoPrecioList(List proveedorProductoPrecioList) {
		this.proveedorProductoPrecioList = proveedorProductoPrecioList;
	}

	@SuppressWarnings("rawtypes")
	public List getProductoPrecioList() {
		return productoPrecioList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductoPrecioList(List productoPrecioList) {
		this.productoPrecioList = productoPrecioList;
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
